package com.frank.netty.im.main.handler;

import com.frank.netty.im.protocol.request.LoginRequestPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Package com.frank.netty.im.main.handler
 * Description: 该实现会自动判断消息的类型，是的话进行逻辑处理，不是的话传递
 * author 016039
 * date 2018/11/17下午1:47
 */
public class TestSimpleChannelInboundHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestPacket loginRequestPacket) throws Exception {
        // 登录的逻辑
    }
}
