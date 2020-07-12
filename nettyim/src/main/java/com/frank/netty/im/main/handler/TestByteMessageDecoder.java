package com.frank.netty.im.main.handler;

import com.frank.netty.im.protocol.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Package com.frank.netty.im.main.handler
 * Description: ${todo}
 * author 016039
 * date 2018/11/17下午1:37
 */
public class TestByteMessageDecoder extends ByteToMessageDecoder {
    /*
    * 已经将消息转换为了 ByteBuf 类
    * 该类会自动进行内存的释放
    * */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        // 这样就可以自动实现结果的传递
        list.add(PacketCodec.INSTANCE.decode(byteBuf));

    }
}
