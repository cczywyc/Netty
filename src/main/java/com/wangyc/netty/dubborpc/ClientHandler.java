package com.wangyc.netty.dubborpc;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

/**
 * Client Handler
 *
 * @author wangyc
 */
public class ClientHandler extends ChannelInboundHandlerAdapter implements Callable {
    /** 上下文 */
    private ChannelHandlerContext context;
    /** 返回结果 */
    private String result;
    /** 客户端调用方法时传入的参数 */
    private String params;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        context = ctx;
    }

    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        result = msg.toString();
        notify();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public synchronized Object call() throws Exception {
        context.writeAndFlush(params);
        wait();
        return result;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
