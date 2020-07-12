package com.hust.hui.quicksilver.commons.test.listener.thread;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yihui on 2017/6/5.
 */
public class SaleTickTest {


    public static class TotalSaleTick implements Runnable {
        private long total = 30;
        AtomicInteger atomicInteger = new AtomicInteger(0);


        @Override
        public void run() {
            while (true) {
//                synchronized (this) {
                    if (total > 0) {
                        atomicInteger.addAndGet(1);
                        System.out.println(Thread.currentThread().getName() + "售出一张,剩余:" + --total);
                    } else {
                        break;
                    }
//                }
            }
        }
    }


    @Test
    public void testTotalSale() throws InterruptedException {
        TotalSaleTick totalSaleTick = new TotalSaleTick();
        Thread thread1 = new Thread(totalSaleTick, "窗口1");
        Thread thread2 = new Thread(totalSaleTick, "窗口2");
        Thread thread3 = new Thread(totalSaleTick, "窗口3");

        thread1.start();
        thread2.start();
        thread3.start();
        thread1.join();
        thread2.join();
        thread3.join();
        System.out.println("count: " + totalSaleTick.atomicInteger.get());
    }


    public static class SplitSaleTick extends Thread {
        private int total = 10;

        public SplitSaleTick(String name) {
            super(name);
        }

        @Override
        public void run() {
            while (true) {
                if (total > 0) {
                    System.out.println(Thread.currentThread().getName() + "售出一张,剩余:" + --total);
                } else {
                    break;
                }
            }
        }
    }


    @Test
    public void testSplitSaleTick() {
        SplitSaleTick splitSaleTick1 = new SplitSaleTick("窗口1");
        SplitSaleTick splitSaleTick2 = new SplitSaleTick("窗口2");
        SplitSaleTick splitSaleTick3 = new SplitSaleTick("窗口3");

        splitSaleTick1.start();
        splitSaleTick2.start();
        splitSaleTick3.start();
        System.out.println("master over");
    }

    /**
     * 继承 Thread 也可以实现共享, 只不过比较恶心而已
     */
    @Test
    public void testSplitSaleTick2() {
        SplitSaleTick splitSaleTick1 = new SplitSaleTick("saleTick");

        Thread thread1 = new Thread(splitSaleTick1, "窗口1");
        Thread thread2 = new Thread(splitSaleTick1, "窗口2");
        Thread thread3 = new Thread(splitSaleTick1, "窗口3");

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
