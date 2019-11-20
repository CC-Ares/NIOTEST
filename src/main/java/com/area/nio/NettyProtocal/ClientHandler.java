package com.area.nio.NettyProtocal;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<PersonProtocol> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("已连接");
        PersonProtocol personProtocal = new PersonProtocol();
        String content = "我来了";
        personProtocal.setContent(content);
        personProtocal.setLength(content.getBytes().length);
        ctx.writeAndFlush(personProtocal);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, PersonProtocol personProtocal) throws Exception {
        System.out.print("服务器说：");
        System.out.println(personProtocal.getContent());
        String content = "去你的!";
        personProtocal.setContent(content);
        personProtocal.setLength(content.getBytes().length);
        channelHandlerContext.writeAndFlush(personProtocal);
    }
}
