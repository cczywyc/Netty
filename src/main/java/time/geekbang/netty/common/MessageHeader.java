package time.geekbang.netty.common;


/**
 * @author wangyc
 */
public class MessageHeader {
    /** version */
    private int version = 1;
    /** opCode */
    private int opCode;
    /** stream id */
    private long streamId;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getOpCode() {
        return opCode;
    }

    public void setOpCode(int opCode) {
        this.opCode = opCode;
    }

    public long getStreamId() {
        return streamId;
    }

    public void setStreamId(long streamId) {
        this.streamId = streamId;
    }
}
