package time.geekbang.netty.common;

/**
 * @author wangyc
 */
public class RequestMessage extends Message<Operation> {

    @Override
    public Class<Operation> getMessageBodyDecodeClass(int opcode) {
        return null;
    }

    public RequestMessage() {
    }

    public RequestMessage(Long streamId, Operation operation) {
        MessageHeader messageHeader = new MessageHeader();
        messageHeader.setStreamId(streamId);
        //messageHeader.setOpCode();
        this.setMessageHeader(messageHeader);
        this.setMessageBody(operation);
    }
}
