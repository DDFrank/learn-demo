package com.frank.netty.im.console;

import com.frank.netty.im.protocol.request.LoginRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * Package com.frank.netty.im.console
 * Description: 登录的指令
 * author 016039
 * date 2018/11/18上午10:48
 */
public class LoginConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        System.out.println("输入用户名登录: ");
        loginRequestPacket.setUserName(scanner.nextLine());
        // 使用默认密码
        loginRequestPacket.setPassword("pwd");

        // 发送登录数据包
        channel.writeAndFlush(loginRequestPacket);
        waitForLoginResponse();

    }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        }catch (InterruptedException ignored) {

        }
    }
}
