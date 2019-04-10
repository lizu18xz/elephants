package com.fayayo.elephants.cluster;

import com.fayayo.elephants.queue.JobElement;
import com.fayayo.elephants.transport.protocol.request.ScheduleRequestPacket;

/**
 * @author dalizu on 2019/2/26.
 * @version v1.0
 * @desc
 */
public interface HaStrategy {


    void call(LoadBalance loadBalance,ScheduleRequestPacket requestPacket);

}
