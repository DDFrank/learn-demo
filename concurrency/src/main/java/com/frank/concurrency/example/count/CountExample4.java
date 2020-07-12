package com.frank.concurrency.example.count;

import com.frank.concurrency.annotations.NotThreadSafe;
import com.frank.concurrency.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author 016039
 * @Package com.frank.concurrency
 * @Description: ${todo}
 * @date 2018/9/2下午3:27
 */
@Slf4j
@NotThreadSafe
public class CountExample4 {
    // 请求总数
    public static int clientTotal = 5000;
    // 同时并发执行的线程数
    public static int threadTotal = 200;

    // volatile没有原子性，不适合计数
    // 比较适合标记状态 true or false
    public static volatile int  count = 0;

    public static void main(String[] args) throws Exception{
        // 创建一个线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for(int i=0;i<clientTotal;i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                }catch (Exception e){
                    log.error("exception", e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        log.info("count:{}", count);
    }

    private static void add(){
        /*
        * 1 取出count
        * 2 +1
        * 3 写回主存
        * 同时加1 然后返回，可能会丢掉一次 +1 的操作
        * */
        count++;
    }
}
