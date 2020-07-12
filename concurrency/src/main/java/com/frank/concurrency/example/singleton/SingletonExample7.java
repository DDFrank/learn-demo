package com.frank.concurrency.example.singleton;

import com.frank.concurrency.annotations.Recommand;
import com.frank.concurrency.annotations.ThreadSafe;

/**
 * @author 016039
 * @Package com.frank.concurrency.example.singleton
 * @Description: ${todo}
 * @date 2018/9/3下午7:51
 */
/*
* 枚举模式
* 最安全的
* */
@ThreadSafe
@Recommand
public class SingletonExample7 {
    // 私有构造器
    private SingletonExample7(){}

    // 通过静态的工厂方法, 线程安全的
    public static SingletonExample7 getInstance() {
        return Singleton.INSTANCE.getInstance();
    }

    private enum Singleton {
        INSTANCE;

        private SingletonExample7 singletonExample7;

        // JVM保证该方法绝对只调用一次
        Singleton() {
            singletonExample7 = new SingletonExample7();
        }

        public SingletonExample7 getInstance() {
            return singletonExample7;
        }
    }
}
