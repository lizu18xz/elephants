package com.fayayo.elephants.request;

import com.fayayo.elephants.tools.CpuLoadPerCoreJob;
import com.fayayo.elephants.tools.MemUseRateJob;
import com.fayayo.elephants.transport.protocol.request.HeartBeatRequestPacket;
import com.fayayo.elephants.work.WorkContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @author dalizu on 2019/3/1.
 * @version v1.0
 * @desc 发送心跳信息
 */
@Slf4j
public class HeartBeatRequest {


    public boolean send(WorkContext workContext){

        log.info("send heart...");
        try {
            //执行shell获取内存和cpu的信息
            MemUseRateJob memUseRateJob = new MemUseRateJob(1);
            memUseRateJob.readMemUsed();
            CpuLoadPerCoreJob loadPerCoreJob = new CpuLoadPerCoreJob();
            loadPerCoreJob.run();


            //组装 Packet
            HeartBeatRequestPacket heartBeatRequestPacket=new HeartBeatRequestPacket();
            heartBeatRequestPacket.setHost(WorkContext.host);
            if(WorkContext.cpuCoreNum!=null){
                heartBeatRequestPacket.setCores(WorkContext.cpuCoreNum);
            }
            heartBeatRequestPacket.setCpuLoadPerCore(loadPerCoreJob.getLoadPerCore());
            heartBeatRequestPacket.setMemRate(memUseRateJob.getRate());
            heartBeatRequestPacket.setMemTotal(memUseRateJob.getMemTotal());
            heartBeatRequestPacket.setTimestamp(System.currentTimeMillis());


            //发送心跳到master
            workContext.getServerChannel().writeAndFlush(heartBeatRequestPacket);

        }catch (Exception e){
            log.error("send heartBeat error");
            e.printStackTrace();
            return false;
        }

        return true;
    }

}





