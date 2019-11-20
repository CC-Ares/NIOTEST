package com.area.nio.NettyProtocal;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline channelPipeline = socketChannel.pipeline();
        channelPipeline.addLast(new PersonProtocolEncoder());
        channelPipeline.addLast(new PersonProtocolDecoder());
        channelPipeline.addLast(new ServerHandler());
    }
}
