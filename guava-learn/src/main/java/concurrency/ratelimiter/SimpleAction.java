package concurrency.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author jinjunliang
 **/
public class SimpleAction {
    public static void main(String[] args) {
        // 限速为2秒一个
        RateLimiter rateLimiter = RateLimiter.create(2.0);
        // 执行任务的线程池
        ExecutorService es = Executors
                .newFixedThreadPool(1);
        final SimpleTimeRecord record = new SimpleTimeRecord(System.nanoTime());
        for (int i = 0; i < 20; i++) {
            // 限流器限流
            rateLimiter.acquire();
            // 提交任务异步执行
            final int count = i;
            es.execute(() -> {
                long cur = System.nanoTime();
                // 打印时间间隔：毫秒
                System.out.println(count + " : " + (cur - record.recordTime) / 100_0000);
                record.recordTime = cur;
            });
        }
        es.shutdown();
    }

    private static class SimpleTimeRecord {
        long recordTime;

        public SimpleTimeRecord(long recordTime) {
            this.recordTime = recordTime;
        }
    }
}
