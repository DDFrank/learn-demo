package com.hust.hui.quicksilver.concurrent.synchronize;

import org.junit.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by yihui on 2017/10/27.
 */
public class TestDemoTest {

    /**
     * 非静态同步方法测试
     */
    private void nonStaticSynFun() throws InterruptedException {

        TestDemo testDemo = new TestDemo();

        Thread thread1 = new Thread(() -> testDemo.a("访问同一加锁方法"), "thread1");
        Thread thread2 = new Thread(() -> testDemo.a("访问同一加锁方法"), "thread2");

        System.out.println("---两个线程，访问同一个加锁方法开始---");
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
        System.out.println("---两个线程，访问同一个加锁方法结束---\n");


        TestDemo testDemo2 = new TestDemo();
        thread1 = new Thread(() -> testDemo.a("访问第一个实例同一加锁方法"), "thread1");
        thread2 = new Thread(() -> testDemo2.a("访问第二个实例同一加锁方法"), "thread2");

        System.out.println("---两个线程，访问两个实例同一个加锁方法开始---");
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
        System.out.println("---两个线程，访问两个同一个加锁方法结束---\n");


        //

        thread1 = new Thread(() -> testDemo.a("访问两个加锁方法"), "thread1");
        thread2 = new Thread(() -> testDemo.b("访问两个加锁方法"), "thread2");
        System.out.println("---两个线程，访问两个加锁方法开始---");
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
        System.out.println("---两个线程，访问两个加锁方法结束---\n");


        //
        thread1 = new Thread(() -> testDemo.a("访问加锁实例方法"), "thread1");
        thread2 = new Thread(() -> TestDemo.c("访问加锁静态方法"), "thread2");
        System.out.println("---两个线程，访问实例和静态加锁方法开始---");
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
        System.out.println("---两个线程，访问实例和静态加锁方法结束---\n");

    }

    @Test
    public void testNoStaticSynFun() throws InterruptedException {
        for (int i = 0; i < 1; i++) {
            nonStaticSynFun();
        }
    }


    private void staticSynFun() throws InterruptedException {
        Thread thread1 = new Thread(() -> TestDemo.c("访问加锁静态方法"), "thread1");
        Thread thread2 = new Thread(() -> TestDemo.c("访问加锁静态方法"), "thread2");

        System.out.println("---两个线程，访问静态加锁方法开始---");
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
        System.out.println("---两个线程，访问静态加锁方法结束---\n");


        //

        TestDemo testDemo1 = new TestDemo(), testDemo2 = new TestDemo();
        thread1 = new Thread(() -> testDemo1.c("访问加锁静态方法"), "thread1");
        thread2 = new Thread(() -> testDemo2.d("访问加锁静态方法"), "thread2");
        Thread thread3 = new Thread(() -> testDemo1.a("访问加锁实例方法"), "thread3");

        System.out.println("---两个线程，访问不同实例的静态加锁方法开始---");
        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();
        System.out.println("---两个线程，访问不同实例的静态加锁方法结束---\n");

    }

    @Test
    public void testStaticSynFunc() throws InterruptedException {
        for (int i = 0; i < 1; i++) {
            staticSynFun();
        }
    }


    @Test
    public void testLock() {
        Lock lock = new ReentrantLock();
        lock.lock();
        lock.unlock();
        lock.unlock();
        System.out.println("123");
    }
}
