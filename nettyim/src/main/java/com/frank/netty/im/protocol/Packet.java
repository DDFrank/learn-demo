package com.frank.netty.im.protocol;

import lombok.Data;

/**
 * Package com.frank.netty.im.protocol
 * Description: 通信用Java对象
 * author 016039
 * date 2018/11/17上午8:32
 */
@Data
public abstract class Packet {
    /*
    * 协议版本
    * */
    private Byte version = 1;

    /*
    * 指令
    * */
    public abstract Byte getCommand();
}
