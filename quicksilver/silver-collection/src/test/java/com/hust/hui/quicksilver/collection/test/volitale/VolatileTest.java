package com.hust.hui.quicksilver.collection.test.volitale;

import org.junit.Test;

/**
 * Created by yihui on 2017/10/18.
 */
public class VolatileTest {

    private volatile int[] tables = new int[20];

    private void init() {
        for (int i = 0; i < 20; i++) {
            tables[i] = i;
        }
    }


    public void printAry() {
        
        System.out.println(Thread.currentThread().getName()
                + " print: " + tables[10] + "|" + tables[13]);
//                + Arrays.asList(tables));
    }


    @Test
    public void testPrint() {
        init();
        printAry();
    }


    private void modify() {
        tables[10] = 999;
        tables[13] = 888;
        System.out.println(Thread.currentThread().getName() + " >>> 修改完成" );
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileTest volatileTest = new VolatileTest();
        volatileTest.init();

        Runnable read = () -> {
            while (true) {
                volatileTest.printAry();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread t1 = new Thread(read);
        Thread t2 = new Thread(read);
        Thread t3 = new Thread(read);
        Thread t4 = new Thread(read);
        t1.start();
        t2.start();
        t3.start();
        t4.start();

        System.out.println("---主线程修改数组内容----");
        volatileTest.modify();
        System.out.println("-----主线程修改完毕-----");


        Thread.sleep(1);
        System.exit(0);
    }


}
