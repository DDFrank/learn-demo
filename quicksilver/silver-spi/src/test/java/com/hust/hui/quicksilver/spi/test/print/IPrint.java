package com.hust.hui.quicksilver.spi.test.print;

import com.hust.hui.quicksilver.spi.api.Spi;

/**
 * Created by yihui on 2017/5/24.
 */
@Spi
public interface IPrint {

    void print(String str);


    void adaptivePrint(String conf, String str);

}
