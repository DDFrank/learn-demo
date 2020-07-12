package io.frank.learn.springboot.config.beanpostprocessor;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author jinjunliang
 **/
@Component
public class Cat implements InitializingBean {

    public Cat() {
        System.out.println("Cat constructor run...");
    }

    @PostConstruct
    public void afterInit() {
        System.out.println("Cat PostConstruct run...");
    }


    @Override
    public void afterPropertiesSet() {
        System.out.println("Cat afterPropertiesSet run...");
    }
}
