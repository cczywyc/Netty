package com.wangyc.nio.reactor;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * SubReactor thread group, include SubReactorThread lists
 * SubReactor thread group provide lb and MainReactor can selector a SubReactorThread in SubReactorThread lists
 *
 * @author cczyWyc
 */
public class SubReactorThreadGroup {
    /** request counter */
    private static final AtomicInteger requestCounter = new AtomicInteger();
    /** the io thread size of the thread pool  */
    private final int ioThreadCount;
    /** business thread pool size */
    private final int businessThreadCount;
    /** default nio thread size */
    private static final int DEFAULT_NIO_THREAD_COUNT;
    /** subReactorThread lists, io threads */
    private SubReactorThread[] ioThreads;
    /** business thread pool */
    private ExecutorService businessExecutePool;

    static {
        DEFAULT_NIO_THREAD_COUNT = 4;
    }

    public SubReactorThreadGroup() {
        this(DEFAULT_NIO_THREAD_COUNT);
    }

    public SubReactorThreadGroup(int ioThreadCount) {
        if (ioThreadCount < 1) {
            ioThreadCount = DEFAULT_NIO_THREAD_COUNT;
        }
        businessThreadCount = 10;
        businessExecutePool = Executors.newFixedThreadPool(businessThreadCount, new ThreadFactory() {
            private AtomicInteger number = new AtomicInteger(0);
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = newThread(r);
                thread.setName("business-thread-" + number.incrementAndGet());
                return thread;
            }
        });
        this.ioThreadCount = ioThreadCount;
        this.ioThreads = new SubReactorThread[ioThreadCount];
        for (int i = 0; i < ioThreadCount; i++) {
            this.ioThreads[i] = new SubReactorThread(businessExecutePool);
            this.ioThreads[i].start();
        }
        System.out.println("nio thread number is: " + ioThreadCount);
    }

    /**
     * dispatch the request from the client
     *
     * @param socketChannel clientChannel
     */
    public void dispatch(SocketChannel socketChannel) {
        if (socketChannel != null) {
            next().register(new Task(socketChannel, SelectionKey.OP_READ));
        }
    }

    /**
     * find the next subReactorThread in the subReactorThread lists
     *
     * @return subReactor thread
     */
    protected SubReactorThread next() {
        return this.ioThreads[requestCounter.getAndIncrement() % ioThreadCount];
    }
}
