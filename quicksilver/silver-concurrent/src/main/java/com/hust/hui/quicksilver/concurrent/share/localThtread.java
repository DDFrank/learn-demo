package com.hust.hui.quicksilver.concurrent.share;

import org.junit.Test;

/**
 * Created by yihui on 2017/11/25.
 */
public class localThtread {
    public static class LocalT implements Runnable {
        ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

        @Override
        public void run() {
            int start = ((int) (Math.random() * 10)) * 100;
            for (int i = 0; i < 10; i = i + 2) {
                threadLocal.set(start + i);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() +
                        " : " + get());
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