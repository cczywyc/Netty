package com.wangyc.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author wangyc
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent event) {
            String eventType = switch (event.state()) {
                case READER_IDLE -> "������";
                case WRITER_IDLE -> "д����";
                case ALL_IDLE -> "��д����";
            };
            System.out.println(ctx.channel().remoteAddress() + "--��ʱʱ��--" + eventType);
            System.out.println("����������Ӧ����..");
        }
    }
}
