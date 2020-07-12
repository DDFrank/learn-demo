package io.frank.learn.netty.demo.bytebuf;

import java.io.FileInputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 *
 * @author jinjunliang
 **/
public class ByteBufferDemo {
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("/Users/jinjunliang/Workspace/idea/aaalearn/learn-netty/src/main/resources/1.txt");
        FileChannel fileChannel = fileInputStream.getChannel();
        ByteBuffer b = ByteBuffer.allocate(10);
        print("初始化", b);

        fileChannel.read(b);
        print("read()", b);

        b.flip();
        print("flip()", b);

        while (b.hasRemaining()) {
            b.get();
        }
        print("get()", b);

        b.clear();
        print("clear()", b);

        fileChannel.close();
    }

    private static void print(String step, Buffer buffer) {
        System.out.print("step: " + step);
        System.out.print(" capacity: " + buffer.capacity());
        System.out.print(" position: " + buffer.position());
        System.out.println(" limit: " + buffer.limit());
    }
}
