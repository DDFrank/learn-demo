package io.frank.learn.springboot.config.customize;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.core.Ordered;

/**
 * 对 内嵌的 Tomcat 进行定制
 *
 * @author jinjunliang
 **/
public class WebMvcCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory>, Ordered {
    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        // 覆盖配置
        factory.setPort(9090);
        factory.setContextPath("/demo");
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
