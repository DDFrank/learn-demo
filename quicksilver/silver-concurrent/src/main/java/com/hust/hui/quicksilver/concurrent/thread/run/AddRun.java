package com.hust.hui.quicksilver.concurrent.thread.run;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yihui on 2017/11/6.
 */
public class AddRun implements Runnable {

    private int start, end;
    private int sum = 0;

    AtomicInteger ato;


    public AddRun(int start, int end) {

        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " 开始执行!");
        for(int i = start; i <= end; i++) {
            sum += i;
        }
        System.out.println(Thread.currentThread().getName() + " 执行完毕! sum=" + sum);
    }


    public static void main(String[] args) throws InterruptedException {
        int start = 0, mid = 500, end = 1000;
        AddRun run1 = new AddRun(start, mid);
        AddRun run2 = new AddRun(mid + 1, end);
        Thread thread1 = new Thread(run1, "线程1");
        Thread thread2 = new Thread(run2, "线程2");

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
        int sum = run1.sum + run2.sum;
        System.out.println("ans: " + sum);
    }
}
