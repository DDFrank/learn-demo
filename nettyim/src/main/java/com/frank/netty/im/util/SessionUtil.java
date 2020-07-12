package com.frank.netty.im.util;

import com.frank.netty.im.bean.Session;
import com.frank.netty.im.protocol.attribute.Attributes;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Package com.frank.netty.im.util
 * Description: 简单的Session实现
 * author 016039
 * date 2018/11/18上午7:50
 */
public class SessionUtil {
    private static final Map<String ,Channel> userIdChannelMap = new ConcurrentHashMap<>();

    private static final Map<String, ChannelGroup> groupIdChannelGroupMap = new ConcurrentHashMap<>();

    // 利用 userId 来绑定session
    public static void bindSession(Session session, Channel channel) {
        userIdChannelMap.put(session.getUserId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    // 解除绑定
    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            userIdChannelMap.remove(getSession(channel).getUserId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    // 通过Session 是否存在来判断是否登录
    public static boolean hasLogin(Channel channel) {
        return channel.hasAttr(Attributes.SESSION);
    }


    public static Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    // 获取连接 相应用户的 channel
    public static Channel getChannel(String userId) {
        return userIdChannelMap.get(userId);
    }

    public static void bindChannelGroup(String groupId, ChannelGroup channelGroup) {
        groupIdChannelGroupMap.put(groupId, channelGroup);
    }

    public static ChannelGroup getChannelGroup(String groupId) {
        return groupIdChannelGroupMap.get(groupId);
    }
}
