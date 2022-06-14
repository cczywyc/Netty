package time.geekbang.netty.common;

/**
 * @author wangyc
 */
public abstract class Operation extends MessageBody {
    public abstract OperationResult execute();
}
