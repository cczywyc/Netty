package com.wangyc.nio.reactor;

import java.io.IOException;
import java.nio.channels.Selector;
import java.util.ArrayList;
import java.util.List;
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
}
