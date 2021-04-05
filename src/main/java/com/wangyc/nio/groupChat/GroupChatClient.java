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
 * 群聊系统客户端
 *
 * @author wangyc
 */
public class GroupChatClient {
    /** 服务器ip */
    private final String HOST = "127.0.0.1";
    /** 端口 */
    private final int PORT = 6667;
    /** 选择器 */
    private Selector selector;
    /** SocketChannel */
    private SocketChannel socketChannel;
    /** 用户名 */
    private String username;

    /**
     * 构造器，初始化
     */
    public GroupChatClient() throws IOException {
        selector = Selector.open();
        //连接服务器
        socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        username = socketChannel.getLocalAddress().toString().substring(1);
        System.out.println(username + "is OK");
    }

    /**
     * 向服务端发送消息
     * @param msg 发送的消息
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
     * 读取从服务端回复的数据
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
        //启动客户端
        GroupChatClient client = new GroupChatClient();
        //启动一个线程，每过3秒读取从服务端发送来的数据
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

        //发送数据库给服务端
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            client.SendMsg(s);
        }
    }
}
