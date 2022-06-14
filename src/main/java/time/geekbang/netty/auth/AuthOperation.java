package time.geekbang.netty.auth;

import lombok.Data;
import lombok.extern.java.Log;
import time.geekbang.netty.common.Operation;
import time.geekbang.netty.common.OperationResult;

/**
 * @author wangyc
 */
@Data
@Log
public class AuthOperation extends Operation {
    private final String username;
    private final String password;

    @Override
    public OperationResult execute() {
        if ("admin".equals(this.username)) {
            return new AuthOperationResult(true);
        }
        return new AuthOperationResult(false);
    }
}
