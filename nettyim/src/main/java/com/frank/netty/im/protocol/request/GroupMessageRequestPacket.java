package com.frank.netty.im.protocol.request;

import com.frank.netty.im.protocol.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.frank.netty.im.protocol.Command.GROUP_MESSAGE_REQUEST;

/**
 * Package com.frank.netty.im.protocol.request
 * Description: ${todo}
 * author 016039
 * date 2018/11/18下午12:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupMessageRequestPacket extends Packet {
    private String toGroupId;
    private String message;

    @Override
    public Byte getCommand() {
        return GROUP_MESSAGE_REQUEST;
    }
}
