package com.wangyc.nio.reactor;

import java.nio.channels.SocketChannel;

/**
 * task
 *
 * @author cczyWyc
 */
public class Task {
    private SocketChannel socketChannel;
    private int op;
    private Object data;

    public Task(SocketChannel socketChannel, int op) {
        this.socketChannel = socketChannel;
        this.op = op;
    }

    public Task(SocketChannel socketChannel, int op, Object data) {
        this.socketChannel = socketChannel;
        this.op = op;
        this.data = data;
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public void setSocketChannel(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    public int getOp() {
        return op;
    }

    public void setOp(int op) {
        this.op = op;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
