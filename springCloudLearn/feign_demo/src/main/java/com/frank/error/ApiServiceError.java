package com.frank.error;

import com.frank.service.ApiService;
import org.springframework.stereotype.Component;

/**
 * @author 016039
 * @Package com.frank.error
 * @Description: 错误类
 * @date 2018/11/3上午8:00
 */
@Component
public class ApiServiceError implements ApiService{
    @Override
    public String index() {
        return "服务发生故障!";
    }
}


