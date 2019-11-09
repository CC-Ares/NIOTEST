package com.area.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyNioClient {
    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8899));

        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT);

        while (true) {
            try {
                selector.select();
                Set<SelectionKey> selectionKeyset = selector.selectedKeys();
                selectionKeyset.forEach(selectionKey -> {
                    final SocketChannel client;

                    try {
                        if (selectionKey.isConnectable()) {
                            client = (SocketChannel) selectionKey.channel();
                            if (client.isConnectionPending()) {
                                client.finishConnect();
                                client.configureBlocking(false);

                                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

                                ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
                                executorService.submit(() -> {
                                    while (true) {
                                        try {
                                            byteBuffer.clear();
                                            InputStreamReader inputStreamReader = new InputStreamReader(System.in);
                                            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                                            String message = bufferedReader.readLine();

                                            byteBuffer.put(message.getBytes());
                                            byteBuffer.flip();
                                            client.write(byteBuffer);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }

                            client.register(selector, SelectionKey.OP_READ);
                        } else if (selectionKey.isReadable()) {
                            client = (SocketChannel) selectionKey.channel();
                            client.configureBlocking(false);

                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

                            int count = client.read(byteBuffer);

                            if (count > 0) {
                                String receiveMessage = new String(byteBuffer.array(), 0, count);
                                System.out.println(receiveMessage);

                            }
                        }

                        selectionKeyset.clear();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                selectionKeyset.clear();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }
}
