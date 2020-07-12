package io.frank.serviceribbon.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Package io.frank.serviceribbon.service
 * Description: ${todo}
 * author 016039
 * date 2019/3/2上午7:08
 */
@Service
public class HelloService {
  @Autowired
  RestTemplate restTemplate;

  // 失败时会去调用 hiError 方法
  @HystrixCommand(fallbackMethod = "hiError")
  public String hiService(String name) {
    return restTemplate.getForObject("http://SERVICE-HI/hi?name=" + name, String.class);
  }

  public String hiError(String name) {
    return "hi, " + name + ", sorry, error!";
  }
}
