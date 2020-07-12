package com.hust.hui.quicksilver.server.test;

import org.junit.Test;

/**
 * Created by yihui on 2017/4/25.
 */
public class ThreadTest implements Runnable {

    @Override
    public void run() {
        synchronized (this) {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " loop " + i);
                if (i == 5) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

    }


    /**
     * 同步代码快, 上面锁的是对象, 所以下面调用没有什么问题,不会出现阻塞
     */
    @Test
    public void test1() {

        ThreadTest t1 = new ThreadTest();

        ThreadTest t2 = new ThreadTest();

        Thread ta = new Thread(t1, "a");

        Thread tb = new Thread(t2, "b");

        ta.start();

        tb.start();
    }


    public synchronized void waitTest() {

        System.out.println(Thread.currentThread().getName() + "-------- in ----------");
        try {
            wait(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "-------- over ----------");
    }


    @Test
    public void testWaitNotify() throws InterruptedException {

        for (int i = 0; i < 3; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    waitTest();
                }
            }).start();
        }

        synchronized (this) {
            System.out.println("----notify------");
            notify();
            System.out.println("-------notify over------");
        }

        Thread.sleep(2000);

        System.out.println(Thread.currentThread().getName() + "------------------- main ---------------");

        synchronized (this) {
            notifyAll();
        }

        System.out.println(Thread.currentThread().getName() + "------------- over -----------------");
    }


    static class Tag {
        boolean tag = false;
    }

    private static Tag tag = new Tag();

    @Test
    public void testVolatile() throws InterruptedException {
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                System.out.println("in A-------");
                while (!tag.tag) {
                    System.out.print((i++) + ",");
                }
                System.out.println("\nout A-------");
            }
        });


        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("in B---------");
                tag.tag = true;
                System.out.println("out B--------");
            }
        });

        threadA.start();
        Thread.sleep(1);
        threadB.start();;
    }
}
