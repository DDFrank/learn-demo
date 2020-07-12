package com.hust.hui.quicksilver.spi.api;

import com.hust.hui.quicksilver.spi.selector.DefaultSelector;
import com.hust.hui.quicksilver.spi.selector.api.ISelector;

import java.lang.annotation.*;

/**
 * SPI 自适应注解, 表示该类or该方法会用到spi实现
 * <p/>
 * Created by yihui on 2017/5/24.
 *
 * 用在SPI类中的某个具体方法上，通常在接口的SPI实现类和该方法的实现类不同时使用
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface SpiAdaptive {
    Class<? extends ISelector> selector() default DefaultSelector.class;
}
