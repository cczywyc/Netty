package time.geekbang.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import time.geekbang.netty.client.codec.OrderFrameDecoder;
import time.geekbang.netty.client.codec.OrderFrameEncoder;
import time.geekbang.netty.client.codec.OrderProtocolDecoder;
import time.geekbang.netty.client.codec.OrderProtocolEncoder;
import time.geekbang.netty.common.RequestMessage;
import time.geekbang.netty.order.OrderOperation;
import time.geekbang.netty.util.IdUtil;

import java.util.concurrent.ExecutionException;

/**
 * @author wangyc
 */
public class Client {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.group(new NioEventLoopGroup());
        bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new OrderFrameDecoder());
                pipeline.addLast(new OrderFrameEncoder());
                pipeline.addLast(new OrderProtocolEncoder());
                pipeline.addLast(new OrderProtocolDecoder());
                pipeline.addLast(new LoggingHandler(LogLevel.INFO));
            }
        });
        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8090);
        channelFuture.sync();
        RequestMessage requestMessage = new RequestMessage(IdUtil.nextId(), new OrderOperation(1001, "potato"));
        channelFuture.channel().writeAndFlush(requestMessage);
        channelFuture.channel().closeFuture().get();
    }
}
