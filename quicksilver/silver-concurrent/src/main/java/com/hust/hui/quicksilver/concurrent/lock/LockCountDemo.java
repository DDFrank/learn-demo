package com.hust.hui.quicksilver.concurrent.lock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by yihui on 2017/10/29.
 */
public class LockCountDemo {

    private int start = 10;
    private int middle = 90;
    private int end = 200;

    private volatile int tmpAns1 = 0;
    private volatile int tmpAns2 = 0;

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private AtomicInteger count = new AtomicInteger(0);


    private int add(int i, int j) {
        try {
            lock.lock();
            int sum = 0;
            for (int tmp = i; tmp < j; tmp++) {
                sum += tmp;
            }
            return sum;
        } finally {
            atomic();
            lock.unlock();
        }
    }


    private int sum() throws InterruptedException {
        try {
            lock.lock();
            condition.await();
            return tmpAns1 + tmpAns2;
        } finally {
            lock.unlock();
        }
    }

    private void atomic() {
        if (2 == count.addAndGet(1)) {
            condition.signal();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        LockCountDemo demo = new LockCountDemo();
        Thread thread1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " : 开始执行");
            demo.tmpAns1 = demo.add(demo.start, demo.middle);
            System.out.println(Thread.currentThread().getName() +
                    " : calculate ans: " + demo.tmpAns1);
        }, "count1");

        Thread thread2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " : 开始执行");
            demo.tmpAns2 = demo.add(demo.middle, demo.end + 1);
            System.out.println(Thread.currentThread().getName() +
                    " : calculate ans: " + demo.tmpAns2);
        }, "count2");

        Thread thread3 = new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " : 开始执行");
                int ans = demo.sum();
                System.out.println("the total result: " + ans);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "sum");


        thread3.start();
        thread1.start();
        thread2.start();

        Thread.sleep(3000);
        System.out.println("over");
    }

}
