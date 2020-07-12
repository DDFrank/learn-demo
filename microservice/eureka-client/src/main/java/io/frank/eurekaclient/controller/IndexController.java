package io.frank.eurekaclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Package io.frank.eurekaclient.controller
 * Description: ${todo}
 * author 016039
 * date 2019/2/24上午10:19
 */
@RestController
public class IndexController {
  @Value("${server.port}")
  String port;

  @GetMapping("/hi")
  public String home(@RequestParam(value = "name", defaultValue = "force") String name) {
    return "hi " + name + ", i am from port:" + port;
  }
}
