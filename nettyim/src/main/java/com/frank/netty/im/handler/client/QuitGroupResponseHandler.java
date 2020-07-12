package com.frank.netty.im.handler.client;

import com.frank.netty.im.protocol.response.QuitGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Package com.frank.netty.im.handler.client
 * Description: ${todo}
 * author 016039
 * date 2018/11/18下午12:25
 */
public class QuitGroupResponseHandler extends SimpleChannelInboundHandler<QuitGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, QuitGroupResponsePacket quitGroupResponsePacket) throws Exception {
        if (quitGroupResponsePacket.isSuccess()) {
            System.out.println("退出群聊[" + quitGroupResponsePacket.getGroupId() + "]成功! ");
        } else {
            System.out.println("退出群聊[" + quitGroupResponsePacket.getGroupId() + "]失败! ");
        }
    }
}
