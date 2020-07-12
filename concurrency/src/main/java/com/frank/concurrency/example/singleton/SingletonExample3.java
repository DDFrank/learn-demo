package com.frank.concurrency.example.singleton;

import com.frank.concurrency.annotations.NotRecommand;
import com.frank.concurrency.annotations.ThreadSafe;

/**
 * @author 016039
 * @Package com.frank.concurrency.example.singleton
 * @Description: ${todo}
 * @date 2018/9/3下午7:51
 */
/*
* 懒汉模式
* 单例实例在第一次使用的时候创建
* 带来了额外的性能开销
* */
@ThreadSafe
@NotRecommand
public class SingletonExample3 {
    // 私有构造器
    private SingletonExample3(){}
    // 单例对象
    private static SingletonExample3 instance = null;

    // 通过静态的工厂方法,线程安全了
    public static synchronized SingletonExample3 getInstance() {
        if(instance == null) {
            instance = new SingletonExample3();
        }
        return instance;
    }
}
