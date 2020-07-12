package com.hust.hui.quicksilver.concurrent.share;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yihui on 2017/11/23.
 */
public class OneTaskMulThread {

    private static class MyTask implements Runnable {

        private int count = 0;

        @Override
        public void run() {
            count += 1;
            System.out.println(Thread.currentThread() + " : " + count);
        }
    }


    public static void main(String[] args) throws InterruptedException {
        MyTask task = new MyTask();

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.submit(task);
        executorService.submit(task);

        Thread thread1 = new Thread(task, "线程1");
        Thread thread2 = new Thread(task, "线程2");

        thread1.start();
        thread2.start();

        thread2.join();
        thread1.join();
        System.out.println("---over----");
    }
}
