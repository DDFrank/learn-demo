package com.frank.netty.im.protocol.request;

import com.frank.netty.im.protocol.Packet;
import lombok.Data;

import static com.frank.netty.im.protocol.Command.LOGIN_REQUEST;

/**
 * Package com.frank.netty.im.protocol
 * Description: 登录指令
 * author 016039
 * date 2018/11/17上午8:34
 */
@Data
public class LoginRequestPacket extends Packet{
    private String userId;

    private String userName;

    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
