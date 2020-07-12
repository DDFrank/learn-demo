package com.frank.netty.im.protocol.response;

import com.frank.netty.im.protocol.Packet;
import lombok.Data;

import static com.frank.netty.im.protocol.Command.MESSAGE_RESPONSE;

/**
 * Package com.frank.netty.im.protocol.response
 * Description: 
 * author 016039
 * date 2018/11/17上午10:10
 */
@Data
public class MessageResponsePacket extends Packet{
    private String fromUserId;
    private String fromUserName;
    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_RESPONSE;
    }
}
