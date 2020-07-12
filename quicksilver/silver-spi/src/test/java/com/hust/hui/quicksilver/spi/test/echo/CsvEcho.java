package com.hust.hui.quicksilver.spi.test.echo;

import com.hust.hui.quicksilver.spi.api.SpiConf;

/**
 * Created by yihui on 2017/5/24.
 */
@SpiConf(params = {"file", "type:csv"}, order = 0)
public class CsvEcho implements IEcho {
    @Override
    public void echo(String str) {
        System.out.println("CsvEcho...." + str);
    }
}
