package com.area.nio.NettyProtocal;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.nio.charset.Charset;
import java.util.List;

public class PersonProtocolDecoder extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int length = byteBuf.readInt();
        byte[] content = new byte[length];
        byteBuf.readBytes(content);
        PersonProtocol personProtocal = new PersonProtocol();
        personProtocal.setLength(length);
        personProtocal.setContent(new String(content, Charset.forName("utf-8")));
        list.add(personProtocal);
    }
}
