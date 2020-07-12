package com.frank.concurrency.example.atomic;

import com.frank.concurrency.annotations.ThreadSafe;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author 016039
 * @Package com.frank.concurrency.example.atomic
 * @Description: ${todo}
 * @date 2018/9/2下午4:06
 */
@Slf4j
@ThreadSafe
public class AtomicExample5 {
    // 原子的更新某个类的字段
    private static AtomicIntegerFieldUpdater<AtomicExample5> updater = AtomicIntegerFieldUpdater.newUpdater(AtomicExample5.class, "count");

    // 必须是 volatile 修饰而且不能是 static修饰的
    @Getter
    public volatile int count = 100;

    private static AtomicExample5 example5 = new AtomicExample5();

    public static void main(String[] args) {
        // 如果这个字段是100的话就更新为120
        if(updater.compareAndSet(example5, 100, 120)) {
            log.info("update success 1, {}",example5.getCount());
        }

        if(updater.compareAndSet(example5, 100, 120)) {
            log.info("update success 2, {}",example5.getCount());
        }else{
            log.info("update failed 2, {}",example5.getCount());
        }
    }
}
