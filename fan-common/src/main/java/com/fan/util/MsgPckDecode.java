package com.fan.util;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;
import org.springframework.ui.Model;

import java.util.List;

/**
 * @author fan
 * @title: MsgPckDecode
 * @projectName fan-share
 * @description: 二进制数据解码器, 将二进制的流解码,与Probuff有类似功能
 * @date 2019/7/17/001715:40
 */
public class MsgPckDecode extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> out) throws Exception {
        final byte[] array;
        final int length = byteBuf.readableBytes();
        array = new byte[length];
        byteBuf.getBytes(byteBuf.readerIndex(), array, 0, length);
        MessagePack pack = new MessagePack();
        out.add(pack.read(array, Model.class));
    }
}
