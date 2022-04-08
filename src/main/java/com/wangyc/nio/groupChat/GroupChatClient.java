package com.wangyc.nio.groupChat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * chat group client
 *
 * @author wangyc
 */
public class GroupChatClient {
    /** server ip */
    private final String HOST = "127.0.0.1";
    /** port */
    private final int PORT = 6667;
    /** selector */
    private Selector selector;
    /** SocketChannel */
    private SocketChannel socketChannel;
    /** account */
    private String username;

    /**
     * constructor
     */
    public GroupChatClient() throws IOException {
        selector = Selector.open();
        //connect the server
        socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        username = socketChannel.getLocalAddress().toString().substring(1);
        System.out.println(username + "is OK");
    }

    /**
     * send message to server
     * @param msg message
     */
    private void SendMsg(String msg) {
        System.out.println(username + "说：" + msg);
        try {
            ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
            socketChannel.write(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * read data from server
     */
    private void readMsg() {
        try {
            int readChannel = selector.select();
            if (readChannel > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isReadable()) {
                        SocketChannel sc = (SocketChannel) key.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        sc.read(byteBuffer);
                        String string = new String(byteBuffer.array());
                        System.out.println(string.trim());
                    }
                }
                iterator.remove();
            } else {
                System.out.println("没有可以使用通道");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        //start client
        GroupChatClient client = new GroupChatClient();
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    client.readMsg();
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        //send message to server
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            client.SendMsg(s);
        }
    }
}
