package io.frank.learn.jdk14.flow;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author jinjunliang
 **/
public class Action {
    public static void main(String[] args) {
        ExecutorService executorService = ForkJoinPool.commonPool();

        try (DockerXDemoPublisher<Integer> publisher = new DockerXDemoPublisher<>(executorService)) {
            demoSubscribe(publisher, "One");
            demoSubscribe(publisher, "Two");
            demoSubscribe(publisher, "Three");
            IntStream.range(1, 5).forEach(publisher::submit);
        } finally {
            try {
                executorService.shutdown();
                int shutdownDelaySec = 1;
                System.out.println(String.format("..............等待%d秒后结束服务.........", shutdownDelaySec));
                executorService.awaitTermination(shutdownDelaySec, TimeUnit.SECONDS);
            } catch (Exception ex) {
                System.out.println(String.format("捕获到execService.awaitTermination()方法的异常: %s", ex.getMessage()));
            } finally {
                System.out.println("调用execService.shutdownNow() 结束服务....");
                List<Runnable> l = executorService.shutdownNow();
                System.out.println(String.format("还剩%d个任务等待执行，服务已经关闭", l.size()));
            }
        }
    }

    private static void demoSubscribe(DockerXDemoPublisher<Integer> publisher, String subscriberName) {
        DockerXDemoSubscriber<Integer> subscriber = new DockerXDemoSubscriber<>(subscriberName, 4L);
        publisher.subscribe(subscriber);
    }
}
