package com.wangyc.netty.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

/**
 *
 * @author: wangyc
 */
public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String msg = "���������䣬�Ի��";
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

        System.out.println("�ͻ��˽��յ�����Ϣ��");
        System.out.println("����=" + length);
        System.out.println("����=" + new String(content, StandardCharsets.UTF_8));

        System.out.println("�ͻ��˽�����Ϣ������=" + (++this.count));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
