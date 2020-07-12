package io.frank.learn.springboot.web.servlet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 使用 ServletRegistrationBean 来注册servlet
 *
 * @author jinjunliang
 **/
@Configuration
public class ServletConfig {
    @Bean
    public DemoServlet.DemoServletRegistryBean demoServletRegistryBean() {
        return new DemoServlet.DemoServletRegistryBean(new DemoServlet(), "/demo/servlet");
    }

}
