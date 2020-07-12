package com.frank.netty.im.protocol.request;

import com.frank.netty.im.protocol.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;

import static com.frank.netty.im.protocol.Command.MESSAGE_REQUEST;

/**
 * Package com.frank.netty.im.protocol.request
 * Description: 消息发送数据包
 * author 016039
 * date 2018/11/17上午10:09
 */
@Data
@AllArgsConstructor
public class MessageRequestPacket extends Packet {
    private String toUserId;
    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
