package com.wangyc.nio.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * nio client, send request to server
 *
 * @author cczyWyc
 */
public class Client {
    public static void main(String[] args) {
        SocketChannel socketChannel;
        Selector selector = null;
        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 8081));
            Set<SelectionKey> ops = null;
            while (true) {
                try {
                    selector.select();
                    ops = selector.selectedKeys();
                    for (Iterator<SelectionKey> iterator = ops.iterator(); iterator.hasNext();) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isConnectable()) {
                            System.out.println("client connect");
                            SocketChannel sc = (SocketChannel) key.channel();
                            if (sc.isConnectionPending()) {
                                sc.finishConnect();
                                System.out.println("finish the connect");
                                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                                byteBuffer.put("hello server".getBytes(StandardCharsets.UTF_8));
                                byteBuffer.flip();
                                sc.write(byteBuffer);
                            }
                            sc.register(selector, SelectionKey.OP_READ);
                        } else if (key.isWritable()) {
                            System.out.println("client write");
                            SocketChannel sc = (SocketChannel) key.channel();
                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                            byteBuffer.put("hello server".getBytes(StandardCharsets.UTF_8));
                            byteBuffer.flip();
                            sc.write(byteBuffer);
                        } else if (key.isReadable()) {
                            System.out.println("client receive data from server");
                            SocketChannel sc = (SocketChannel) key.channel();
                            ByteBuffer byteBuffer = ByteBuffer.allocate(1014);
                            int read = sc.read(byteBuffer);
                            if (read > 0) {
                                byteBuffer.flip();
                                byte[] response = new byte[byteBuffer.remaining()];
                                byteBuffer.get(response);
                                System.out.println(new String(response));
                            }

                            //send message again
                            byteBuffer = ByteBuffer.allocate(1024);
                            byteBuffer.put("hello server".getBytes(StandardCharsets.UTF_8));
                            byteBuffer.flip();
                            sc.write(byteBuffer);
                        }
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
