package time.geekbang.netty.auth;

import time.geekbang.netty.common.OperationResult;

/**
 * @author wangyc
 */
public class AuthOperationResult extends OperationResult {
    private final boolean passAuth;

    public AuthOperationResult(boolean passAuth) {
        this.passAuth = passAuth;
    }
}
