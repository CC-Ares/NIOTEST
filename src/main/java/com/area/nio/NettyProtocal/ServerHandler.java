package com.area.nio.NettyProtocal;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerHandler extends SimpleChannelInboundHandler<PersonProtocol> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, PersonProtocol personProtocal) throws Exception {
        System.out.print("收到客户端消息，客户端说：");
        System.out.println(personProtocal.getContent());
        String content = "滚!";
        personProtocal.setContent(content);
        personProtocal.setLength(content.getBytes().length);
        channelHandlerContext.writeAndFlush(personProtocal);
    }
}
