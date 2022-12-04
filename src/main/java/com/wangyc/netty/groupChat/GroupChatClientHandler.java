package com.wangyc.netty.groupChat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 *
 * @author wangyc
 */
public class GroupChatClientHandler extends SimpleChannelInboundHandler<String> {
    /**
     * �����������Ϣ
     * @param ctx ������
     * @param msg ��Ϣ
     * @throws Exception �쳣
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(msg.trim());
    }
}
