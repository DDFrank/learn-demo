package io.frank.servicefeign.controller;

import io.frank.servicefeign.client.ScheduleServiceHi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Package io.frank.servicefeign.controller
 * Description: ${todo}
 * author 016039
 * date 2019/3/2上午7:21
 */
@RestController
public class HiController {
  @Autowired
  private ScheduleServiceHi scheduleServiceHi;

  @GetMapping(value = "/hi")
  public String sayHi(@RequestParam String name) {
    return scheduleServiceHi.sayHiFromClientOne(name);
  }
}
