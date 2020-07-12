package com.frank.netty.im.demo.callback;

/**
 * Package com.frank.netty.im.demo.callback
 * Description: 数据类
 * author 016039
 * date 2018/11/19下午8:28
 */

public class Data {
    private int n;
    private int m;

    public Data(int n, int m) {
        this.n = n;
        this.m = m;
    }

    @Override
    public String toString() {
        int r =n/m;
        return n + "/" + m + "="  + r;
    }
}
