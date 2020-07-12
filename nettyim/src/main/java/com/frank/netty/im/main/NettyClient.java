package com.frank.netty.im.main;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Package com.frank.netty.im.main
 * Description: Netty 客户端的启动流程
 * author 016039
 * date 2018/11/14下午9:10
 */
public class NettyClient {
    private static final int MAX_RETRY = 5;
    private static final String HOST = "localhost";
    private static final int PORT = 8000;
    public static void main(String[] args) {
        // 创建工作线程
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        // 创建引导类
        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                // 指定线程模型
                .group(workGroup)
                // 设置自定义属性
                .attr(AttributeKey.newInstance("clientName"), "nettyClient")
                /*
                * 设置一些TCP底层相关的属性
                * */
                // 开启TCP底层的心跳机制
                .option(ChannelOption.SO_KEEPALIVE, true)
                // 开启Nagle算法
                .option(ChannelOption.TCP_NODELAY, true)
                // 表示连接的超时时间
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                // 指定IO类型为NIO
                .channel(NioSocketChannel.class)
                // 处理IO逻辑
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new FirstClientHandler());
                    }
                });
        // 建立连接
        connect(bootstrap, HOST, PORT, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功");
            } else if (retry == 0) {
                System.out.println("重试次数已完，放弃连接");
            }else {
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败, 第" + order + "次重连");
                // 失败的时候重连一下, 这里设定定时任务
                bootstrap
                        // 返回配置参数的抽象
                        .config()
                        // 返回一开始的配置的工作线程模型
                        .group()
                        // 实现定时任务逻辑
                        .schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);

            }
        });
    }
}
