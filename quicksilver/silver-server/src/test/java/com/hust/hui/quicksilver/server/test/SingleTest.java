package com.hust.hui.quicksilver.server.test;

/**
 * Created by yihui on 2017/4/20.
 */
public class SingleTest {
    private static class InnerClass {
        private static SingleTest instance = new SingleTest();
    }


    private SingleTest() {}

    public static SingleTest getInstance() {
        return InnerClass.instance;
    }
}
