package com.area.nio.NettyProtocal;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class ClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline channelPipeline =  socketChannel.pipeline();
        channelPipeline.addLast(new PersonProtocolDecoder());
        channelPipeline.addLast(new PersonProtocolEncoder());
        channelPipeline.addLast(new ClientHandler());
    }
}
