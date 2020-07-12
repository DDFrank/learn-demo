package io.frank.servicefeign.client;

import io.frank.servicefeign.client.hystrix.SchedualServiceHiHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Package io.frank.servicefeign.client
 * Description: ${todo}
 * author 016039
 * date 2019/3/2上午7:20
 */
// 这里写服务名, 指定降级服务
@FeignClient(value = "service-hi", fallback = SchedualServiceHiHystrix.class)
public interface ScheduleServiceHi {
  /*
  * 这里的参数注解是必要的，不然会当做 post 解析
  * */
  @GetMapping(value = "/hi")
  String sayHiFromClientOne(@RequestParam(value = "name") String name);
}
