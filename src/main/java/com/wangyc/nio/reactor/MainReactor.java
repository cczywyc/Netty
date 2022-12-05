package com.wangyc.nio.reactor;

import java.nio.channels.Selector;

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

    @Override
    public void run() {

    }
}
