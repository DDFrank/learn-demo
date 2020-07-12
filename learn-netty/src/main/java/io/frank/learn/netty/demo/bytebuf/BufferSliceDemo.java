package io.frank.learn.netty.demo.bytebuf;

import java.nio.ByteBuffer;

/**
 * @author jinjunliang
 **/
public class BufferSliceDemo {
    public static void main(String[] args) {
        ByteBuffer b = ByteBuffer.allocate(10);

        for (int i=0;i<b.capacity();i++) {
            b.put((byte)i);
        }

        // 指定子缓冲区的大小并创建
        b.position(3);
        b.limit(7);
        ByteBuffer s = b.slice();

        for (int i=0;i<s.capacity();i++) {
            s.put(i, (byte)(i*10));
        }

        b.position(0);
        b.limit(b.capacity());

        while (b.hasRemaining()) {
            System.out.println(b.get());
        }
    }
}
