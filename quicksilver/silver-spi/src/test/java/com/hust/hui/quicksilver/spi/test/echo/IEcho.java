package com.hust.hui.quicksilver.spi.test.echo;

import com.hust.hui.quicksilver.spi.api.Spi;
import com.hust.hui.quicksilver.spi.selector.ParamsSelector;

/**
 * Created by yihui on 2017/5/24.
 */
@Spi(selector = ParamsSelector.class)
public interface IEcho {
    void echo(String str);
}
