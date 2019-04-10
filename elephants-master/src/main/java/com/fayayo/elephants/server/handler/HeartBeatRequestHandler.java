package com.fayayo.elephants.server.handler;

import com.fayayo.elephants.master.MasterContext;
import com.fayayo.elephants.transport.protocol.request.HeartBeatRequestPacket;
import com.fayayo.elephants.work.HeartBeatInfo;
import com.fayayo.elephants.work.WorkHolder;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author dalizu on 2019/2/27.
 * @version v1.0
 * @desc
 */
@ChannelHandler.Sharable
@Slf4j
public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequestPacket>{

    public static final HeartBeatRequestHandler INSTANCE = new HeartBeatRequestHandler();

    private HeartBeatRequestHandler(){

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatRequestPacket heartBeatRequestPacket) throws Exception {

        HeartBeatInfo heartBeatInfo=new HeartBeatInfo();

        heartBeatInfo.setCores(heartBeatRequestPacket.getCores());
        heartBeatInfo.setCpuLoadPerCore(heartBeatRequestPacket.getCpuLoadPerCore());
        heartBeatInfo.setHost(heartBeatRequestPacket.getHost());
        heartBeatInfo.setMemRate(heartBeatRequestPacket.getMemRate());
        heartBeatInfo.setMemTotal(heartBeatRequestPacket.getMemTotal());
        heartBeatInfo.setTimestamp(heartBeatRequestPacket.getTimestamp());
        WorkHolder workHolder=MasterContext.start().getWorkHolders().get(ctx.channel());

        workHolder.setHeartBeatInfo(heartBeatInfo);

        log.info("接收到来自客户端【work机器】的心跳包......{},{}",heartBeatRequestPacket.getHost(),heartBeatRequestPacket.toString());

    }


}
