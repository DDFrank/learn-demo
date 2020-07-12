package com.frank.netty.im.demo.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Package com.frank.netty.im.demo.future
 * Description: Future 的例子
 * author 016039
 * date 2018/11/19下午8:40
 */
public class FutureExample {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Runnable task1 = () -> System.out.println("i am task1");

        Callable<Integer> task2 = () -> 100;
        Future<?> f1 = executorService.submit(task1);
        Future<Integer> f2 = executorService.submit(task2);
        System.out.println("task1 is completed? " + f1.isDone());
        System.out.println("task2 is completed? " + f2.isDone());
        while (f1.isDone()) {
            System.out.println("task1 completed.");
            break;
        }
        while (f2.isDone()) {
            System.out.println("task2 completed.");
            break;
        }
    }
}
