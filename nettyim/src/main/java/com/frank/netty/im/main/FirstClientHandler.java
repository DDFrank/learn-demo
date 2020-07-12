package com.frank.netty.im.main;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * Package com.frank.netty.im.main
 * Description: 自定义处理器
 * author 016039
 * date 2018/11/14下午9:59
 */
public class FirstClientHandler extends ChannelInboundHandlerAdapter {
    /*
        该方法会在客户端连接建立成功之后被调用
        所以可以在改方法里编写面向服务端的数据
    */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ": 客户端写出数据");

        // 1 获取数据
        ByteBuf buf = getByteBuf(ctx);

        // 2 将数据写到服务端
        ctx.channel().writeAndFlush(buf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf)msg;

        System.out.println(new Date() + ": 客户端读到数据 -> " + buf.toString(Charset.forName("utf-8")));
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        // 1 获取二进制抽象 ByteBuf
        ByteBuf buffer = ctx
                // 获取一个 ByteBuf 的内存管理器,这个管理器的作用就是分配一个 ByteBuf
                .alloc()
                .buffer();

        // 2 准备数据，指定字符串的字符集为 utf-8
        byte[] bytes = "你好，包包!".getBytes(Charset.forName("utf-8"));

        // 3 填充数据到 ByteBuf
        buffer.writeBytes(bytes);

        return buffer;
    }
}
