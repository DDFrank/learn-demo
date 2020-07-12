package com.frank.concurrency.example.sync;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 016039
 * @Package com.frank.concurrency.example.sync
 * @Description: ${todo}
 * @date 2018/9/2下午4:41
 */
@Slf4j
public class SyncExample2 {

    // 修饰一个类
    public static void test1(int j) {
        synchronized (SyncExample2.class) {
            for(int i=0;i<10;i++) {
                log.info("test1 - {} - {}", j, i);
            }
        }
    }

    // 修饰一个静态方法
    public static synchronized  void test2(int j) {
        for(int i=0;i<10;i++) {
            log.info("test2 - {}- {}", j, i);
        }
    }

    public static void main(String[] args) {
        SyncExample2 example1 = new SyncExample2();
        SyncExample2 example2 = new SyncExample2();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> {
            example1.test1(1);
        });

        executorService.execute(() -> {
            example2.test1(2);
        });
        executorService.shutdown();
    }
}
