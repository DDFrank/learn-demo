package com.frank.netty.im.main.handler;

import com.frank.netty.im.protocol.Packet;
import com.frank.netty.im.protocol.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Package com.frank.netty.im.main.handler
 * Description: 专门来处理编码逻辑, 将对象转为二进制数据
 * author 016039
 * date 2018/11/17下午1:48
 */
public class TestMessageToByteEncoder extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) throws Exception {
        PacketCodec.INSTANCE.encode(byteBuf, packet);
    }
}
