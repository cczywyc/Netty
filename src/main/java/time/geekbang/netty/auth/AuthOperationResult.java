package time.geekbang.netty.auth;

import lombok.Data;
import time.geekbang.netty.common.OperationResult;

/**
 * @author wangyc
 */
@Data
public class AuthOperationResult extends OperationResult {
    private final boolean passAuth;
}
