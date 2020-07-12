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
public class SyncExample1 {

    // 修饰一个代码块
    public void test1(int j) {
        synchronized (this) {
            for(int i=0;i<10;i++) {
                log.info("test1 - {} - {}", j, i);
            }
        }
    }

    // 修饰一个方法
    public synchronized  void test2(int j) {
        for(int i=0;i<10;i++) {
            log.info("test2 - {}- {}", j, i);
        }
    }

    public static void main(String[] args) {
        SyncExample1 example1 = new SyncExample1();
        SyncExample1 example2 = new SyncExample1();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> {
            example1.test2(1);
        });

        executorService.execute(() -> {
            example2.test2(2);
        });
    }
}
