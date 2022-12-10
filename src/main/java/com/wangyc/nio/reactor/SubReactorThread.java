package com.wangyc.nio.reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.ReentrantLock;

/**
 * SubReactorThread IO thread
 *
 * @author cczywyc
 */
public class SubReactorThread extends Thread {
    /** event selector */
    private Selector selector;
    /** business thread pool */
    private ExecutorService businessExecutorPool;
    /** task list */
    private List<Task> taskList = new ArrayList<>(512);
    /** lock */
    private ReentrantLock taskMainLock = new ReentrantLock();

    public SubReactorThread(ExecutorService businessExecutorPool) {
        try {
            this.businessExecutorPool = businessExecutorPool;
            this.selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * register task, add task into task list
     *
     * @param task task
     */
    public void register(Task task) {
        if (task != null) {
            try {
                taskMainLock.lock();
                taskList.add(task);
            } finally {
                taskMainLock.unlock();
            }
        }
    }

    /**
     * reqBuffer is writeable
     * @param socketChannel client socketChannel
     * @param reqBuffer buffer
     */
    public void dispatch(SocketChannel socketChannel, ByteBuffer reqBuffer) {
        businessExecutorPool.submit(new Handler(socketChannel, reqBuffer, this));
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            Set<SelectionKey> ops = null;
            try {
                selector.select(1000);
                ops = selector.selectedKeys();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            for (Iterator<SelectionKey> iterator = ops.iterator(); iterator.hasNext();) {
                SelectionKey key = iterator.next();
                iterator.remove();
                try {
                    if (key.isWritable()) {
                        //channel write event is ready
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        //get the un write data from the last time
                        ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
                        socketChannel.write(byteBuffer);
                        System.out.println("server send message to the client");
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        System.out.println("The server receive request from client");
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        System.out.println(byteBuffer.capacity());
                        int read = socketChannel.read(byteBuffer);
                        dispatch(socketChannel, byteBuffer);
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                    System.out.println("the client disconnect");
                }
            }

            if (!taskList.isEmpty()) {
                try {
                    taskMainLock.lock();
                    for (Iterator<Task> iterator = taskList.iterator(); iterator.hasNext(); ) {
                        Task task = iterator.next();
                        try {
                            SocketChannel socketChannel = task.getSocketChannel();
                            if (task.getData() != null) {
                                ByteBuffer byteBuffer = (ByteBuffer) task.getData();
                                byteBuffer.flip();
                                int write = socketChannel.write(byteBuffer);
                                System.out.println("Server send data to the client");
                                if (write < 1 && byteBuffer.hasRemaining()) {
                                    socketChannel.register(selector, task.getOp(), task.getSocketChannel());
                                    continue;
                                }
                                byteBuffer = null;
                            } else {
                                socketChannel.register(selector, task.getOp());
                            }
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                        iterator.remove();
                    }
                } finally {
                    taskMainLock.unlock();
                }
            }
        }
    }
}
