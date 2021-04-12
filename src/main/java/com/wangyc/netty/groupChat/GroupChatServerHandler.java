package com.wangyc.netty.groupChat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 群聊系统服务端处理器
 *
 * @author wangyc
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {
    /** channelGroup，管理所有的Channel。 GlobalEventExecutor.INSTANCE全局事件执行器，是一个单例*/
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 提示客户端上线了
     * @param ctx 上下文
     * @throws Exception 异常
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "上线了");
    }

    /**
     * 提示客户端下线
     * @param ctx 上下文
     * @throws Exception 异常
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "离线了");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    /**
     * 连接一旦建立，第一个被执行。将当前channel加入到channelGroup
     * @param ctx 上下文
     * @throws Exception 异常
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //获取当前channel
        Channel channel = ctx.channel();
        //将该客户加入聊天发送给其他在线的客户端。该方法会遍历所有的channel并发送消息，不需要再次遍历
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + "加入聊天" + sdf.format(new Date()) + "\n");
        channelGroup.add(channel);
    }

    /**
     * 断开连接，将当前客户断开连接的消息推送给当前其他在线的客户
     * @param ctx 上下文
     * @throws Exception 异常
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + "离开了\n");
        System.out.println("channelGroup size" + channelGroup.size());
    }

    /**
     * 处理客户端消息
     * @param ctx 上下文
     * @param msg 客户端发来的消息
     * @throws Exception 异常
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        /** 获取当前channel */
        Channel channel = ctx.channel();
        channelGroup.forEach(ch -> {
            if (channel != ch) {
                //不是当前的channel，服务端转发此消息
                ch.writeAndFlush("[客户]" + channel.remoteAddress() + "发送了消息" + msg + "\n");
            } else {
                //回显自己发送的消息
                ch.writeAndFlush("[自己]发送了消息" + msg + "\n");
            }
        });
    }
}
