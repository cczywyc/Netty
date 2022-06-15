package time.geekbang.netty.common;

import time.geekbang.netty.auth.AuthOperation;
import time.geekbang.netty.auth.AuthOperationResult;
import time.geekbang.netty.keepalive.KeepaliveOperation;
import time.geekbang.netty.keepalive.KeepaliveOperationResult;
import time.geekbang.netty.order.OrderOperation;
import time.geekbang.netty.order.OrderOperationResult;

/**
 * @author wangyc
 */
public enum OperationType {
    AUTH(1, AuthOperation.class, AuthOperationResult.class),
    KEEPALIVE(2, KeepaliveOperation.class, KeepaliveOperationResult.class),
    ORDER(3, OrderOperation.class, OrderOperationResult.class);

    private int opCode;
    private Class<? extends Operation> operationClazz;
    private Class<? extends OperationResult> operationResultClazz;

    OperationType(int opCode, Class<? extends Operation> operationClazz, Class<? extends OperationResult> operationResultClazz) {
        this.opCode = opCode;
        this.operationClazz = operationClazz;
        this.operationResultClazz = operationResultClazz;
    }

    public int getOpCode() {
        return opCode;
    }

    public Class<? extends Operation> getOperationClazz() {
        return operationClazz;
    }

    public Class<? extends OperationResult> getOperationResultClazz() {
        return operationResultClazz;
    }
}
