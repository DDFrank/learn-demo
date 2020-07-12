package com.hust.hui.quicksilver.spi.test.echo;

import com.hust.hui.quicksilver.spi.api.SpiConf;

/**
 * Created by yihui on 2017/5/24.
 */
@SpiConf(params = {"console"})
public class ConsoleEcho implements IEcho{

    @Override
    public void echo(String str) {
        System.out.println("ConsoleEcho: ..." + str);
    }
}
