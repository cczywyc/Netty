package time.geekbang.netty.client.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import time.geekbang.netty.common.RequestMessage;

import java.util.List;

/**
 * @author wangyc
 */
public class OrderProtocolEncoder extends MessageToMessageEncoder<RequestMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, RequestMessage msg, List<Object> out) throws Exception {
        ByteBuf byteBuf = ctx.alloc().buffer();
        msg.encode(byteBuf);

        out.add(byteBuf);
    }
}
