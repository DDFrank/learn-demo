package com.hust.hui.quicksilver.concurrent.schedule;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yihui on 2017/12/12.
 */
public class ScheduleQADemo {


    public static void main(String[] args) {

        AtomicInteger ato = new AtomicInteger(1);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("count: " + ato.getAndAdd(1) + " now: " + System.currentTimeMillis());
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("------over--------- now: " + System.currentTimeMillis());
            }
        };


        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 1000, 1000);
        System.out.println("--------------end-------------");
    }


}
