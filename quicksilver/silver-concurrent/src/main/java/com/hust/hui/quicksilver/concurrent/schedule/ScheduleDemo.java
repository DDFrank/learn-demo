package com.hust.hui.quicksilver.concurrent.schedule;

import java.util.Calendar;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by yihui on 2017/11/21.
 */
public class ScheduleDemo {

    static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);

    static class Task extends TimerTask {
        @Override
        public void run() {
            System.out.println("开始执行任务");

            // 执行完毕，等待到6点发送报警
            int min = Calendar.getInstance().get(Calendar.MINUTE);
            int sec = Calendar.getInstance().get(Calendar.SECOND);
            long delayTime = (59 - min) * 60 + 60 - sec;
            executorService.schedule(() -> System.out.println("报警"), delayTime, TimeUnit.SECONDS);
        }
    }

    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        // 计算sleep的小时数
        int sleepHour = 5 - calendar.get(Calendar.HOUR_OF_DAY);
        if(sleepHour < 0) { // 算下一天
            sleepHour = 24 + sleepHour;
        }

        // 计算sleep的分钟数
        int sleepMin = 15 - calendar.get(Calendar.MINUTE);
        long sleepTime = ((sleepHour * 60) + sleepMin) * 60L;

        executorService.scheduleAtFixedRate(new Task(), sleepTime, 24 * 3600, TimeUnit.SECONDS);
    }
}
