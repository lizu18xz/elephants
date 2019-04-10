package com.fayayo.elephants.work;

import com.fayayo.elephants.transport.protocol.request.ScheduleRequestPacket;
import lombok.extern.slf4j.Slf4j;

/**
 * @author dalizu on 2019/3/12.
 * @version v1.0
 * @desc
 */
@Slf4j
public abstract class AbstractReferer {

    protected WorkHolder workHolder;

    /**
     *
     * 发起真正的请求
     *
     * */
    public void call(ScheduleRequestPacket requestPacket) {
        //TODO  等待请求完成,异常抛出
        if(this.workHolder!=null){
            this.workHolder.getChannel().writeAndFlush(requestPacket);
        }else {
            log.error("~~referer doesn't have channel~~");
        }
    }


}
