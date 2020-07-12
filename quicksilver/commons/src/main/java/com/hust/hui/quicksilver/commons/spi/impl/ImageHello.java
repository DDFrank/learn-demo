package com.hust.hui.quicksilver.commons.spi.impl;

import com.hust.hui.quicksilver.commons.spi.HelloInterface;

/**
 * Created by yihui on 2017/3/17.
 */
public class ImageHello implements HelloInterface {
    @Override
    public void sayHello() {
        System.out.println("image hello!");
    }
}
