package com.frank.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 016039
 * @Package com.frank.controller
 * @Description:
 * @date 2018/10/30下午9:51
 */
@RestController
@RefreshScope
public class HelloController {
    @Value("${server.port}")
    private int port;

    @GetMapping("index")
    public String index() {
        return "Hello world!, 端口: " + port;
    }

}
