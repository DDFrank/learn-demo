package com.hust.hui.quicksilver.spi.def.spi;

import com.hust.hui.quicksilver.spi.def.entity.UserDO;
import com.hust.hui.quicksilver.spi.exception.NoSpiMatchException;
import com.hust.hui.quicksilver.spi.selector.api.ISelector;
import com.hust.hui.quicksilver.spi.selector.api.SpiImplWrapper;

import java.util.Map;

/**
 * Created by yihui on 2017/5/26.
 */
public class UserSelector implements ISelector<UserDO> {

    @Override
    public <K> K selector(Map<String, SpiImplWrapper<K>> map, UserDO conf) throws NoSpiMatchException {

        if (conf == null || conf.getMarket() == null) {
            throw new IllegalArgumentException("userDo or userDO#market should not be null!");
        }


        String name = conf.getMarket().getName();
        if (map.containsKey(name)) {
            return map.get(name).getSpiImpl();
        }


        throw new NoSpiMatchException("no spiImp matched marked: " + conf.getMarket());
    }
}
