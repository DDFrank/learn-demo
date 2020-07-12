package com.frank.netty.im.main;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * @author 016039
 * Package com.frank.netty.im.main
 * Description: Netty 服务端启动流程
 * 一般来说就是
 *  1 线程模型
 *  2 IO模型
 *  3 IO业务处理逻辑
 * date 2018/11/14下午8:40
 */
public class NettyServer {
    public static void main(String[] args) {
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
                // 可以给服务端的 channel 指定一些自定义属性
                //.attr(AttributeKey.newInstance("test"), "nettyServer")
                // 给每一条连接自定义属性
                //.childAttr(AttributeKey.newInstance("clientKey"), "clientValue")
                /*
                * 给每一条连接设置一些TCP底层的相关属性
                * */
                // 开启TCP底层心跳机制
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                // 是否开启 Nagle 算法, 假如需要高实时性，就关闭，需要减少发送次数网络交互，就关闭
                .childOption(ChannelOption.TCP_NODELAY, true)
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
                        ch.pipeline().addLast(new FirstServerHandler());
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
