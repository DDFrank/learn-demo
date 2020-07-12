/**
 * Package com.frank.netty.im.main
 * Description: ${todo}
 * author 016039
 * date 2018/11/17上午8:19
 */
package com.frank.netty.im.main;

/*
 * 通信协议的设计
 * 1 魔数，为几个固定的字节,用以辨别数据包的是否是遵循的自定义协议的
 * 2 一个字节，版本号，用来支持协议升级
 * 3 序列化算法，用于表示如何把 java 对象的序列化和反序列化
 * 4 指令，假设用一个字节表示，那就有 256 中指令
 * 5 数据部分的长度 4个字节
 * 6 数据内容,也就是 payload
 * */