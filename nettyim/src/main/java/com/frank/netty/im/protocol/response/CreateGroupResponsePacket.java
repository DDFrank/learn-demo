package com.frank.netty.im.protocol.response;

import com.frank.netty.im.protocol.Packet;
import lombok.Data;

import java.util.List;

import static com.frank.netty.im.protocol.Command.CREATE_GROUP_RESPONSE;

/**
 * Package com.frank.netty.im.protocol.response
 * Description: ${todo}
 * author 016039
 * date 2018/11/18上午10:27
 */
@Data
public class CreateGroupResponsePacket extends Packet {
    private boolean success;

    private String groupId;

    private List<String> userNameList;

    @Override
    public Byte getCommand() {
        return CREATE_GROUP_RESPONSE;
    }
}
