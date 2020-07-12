package com.frank.concurrency.example.singleton;

import com.frank.concurrency.annotations.NotRecommand;
import com.frank.concurrency.annotations.NotThreadSafe;
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
* 双重同步锁模式
* */
@NotThreadSafe
public class SingletonExample4 {
    // 私有构造器
    private SingletonExample4(){}
    /*
    * 1 memory = allocate() 分配对象的内存空间
    * 2 ctorInstance() 初始化对象
    * 3 instance = memory 设置 instance 指向刚分配的内存
    *
    * 假如 jvm和cpu优化，发生了指令重排
    *
    * 1 memory = allocate() 分配对象的内存空间
    * 3 instance = memory 设置 instance 指向刚分配的内存
    * 2 ctorInstance() 初始化对象
    *
    * */
    // 单例对象
    private static SingletonExample4 instance = null;

    // 通过静态的工厂方法,线程安全了
    public static SingletonExample4 getInstance() {
        /*
        *
        * 双重检测机制
        * */
        if(instance == null) {
            synchronized (SingletonExample4.class) {
                if(instance == null) {
                    instance = new SingletonExample4();
                }
            }

        }
        return instance;
    }
}
