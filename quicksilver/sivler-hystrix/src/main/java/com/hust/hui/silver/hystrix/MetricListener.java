package com.hust.hui.silver.hystrix;

import com.netflix.hystrix.*;
import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.metrics.*;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.function.Function;

/**
 * Created by yihui on 2018/3/14.
 */
public class MetricListener {

    public MetricListener() {
//        HystrixPlugins.getInstance().registerEventNotifier(new MertircCollectEvent());
//        HystrixPlugins.getInstance().registerMetricsPublisher(HystrixMetricsPublisherDefault.getInstance());
        HystrixPlugins.getInstance().registerMetricsPublisher(new MyHystrixMetricsPublisher());
    }

    public class MyHystrixMetricsPublisher extends HystrixMetricsPublisher {
        public MyHystrixMetricsPublisher() {
        }

        public HystrixMetricsPublisherCommand getMetricsPublisherForCommand(HystrixCommandKey commandKey,
                                                                            HystrixCommandGroupKey commandGroupKey,
                                                                            HystrixCommandMetrics metrics,
                                                                            HystrixCircuitBreaker circuitBreaker,
                                                                            HystrixCommandProperties properties) {

//            System.out.println("groupKey: " + commandGroupKey + " cmdKey: " + commandKey + " metrics: " + metrics + " breaker: " + circuitBreaker + " properties: " + properties);
            return new HystrixMetricsPublisherCommandDefault(commandKey, commandGroupKey, metrics, circuitBreaker, properties);
        }

        public HystrixMetricsPublisherThreadPool getMetricsPublisherForThreadPool(HystrixThreadPoolKey threadPoolKey, HystrixThreadPoolMetrics metrics, HystrixThreadPoolProperties properties) {
            return new HystrixMetricsPublisherThreadPoolDefault(threadPoolKey, metrics, properties);
        }

        public HystrixMetricsPublisherCollapser getMetricsPublisherForCollapser(HystrixCollapserKey collapserKey, HystrixCollapserMetrics metrics, HystrixCollapserProperties properties) {
            return new HystrixMetricsPublisherCollapserDefault(collapserKey, metrics, properties);
        }
    }


    public static final class InnerTest {
        public String execute(int num) throws Throwable {
            if (num % 2 == 0) {
                throw new IllegalArgumentException("canshu yicang!");
            }

            if (num % 3 == 0) {
                throw new FileNotFoundException("测试编译异常!");
            }

            return "success";
        }


        @Test
        public void testExec() throws InterruptedException {
            Function<Integer, String> func = (n) -> {
                try {
                    return execute(n);
                } catch (Throwable e) {
                    if (e instanceof RuntimeException) {
                        throw (RuntimeException) e;
                    } else {
                        throw new RuntimeException(e);
                    }
                }
            };


            System.out.println(func.apply(11));

            try {
                System.out.println(func.apply(12));
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("----------------------");
            Thread.sleep(1000);

            try {
                System.out.println(func.apply(9));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
