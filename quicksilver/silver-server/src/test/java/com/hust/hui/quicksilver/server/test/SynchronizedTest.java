package com.hust.hui.quicksilver.server.test;

/**
 * Created by yihui on 2017/4/27.
 */
public class SynchronizedTest {

    public void staticFunc() {
        System.out.println(Thread.currentThread().getName() + " in 1--->");
        try {
            synchronized (this) {
                System.out.println(Thread.currentThread().getName() + "-->synch staticFunc print");
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " out 1--->");
    }

    public void staticFunc2() {
        System.out.println(Thread.currentThread().getName() + " in 2--->");
        try {
            synchronized (this) {
                System.out.println(Thread.currentThread().getName() + "-->synch staticFunc2 print");
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " out 2--->");
    }


    public static void main(String[] args) {
        SynchronizedTest synchronizedTest = new SynchronizedTest();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronizedTest.staticFunc();
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronizedTest.staticFunc2();
            }
        });

        thread1.start();
        thread2.start();
    }

}
