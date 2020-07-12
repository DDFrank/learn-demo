package com.hust.hui.quicksilver.spi.adaptive.code;

import com.hust.hui.quicksilver.spi.api.Spi;
import com.hust.hui.quicksilver.spi.api.SpiAdaptive;
import com.hust.hui.quicksilver.spi.selector.ParamsSelector;
import com.hust.hui.quicksilver.spi.selector.api.Context;

/**
 * Created by yihui on 2017/5/25.
 */
@Spi
public interface ICode {

    void print(String name, String contet);


    @SpiAdaptive
    void echo(String name, String content);


    @SpiAdaptive(selector = ParamsSelector.class)
    void write(Context context, String content);


    @SpiAdaptive(selector = ParamsSelector.class)
    void pp(Context context, String content);
}
