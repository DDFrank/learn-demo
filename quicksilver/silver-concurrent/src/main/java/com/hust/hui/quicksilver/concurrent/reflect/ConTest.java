package com.hust.hui.quicksilver.concurrent.reflect;

import java.lang.reflect.Constructor;

/**
 * Created by yihui on 2017/11/10.
 */
public class ConTest {

    private int a,b;

    public ConTest(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public String toString() {
        return "ConTest{" + "a=" + a + ", b=" + b + '}';
    }

    public static void main(String[] args) throws Exception {
        Class clz = ConTest.class;
        Constructor constructor = clz.getConstructor(int.class, int.class);
        ConTest test = (ConTest) constructor.newInstance(10, 20);
        System.out.println(test.toString());
    }
}
