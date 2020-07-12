package com.frank.netty.im.protocol.request;

import com.frank.netty.im.protocol.Packet;
import lombok.Data;

import static com.frank.netty.im.protocol.Command.JOIN_GROUP_REQUEST;

/**
 * Package com.frank.netty.im.protocol.request
 * Description: ${todo}
 * author 016039
 * date 2018/11/18上午10:16
 */
@Data
public class JoinGroupRequestPacket extends Packet {
    private String groupId;

    @Override
    public Byte getCommand() {
        return JOIN_GROUP_REQUEST;
    }
}
