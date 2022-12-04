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
 *
 * @author wangyc
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {
    /** channelGroup���������е�Channel�� GlobalEventExecutor.INSTANCEȫ���¼�ִ��������һ������*/
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * ��ʾ�ͻ���������
     * @param ctx ������
     * @throws Exception �쳣
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "������");
    }

    /**
     * ��ʾ�ͻ�������
     * @param ctx ������
     * @throws Exception �쳣
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "������");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    /**
     * ����һ����������һ����ִ�С�����ǰchannel���뵽channelGroup
     * @param ctx ������
     * @throws Exception �쳣
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //��ȡ��ǰchannel
        Channel channel = ctx.channel();
        //���ÿͻ��������췢�͸��������ߵĿͻ��ˡ��÷�����������е�channel��������Ϣ������Ҫ�ٴα���
        channelGroup.writeAndFlush("[�ͻ���]" + channel.remoteAddress() + "��������" + sdf.format(new Date()) + "\n");
        channelGroup.add(channel);
    }

    /**
     * �Ͽ����ӣ�����ǰ�ͻ��Ͽ����ӵ���Ϣ���͸���ǰ�������ߵĿͻ�
     * @param ctx ������
     * @throws Exception �쳣
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[�ͻ���]" + channel.remoteAddress() + "�뿪��\n");
        System.out.println("channelGroup size" + channelGroup.size());
    }

    /**
     * ����ͻ�����Ϣ
     * @param ctx ������
     * @param msg �ͻ��˷�������Ϣ
     * @throws Exception �쳣
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        /** ��ȡ��ǰchannel */
        Channel channel = ctx.channel();
        channelGroup.forEach(ch -> {
            if (channel != ch) {
                //���ǵ�ǰ��channel�������ת������Ϣ
                ch.writeAndFlush("[�ͻ�]" + channel.remoteAddress() + "��������Ϣ" + msg + "\n");
            } else {
                //�����Լ����͵���Ϣ
                ch.writeAndFlush("[�Լ�]��������Ϣ" + msg + "\n");
            }
        });
    }
}
