package io.frank.springtest.config;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;

/**
 * @author jinjunliang
 **/
@Configuration
@Getter
public class CustomConfig {
    private String config1 = "1";
    private String config2 = "2";
}
