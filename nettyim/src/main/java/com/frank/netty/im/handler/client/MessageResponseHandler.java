package com.frank.netty.im.handler.client;

import com.frank.netty.im.protocol.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Package com.frank.netty.im.handler.client
 * Description: ${todo}
 * author 016039
 * date 2018/11/17下午2:39
 */
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    // 直接打印回复消息
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageResponsePacket messageResponsePacket) throws Exception {
        String fromUserId = messageResponsePacket.getFromUserId();
        String fromUserName = messageResponsePacket.getFromUserName();
        System.out.println(fromUserId + ":" + " -> " + messageResponsePacket.getMessage());
    }
}
