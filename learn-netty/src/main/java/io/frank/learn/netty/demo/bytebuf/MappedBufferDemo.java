package io.frank.learn.netty.demo.bytebuf;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 内存映射
 *
 * @author jinjunliang
 **/
public class MappedBufferDemo {
    private static final int start = 0;
    private static final int size = 1024;

    public static void main(String[] args) throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("/Users/jinjunliang/Workspace/idea/aaalearn/learn-netty/src/main/resources/1.txt", "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();

        // 把缓冲区跟一个文件系统的一个文件相关联
        // 对缓冲区的操作会映射到该文件
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, start, size);
        mappedByteBuffer.put(0, (byte)97);
        mappedByteBuffer.put(1023, (byte)122);
        randomAccessFile.close();
    }
}
