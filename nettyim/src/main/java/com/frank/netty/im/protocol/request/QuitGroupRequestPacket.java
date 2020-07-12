package com.frank.netty.im.protocol.request;

import com.frank.netty.im.protocol.Packet;
import lombok.Data;

import static com.frank.netty.im.protocol.Command.QUIT_GROUP_REQUEST;

/**
 * Package com.frank.netty.im.protocol.request
 * Description: ${todo}
 * author 016039
 * date 2018/11/18上午10:18
 */
@Data
public class QuitGroupRequestPacket extends Packet {
    private String groupId;

    @Override
    public Byte getCommand() {
        return QUIT_GROUP_REQUEST;
    }
}
