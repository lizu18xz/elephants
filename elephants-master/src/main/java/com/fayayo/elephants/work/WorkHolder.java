package com.fayayo.elephants.work;

import com.fayayo.elephants.transport.channel.NettyChannel;
import lombok.Data;


/**
 * @author dalizu on 2019/3/1.
 * @version v1.0
 * @desc 保存work 的详细状态
 */
@Data
public class WorkHolder {


    private NettyChannel channel;

    private HeartBeatInfo heartBeatInfo;


    //......待完善(当前正在运行的任务等......)

    public WorkHolder(NettyChannel channel) {
        this.channel = channel;
    }
}
