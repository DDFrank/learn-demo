package com.frank.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 016039
 * @Package com.frank.controller
 * @Description: ${todo}
 * @date 2018/10/29下午6:57
 */
@RestController
public class HelloController {
    @GetMapping("index")
    public String index() {
        return "Hello World!";
    }
}
