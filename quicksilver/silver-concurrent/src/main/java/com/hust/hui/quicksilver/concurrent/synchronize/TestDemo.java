package com.hust.hui.quicksilver.concurrent.synchronize;

/**
 * Created by yihui on 2017/10/27.
 */
public class TestDemo {

    public synchronized void a(String msg) {
        System.out.println(Thread.currentThread().getName() + ":a() before");
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ":a() after: " + msg);
    }


    public synchronized void b(String msg) {
        System.out.println(Thread.currentThread().getName() + ":b() before");
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ":b() after: " + msg);
    }

    public static synchronized void c(String msg) {
        System.out.println(Thread.currentThread().getName() + ":c() before");
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ":c() after: " + msg);
    }


    public static synchronized void d(String msg) {
        System.out.println(Thread.currentThread().getName() + ":d() before");
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ":d() after: " + msg);
    }


    public void e(String msg) {
        System.out.println(Thread.currentThread().getName() + ":e() before");
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ":e() after: " + msg);
    }


    public void f(String msg) {
        System.out.println(Thread.currentThread().getName() + ":f() before");
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ":f() after: " + msg);
    }


    public void g(String msg) {
        synchronized (this) {
            System.out.println(Thread.currentThread().getName() + ":a() before");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ":a() after: " + msg);
        }
    }


    public void h(String msg) {
        synchronized (this) {
            System.out.println(Thread.currentThread().getName() + ":h() before");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ":h() after: " + msg);
        }
    }
}
