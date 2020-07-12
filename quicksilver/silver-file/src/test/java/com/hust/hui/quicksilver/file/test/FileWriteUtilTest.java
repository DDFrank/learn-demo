package com.hust.hui.quicksilver.file.test;

import com.hust.hui.quicksilver.file.FileWriteUtil;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by yihui on 2017/5/17.
 */
public class FileWriteUtilTest {

    @Test
    public void testBufWrite() throws IOException {
        FileWriteUtil fileWrite = FileWriteUtil.newInstance(FileWriteUtil.WriteType.BUFFER, "bufWrite.txt", false);
        try {

            fileWrite.write("hello world")
                    .write("\n")
                    .write("你好😄《-表情符》");

        } finally {
            fileWrite.close();
        }
    }


    @Test
    public void testWriterWrite() throws IOException {
        FileWriteUtil fileWrite = FileWriteUtil.newInstance(FileWriteUtil.WriteType.WRITER, "writerWrite.txt", false);

        try {

            fileWrite.write("hello world")
                    .write("\n")
                    .write("你好😄《-表情符》");

        } finally {
            fileWrite.close();
        }
    }


}
