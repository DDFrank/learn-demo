package com.hust.hui.quicksilver.file.test.basic;

import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Created by yihui on 2018/3/18.
 */
public class FileIOReadTest {

    @Test
    public void testPrintFile() throws IOException {
        String fileName = "/tmp/test.d";
        File file = new File(fileName);
        InputStream input = new FileInputStream(file);
        InputStreamReader reader = new InputStreamReader(input, Charset.forName("utf-8"));
        BufferedReader bufferedReader = new BufferedReader(reader);

        String ans = bufferedReader.readLine();
        while (ans !=null ) {
            System.out.println(ans);
            ans = bufferedReader.readLine();
        }

        bufferedReader.close();
        reader.close();
        input.close();
    }


    @Test
    public void testSourceStream() throws IOException {
        byte[] bytes = new byte[]{'a', 'b', 'c', '1', '2'};
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

        File file = new File("/tmp/test.d");
        OutputStream out = new FileOutputStream(file);
        Writer writer = new OutputStreamWriter(out);

        int a = 0;
        while((a = byteArrayInputStream.read()) != -1) {
            writer.write(a);
        }

        writer.flush();
        writer.close();
        byteArrayInputStream.close();


        testPrintFile();
    }

    public static class Demo implements Serializable {

        private String name;
        private Integer age;
        private boolean isBoy;
        private transient String ignore;

        public Demo(String name, Integer age, boolean isBoy, String ignore) {
            this.name = name;
            this.age = age;
            this.isBoy = isBoy;
            this.ignore = ignore;
        }

        @Override
        public String toString() {
            return "Demo{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", isBoy=" + isBoy +
                    ", ignore='" + ignore + '\'' +
                    '}';
        }
    }

    @Test
    public void testObjStream() throws IOException, ClassNotFoundException {
        Demo demo = new Demo("测试", 123, true, "忽略的一段文本");

        // 将对象写入到文件中
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("/tmp/out.t"));
        oos.writeObject(demo);
        oos.flush();
        oos.close();

        System.out.println("-------- over ----------");
        // 从文件读取
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("/tmp/out.t"));
        Object obj = ois.readObject();
        System.out.println("反序列化: " + obj + " \n类型：" + obj.getClass().getSimpleName());
    }

}
