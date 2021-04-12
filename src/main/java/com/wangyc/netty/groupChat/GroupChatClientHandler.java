package com.wangyc.netty.groupChat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 群聊系统客户端处理器
 *
 * @author wangyc
 */
public class GroupChatClientHandler extends SimpleChannelInboundHandler<String> {
    /**
     * 处理服务器消息
     * @param ctx 上下文
     * @param msg 消息
     * @throws Exception 异常
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(msg.trim());
    }
}
