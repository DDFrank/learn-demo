package io.frank.serviceribbon.controller;

import io.frank.serviceribbon.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Package io.frank.serviceribbon.controller
 * Description: ${todo}
 * author 016039
 * date 2019/3/2上午7:10
 */
@RestController
public class HelloController {
  @Autowired
  HelloService helloService;

  @GetMapping(value = "/hi")
  public String hi(String name) {
    return helloService.hiService(name);
  }
}
