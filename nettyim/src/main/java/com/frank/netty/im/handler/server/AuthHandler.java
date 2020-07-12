package com.frank.netty.im.handler.server;

import com.frank.netty.im.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Package com.frank.netty.im.handler.server
 * Description: 判断用户是否登录的处理器
 * author 016039
 * date 2018/11/17下午6:15
 */
@ChannelHandler.Sharable
public class AuthHandler extends ChannelInboundHandlerAdapter{
    public static AuthHandler INSTANCE = new AuthHandler();

    protected AuthHandler(){}

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!SessionUtil.hasLogin(ctx.channel())) {
            // 如果未登录就强制关闭连接
            ctx.channel().close();
        } else {
            // 删除自身
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if (SessionUtil.hasLogin(ctx.channel())) {
            System.out.println("当前连接登录验证完毕，无需再次验证，AuthHandler 被移除");
        } else {
            System.out.println("无登录验证，强制关闭连接!");
        }
    }
}
