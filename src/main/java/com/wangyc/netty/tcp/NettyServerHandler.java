package com.wangyc.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

/**
 * Netty服务器处理器
 *
 * @author wangyc
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Server read thread " + Thread.currentThread().getName() + "channel = " + ctx.channel());
        System.out.println("Server ctx = " + ctx);
        Channel channel = ctx.channel();
        ChannelPipeline pipeline = ctx.pipeline();
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("Client address is:" + channel.remoteAddress());
        System.out.println("Server send message is:" + buf.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello, client", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
