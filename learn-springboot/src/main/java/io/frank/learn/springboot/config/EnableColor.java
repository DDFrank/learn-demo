package io.frank.learn.springboot.config;

import io.frank.learn.springboot.config.color.Red;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 使用模块装配
 *
 * @author jinjunliang
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
// 导入普通类, 各种配置类
@Import({Red.class, ColorRegistrarConfiguration.class, ColorImportSelector.class, ColorImportBeanDefinitionRegistrar.class})
public @interface EnableColor {
}
