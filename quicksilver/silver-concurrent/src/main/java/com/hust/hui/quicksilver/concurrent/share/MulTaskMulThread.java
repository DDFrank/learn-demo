package com.hust.hui.quicksilver.concurrent.share;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yihui on 2017/11/24.
 */
public class MulTaskMulThread {

    private int count = 0;

    private class MyTask implements Runnable {
        @Override
        public void run() {
            count += 1;
            System.out.println(Thread.currentThread() + " : " + count);
        }
    }


    public static void main(String[] args) throws InterruptedException {
        MulTaskMulThread mul = new MulTaskMulThread();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.submit(mul.new MyTask());
        executorService.submit(mul.new MyTask());

        Thread thread1 = new Thread(mul.new MyTask(), "线程1");
        Thread thread2 = new Thread(mul.new MyTask(), "线程2");

        thread1.start();
        thread2.start();

        thread2.join();
        thread1.join();
        System.out.println("---over----");
    }

}
