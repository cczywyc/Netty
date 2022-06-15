package time.geekbang.netty.server.codec;

import io.netty.handler.codec.LengthFieldPrepender;

/**
 * @author wangyc
 */
public class OrderFrameEncoder extends LengthFieldPrepender {
    public OrderFrameEncoder() {
        super(2);
    }
}
