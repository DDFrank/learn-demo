package io.frank.servicefeign.client.hystrix;

import io.frank.servicefeign.client.ScheduleServiceHi;
import org.springframework.stereotype.Component;

/**
 * Package io.frank.servicefeign.client.hystrix
 * Description: ${todo}
 * author 016039
 * date 2019/3/2上午7:43
 */
@Component
public class SchedualServiceHiHystrix implements ScheduleServiceHi {
  @Override
  public String sayHiFromClientOne(String name) {
    return "sorry " + name;
  }
}
