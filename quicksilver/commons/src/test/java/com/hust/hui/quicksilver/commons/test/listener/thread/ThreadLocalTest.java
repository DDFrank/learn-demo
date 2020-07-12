package com.hust.hui.quicksilver.commons.test.listener.thread;

import org.junit.Test;

/**
 * Created by yihui on 2017/6/6.
 */
public class ThreadLocalTest {

    private static int begin = 10;

    public static class LocalT implements Runnable {

        ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

        @Override
        public void run() {
            int start = begin;
            if (start == 10) {
                begin = 200;
            }

            for (int i =0 ; i < 100; i = i+2) {
                threadLocal.set(start + i);
                System.out.println(Thread.currentThread().getName() + " : " + get());
            }
        }


        public int get() {
            return threadLocal.get();
        }
    }


    @Test
    public void testLocal() throws InterruptedException {
        LocalT local = new LocalT();

        Thread thread1 = new Thread(local);
        Thread thread2 = new Thread(local);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }

}
