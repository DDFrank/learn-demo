package com.frank.netty.im.handler.server;

import com.frank.netty.im.protocol.request.LogoutRequestPacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Package com.frank.netty.im.handler.server
 * Description: ${todo}
 * author 016039
 * date 2018/11/18上午10:06
 */
@ChannelHandler.Sharable
public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequestPacket> {

    public static LogoutRequestHandler INSTANCE = new LogoutRequestHandler();

    protected LogoutRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LogoutRequestPacket logoutRequestPacket) throws Exception {

    }
}
