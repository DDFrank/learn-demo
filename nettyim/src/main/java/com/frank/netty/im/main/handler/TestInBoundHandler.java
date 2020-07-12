package com.frank.netty.im.main.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Package com.frank.netty.im.main
 * Description: ${todo}
 * author 016039
 * date 2018/11/17下午1:33
 */
public class TestInBoundHandler extends ChannelInboundHandlerAdapter {
    public TestInBoundHandler() {
        super();
    }

    /*
    * 可以用来做资源的申请
    * */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("逻辑处理器被添加：handlerAdded()" );
        super.handlerAdded(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel 绑定到线程(NioEventLoop): channelRegistered()");
        super.channelRegistered(ctx);
    }

    /*
    * 表明TCP连接的建立，可以统计现在的连接数
    * */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel 准备就绪: channelActive()");
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channel 有数据可读: channelRead()");
        super.channelRead(ctx, msg);
    }

    /*
    * 可以在这里调用 ctx.channel().flush() 替代之前的刷新
    * */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel 某次数据读完: channelReadComplete()");
        super.channelReadComplete(ctx);
    }

    /*
    * 表明TCP连接的释放，可以统计现在的连接数, -1
    * */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel 被关闭: channelInactive()");
        super.channelInactive(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel 取消线程(NioEventLoop)的绑定: channelUnregistered()");
        super.channelUnregistered(ctx);
    }

    /*
    * 可以用来做资源的释放
    * */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("逻辑处理器被移除: handleRemoved()");
        super.handlerRemoved(ctx);
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public boolean isSharable() {
        return super.isSharable();
    }
}
