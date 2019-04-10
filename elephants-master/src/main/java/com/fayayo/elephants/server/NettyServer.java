package com.fayayo.elephants.server;

import com.fayayo.elephants.master.MasterService;
import com.fayayo.elephants.transport.codec.PacketDecoder;
import com.fayayo.elephants.transport.codec.PacketEncoder;
import com.fayayo.elephants.transport.codec.Spliter;
import com.fayayo.elephants.server.handler.HeartBeatRequestHandler;
import com.fayayo.elephants.server.handler.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import java.util.Date;

/**
 * @author dalizu on 2019/2/26.
 * @version v1.0
 * @desc
 */
@Slf4j
public class NettyServer {

    private NioEventLoopGroup boosGroup;
    private NioEventLoopGroup workerGroup;
    private ServerBootstrap serverBootstrap;

    /**
     *
     * master启动后  会启动服务端
     * 然后  接收 客户端的连接或者请求信息,并且保存channel。
     *
     * */

    private Integer port;

    public NettyServer(Integer port) {
        this.port = port;
        init();
    }

    private void init(){
        boosGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(boosGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) {

                        ch.pipeline().addLast(new Spliter());

                        ch.pipeline().addLast(new PacketDecoder());

                        ch.pipeline().addLast(ServerHandler.INSTANCE);

                        ch.pipeline().addLast(HeartBeatRequestHandler.INSTANCE);

                        ch.pipeline().addLast(new PacketEncoder());

                    }
                });
    }


    public synchronized void start(MasterService.MasterListener masterListener){

        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                log.info(new Date() + ": 端口[" + port + "]绑定成功!");
                masterListener.listener();
            } else {
                log.error("端口[" + port + "]绑定失败!");
            }
        });

    }


}
