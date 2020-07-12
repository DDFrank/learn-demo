package com.frank.netty.im.handler.client;

import com.frank.netty.im.protocol.response.ListGroupMemberResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Package com.frank.netty.im.handler.client
 * Description: 获取
 * author 016039
 * date 2018/11/18下午12:35
 */
public class ListGroupMembersResponseHandler extends SimpleChannelInboundHandler<ListGroupMemberResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ListGroupMemberResponsePacket listGroupMemberResponsePacket) throws Exception {
        System.out.println("群[" + listGroupMemberResponsePacket.getGroupId() + "]中的人包括: " + listGroupMemberResponsePacket.getSessionList());
    }
}
