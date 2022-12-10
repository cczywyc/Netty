package com.wangyc.nio.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * nio server
 *
 * @author cczyWyc
 */
public class Server {
    /** server port */
    private static final int PORT = 8081;

    public static void main(String[] args) {
        (new Thread(new Acceptor())).start();
    }

    private static class Acceptor implements Runnable {
        // main reactor thread pool, handle request from client
        private static ExecutorService mainReactor = Executors.newSingleThreadExecutor(new ThreadFactory() {
            private AtomicInteger number = new AtomicInteger(0);
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = newThread(r);
                thread.setName("main-reactor-" + number.incrementAndGet());
                return thread;
            }
        });

        @Override
        public void run() {
            ServerSocketChannel serverSocketChannel = null;
            try {
                serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.configureBlocking(false);
                serverSocketChannel.bind(new InetSocketAddress(PORT));

                //dispatch
                dispatch(serverSocketChannel);
                System.out.println("The server start succeed!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * handle connect request
         *
         * @param serverSocketChannel serverSocketChannel
         */
        private void dispatch(ServerSocketChannel serverSocketChannel) {
            mainReactor.submit(new MainReactor(serverSocketChannel));
        }
    }
}
