package com.frank.netty.im.protocol.request;

import com.frank.netty.im.protocol.Packet;

import static com.frank.netty.im.protocol.Command.HEART_BEAT_REQUEST;

/**
 * Package com.frank.netty.im.protocol.request
 * Description: ${todo}
 * author 016039
 * date 2018/11/18下午2:13
 */
public class HeartBeatRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return HEART_BEAT_REQUEST;
    }
}
