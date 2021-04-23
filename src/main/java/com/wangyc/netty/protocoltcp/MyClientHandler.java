package com.wangyc.netty.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

/**
 * 客户端处理器你
 *
 * @author: wangyc
 */
public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    /** 消息计数 */
    private int count;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String msg = "今天天气冷，吃火锅";
        byte[] content = msg.getBytes(StandardCharsets.UTF_8);
        int length = msg.getBytes(StandardCharsets.UTF_8).length;

        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(length);
        messageProtocol.setContent(content);
        ctx.writeAndFlush(messageProtocol);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        int length = msg.getLen();
        byte[] content = msg.getContent();

        System.out.println("客户端接收到的消息：");
        System.out.println("长度=" + length);
        System.out.println("内容=" + new String(content, StandardCharsets.UTF_8));

        System.out.println("客户端接收消息的数量=" + (++this.count));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
