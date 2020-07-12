package io.frank.learn.netty.custom.tomcat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import java.io.FileInputStream;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author jinjunliang
 **/
public class GPTomcat {
    private int port = 8080;
    private ServerSocket serverSocket;
    private Map<String, GPServlet> stringGPServletMap = new HashMap<>();
    private Properties webProperties = new Properties();

    public void start() throws Exception {
        // 加载配置文件
        init();

        // 使用NETTY
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup wokerGroup = new NioEventLoopGroup();
        try {
            // 创建对象
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 配置参数
            serverBootstrap.group(boosGroup, wokerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new HttpResponseEncoder());
                            socketChannel.pipeline().addLast(new HttpRequestDecoder());
                            //
                            socketChannel.pipeline().addLast(new GPTomcatHandler());
                        }
                    })
                    // 针对主线程的配置，分配最大线程数为128
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // 针对子线程，保持长连接
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // 启动服务器
            ChannelFuture f = serverBootstrap.bind(port).sync();
            System.out.println("GPTomcat 已经启动, 监听的端口是:" + port);
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            boosGroup.shutdownGracefully();
            wokerGroup.shutdownGracefully();
        }
    }

    private void init() throws Exception {
        // 加载配置文件
        String WEB_INF = this.getClass().getResource("/").getPath();
        FileInputStream in = new FileInputStream(WEB_INF + "web.properties");
        webProperties.load(in);

        webProperties.forEach((k, v) -> {
            String key = k.toString();
            if (key.endsWith(".url")) {
                String servletName = key.replaceAll("\\.url$", "");
                String url = webProperties.getProperty(key);
                String className = webProperties.getProperty(servletName + ".className");
                try {
                    GPServlet o = (GPServlet) Class.forName(className).newInstance();
                    stringGPServletMap.put(url, o);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public class GPTomcatHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            if (msg instanceof HttpRequest) {
                System.out.println("hello");
                HttpRequest req = (HttpRequest) msg;
                GPRequest request = new GPRequest(ctx, req);
                GPResponse response = new GPResponse(ctx, req);
                String url = request.getUrl();

                if (stringGPServletMap.containsKey(url)) {
                    stringGPServletMap.get(url).service(request, response);
                } else {
                    response.write("404 - Not Found");
                }
            }
        }
    }
}
