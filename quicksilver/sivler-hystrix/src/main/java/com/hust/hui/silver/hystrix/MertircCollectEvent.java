package com.hust.hui.silver.hystrix;

import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixEventType;
import com.netflix.hystrix.strategy.eventnotifier.HystrixEventNotifier;
import org.junit.Test;

import java.util.List;
import java.util.function.Function;

/**
 * Created by yihui on 2018/3/14.
 */
public class MertircCollectEvent extends HystrixEventNotifier {

    public MertircCollectEvent() {
    }

    public void markEvent(HystrixEventType eventType, HystrixCommandKey key) {
        System.out.println("get collect event!: eventType: " + eventType + " cmdKey: " + key);
    }

    public void markCommandExecution(HystrixCommandKey key, HystrixCommandProperties.ExecutionIsolationStrategy isolationStrategy, int duration, List<HystrixEventType> eventsDuringExecution) {
        System.out.println("markCommandExecution#get collect event!: strategy: " + isolationStrategy + " cmdKey: " + key + " dration: " + duration + " type: " + eventsDuringExecution);
    }


    public static  class InnerTest {

        @Test
        public void test() {
            Function<Integer, String> func = new Function<Integer, String>() {
                @Override
                public String apply(Integer integer) {
                    return null;
                }
            };
        }
    }

}
