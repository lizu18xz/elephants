package com.fayayo.elephants.master;

import com.fayayo.elephants.cluster.support.ClusterService;
import com.fayayo.elephants.cluster.support.ClusterSupport;
import com.fayayo.elephants.config.MaterConfiguration;
import com.fayayo.elephants.queue.JobElement;
import com.fayayo.elephants.tools.SpringHelper;
import com.fayayo.elephants.server.NettyServer;
import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.TimeUnit;

/**
 * @author dalizu on 2019/2/25.
 * @version v1.0
 * @desc
 */
@Slf4j
public class MasterService {

    private MaterConfiguration materConfiguration;

    private MasterListener masterListener;

    /**
     * 主节点启动后执行的操作
     * <p>
     * 初始化 所有 的任务
     * 从队列获取任务执行
     * 监控等信息
     * 启动server
     */

    public void init(MasterListener masterListener) {
        log.info("init MasterService");

        this.masterListener = masterListener;

        materConfiguration = SpringHelper.popBean(MaterConfiguration.class);

        //启动netty server
        startServer();

        //异步从数据库加载状态是RUNNING的任务,执行..加载其他任务到quartz中
        loadTaskFromDB();

        //从数据库加载配置的work机器信息,到内存中
        MasterContext.start().refreshHostGroupCache();

        //从队列中获取任务,执行
        waitingQueue();

    }


    private void startServer() {
        NettyServer nettyServer = new NettyServer(materConfiguration.getConnectPort());
        nettyServer.start(masterListener);
    }

    private void loadTaskFromDB() {

    }

    private void waitingQueue() {

        MasterContext.start().masterSchedule.schedule(new Runnable() {

            private final Integer DELAY_TIME = 100;
            private final Integer MAX_DELAY_TIME = 10 * 1000;
            private Integer nextTime = 1000;

            @Override
            public void run() {
                try {
                    if (scan()) {
                        nextTime = 1000;
                    } else {
                        nextTime = (nextTime + DELAY_TIME) > MAX_DELAY_TIME ? MAX_DELAY_TIME : nextTime + DELAY_TIME;
                    }
                } catch (Exception e) {
                    log.error("scan waiting queueTask exception", e);
                } finally {
                    MasterContext.start().masterSchedule.schedule(this, nextTime, TimeUnit.MILLISECONDS);
                }
            }
        }, 1000, TimeUnit.MILLISECONDS);

    }



    //从队列中获取待调度的任务,开始执行
    private boolean scan() throws InterruptedException {

        if (!MasterContext.start().getScheduleQueue().isEmpty()) {

            JobElement jobElement = MasterContext.start().getScheduleQueue().take();

            //执行任务
            ClusterSupport support=new ClusterSupport(jobElement);

            ClusterService clusterService=support.buildCluster();

            clusterService.call();

            return true;

        } else {
            return false;
        }
    }


    public interface MasterListener {
        void listener();
    }
}
