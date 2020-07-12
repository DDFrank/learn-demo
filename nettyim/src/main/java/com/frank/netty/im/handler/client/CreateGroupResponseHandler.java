package com.frank.netty.im.handler.client;

import com.frank.netty.im.protocol.response.CreateGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Package com.frank.netty.im.handler.client
 * Description: ${todo}
 * author 016039
 * date 2018/11/18上午11:21
 */
public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, CreateGroupResponsePacket createGroupResponsePacket) throws Exception {
        System.out.println("群创建成功, id为[" + createGroupResponsePacket.getGroupId() + "], ");
        System.out.println("群里面有:" + createGroupResponsePacket.getUserNameList());
    }
}
