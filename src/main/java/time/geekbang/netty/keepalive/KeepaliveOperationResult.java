package time.geekbang.netty.keepalive;

import lombok.Data;
import time.geekbang.netty.common.OperationResult;

/**
 * @author wangyc
 */
@Data
public class KeepaliveOperationResult extends OperationResult {
    private final long time;
}
