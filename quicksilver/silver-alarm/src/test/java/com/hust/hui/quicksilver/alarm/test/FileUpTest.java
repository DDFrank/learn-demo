package com.hust.hui.quicksilver.alarm.test;

import org.junit.Test;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 测试文件变更
 *
 * Created by yihui on 2017/4/28.
 */
public class FileUpTest {

    private long lastTime;


    @Test
    public void testFileUpdate() {
        File file = new File("/tmp/alarmConfig");

        lastTime = file.lastModified();

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (file.lastModified() > lastTime) {
                    System.out.println("file update! time : " + file.lastModified());
                    lastTime = file.lastModified();
                }
            }
        },0, 1, TimeUnit.SECONDS);


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
