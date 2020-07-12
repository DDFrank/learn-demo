package io.frank.learn.springboot.config;

import io.frank.learn.springboot.config.color.Blue;
import io.frank.learn.springboot.config.color.Green;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author jinjunliang
 **/
public class ColorImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        registry.registerBeanDefinition("blue", new RootBeanDefinition(Blue.class));
        registry.registerBeanDefinition("green", new RootBeanDefinition(Green.class));
    }
}
