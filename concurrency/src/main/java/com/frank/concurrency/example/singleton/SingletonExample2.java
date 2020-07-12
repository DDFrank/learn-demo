package com.frank.concurrency.example.singleton;

import com.frank.concurrency.annotations.NotThreadSafe;
import com.frank.concurrency.annotations.ThreadSafe;

/**
 * @author 016039
 * @Package com.frank.concurrency.example.singleton
 * @Description: ${todo}
 * @date 2018/9/3下午7:51
 */
/*
* 饿汉模式
* 单例实例在类装载的时候创建
* 假如类初始化的过程特别长，那么会影响性能
* 假如该类并未被使用，那么会造成资源浪费
* */
@ThreadSafe
public class SingletonExample2 {
    // 私有构造器
    private SingletonExample2(){}
    // 单例对象
    private static SingletonExample2 instance = new SingletonExample2();

    // 通过静态的工厂方法, 线程安全的
    public static SingletonExample2 getInstance() {
        return instance;
    }
}
