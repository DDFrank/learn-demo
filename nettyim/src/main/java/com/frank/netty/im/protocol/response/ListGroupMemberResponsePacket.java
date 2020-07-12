package com.frank.netty.im.protocol.response;

import com.frank.netty.im.bean.Session;
import com.frank.netty.im.protocol.Packet;
import lombok.Data;

import java.util.List;

import static com.frank.netty.im.protocol.Command.LIST_GROUP_MEMBERS_RESPONSE;

/**
 * Package com.frank.netty.im.protocol.response
 * Description: ${todo}
 * author 016039
 * date 2018/11/18上午10:30
 */
@Data
public class ListGroupMemberResponsePacket extends Packet {
    private String groupId;

    private List<Session> sessionList;

    @Override
    public Byte getCommand() {
        return LIST_GROUP_MEMBERS_RESPONSE;
    }
}
