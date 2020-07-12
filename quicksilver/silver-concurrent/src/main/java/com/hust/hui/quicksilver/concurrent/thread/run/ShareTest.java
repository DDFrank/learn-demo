package com.hust.hui.quicksilver.concurrent.thread.run;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yihui on 2017/11/7.
 */
public class ShareTest {

    private static class MyRun implements Runnable {
        private volatile AtomicInteger ato = new AtomicInteger(5);
        @Override
        public void run() {
            while (true) {
                int tmp = ato.decrementAndGet();
                System.out.println(Thread.currentThread() + " : " + tmp);
                if (tmp <= 0) {
                    break;
                }
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        MyRun run = new MyRun();
        Thread thread1 = new Thread(run, "线程1");
        Thread thread2 = new Thread(run, "线程2");

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
        System.out.println("over");
    }
}
