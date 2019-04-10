package com.fayayo.elephants.work;

import com.fayayo.elephants.master.MasterContext;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dalizu on 2019/3/1.
 * @version v1.0
 * @desc 代表 一个 work连接
 */
@Getter
@Setter
public class Referer extends AbstractReferer{

    private String host;

    public Referer(String host) {
        this.host = host;
    }

    /**
     *
     * 判断当前节点是否符合可以接收任务的条件
     *
     * */
    public boolean isAvailable(){

        /*//遍历内存信息
        for (WorkHolder worker:MasterContext.start().getWorkHolders().values()){

            if(worker==null){
                continue;
            }

            if(worker.getHeartBeatInfo()==null){
                continue;
            }

            HeartBeatInfo heartBeatInfo = worker.getHeartBeatInfo();
            if(!heartBeatInfo.getHost().equals(host)){
                continue;
            }

            //内存超过 限制
            if (heartBeatInfo.getMemRate() == null || heartBeatInfo.getMemRate() > 0.7) {

                return false;
            }

            if (heartBeatInfo.getCpuLoadPerCore() == null || heartBeatInfo.getCpuLoadPerCore() > 4) {

                return false;
            }

            // 配置计算数量
            *//*Float assignTaskNum = (heartBeatInfo.getMemTotal() - HeraGlobalEnvironment.getSystemMemUsed()) / HeraGlobalEnvironment.getPerTaskUseMem();
            int sum = heartBeatInfo.getDebugRunning().size() + heartBeatInfo.getManualRunning().size() + heartBeatInfo.getRunning().size();
            if (sum > assignTaskNum.intValue()) {
                MasterLog.warn(ResultReason.TASK_LIMIT.getMsg() + ":{}, host:{}", sum, heartBeatInfo.getHost());
                return false;
            }*//*

            this.workHolder=worker;

            return true;
        }

        return false;*/

        return true;
    }


    @Override
    public String toString() {
        return "Referer{" +
                "host='" + host + '\'' +
                '}';
    }
}
