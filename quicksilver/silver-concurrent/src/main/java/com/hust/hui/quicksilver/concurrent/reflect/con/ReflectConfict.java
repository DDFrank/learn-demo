package com.hust.hui.quicksilver.concurrent.reflect.con;


import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 反射的线程安全测试
 *
 * Created by yihui on 2017/12/15.
 */
public class ReflectConfict {

    private BaseDemo source;

    @Before
    public void init() {
        BaseDemo baseDemo = new BaseDemo();
        baseDemo.setList(Arrays.asList(
                new BaseCell(1, "text1"),
                new BaseCell(2, "text2"),
                new BaseCell(3, "text3")
        ));

        source = baseDemo;
    }


    Field field = null;
    {
        try {
            field = BaseDemo.class.getDeclaredField("list");
            field.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private List<BaseCell> getElement(BaseDemo demo)  {
        try {
            Thread.sleep(10);
            return (List<BaseCell>) field.get(demo);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }


    public void modify(BaseDemo baseDemo, Integer count) {
        List<BaseCell> list = getElement(baseDemo);

        int i = 1;
        for (BaseCell cell: list) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cell.setNum(1000 * i + count);
            cell.setText("modify_" + i + "_" + count);
            i++;
        }
    }


    @Test
    public void testConrrent() throws InterruptedException {

        AtomicInteger atomicInteger = new AtomicInteger(1);
        for(int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    BaseDemo demo = source.deepClone();
                    Integer count = new Integer(atomicInteger.getAndAdd(1));
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    modify(demo, count);
                    System.out.println(demo);
                }
            }).start();
        }

        Thread.sleep(1000 * 60);
//        BaseDemo demo = source.deepClone();
//        int count = atomicInteger.getAndAdd(1);
//        modify(demo, count);
//        System.out.println(demo);
//        System.out.println("------------");
    }




}
