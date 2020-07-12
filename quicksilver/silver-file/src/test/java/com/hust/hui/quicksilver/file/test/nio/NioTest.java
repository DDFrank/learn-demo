package com.hust.hui.quicksilver.file.test.nio;

import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by yihui on 2017/5/18.
 */
public class NioTest {

    @Test
    public void testNio() throws IOException {
//        FileInputStream fileInputStream = new FileInputStream(new File("/tmp/tags.log"));
//
//        FileChannel fileChannel = (fileInputStream).getChannel();

        RandomAccessFile randomAccessFile = new RandomAccessFile("/tmp/tags.log", "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();

        String newData = "New String to write to file..." + System.currentTimeMillis();

        ByteBuffer buf = ByteBuffer.allocate(100);
        buf.clear();
        buf.put(newData.getBytes());

        buf.flip();

        while(buf.hasRemaining()) {
            fileChannel.write(buf);
        }
        // 强制写入文件
        fileChannel.force(true);



        ByteBuffer buffer = ByteBuffer.allocate(100);
        int size = fileChannel.read(buffer);

        buffer.flip();

        while (buffer.hasRemaining()) {
            byte[] bytes = new byte[buffer.limit()];
            buffer.get(bytes, 0, buffer.limit());
            System.out.print(new String(bytes));
        }


        fileChannel.close();
        randomAccessFile.close();
    }

}
