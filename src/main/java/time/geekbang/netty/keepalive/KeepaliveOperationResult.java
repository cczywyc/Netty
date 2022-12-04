package time.geekbang.netty.keepalive;

import time.geekbang.netty.common.OperationResult;

/**
 * @author wangyc
 */
public class KeepaliveOperationResult extends OperationResult {
    private final long time;

    public KeepaliveOperationResult(long time) {
        this.time = time;
    }
}
