package com.frank.netty.im.client;

import com.frank.netty.im.console.ConsoleCommandManager;
import com.frank.netty.im.console.LoginConsoleCommand;
import com.frank.netty.im.handler.IMIdleStateHandler;
import com.frank.netty.im.handler.PacketCodecHandler;
import com.frank.netty.im.handler.Spliter;
import com.frank.netty.im.handler.client.*;
import com.frank.netty.im.util.SessionUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Package com.frank.netty.im.client
 * Description: ${todo}
 * author 016039
 * date 2018/11/17上午9:19
 */
public class MainClient {
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
                // 表示连接的超时时间
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                // 指定IO类型为NIO
                .channel(NioSocketChannel.class)
                // 处理IO逻辑
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        // 空闲检测
                        socketChannel.pipeline().addLast(new IMIdleStateHandler());
                        /*
                        * 拆包器，
                        * 1 数据包最大长度
                        * 2 长度域的偏移量(根据自定义协议定，这里是7)
                        * 3 长度域的长度，根据自定义协议定，这里是4
                        * */
                        //socketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 7, 4));
                        socketChannel.pipeline().addLast(new Spliter());
                        // 同时负责编解码
                        socketChannel.pipeline().addLast(PacketCodecHandler.INSTANCE);
                        socketChannel.pipeline().addLast(new LoginResponseHandler());
                        socketChannel.pipeline().addLast(new MessageResponseHandler());
                        // 创建群聊的处理器
                        socketChannel.pipeline().addLast(new CreateGroupResponseHandler());
                        // 加入群聊的处理器
                        socketChannel.pipeline().addLast(new JoinGroupResponseHandler());
                        // 退出群聊
                        socketChannel.pipeline().addLast(new QuitGroupResponseHandler());
                        // 获取群成员列表
                        socketChannel.pipeline().addLast(new ListGroupMembersResponseHandler());
                        // 群聊消息
                        socketChannel.pipeline().addLast(new GroupMessageResponseHandler());
                        // 退出
                        socketChannel.pipeline().addLast(new QuitGroupResponseHandler());
                    }
                });
        // 建立连接
        connect(bootstrap, HOST, PORT, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                Channel channel = ((ChannelFuture)future).channel();
                // 连接成功后，启动控制台线程
                startConsoleThread(channel);
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

    // 开始控制台的监听线程
    private static void startConsoleThread(Channel channel) {
        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        Scanner scanner = new Scanner(System.in);

        new Thread(() -> {
            while (!Thread.interrupted()) {
                // 没登录的话要求登录
                if (!SessionUtil.hasLogin(channel)) {
                    // 执行登录指令
                    loginConsoleCommand.exec(scanner, channel);
                } else {
                    // 根据输入的其它指令执行对应的指令
                    consoleCommandManager.exec(scanner, channel);
                }
            }
        })
        .start();
    }
}
