package com.frank.netty.im.main;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * Package com.frank.netty.im.main
 * Description: 服务端处理消息的流程
 * author 016039
 * date 2018/11/14下午10:08
 */
public class FirstServerHandler extends ChannelInboundHandlerAdapter {
    /*
    * 收到客户端的数据后被调用
    * */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf)msg;

        System.out.println(new Date() + ": 服务端读到数据 -> " + buf.toString(Charset.forName("utf-8")));

        // 回复数据
        ByteBuf out = getByteBuf(ctx);
        ctx.channel().writeAndFlush(out);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        byte[] bytes = "欢迎啊，我们有很多草".getBytes(Charset.forName("utf-8"));

        ByteBuf buf = ctx.alloc().buffer();

        buf.writeBytes(bytes);
        return buf;
    }
}
