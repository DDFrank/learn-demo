package com.hust.hui.quicksilver.concurrent.schedule;

import java.util.Calendar;

/**
 * Created by yihui on 2017/11/21.
 */
public class SleepDemo {

    static class Task implements Runnable {

        @Override
        public void run() {
            while (true) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int min = calendar.get(Calendar.MINUTE);
                int sleepHour, sleepMin;

                // 计算sleep的小时数
                if (hour < 5) {
                    sleepHour = 5 - hour;
                } else if (hour == 5) {
                    sleepHour = 0;
                } else {
                    sleepHour = 24 + 5 - hour;
                }

                // 计算sleep的分钟数
                sleepMin = 15 - min;


                long sleepTime = ((sleepHour * 60) + sleepMin) * 60 * 1000L;
                try {
                    Thread.sleep(sleepTime);

                    // 开始校验数据是否存在

                    System.out.println("数据校验");

                    // 等待到6点，开始报警
                    min = calendar.get(Calendar.MINUTE);
                    int second = calendar.get(Calendar.SECOND);
                    sleepTime = ((59 - min) * 60 + 60 - second) * 1000L;
                    Thread.sleep(sleepTime);


                    System.out.println("开始报警");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void main(String[] args) {

        new Thread(new Task(),"sleepDemo").start();
        // .....
    }

}
