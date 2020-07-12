package com.frank.service;

import com.frank.error.ApiServiceError;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author 016039
 * @Package com.frank.service
 * @Description: ${todo}
 * @date 2018/10/31下午9:16
 */
/*
* fallback 服务降级
* 当当前服务不可用时，会切换到另一个返回
* */
@FeignClient(value = "eurekaClient", fallback = ApiServiceError.class)
public interface ApiService {

    @GetMapping("/index")
    String index();
}
