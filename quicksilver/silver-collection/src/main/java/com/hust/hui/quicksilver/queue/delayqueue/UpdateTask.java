package com.hust.hui.quicksilver.queue.delayqueue;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Created by yihui on 2017/10/22.
 */
@Data
@AllArgsConstructor
public class UpdateTask implements Delayed {

    private int itemId;

    private long delayTime;


    @Override
    public long getDelay(TimeUnit unit) {
        return delayTime - System.currentTimeMillis();
    }

    @Override
    public int compareTo(Delayed o) {
        return (int) (getDelay(TimeUnit.MICROSECONDS) - o.getDelay(TimeUnit.MICROSECONDS));
    }
}
