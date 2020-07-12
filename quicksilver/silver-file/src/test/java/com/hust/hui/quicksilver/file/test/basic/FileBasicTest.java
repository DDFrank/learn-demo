package com.hust.hui.quicksilver.file.test.basic;

import org.junit.Test;

import java.io.*;
import java.nio.channels.FileChannel;

/**
 * Created by yihui on 2017/6/5.
 */
public class FileBasicTest {


    public void read() {

    }

    class EndException extends Exception {
        @Override
        public synchronized Throwable fillInStackTrace() {
            return this;
        }
    }


    class FileManager {
        public Reader reader;


        public FileManager(String path) throws FileNotFoundException, UnsupportedEncodingException {
            reader = new InputStreamReader(new FileInputStream(path), "utf-8");
        }


        public String nextWord(char... splitChars) throws IOException, EndException {
            if (splitChars.length == 0) {
                throw new IllegalArgumentException("illegal split char!");
            }

            StringBuilder stringBuilder = new StringBuilder();
            int temp = reader.read();
            if (temp == -1) {
                throw new EndException();
            }

            s1:
            do {
                for (char ch : splitChars) {
                    if (temp == (int) ch) {
                        break s1;
                    }
                }

                stringBuilder.append((char) temp);
            } while ((temp = reader.read()) != -1);

            return stringBuilder.toString();
        }


        public void clear() throws IOException {
            reader.close();
        }

    }

    public static void listFile(String name) {
        File file = new File(name);
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(".java");
                }
            });
        }

        if (!file.exists()) {
            file.mkdir();
            file.mkdirs();
        }
    }


    public static void copy(String src, String des) {
        try {
            FileInputStream srcIn = new FileInputStream(src);
            FileOutputStream desIn = new FileOutputStream(des);

            FileChannel srcF = srcIn.getChannel();
            FileChannel desF = desIn.getChannel();

            srcF.transferTo(0, srcF.size(), desF);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCopy() {
        String src = "/tmp/r1.txt";
        String des = "/tmp/r2.txt";

        copy(src, des);
    }


    @Test
    public void testMerge() {
        try {
            FileManager f1 = new FileManager("/tmp/r1.txt");
            FileManager f2 = new FileManager("/tmp/r2.txt");

            Writer writer = new OutputStreamWriter(new FileOutputStream("/tmp/r3.txt"), "UTF-8");


            boolean f1End = false;
            boolean f2end = false;
            String temp;
            while (true) {
                try {
                    temp = f2.nextWord(' ', '\n');
                    writer.write(temp + "[A]\n");
                    writer.flush();
                } catch (EndException e) {
                    f2end = true;
                }

                try {
                    temp = f1.nextWord('\n');
                    writer.write(temp + "[B]\n");
                    writer.flush();
                } catch (EndException e) {
                    f1End = true;
                    break;
                }
            }

            if (!f1End) {
                while (true) {
                    try {
                        temp = f2.nextWord(' ', '\n');
                        writer.write(temp);
                    } catch (EndException e) {
                        writer.flush();
                        break;
                    }
                }
            }


            if (!f2end) {
                while (true) {
                    try {
                        temp = f1.nextWord(' ');
                        writer.write(temp + "\n");
                    } catch (EndException e) {
                        writer.flush();
                        break;
                    }
                }
            }

            writer.flush();
            writer.close();
            f1.clear();
            f2.clear();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public void testThread() {
        final Integer[] i = new Integer[]{0};

        for (int num =0; num< 100; num ++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ++i[0];
                }
            }).start();
        }

        System.out.println(i[0]);
    }

    @Test
    public void testT() {
        for (int i =0; i < 9; i++) {
            testThread();
        }
    }
}
