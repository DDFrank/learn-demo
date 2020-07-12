package com.hust.hui.quicksilver.alarm.test.execute;

import com.hust.hui.quicksilver.alarm.execut.spi.LogExecute;

import java.util.List;

/**
 * Created by yihui on 2018/2/7.
 */
public class EmailExecute extends LogExecute {
    @Override
    public void sendMsg(List<String> users, String title, String msg) {
        super.sendMsg(users, title, msg);
    }

}
