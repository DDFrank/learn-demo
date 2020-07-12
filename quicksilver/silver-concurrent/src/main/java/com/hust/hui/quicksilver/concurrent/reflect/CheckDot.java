package com.hust.hui.quicksilver.concurrent.reflect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yihui on 2017/11/10.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CheckDot {
    // 校验规则
    Class<? extends ICheckRule> check() default DefaultCheckRule.class;
}
