package time.geekbang.netty.common;

/**
 * @author wangyc
 */
public class ResponseMessage extends Message<OperationResult> {
    @Override
    public Class<OperationResult> getMessageBodyDecodeClass(int opcode) {
        return null;
    }
}
