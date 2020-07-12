package io.frank.learn.netty.demo.bytebuf;

import java.nio.ByteBuffer;

/**
 * @author jinjunliang
 **/
public class ReadOnlyBufferDemo {
    public static void main(String[] args) {
        ByteBuffer b = ByteBuffer.allocate(10);
        for (int i=0;i<b.capacity();i++) {
            b.put((byte)i);
        }

        ByteBuffer readOnlyBuffer = b.asReadOnlyBuffer();

        for (int i=0;i<b.capacity();i++) {
            b.put(i, (byte)10);
        }

        readOnlyBuffer.position(0);
        while (readOnlyBuffer.hasRemaining()) {
            System.out.println(readOnlyBuffer.get());
        }
    }
}
