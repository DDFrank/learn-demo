package com.hust.hui.silver.hystrix;

import com.netflix.hystrix.*;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

/**
 * Created by yihui on 2018/3/14.
 */
public class HystrixConfigTest extends HystrixCommand<String> {

    private final String name;

    public HystrixConfigTest(String name, boolean ans) {
//        注意的是同一个任务，
        super(Setter.withGroupKey(
//                CommandGroup是每个命令最少配置的必选参数，在不指定ThreadPoolKey的情况下，字面值用于对不同依赖的线程池/信号区分
                HystrixCommandGroupKey.Factory.asKey("CircuitBreakerTestGroup"))
//                每个CommandKey代表一个依赖抽象,相同的依赖要使用相同的CommandKey名称。依赖隔离的根本就是对相同CommandKey的依赖做隔离.
                        .andCommandKey(HystrixCommandKey.Factory.asKey("CircuitBreakerTestKey_" + ans))
//                当对同一业务依赖做隔离时使用CommandGroup做区分,但是对同一依赖的不同远程调用如(一个是redis 一个是http),可以使用HystrixThreadPoolKey做隔离区分
                        .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("CircuitBreakerTest_" + ans))
                        .andThreadPoolPropertiesDefaults(    // 配置线程池
                                HystrixThreadPoolProperties.Setter()
                                        .withCoreSize(12)    // 配置线程池里的线程数，设置足够多线程，以防未熔断却打满threadpool
                        )
                        .andCommandPropertiesDefaults(    // 配置熔断器
                                HystrixCommandProperties.Setter()
                                        .withCircuitBreakerEnabled(true)
                                        .withCircuitBreakerRequestVolumeThreshold(3)
                                        .withCircuitBreakerErrorThresholdPercentage(80)
//                		.withCircuitBreakerForceOpen(true)	// 置为true时，所有请求都将被拒绝，直接到fallback
//                		.withCircuitBreakerForceClosed(true)	// 置为true时，将忽略错误
//                                        .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)    // 信号量隔离
                                        .withExecutionIsolationSemaphoreMaxConcurrentRequests(20)
                                        .withExecutionTimeoutEnabled(true)
                                        .withExecutionTimeoutInMilliseconds(200)
                                .withCircuitBreakerSleepWindowInMilliseconds(1000) //熔断器打开到关闭的时间窗长度
//                		.withExecutionTimeoutInMilliseconds(5000)
                        )
        );
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        System.out.println("running run():" + name + " thread: " + Thread.currentThread().getName());
        int num = Integer.valueOf(name);
        if (num % 2 == 0 && num < 10) {    // 直接返回
            return name;
        } else if (num < 40) {
            Thread.sleep(300);
            return "sleep+"+ name;
        } else {    // 无限循环模拟超时
            return name;
        }
//		return name;
    }
//
//    @Override
//    protected String getFallback() {
//        Throwable t = this.getExecutionException();
//        if(t instanceof HystrixRuntimeException) {
//            System.out.println(Thread.currentThread() + " --> " + ((HystrixRuntimeException) t).getFailureType());
//        } else if (t instanceof HystrixTimeoutException) {
//            System.out.println(t.getCause());
//        } else {
//            t.printStackTrace();
//        }
//        System.out.println(Thread.currentThread() + " --> ----------over------------");
//        return "CircuitBreaker fallback: " + name;
//    }

    public static class UnitTest {

        @Test
        public void testSynchronous() throws IOException, InterruptedException {
            for (int i = 0; i < 50; i++) {
                if (i == 41) {
                    Thread.sleep(2000);
                }
                try {
                    System.out.println("===========" + new HystrixConfigTest(String.valueOf(i), i % 2 == 0).execute());
//	        		try {
//	            		TimeUnit.MILLISECONDS.sleep(1000);
//	            	}catch(Exception e) {}
//	        		Future<String> future = new HystrixCommand4CircuitBreakerTest("Hlx"+i).queue();
//	        		System.out.println("===========" + future);
                } catch (HystrixRuntimeException e) {
                    System.out.println(i + " : " + e.getFailureType() + " >>>> " + e.getCause() + " <<<<<");
                } catch (Exception e) {
                    System.out.println("run()抛出HystrixBadRequestException时，被捕获到这里" + e.getCause());
                }
            }

            System.out.println("------开始打印现有线程---------");
            Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
            for (Thread thread : map.keySet()) {
                System.out.println("--->name-->" + thread.getName());
            }
            System.out.println("thread num: " + map.size());

            System.in.read();
        }
    }
}
