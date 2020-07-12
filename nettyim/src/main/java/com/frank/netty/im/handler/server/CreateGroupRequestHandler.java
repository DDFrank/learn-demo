package com.frank.netty.im.handler.server;

import com.frank.netty.im.protocol.request.CreateGroupRequestPacket;
import com.frank.netty.im.protocol.response.CreateGroupResponsePacket;
import com.frank.netty.im.util.IDUtil;
import com.frank.netty.im.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Package com.frank.netty.im.handler.server
 * Description: ${todo}
 * author 016039
 * date 2018/11/18上午11:01
 */
@ChannelHandler.Sharable
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {

    public static CreateGroupRequestHandler INSTANCE = new CreateGroupRequestHandler();

    protected CreateGroupRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket createGroupRequestPacket) throws Exception {
        // 获取用户列表
        List<String> userIdList = createGroupRequestPacket.getUserIdList();

        List<String> userNameList = new ArrayList<>();
        // 创建一个 channel 分组
        /*
        * channelGroup 可以把多个 channel 的操作聚合在一起
        * 可以进行批量读写，关闭等操作
        * 这里一个群组其实就是一个 channel 的分组集合
        * */
        ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());

        // 筛选出待加入群聊的用户的 channel 和 userName
        for (String userId : userIdList) {
            Channel channel = SessionUtil.getChannel(userId);
            if (channel != null) {
                channelGroup.add(channel);
                userNameList.add(SessionUtil.getSession(channel).getUserName());
            }
        }

        // 创建群聊创建结果的响应
        String groupId = IDUtil.randomId();
        CreateGroupResponsePacket createGroupResponsePacket = new CreateGroupResponsePacket();
        createGroupResponsePacket.setSuccess(true);
        createGroupResponsePacket.setGroupId(groupId);
        createGroupResponsePacket.setUserNameList(userNameList);

        // 给每个客户端发送拉群通知
        /*
        * 该群通知的消息会批量的发送到客户端
        * */
        channelGroup.writeAndFlush(createGroupResponsePacket);

        System.out.println("群创建成功，id为[" + createGroupResponsePacket.getGroupId()+"], ");
        System.out.println("群里面有: " + createGroupResponsePacket.getUserNameList());

        // 保存群组相关的消息
        SessionUtil.bindChannelGroup(groupId, channelGroup);
    }
}
