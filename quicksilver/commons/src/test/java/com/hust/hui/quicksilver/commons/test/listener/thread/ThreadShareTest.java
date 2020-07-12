package com.hust.hui.quicksilver.commons.test.listener.thread;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yihui on 2017/6/6.
 */
public class ThreadShareTest {

    private AtomicInteger count = new AtomicInteger(0);
    private int sum = 3000;

    public class MyThread extends Thread {
        public void run() {
            while (true) {
                if (sum > 0) {
                    synchronized (ThreadShareTest.class) {
                        if (sum > 0) {
                            count.addAndGet(1);
                            --sum;
                        }
                    }
                } else {
                    break;
                }
            }
            System.out.println(Thread.currentThread().getName() + " over " + sum);
        }
    }


    @Test
    public void testAdd() throws InterruptedException {
        MyThread myThread1 = new MyThread();
        MyThread myThread2 = new MyThread();

        myThread1.start();
        myThread2.start();

        myThread1.join();
        myThread2.join();

        System.out.println("num: " + sum + " count: " + count.get());
    }


    @Test
    public void testAdd2() throws InterruptedException {
        final int[] num = {3000};
        AtomicInteger c = new AtomicInteger(0);


        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (num[0] > 0) {
                        c.addAndGet(1);
                        num[0]--;
                    } else {
                        break;
                    }
                }

                System.out.println(Thread.currentThread().getName() + " over " + num[0]);
            }
        };

        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("num: " + num[0] + " count: " + c.get());
    }



    public static class ThreadData implements Runnable {
        private boolean tag = true;

        private AtomicInteger atomicInteger = new AtomicInteger(0);

        public void run() {
            while (tag) {
                System.out.println(Thread.currentThread().getName() + " now: " + atomicInteger.addAndGet(1));
            }

            System.out.println(Thread.currentThread().getName() + " num: " + atomicInteger.get());
        }

        public void close() {
            tag = false;
            System.out.println("close");
        }
    }


    @Test
    public void testThreadSetData() throws InterruptedException {
        ThreadData threadData = new ThreadData();

        Thread thread1 = new Thread(threadData);
        Thread thread2 = new Thread(threadData);

        thread1.start();
        thread2.start();

        Thread.sleep(100);
        threadData.close();

        thread1.join();
        thread1.join();
    }

}
