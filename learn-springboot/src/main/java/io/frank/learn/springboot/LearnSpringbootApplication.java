package io.frank.learn.springboot;

import io.frank.learn.springboot.config.initializer.ApplicationContextInitializerDemo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class LearnSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(LearnSpringbootApplication.class);
		// 添加容器初始化前的逻辑
		// 也可以在 application.properties 中配置
		// 也可以在 spring.factories 中配置
		springApplication.addInitializers(new ApplicationContextInitializerDemo());
		springApplication.run(args);
	}

}
