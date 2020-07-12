package io.frank.learn.netty.demo.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 专用于操作文件的通道
 *
 * @author jinjunliang
 **/
public class FileChannelDemo {
    // 演示复制文件
    public static void main(String[] args) throws IOException {

        try (
                FileInputStream fileInputStream =
                        new FileInputStream("/Users/jinjunliang/Workspace/idea/aaalearn/learn-netty/src/main/resources/1.txt");
                FileChannel fic = fileInputStream.getChannel();
                FileOutputStream fileOutputStream =
                        new FileOutputStream("/Users/jinjunliang/Workspace/idea/aaalearn/learn-netty/src/main/resources/2.txt");
                FileChannel foc = fileOutputStream.getChannel()
                ){
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int length = -1;
            while ((length = fic.read(byteBuffer)) != -1) {
                // 将数据写入到 文件通道了，因此要转换为读取模式
                byteBuffer.flip();

                int outLength = 0;
                while ((outLength = foc.write(byteBuffer)) != 0) {
                    System.out.println("写入了" + outLength + "字节");
                }
                // 清除缓冲区数据，变为写模式
                byteBuffer.clear();
            }
            foc.force(true);
        }
    }
}
