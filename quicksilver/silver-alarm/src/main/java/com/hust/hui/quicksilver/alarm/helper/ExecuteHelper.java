package com.hust.hui.quicksilver.alarm.helper;

import com.hust.hui.quicksilver.alarm.execut.SimpleExecuteFactory;
import com.hust.hui.quicksilver.alarm.execut.api.IExecute;
import com.hust.hui.quicksilver.alarm.execut.spi.NoneExecute;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

/**
 * Created by yihui on 2017/5/12.
 */
@Getter
@Setter
public class ExecuteHelper {

    public static ExecuteHelper DEFAULT_EXECUTE = new ExecuteHelper(SimpleExecuteFactory.getExecute(NoneExecute.NAME), Collections.emptyList());

    private IExecute iExecute;

    private List<String> users;

    public ExecuteHelper(IExecute iExecute, List<String> users) {
        this.iExecute = iExecute;
        this.users = users;
    }


}
