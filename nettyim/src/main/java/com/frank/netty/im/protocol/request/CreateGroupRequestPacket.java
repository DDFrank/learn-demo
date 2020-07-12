package com.frank.netty.im.protocol.request;

import com.frank.netty.im.protocol.Packet;
import lombok.Data;

import java.util.List;

import static com.frank.netty.im.protocol.Command.CREATE_GROUP_REQUEST;

/**
 * Package com.frank.netty.im.protocol.request
 * Description: ${todo}
 * author 016039
 * date 2018/11/18上午10:14
 */
@Data
public class CreateGroupRequestPacket extends Packet {
    private List<String> userIdList;

    @Override
    public Byte getCommand() {
        return CREATE_GROUP_REQUEST;
    }
}
