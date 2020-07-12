package com.frank.concurrency.example.atomic;

import com.frank.concurrency.annotations.ThreadSafe;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author 016039
 * @Package com.frank.concurrency.example.atomic
 * @Description: ${todo}
 * @date 2018/9/2下午4:06
 */
@Slf4j
@ThreadSafe
public class AtomicExample6 {
    // 原子的更新某个类的字段
    private static AtomicBoolean isHappened= new AtomicBoolean();

    // 请求总数
    public static int clientTotal = 5000;
    // 同时并发执行的线程数
    public static int threadTotal = 200;

    //
    public static LongAdder count = new LongAdder();

    public static void main(String[] args) throws Exception{
        // 创建一个线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for(int i=0;i<clientTotal;i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    test();
                    semaphore.release();
                }catch (Exception e){
                    log.error("exception", e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        log.info("isHappened", count);
    }

    private static void test(){
        if(isHappened.compareAndSet(false, true)) {
            log.info("execute");
        }
    }
}
