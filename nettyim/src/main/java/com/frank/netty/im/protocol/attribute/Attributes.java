package com.frank.netty.im.protocol.attribute;

import com.frank.netty.im.bean.Session;
import io.netty.util.AttributeKey;

/**
 * Package com.frank.netty.im.protocol.attribute
 * Description: channel 的自定义属性
 * author 016039
 * date 2018/11/17上午10:13
 */
public interface Attributes {
    /*登录成功与否的标志位*/
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
    AttributeKey<Session> SESSION = AttributeKey.newInstance(null);
}
