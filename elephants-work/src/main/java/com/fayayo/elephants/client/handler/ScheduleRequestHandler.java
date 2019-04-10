package com.fayayo.elephants.client.handler;

import com.fayayo.elephants.transport.protocol.request.ScheduleRequestPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author dalizu on 2019/3/12.
 * @version v1.0
 * @desc 处理 服务端  分发任务的请求
 */
@Slf4j
public class ScheduleRequestHandler extends SimpleChannelInboundHandler<ScheduleRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ScheduleRequestPacket scheduleRequestPacket)
            throws Exception {


        log.info("~~receive server message requestId{},jobId{}~~",scheduleRequestPacket.getRequestId(),scheduleRequestPacket.getJobId());

    }
}
