package com.hust.hui.quicksilver.server.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yihui on 2017/4/27.
 */
public class ThreadLocalTest {


    private static final ThreadLocal<AtomicInteger> threadLocal =new ThreadLocal<AtomicInteger>() {

        @Override
        protected AtomicInteger initialValue() {
            return new AtomicInteger(0);
        }
    };


    static class Task implements Runnable {

        @Override
        public void run() {
            AtomicInteger s = threadLocal.get();
            int initial = s.getAndIncrement();
            // 期望初始为0
            System.out.println(initial);
        }
    }


    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(new Task());
        executor.execute(new Task());
        executor.execute(new Task());
        executor.shutdown();
    }
}
