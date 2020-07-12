package com.hust.hui.quicksilver.concurrent.thread.call;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by yihui on 2017/11/6.
 */
public class AddCall implements Callable<Integer> {

    private int start, end;

    public AddCall(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        System.out.println(Thread.currentThread().getName() + " 开始执行!");
        for (int i = start; i <= end; i++) {
            sum += i;
        }
        System.out.println(Thread.currentThread().getName() + " 执行完毕! sum=" + sum);
        return sum;
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int start = 0, mid = 500, end = 1000;
        FutureTask<Integer> future1 = new FutureTask<>(new AddCall(start, mid));
        FutureTask<Integer> future2 = new FutureTask<>(new AddCall(mid + 1, end));

        Thread thread1 = new Thread(future1, "线程1");
        Thread thread2 = new Thread(future2, "线程2");

        thread1.start();
        thread2.start();

        int sum1 = future1.get();
        int sum2 = future2.get();
        System.out.println("ans: " + (sum1 + sum2));
    }
}
