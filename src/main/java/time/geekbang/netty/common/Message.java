package time.geekbang.netty.common;

import lombok.Data;

/**
 * @author wangyc
 */
@Data
public abstract class Message<T extends MessageBody> {
    /** message header */
    private MessageHeader messageHeader;
    /** message body */
    private T messageBody;

    public T getMessageBody() {
        return messageBody;
    }
}
