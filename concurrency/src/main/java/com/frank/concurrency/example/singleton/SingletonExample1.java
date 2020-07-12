package com.frank.concurrency.example.singleton;

import com.frank.concurrency.annotations.NotThreadSafe;

/**
 * @author 016039
 * @Package com.frank.concurrency.example.singleton
 * @Description: ${todo}
 * @date 2018/9/3下午7:51
 */
/*
* 懒汉模式
* 单例实例在第一次使用的时候创建
* */
@NotThreadSafe
public class SingletonExample1 {
    // 私有构造器
    private SingletonExample1(){}
    // 单例对象
    private static SingletonExample1 instance = null;

    // 通过静态的工厂方法
    public static SingletonExample1 getInstance() {
        if(instance == null) {
            // 多线程环境下，可能会调用两次构造器
            instance = new SingletonExample1();
        }
        return instance;
    }
}
