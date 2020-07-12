package io.frank.learn.springboot.config;

import io.frank.learn.springboot.config.color.Black;
import io.frank.learn.springboot.config.color.White;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author jinjunliang
 **/
public class ColorImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{Black.class.getName(), White.class.getName()};
    }
}
