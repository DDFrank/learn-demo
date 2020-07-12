package com.frank.netty.im.protocol;

/**
 * Package com.frank.netty.im.protocol
 * Description: 序列化接口
 * author 016039
 * date 2018/11/17上午8:36
 */
public interface Serializer {
    /*

        josn 序列化
    */
    byte JSON_SERIALIZER = 1;

    Serializer DEFAULT = new JSONSerializer();

    /*
    * 序列化算法
    * */
    byte getSerializerAlgorithm();

    /*
    * java 对象性转换为 二进制
    * */
    byte[] serialize(Object object);

    /*
    * 二进制转换为 java 对象
    * */
    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
