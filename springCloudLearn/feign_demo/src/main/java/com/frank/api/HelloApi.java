package com.frank.api;

import com.frank.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 016039
 * @Package com.frank.api
 * @Description: ${todo}
 * @date 2018/10/31下午9:48
 */
@RestController
public class HelloApi {

    @Autowired
    private ApiService apiService;

    @GetMapping("index")
    public String index() {
        return apiService.index();
    }
}
