package com.hust.hui.quicksilver.concurrent.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by yihui on 2017/11/10.
 */
public class BeanUtils {

    public static void copy(Object source, Object dest) throws Exception {
        Class destClz = dest.getClass();


        // 获取目标的所有成员
        Field[] destFields = destClz.getDeclaredFields();
        Object value;
        for (Field field : destFields) { // 遍历所有的成员，并赋值
            // 获取value值
            value = getVal(field.getName(), source);

            field.setAccessible(true);
            field.set(dest, value);
        }
    }


    private static Object getVal(String name, Object obj) throws Exception {
        try {
            // 优先获取obj中同名的成员变量
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return field.get(obj);
        } catch (NoSuchFieldException e) {
            // 表示没有同名的变量
        }

        // 获取对应的 getXxx() 或者 isXxx() 方法
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        String methodName = "get" + name;
        String methodName2 = "is" + name;
        Method[] methods = obj.getClass().getMethods();
        for (Method method : methods) {
            // 只获取无参的方法
            if (method.getParameterCount() > 0) {
                continue;
            }

            if (method.getName().equals(methodName)
                    || method.getName().equals(methodName2)) {
                return method.invoke(obj);
            }
        }

        return null;
    }
}
