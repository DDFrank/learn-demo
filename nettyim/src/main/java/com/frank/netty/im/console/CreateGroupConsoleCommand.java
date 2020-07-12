package com.frank.netty.im.console;

import com.frank.netty.im.protocol.request.CreateGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Package com.frank.netty.im.console
 * Description: 创建用户组的指令
 * author 016039
 * date 2018/11/18上午10:34
 */
public class CreateGroupConsoleCommand implements ConsoleCommand {
    private static final String USER_ID_SPLITER = ",";

    @Override
    public void exec(Scanner scanner, Channel channel) {
        CreateGroupRequestPacket createGroupRequestPacket = new CreateGroupRequestPacket();

        System.out.println("[拉人群聊]输入 userId 列表, userId 之间英文逗号隔开: ");
        String userIds = scanner.next();
        // 保存用户列表并发送
        createGroupRequestPacket.setUserIdList(Arrays.asList(userIds.split(USER_ID_SPLITER)));
        channel.writeAndFlush(createGroupRequestPacket);
    }
}
