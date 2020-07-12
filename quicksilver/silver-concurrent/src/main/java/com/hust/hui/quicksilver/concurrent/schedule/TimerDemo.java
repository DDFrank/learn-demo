package com.hust.hui.quicksilver.concurrent.schedule;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yihui on 2017/11/21.
 */
public class TimerDemo {

    static class Task extends TimerTask {
        @Override
        public void run() {
            System.out.println("开始执行任务");

            // 执行完毕，等待到6点发送报警
            int min = Calendar.getInstance().get(Calendar.MINUTE);
            int sec = Calendar.getInstance().get(Calendar.SECOND);
            long delayTime = ((59 - min) * 60 + 60 - sec) * 1000L;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("报警");
                }
            }, delayTime);
        }
    }

    public static void main(String[] args) {
        Date date = new Date();
        if (date.getHours() == 5 && date.getMinutes() > 15 || date.getHours() > 5) {
            date.setHours(5);
        } else {
            date.setMinutes(15);
        }
        date.setSeconds(0);

        new Timer().scheduleAtFixedRate(new Task(), date, 24 * 2600 * 1000L);
    }

//
//
//    public static void main(String[] args) throws InterruptedException {
//        System.out.println("now: " + System.currentTimeMillis());
//        new Timer("schedule").schedule(new TimerTask() {
//            @Override
//            public void run() {
//                System.out.println("timerTask: " + System.currentTimeMillis());
//            }
//        }, 100);
//        System.out.println("over: " + System.currentTimeMillis());
//        Thread.sleep(3000);
//
//
//        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
//        executorService.schedule(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("task: " + System.currentTimeMillis());
//            }
//        }, 100, TimeUnit.MILLISECONDS);
//
//
//        executorService.scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("task: " + System.currentTimeMillis());
//            }
//        }, 100, 100, TimeUnit.MILLISECONDS);
//    }

}
