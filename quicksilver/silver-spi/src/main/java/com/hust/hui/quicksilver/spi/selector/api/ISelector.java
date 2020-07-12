package com.hust.hui.quicksilver.spi.selector.api;

import com.hust.hui.quicksilver.spi.exception.NoSpiMatchException;

import java.util.Map;

/**
 * Created by yihui on 2017/5/24.
 * 用于获取SPI实现类
 */
public interface ISelector<T> {
    /**
    * 根据传入的条件，在所有的实现类中，找回一个最匹配的实现类
    * @param map 所有的实现类列表
     * @param conf 用于判断的输入条件
    * @throws NoSpiMatchException 找不到合适的实现类
    * */
    <K> K selector(Map<String, SpiImplWrapper<K>> map, T conf) throws NoSpiMatchException;
}
