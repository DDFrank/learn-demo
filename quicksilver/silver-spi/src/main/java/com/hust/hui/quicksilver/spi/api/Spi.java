package com.hust.hui.quicksilver.spi.api;

import com.hust.hui.quicksilver.spi.selector.DefaultSelector;
import com.hust.hui.quicksilver.spi.selector.api.ISelector;

import java.lang.annotation.*;

/**
 * Created by yihui on 2017/5/22.
 * 所有的SPI接口都必须有的注解,增强可读性
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Spi {
    // 指定默认的SPI实现类
    Class<? extends ISelector> selector() default DefaultSelector.class;
}
