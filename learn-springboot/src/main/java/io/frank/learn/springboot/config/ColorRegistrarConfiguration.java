package io.frank.learn.springboot.config;

import io.frank.learn.springboot.config.color.Yellow;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jinjunliang
 **/
@Configuration
public class ColorRegistrarConfiguration {
    @Bean
    public Yellow yellow() {
        return new Yellow();
    }
}
