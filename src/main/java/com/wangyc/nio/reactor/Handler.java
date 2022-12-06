package com.wangyc.nio.reactor;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * handle the business
 *
 * @author cczyWyc
 */
public class Handler implements Runnable {
    /** the response to client */
    private static final byte[] b = "hello, the server receive your message".getBytes(StandardCharsets.UTF_8);
    /** SocketChannel */
    private SocketChannel socketChannel;
    /** request buffer */
    private ByteBuffer requestBuffer;
    /** subReactorThread */
    private SubReactorThread parent;

    public Handler(SocketChannel socketChannel, ByteBuffer requestBuffer, SubReactorThread parent) {
        super();
        this.socketChannel = socketChannel;
        this.requestBuffer = requestBuffer;
        this.parent = parent;
    }

    @Override
    public void run() {
        System.out.println("execute the business in the handler");
        requestBuffer.put(b);
        parent.register(new Task(socketChannel, SelectionKey.OP_WRITE, requestBuffer));
        System.out.println("execute finished");
    }
}
