package com.hust.hui.quicksilver.spi.selector;


import com.hust.hui.quicksilver.spi.exception.NoSpiMatchException;
import com.hust.hui.quicksilver.spi.selector.api.ISelector;
import com.hust.hui.quicksilver.spi.selector.api.SpiImplWrapper;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 默认的根据name 获取具体的实现类
 * <p/>
 * Created by yihui on 2017/5/24.
 */
public class DefaultSelector implements ISelector<String> {

    /**
    * @Description: 参数校验后，根据名字来获取实现类
    * @param
    * @return
    * @author 016039
    * @date 2018/10/28 下午7:06
    */
    @Override
    public <K> K selector(Map<String, SpiImplWrapper<K>> map, String name) throws NoSpiMatchException {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("spiName should not be empty!");
        }

        if (map == null || map.size() == 0) {
            throw new IllegalArgumentException("no impl spi!");
        }


        if (!map.containsKey(name)) {
            throw new NoSpiMatchException("no spiImpl match the name you choose! your choose is: " + name);
        }

        return map.get(name).getSpiImpl();
    }

}
