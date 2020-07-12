package com.frank.netty.im.protocol.request;

import com.frank.netty.im.protocol.Packet;

import static com.frank.netty.im.protocol.Command.LOGOUT_REQUEST;

/**
 * Package com.frank.netty.im.protocol.request
 * Description: ${todo}
 * author 016039
 * date 2018/11/18上午10:07
 */
public class LogoutRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return LOGOUT_REQUEST;
    }
}

