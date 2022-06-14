package time.geekbang.netty.keepalive;

import time.geekbang.netty.common.Operation;
import time.geekbang.netty.common.OperationResult;

/**
 * @author wangyc
 */
public class KeepaliveOperation extends Operation {

    private long time;

    public KeepaliveOperation() {
        this.time = System.nanoTime();
    }


    @Override
    public OperationResult execute() {
        return new KeepaliveOperationResult(time);
    }
}
