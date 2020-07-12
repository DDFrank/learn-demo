package com.frank.concurrency.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 016039
 * @Package com.frank.concurrency.annotations
 * @Description: 不推荐的写法
 * @date 2018/9/2下午3:18
 */
@Target(ElementType.TYPE)
// 编辑期存在就可以了
@Retention(RetentionPolicy.SOURCE)
public @interface NotRecommand {
    String value() default "";
}
