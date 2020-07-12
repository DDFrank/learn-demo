package com.hust.hui.quicksilver.spi.api;

import java.lang.annotation.*;

/**
 *
 * Created by yihui on 2017/5/24.
 * 用于SPI实现类,存储一些匹配条件用于给选择器匹配
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface SpiConf {

    /**
     * 唯一标识 用于 DefaultSelector
     *
     * @return
     */
    String name() default "";


    /**
     * 参数过滤, 单独一个元素,表示参数必须包含; 用英文分号,左边为参数名,右边为参数值,表示参数的值必须是右边的
     * <p/>
     * 形如  {"a", "a:12", "b:TAG"}
     * 用于 ParamsSelector
     * @return
     */
    String[] params() default {};


    /**
     * 排序, 越小优先级越高
     * 当多个实现类都满足条件时，优先使用优先级高的
     * @return
     */
    int order() default -1;
}
