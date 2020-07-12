package com.frank.netty.im.protocol.response;

import com.frank.netty.im.protocol.Packet;
import lombok.Data;

import static com.frank.netty.im.protocol.Command.QUIT_GROUP_RESPONSE;

/**
 * Package com.frank.netty.im.protocol.response
 * Description: ${todo}
 * author 016039
 * date 2018/11/18上午10:32
 */
@Data
public class QuitGroupResponsePacket extends Packet {
    private String groupId;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return QUIT_GROUP_RESPONSE;
    }
}
