package com.hust.hui.silver.hystrix;

import com.netflix.hystrix.*;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by yihui on 2018/3/14.
 */
public class InvokeHystrixCommand extends HystrixCommand<String> {
    private String group;
    private String name;

    private String prefix;

    public InvokeHystrixCommand(String group, String name, int outtime) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(group))
                        .andCommandKey(HystrixCommandKey.Factory.asKey(name.substring(0, name.indexOf("#"))))
                        .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey(group + " -" + name.substring(0, name.indexOf("#")) + "-" + "pool"))
                        .andThreadPoolPropertiesDefaults(
                                HystrixThreadPoolProperties.Setter().withCoreSize(2)
                        ) //服务线程池数量
                        .andCommandPropertiesDefaults(
                                HystrixCommandProperties.Setter()
                                        .withExecutionTimeoutEnabled(true) // 是否允许超时
                                        .withExecutionTimeoutInMilliseconds(outtime)  // 超时时间，线程池方式，主动超时中断；信号量方式，执行完之后判断是否超时
                                        // 采用线程池方式
                                        .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)
//                                        .withExecutionIsolationSemaphoreMaxConcurrentRequests(1) // 信号量方式的请求上限
                                        // 开启熔断机制
                                        .withCircuitBreakerEnabled(true)
                                        // 10s内触发熔断的最小请求数
                                        .withCircuitBreakerRequestVolumeThreshold(2)
                                        // 异常数超过5%开启
                                        .withCircuitBreakerErrorThresholdPercentage(10)
                                        // 熔断器打开到关闭的时间长度
                                        .withCircuitBreakerSleepWindowInMilliseconds(1000)
//                                .withCircuitBreakerForceClosed(true)
//                                .withCircuitBreakerForceClosed(force) // 强制关闭
                        )
        );

        this.group = group;
        this.name = name;

        prefix = Thread.currentThread() + "_" + group + "_" + name + "_";
    }


    @Override
    protected String run() throws InterruptedException {
        int count = Integer.parseInt(name.substring(name.indexOf("#") + 1));
        if (count >= 6 && count < 10) {
//            System.out.println("----->执行了--->" + prefix + " now: " + System.currentTimeMillis());
            throw new HystrixBadRequestException("自定义!!! + " + prefix);
        } else if (count > 15) {
//            System.out.println("----->执行了--->" + prefix + " now: " + System.currentTimeMillis());
            Thread.sleep(100);
        } else {
//            System.out.println("----->执行了--->" + prefix + " now: " + System.currentTimeMillis());
        }

        return prefix + " success!";
    }

//
//    protected String getFallback() {
//        return prefix + " 降级!";
//    }


    public static class CmdUnit {

        @Before
        public void init() {
//            new MetricListener();
        }


        @Test
        public void testSer() throws InterruptedException, IOException {
            for (int i = 0; i < 20; i++) {
                int finalI = i;
                new Thread(() ->
                {
                    try {
                        String ans = new InvokeHystrixCommand("group", "key#" + finalI, finalI < 13 ? 400 : 50).execute();
                        System.out.println("ans: " + ans);
                    } catch (HystrixRuntimeException e) {
                        System.out.println(e.getFailureType() + "#" + finalI);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                ).start();

                if (i == 10) {
                    Thread.sleep(30);
                }
            }

            System.in.read();
//            Thread.sleep(10000);
        }


        @Test
        public void testCmd() throws InterruptedException {
            final boolean[] force = {false};
            for (int i = 0; i < 20; i++) {

                int finalI = i;
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
                if (finalI > 10) {
                    force[0] = true;
                }
                String ans = new InvokeHystrixCommand("group", "key#" + finalI, 400).execute();
                System.out.println(ans);

//                        if (finalI == 10) {
//                            try {
//                                Thread.sleep(11000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }).start();

            }

            Thread.sleep(50000);
        }
    }
}
