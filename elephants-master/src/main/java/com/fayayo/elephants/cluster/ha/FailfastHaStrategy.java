package com.fayayo.elephants.cluster.ha;

import com.fayayo.elephants.cluster.LoadBalance;
import com.fayayo.elephants.queue.JobElement;
import com.fayayo.elephants.transport.protocol.request.ScheduleRequestPacket;
import com.fayayo.elephants.work.Referer;
import lombok.extern.slf4j.Slf4j;

/**
 * @author dalizu on 2019/3/12.
 * @version v1.0
 * @desc
 */
@Slf4j
public class FailfastHaStrategy extends AbstractHaStrategy {


    @Override
    public void call(LoadBalance loadBalance,ScheduleRequestPacket requestPacket) {

        Referer referer=loadBalance.select(requestPacket.getJobId());

        log.info("call referer:{}",referer.getHost());

        //发送请求到客户端
        referer.call(requestPacket);
    }

}
