package com.frank.netty.im.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Package com.frank.netty.im.bean
 * Description: ${todo}
 * author 016039
 * date 2018/11/18上午7:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Session {
    // 用户唯一性标识
    private String userId;
    private String userName;
    
}
