package com.frank.netty.im.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * Package com.frank.netty.im.handler
 * Description: 检测客户端每隔一段时间是否有数据的处理器
 * author 016039
 * date 2018/11/18下午2:04
 */
public class IMIdleStateHandler extends IdleStateHandler {
    private static final int READER_IDLE_TIME = 15;

    public IMIdleStateHandler() {
        /*
        * 1 空闲时间
        * 2 写空闲时间 为0表示不关心
        * 3 读空闲时间 为0表示不关心
        * 4 单位
        * */
        super(READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
    }
    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        System.out.println(READER_IDLE_TIME + "秒内未读取到数据，关闭连接");
        ctx.channel().close();
    }
}
