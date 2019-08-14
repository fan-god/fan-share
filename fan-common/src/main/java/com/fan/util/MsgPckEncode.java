package com.fan.util;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * @author fan
 * @title: MsgPckDecode
 * @projectName fan-share
 * @description: 二进制数据编码器, 将对象序列化成二进制的流并编码,与Probuff有类似功能
 * @date 2019/7/17/001715:40
 */
public class MsgPckEncode extends MessageToByteEncoder<Object> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        MessagePack pack = new MessagePack();
        byte[] bs = pack.write(o);
        byteBuf.writeBytes(bs);
    }
}
