package com.wangyc.netty.dubborpc;

/**
 * @author wangyc
 */
public class ServerBootstrap {
    public static void main(String[] args) {
        Server.startServer("127.0.0.1", 7000);
    }
}
