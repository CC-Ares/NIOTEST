package com.area.nio.NettyProtocal;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PersonProtocolEncoder extends MessageToByteEncoder<PersonProtocol>{
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, PersonProtocol personProtocal, ByteBuf byteBuf) throws Exception {
        int length = personProtocal.getLength();
        String content = personProtocal.getContent();
        byteBuf.writeInt(length);
        byteBuf.writeBytes(content.getBytes("utf-8"));
    }
}
