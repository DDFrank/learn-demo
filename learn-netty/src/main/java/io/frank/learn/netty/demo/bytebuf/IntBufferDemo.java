package io.frank.learn.netty.demo.bytebuf;

import java.nio.IntBuffer;

/**
 * @author jinjunliang
 **/
public class IntBufferDemo {
    public static void main(String[] args) {
        IntBuffer b = IntBuffer.allocate(8);
        for (int i=0;i<b.capacity();i++) {
            b.put(2 * (i+1));
        }
        b.flip();
        while (b.hasRemaining()) {
            System.out.println(b.get());
        }
    }
}
