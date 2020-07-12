package com.frank.netty.im.handler.server;

import com.frank.netty.im.bean.Session;
import com.frank.netty.im.protocol.request.LoginRequestPacket;
import com.frank.netty.im.protocol.response.LoginResponsePacket;
import com.frank.netty.im.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.UUID;

/**
 * Package com.frank.netty.im.handler.server
 * Description: 处理登录的逻辑
 * author 016039
 * date 2018/11/17下午2:20
 */
// 要共享的话必须注解,表明 handler 是可以多个 channel 共享的
@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    // 构造单例
    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();

    protected LoginRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {
        // 构建响应数据包
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        loginResponsePacket.setUserName(loginRequestPacket.getUserName());

        // 登录校验
        if (valid(loginRequestPacket)) {
            // 校验成功
            loginResponsePacket.setSuccess(true);
            String userId = randomUserId();
            loginResponsePacket.setUserId(userId);

            System.out.println("[" + loginRequestPacket.getUserName() + "]登录成功");
            /*
            * 登录成功后，服务端创建一个 Session 对象，该对象表示用户当前的会话消息
            * */
            SessionUtil.bindSession(new Session(userId, loginRequestPacket.getUserName()), ctx.channel());
            /// 因为登录与否交给session判断了，所以这里注释了
            //LoginUtil.markAsLogin(ctx.channel());
        } else {
            // 校验失败
            loginResponsePacket.setReason("账号密码校验失败");
            loginResponsePacket.setSuccess(false);
            System.out.println(new Date() + ": 登录失败!");
        }

        ctx.channel()
            .writeAndFlush(loginResponsePacket);
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }

    // 获取随机的用户Id
    private static String randomUserId() {
        return UUID.randomUUID().toString().split("-")[0];
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }
}
