package com.fayayo.elephants.server.handler;

import com.fayayo.elephants.master.MasterContext;
import com.fayayo.elephants.transport.channel.NettyChannel;
import com.fayayo.elephants.work.WorkHolder;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author dalizu on 2019/2/27.
 * @version v1.0
 * @desc 客户端连接后会从这里先注册
 */
@Slf4j
@ChannelHandler.Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {

    public static final ServerHandler INSTANCE = new ServerHandler();

    private ServerHandler(){

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("~~有客户端连接了~~");
        MasterContext.start().getWorkHolders().put(ctx.channel(),new WorkHolder(new NettyChannel(ctx.channel())));
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.info("~~有客户端退出了~~");
        MasterContext.start().getWorkHolders().remove(ctx.channel());
    }

}
