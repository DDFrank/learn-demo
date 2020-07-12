package com.frank.netty.im.protocol;

import com.alibaba.fastjson.JSON;

/**
 * Package com.frank.netty.im.protocol
 * Description: Json序列化用类
 * author 016039
 * date 2018/11/17上午8:39
 */
public class JSONSerializer implements Serializer{
    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
