package com.frank.netty.im.protocol.response;

import com.frank.netty.im.bean.Session;
import com.frank.netty.im.protocol.Packet;
import lombok.Data;

import static com.frank.netty.im.protocol.Command.GROUP_MESSAGE_RESPONSE;

/**
 * Package com.frank.netty.im.protocol.response
 * Description: 群聊消息响应数据包
 * author 016039
 * date 2018/11/18下午12:41
 */
@Data
public class GroupMessageResponsePacket extends Packet {
    private String fromGroupId;

    private Session fromUser;

    private String message;

    @Override
    public Byte getCommand() {
        return GROUP_MESSAGE_RESPONSE;
    }
}
