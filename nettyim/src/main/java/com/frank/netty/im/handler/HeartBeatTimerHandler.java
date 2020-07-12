package com.frank.netty.im.handler;

import com.frank.netty.im.protocol.request.HeartBeatRequestPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;

/**
 * Package com.frank.netty.im.handler
 * Description: 定时发送心跳数据包的处理器
 * author 016039
 * date 2018/11/18下午2:10
 */
public class HeartBeatTimerHandler extends ChannelInboundHandlerAdapter{

    private static final int HEARTBEAT_INTERVAL = 5;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        scheduleSendHeartBeat(ctx);
        super.channelActive(ctx);
    }

    private void scheduleSendHeartBeat(ChannelHandlerContext ctx) {
        // 执行定时任务
        ctx.executor().schedule(() -> {
           if (ctx.channel().isActive()) {
               ctx.writeAndFlush(new HeartBeatRequestPacket());
               scheduleSendHeartBeat(ctx);
           }
        }, HEARTBEAT_INTERVAL, TimeUnit.SECONDS);
    }
}
