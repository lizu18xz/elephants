package com.fayayo.elephants.cluster.support;

import com.fayayo.elephants.cluster.Cluster;
import com.fayayo.elephants.cluster.HaStrategy;
import com.fayayo.elephants.cluster.LoadBalance;
import com.fayayo.elephants.queue.JobElement;
import com.fayayo.elephants.transport.protocol.request.ScheduleRequestPacket;
import com.fayayo.elephants.utils.RequestIdGenerator;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dalizu on 2019/2/26.
 * @version v1.0
 * @desc
 */
@Getter
@Setter
public class ClusterService implements Cluster {

    private HaStrategy haStrategy;

    private LoadBalance loadBalance;

    private JobElement jobElement;


    public ClusterService(HaStrategy haStrategy, LoadBalance loadBalance) {
        this.haStrategy = haStrategy;
        this.loadBalance = loadBalance;
    }

    @Override
    public void call() {
        //构建request,发送请求,真正的执行调度的方法  TODO 修改为异步,记录历史信息

        ScheduleRequestPacket requestPacket=new ScheduleRequestPacket();
        requestPacket.setRequestId(RequestIdGenerator.getRequestId());
        requestPacket.setJobId(jobElement.getJobId());


        this.haStrategy.call(this.loadBalance,requestPacket);

    }


}
