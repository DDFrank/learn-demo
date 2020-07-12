package com.frank.netty.im.protocol.response;

import com.frank.netty.im.protocol.Packet;
import lombok.Data;

import static com.frank.netty.im.protocol.Command.LOGIN_RESPONSE;

/**
 * Package com.frank.netty.im.protocol.response
 * Description: ${todo}
 * author 016039
 * date 2018/11/17上午9:49
 */
@Data
public class LoginResponsePacket extends Packet{
    private boolean success;

    private String reason;

    private String userName;

    private String userId;

    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
