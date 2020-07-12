package io.frank.learn.netty.demo.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author jinjunliang
 **/
public class SelectorDemo {
    private static final int PORT = 8080;
    public static void main(String[] args) {

    }

    private Selector getSelector() throws Exception {
        // 创建Selector对象
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);

        // 绑定通道到指定端口
        ServerSocket serverSocket = serverSocketChannel.socket();
        InetSocketAddress address = new InetSocketAddress(PORT);
        serverSocket.bind(address);

        // 注册感兴趣的事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        return selector;
    }

    /**
     * 开始监听
     * @param selector
     */
    public void listen(Selector selector) {
        try {
            while (true) {
                selector.select();
                Set<SelectionKey> selectionKeys =  selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    process(selector, key);
                }

            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 根据不同的事件进行不同的处理逻辑
    private void process(Selector selector, SelectionKey key) throws IOException {
        if (key.isAcceptable()) {
            ServerSocketChannel server = (ServerSocketChannel)key.channel();
            SocketChannel channel = server.accept();
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ);
        } else if (key.isReadable()) {
            SocketChannel socketChannel = (SocketChannel) key.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int len = socketChannel.read(byteBuffer);
            if (len > 0) {
                byteBuffer.flip();
                String content = new String(byteBuffer.array(), 0, len);
                SelectionKey key1 = socketChannel.register(selector, SelectionKey.OP_WRITE);
                key1.attach(content);
            } else {
                socketChannel.close();
            }
        } else if (key.isWritable()) {
            SocketChannel socketChannel = (SocketChannel)key.channel();
            String content = (String)key.attachment();
            ByteBuffer block = ByteBuffer.wrap(("输出内容: " + content).getBytes());
            socketChannel.write(block);
        }
    }
}
