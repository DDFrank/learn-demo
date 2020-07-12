package com.frank.netty.im.console;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * Package com.frank.netty.im.console
 * Description: 控制台执行的操作的抽象
 * author 016039
 * date 2018/11/18上午9:52
 */
public interface ConsoleCommand {
    void exec(Scanner scanner, Channel channel);
}
