package com.hust.hui.quicksilver.commons.test.listener.thread;

import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 创建线程测试
 * 1. 继承 Thread
 * 2. 实现 Runnable
 * <p/>
 * <p/>
 * Created by yihui on 2017/6/5.
 */
public class ThreadCreateTest {

    private final int[] num = new int[]{0};

    private int count = 0;

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    class TA extends Thread {
        public void run() {
            num[0]++;
            count++;
            atomicInteger.addAndGet(1);
            print();
        }
    }

    class TB implements Runnable {

        @Override
        public void run() {
            num[0]++;
            count++;
            atomicInteger.addAndGet(1);
            print();
        }
    }


    class TC implements Callable {

        @Override
        public Object call() throws Exception {
            num[0]++;
            count++;
            atomicInteger.addAndGet(1);
            print();
            return true;
        }
    }

    private void print() {
        System.out.println(Thread.currentThread().getName() + ">>" + num[0] + " == " + count + " == " + atomicInteger.get());
    }



    @Test
    public void testFuture() throws ExecutionException, InterruptedException {
        ThreadCreateTest test = new ThreadCreateTest();
        TA ta = test.new TA();
        ta.start();


        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future future = executorService.submit(test.new TC());
        System.out.println("master");

        future.get();
        System.out.println("last one");
    }


    public void cal() {
        for (int i = 0; i < 100; i++) {
            new TA().start();
        }

        num[0] = 0;
        count = 0;
        atomicInteger.set(0);


        for (int i = 0; i < 10; i++) {
            new Thread(new TB()).start();
        }
        System.out.println("\t||" + num[0] + " == " + count + " == " + atomicInteger.get());
        num[0] = 0;
        count = 0;
        atomicInteger.set(0);
    }

    @Test
    public void testCal() throws InterruptedException {
        for (int i = 0; i < 20; i++) {
            new TA().start();
        }



        Thread.sleep(200);
    }
}
