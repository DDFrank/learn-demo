package com.hust.hui.quicksilver.concurrent.synchronize;

import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yihui on 2017/11/10.
 */
public class RefectTest {

    private static String s1 = "hello";

    private static int s2 = 100;

    private int s3 = 200;

    private boolean ans;

    protected RefectTest next;

    public RefectTest() {
    }

    public RefectTest(int s3, boolean ans, RefectTest next) {
        this.s3 = s3;
        this.ans = ans;
        this.next = next;
    }


    public static <T> T getInstance(Class<T> clz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor con = clz.getConstructor(int.class, boolean.class, RefectTest.class);
        
        return (T) con.newInstance(10, true, new RefectTest());
    }

    /**
     * 获取class类的成员变量及其值
     * @param clz
     * @return
     */
    public static Map<String, Object> getFieldValues(Class clz) throws IllegalAccessException, InstantiationException {
        Map<String, Object> map = new HashMap<>();

        Method[] methods = clz.getDeclaredMethods();

        Field[] fields = clz.getDeclaredFields();
        if (fields == null || fields.length == 0) {
            return map;
        }

        Object instance = clz.newInstance();

        String name;
        Object value;
        for (Field f: fields) {
            name = f.getName();

            f.setAccessible(true);
            if (Modifier.isStatic(f.getModifiers())) {
                // 静态变量，直接获取即可
                value = f.get(null);
            } else {
                // 非静态变量，需要传入一个实例对象
                value = f.get(instance);
            }

            map.put(name, value);
        }

        return map;
    }


    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        Map<String, Object> ans = getFieldValues(RefectTest.class);
        System.out.println(ans);
    }
}
