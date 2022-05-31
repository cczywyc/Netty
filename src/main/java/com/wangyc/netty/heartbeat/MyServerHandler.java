package com.wangyc.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author wangyc
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     *重连机制
     * @param ctx 上下文
     * @param evt 事件
     * @throws Exception 异常
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent event) {
            String eventType = switch (event.state()) {
                case READER_IDLE -> "读空闲";
                case WRITER_IDLE -> "写空闲";
                case ALL_IDLE -> "读写空闲";
            };
            System.out.println(ctx.channel().remoteAddress() + "--超时时间--" + eventType);
            System.out.println("服务器做相应处理..");
        }
    }
}
