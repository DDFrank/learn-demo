package com.hust.hui.quicksilver.server.test;

/**
 * Created by yihui on 2017/4/27.
 */
public class TestVolatile {
    int a = 1;
    int b = 2;

    public void change() throws InterruptedException {
        a = 3;
        b = a;
    }

    public void print() {
        System.out.println(Thread.currentThread().getName() + "->b=" + b + ";a=" + a);
    }

    public static void main(String[] args) {
        while (true) {
            final TestVolatile test = new TestVolatile();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(10);
                        test.change();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }).start();


            for (int i = 0; i < 3; i++) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        test.print();
                    }
                }).start();
            }

        }
    }
}
