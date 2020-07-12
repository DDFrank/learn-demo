package com.hust.hui.quicksilver.commons.test.listener.thread;

import org.junit.Test;

/**
 * Created by yihui on 2017/6/5.
 */
public class ThreadCreate {


    /**
     * 通过继承  Thread 方式来创建一个新的线程
     */
    public static class ThreadExtend extends Thread {

        @Test
        public void run() {
            System.out.println("new extend thread");
        }
    }


    /**
     * 通过实现 Runnable 方式来创建一个线程
     */
    public static class RunnableImplement implements Runnable {

        @Override
        public void run() {
            System.out.println("new runnable thread");
        }
    }

    @Test
    public void testCreate() {
        new ThreadExtend().start();

        new Thread(new RunnableImplement()).start();

        System.out.println("main!");
    }

}
