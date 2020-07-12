package com.hust.hui.quicksilver.concurrent.reflect.con;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yihui on 2017/12/15.
 */
@Data
public class BaseDemo {

    private List<BaseCell> list = new ArrayList<>();


    public BaseDemo deepClone() {
        BaseDemo baseDemo = new BaseDemo();

        List<BaseCell> copy = new ArrayList<>();
        for (BaseCell cell: list) {
            copy.add(new BaseCell(cell.getNum(), cell.getText()));
        }

        baseDemo.setList(copy);
        return baseDemo;
    }

}
