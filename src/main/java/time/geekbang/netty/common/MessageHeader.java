package time.geekbang.netty.common;

import lombok.Data;

/**
 * @author wangyc
 */
@Data
public class MessageHeader {
    /** version */
    private int version = 1;
    /** opCode */
    private int opCode;
    /** stream id */
    private long streamId;
}
