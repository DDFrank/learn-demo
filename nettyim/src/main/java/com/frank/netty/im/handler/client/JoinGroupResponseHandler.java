package com.frank.netty.im.handler.client;

import com.frank.netty.im.protocol.response.JoinGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Package com.frank.netty.im.handler.client
 * Description: 加入群聊的
 * author 016039
 * date 2018/11/18下午12:12
 */
public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupResponsePacket joinGroupResponsePacket) throws Exception {
        if (joinGroupResponsePacket.isSuccess()) {
            System.out.println("加入群[" + joinGroupResponsePacket.getGroupId() + "]成功!");
        } else {
            System.out.println("加入群[" + joinGroupResponsePacket.getGroupId() + "]失败, 原因为: " + joinGroupResponsePacket.getReason());
        }
    }
}
