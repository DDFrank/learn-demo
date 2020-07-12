package com.frank.netty.im.server;

import com.frank.netty.im.handler.IMIdleStateHandler;
import com.frank.netty.im.handler.PacketCodecHandler;
import com.frank.netty.im.handler.Spliter;
import com.frank.netty.im.handler.server.AuthHandler;
import com.frank.netty.im.handler.server.HeartBeatRequestHandler;
import com.frank.netty.im.handler.server.IMHandler;
import com.frank.netty.im.handler.server.LoginRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * Package com.frank.netty.im.server
 * Description: ${todo}
 * author 016039
 * date 2018/11/17上午9:21
 */
public class MainServer {
    public static void main(String[] args) {
        /*
         * 将 连接用的线程组和 工作用的线程组分开
         * 这是 主从 Reactor 多线程模式, 比将 非主从的 Reactor 多线程模式更加高效
         */
        // 监听端口， accept 新连接的线程组
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        // 处理每一条连接的数据读写的线程组
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        // 引导类，用来启动服务端的
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(bossGroup, workerGroup)
                // 指定IO模型为 NIO
                .channel(NioServerSocketChannel.class)
                /*
                * 给服务端设置属性
                * */
                // 系统用于存放临时存放已完成三次握手的请求的队列的最大长度,如果连接建立频繁可以适当调大这个参数
                .option(ChannelOption.SO_BACKLOG, 1024)
                // 指定在服务端启动过程中的一些逻辑
                .handler(new ChannelInitializer<NioServerSocketChannel>() {
                    protected void initChannel(NioServerSocketChannel channel) {
                        System.out.println("服务端启动中");
                    }
                })
                /*
                    定义每条数据的， 也就是每条连接的业务处理
                    NioSocketChannel 客户端 Socket的抽象
                */
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) {
                        // 空闲检测
                        ch.pipeline().addLast(new IMIdleStateHandler());
                        /*
                        * 拆包器，
                        * 1 数据包最大长度
                        * 2 长度域的偏移量(根据自定义协议定，这里是7)
                        * 3 长度域的长度，根据自定义协议定，这里是4
                        * */
                        //ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 7, 4));
                        ch.pipeline().addLast(new Spliter());
                        // 同时负责编解码
                        ch.pipeline().addLast(PacketCodecHandler.INSTANCE);
                        // 无状态的处理器都可以这样改造
                        ch.pipeline().addLast(LoginRequestHandler.INSTANCE);
                        // 心跳发送不需要登录
                        ch.pipeline().addLast(HeartBeatRequestHandler.INSTANCE);
                        // 判断用户是否登录
                        ch.pipeline().addLast(AuthHandler.INSTANCE);
                        // 指令组
                        ch.pipeline().addLast(IMHandler.INSTANCE);
                    }
                });

        bind(serverBootstrap, 8000);
    }

    /*
    * 从传递的端口号开始，往上尝试哪个端口能绑定成功
    * */
    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("端口[" + port + "]绑定成功");
                } else {
                    System.out.println("端口["+port+"]绑定失败");
                    //
                    bind(serverBootstrap, port+1);
                }
            }
        });
    }
}
