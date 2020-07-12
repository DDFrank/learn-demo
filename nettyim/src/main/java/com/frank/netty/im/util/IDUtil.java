package com.frank.netty.im.util;

import java.util.UUID;

/**
 * Package com.frank.netty.im.util
 * Description: ${todo}
 * author 016039
 * date 2018/11/18上午11:16
 */
public class IDUtil {

    public static String randomId() {
        return UUID.randomUUID().toString().split("-")[0];
    }
}
