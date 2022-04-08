package com.wangyc.netty.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 *
 *
 * @author: wangyc
 */
public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        int len = msg.getLen();
        byte[] content = msg.getContent();

        System.out.println("����˽��յ���Ϣ");
        System.out.println("����=" + len);
        System.out.println("����=" + new String(content, StandardCharsets.UTF_8));

        System.out.println("���������յ���Ϣ������=" + (++this.count));

        //�ظ���Ϣ
        String responseContent = UUID.randomUUID().toString();
        int responseLen = responseContent.getBytes(StandardCharsets.UTF_8).length;
        byte[] respContent = responseContent.getBytes(StandardCharsets.UTF_8);
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(responseLen);
        messageProtocol.setContent(respContent);
        ctx.writeAndFlush(messageProtocol);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
