package com.fayayo.elephants.client;

import com.fayayo.elephants.transport.codec.PacketDecoder;
import com.fayayo.elephants.transport.codec.PacketEncoder;
import com.fayayo.elephants.transport.codec.Spliter;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author dalizu on 2019/2/26.
 * @version v1.0
 * @desc work 负责和  master 进行 连接
 */
@Slf4j
public class NettyClient {

    private NioEventLoopGroup workerGroup;

    private Bootstrap bootstrap;

    private Channel channel;

    public NettyClient() {
        init();
    }

    private void init() {
        workerGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap
                .group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {

                        ch.pipeline().addLast(new Spliter());

                        ch.pipeline().addLast(new PacketDecoder());



                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });
    }


    //TODO 重连机制
    public Channel connect(String host,Integer port) {
        log.info("connect:{}:{}", host,port);
        ChannelFuture channelFuture = bootstrap.connect(host, port);
        //连接超时时间
        int timeout = 3000;
        boolean result = channelFuture.awaitUninterruptibly(timeout, TimeUnit.MILLISECONDS);//阻塞直到连接建立
        boolean success = channelFuture.isSuccess();
        log.info("连接超时时间:{},连接是否成功:{}",timeout,(result && success));
        if (result && success) {
            channel = channelFuture.channel();//获取channel
        }
        return channel;
    }


}
