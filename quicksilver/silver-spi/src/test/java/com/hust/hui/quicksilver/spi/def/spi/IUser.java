package com.hust.hui.quicksilver.spi.def.spi;

import com.hust.hui.quicksilver.spi.api.Spi;
import com.hust.hui.quicksilver.spi.api.SpiAdaptive;
import com.hust.hui.quicksilver.spi.def.entity.UserDO;

/**
 * Created by yihui on 2017/5/26.
 */
@Spi
public interface IUser {


    @SpiAdaptive(selector = UserSelector.class)
    void welcome(UserDO userDO);


}
