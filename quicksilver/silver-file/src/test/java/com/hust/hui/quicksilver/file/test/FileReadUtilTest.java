package com.hust.hui.quicksilver.file.test;

import com.hust.hui.quicksilver.file.FileReadUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

/**
 * Created by yihui on 2017/5/6.
 */
public class FileReadUtilTest {

    private static final Logger logger = LoggerFactory.getLogger(FileReadUtilTest.class);

//    String fileName = "/tmp/test.d";

    String fileName = "test.d";



    /**
     * 字节方式读取文件
     *
     * @throws IOException
     */
    @Test
    public void testByteRead() throws IOException {
        InputStream in = FileReadUtil.createByteRead(fileName);

        int temp;
        while ((temp = in.read()) != -1) {
            logger.info("read bytes: {}", temp);
        }

        in.close(); // 关闭输入流


        in = FileReadUtil.createByteRead(fileName);
        byte[] tmpAry = new byte[100];
        while ((temp = in.read(tmpAry)) != -1) {
            logger.info("read 100 bytes: {}, return: {}", tmpAry, temp);
        }
        in.close();
    }


    @Test
    public void testCharRead() throws IOException {
        Reader reader = FileReadUtil.createCharRead(fileName);


        int temp;
        while ((temp = reader.read()) != -1) {
            logger.info("read char: {}", (char) temp);
        }
        reader.close();


        reader = FileReadUtil.createCharRead(fileName);
        char[] tmpAry = new char[100];
        while ((temp = reader.read(tmpAry)) != -1) {
            logger.info("read 100 chars: {}, return: {}", tmpAry, temp);
        }
        reader.close();
    }


    @Test
    public void testLineRead() throws IOException {
        BufferedReader bufferedReader = FileReadUtil.createLineRead(fileName);
        String temp;

        while ((temp = bufferedReader.readLine()) != null) { // 会过滤掉换行符
            logger.info("read line: >>>{}<<<", temp);
        }
        bufferedReader.close();
    }



    /**
     * 随机读取文件内容
     *
     *            文件名
     */
    @Test
    public void testRandomRead() {
        String fileName = "/tmp/test.d";
        RandomAccessFile randomFile = null;
        try {
            System.out.println("随机读取一段文件内容：");
            // 打开一个随机访问文件流，按只读方式
            randomFile = new RandomAccessFile(fileName, "r");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 读文件的起始位置
            int beginIndex = (fileLength > 4) ? 4 : 0;
            // 将读文件的开始位置移到beginIndex位置。
            randomFile.seek(beginIndex);
            byte[] bytes = new byte[10];
            int byteread = 0;
            // 一次读10个字节，如果文件内容不足10个字节，则读剩下的字节。
            // 将一次读取的字节数赋给byteread
            while ((byteread = randomFile.read(bytes)) != -1) {
                System.out.write(bytes, 0, byteread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (randomFile != null) {
                try {
                    randomFile.close();
                } catch (IOException e1) {
                }
            }
        }
    }


    @Test
    public void testReadFile() {
        try {
            // 绝对路径读取
            String img = "/tmp/img.jpg";
            BufferedImage bf1 = ImageIO.read(FileReadUtil.getStreamByFileName(img));
            logger.info("read success!");
        } catch (IOException e) {
            logger.error("read absolute img error!");
        }


        try {
            // 相对路径
            String img2 = "avatar.jpg";
            BufferedImage bf2 = ImageIO.read(FileReadUtil.getStreamByFileName(img2));
            logger.info("read success!");
        } catch (Exception e) {
            logger.error("read relative img error!");
        }


        try {
            String img3 = "http://fanyi.baidu.com/static/translation/img/header/logo_cbfea26.png";
            URL url = new URL(img3);
            BufferedImage bf3 = ImageIO.read(FileReadUtil.getStreamByFileName(img3));
            logger.info("read success!");
        } catch (Exception e) {
            logger.error("read net img error!");
        }
    }
}
