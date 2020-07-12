package com.frank.controller;

import com.frank.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 016039
 * @Package com.frank.controller
 * @Description: ${todo}
 * @date 2018/11/3上午8:02
 */
@RestController
public class ApiController {

    @Autowired
    private ApiService apiService;

    @RequestMapping("index")
    public String index() {
        return apiService.index();
    }
}
