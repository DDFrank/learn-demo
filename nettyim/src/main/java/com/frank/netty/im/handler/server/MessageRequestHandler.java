package com.frank.netty.im.handler.server;

import com.frank.netty.im.bean.Session;
import com.frank.netty.im.protocol.request.MessageRequestPacket;
import com.frank.netty.im.protocol.response.MessageResponsePacket;
import com.frank.netty.im.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * Package com.frank.netty.im.handler.server
 * Description: ${todo}
 * author 016039
 * date 2018/11/17下午2:22
 */
@ChannelHandler.Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    public static MessageRequestHandler INSTANCE = new MessageRequestHandler();

    protected MessageRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {
        System.out.println(new Date() + ": 收到客户端消息: " + messageRequestPacket.getMessage());
        // 拿到消息发送方的会话信息
        Session session = SessionUtil.getSession(ctx.channel());

        // 通过消息发送方的会话消息构造要发送的消息
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());
        messageResponsePacket.setMessage("服务端回复[" + messageRequestPacket.getMessage() + "]");

        // 拿到消息接收方的 channel
        Channel toUserChannel = SessionUtil.getChannel(messageRequestPacket.getToUserId());

        //将消息发送给消息接收方
        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
            toUserChannel.writeAndFlush(messageResponsePacket);
        } else {
            System.out.println("[" + messageRequestPacket.getToUserId() + "] 不在线，发送失败");
        }
        ctx.channel().writeAndFlush(messageResponsePacket);
    }
}
