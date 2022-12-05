package com.wangyc.nio.reactor;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Main reactor thread, main reactor will create nio event listener and register OP_ACCEPT
 * main reactor listen the connection request under the server port
 *
 * @author cczyWyc
 */
public class MainReactor implements Runnable {

    /** default the io thread counts */
    private static final int DEFAULT_IO_THREAD_COUNT = 4;
    /** nio selector */
    private Selector selector;
    /** sub reactor thread group */
    private SubReactorThreadGroup subReactorThreadGroup;

    public MainReactor(ServerSocketChannel serverSocketChannel) {
        try {
            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        subReactorThreadGroup = new SubReactorThreadGroup(DEFAULT_IO_THREAD_COUNT);
    }

    @Override
    public void run() {
        System.out.println("MainReactor is running");
        while (!Thread.interrupted()) {
            Set<SelectionKey> ops = null;
            try {
                selector.select(1000);
                ops = selector.selectedKeys();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //handle events
            for (Iterator<SelectionKey> iterator = ops.iterator(); iterator.hasNext();) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isAcceptable()) {
                    System.out.println("receive request from client");
                    try {
                        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                        SocketChannel clientChannel = serverSocketChannel.accept();
                        clientChannel.configureBlocking(false);
                        //subReactorThreadGroup dispatch the request
                        subReactorThreadGroup.dispatch(clientChannel);
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("the client is closed the connection");
                    }
                }
            }
        }
    }
}
