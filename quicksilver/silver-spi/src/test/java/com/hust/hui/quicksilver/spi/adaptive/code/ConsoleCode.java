package com.hust.hui.quicksilver.spi.adaptive.code;

import com.hust.hui.quicksilver.spi.api.SpiConf;
import com.hust.hui.quicksilver.spi.selector.api.Context;

/**
 * Created by yihui on 2017/5/25.
 */
@SpiConf(params = "code", order = 1)
public class ConsoleCode implements ICode {
    @Override
    public void print(String name, String contet) {
        System.out.println("console print:--->" + contet);
    }


    /**
     * 显示指定了name, 因此可以直接通过 consoleEcho 来确定调用本实现方法
     * @param name
     * @param content
     */
    @Override
    @SpiConf(name = "consoleEcho")
    public void echo(String name, String content) {
        System.out.println("console echo:---->" + content);
    }


    /**
     * 实际的优先级取 方法 和类上的最高优先级, 实际为1
     * @param context
     * @param content
     */
    @Override
    @SpiConf(params = {"type:console"}, order = 3)
    public void write(Context context, String content) {
        System.out.println("console write:---->" + content);
    }

    @Override
    public void pp(Context context, String content) {
        System.out.println("console pp---->" + content);
    }
}
