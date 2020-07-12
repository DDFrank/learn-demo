package io.frank.learn.springboot.config.initializer;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author jinjunliang
 **/
public class ApplicationContextInitializerDemo implements ApplicationContextInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        // 执行容器刷新前的初始化逻辑
        System.out.println("ApplicationContextInitializerDemo#initialize run...");
    }
}
