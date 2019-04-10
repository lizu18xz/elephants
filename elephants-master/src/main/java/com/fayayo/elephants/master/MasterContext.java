package com.fayayo.elephants.master;

import com.fayayo.elephants.queue.JobElement;
import com.fayayo.elephants.service.HostGroupService;
import com.fayayo.elephants.tools.SpringHelper;
import com.fayayo.elephants.vo.HostGroupVo;
import com.fayayo.elephants.work.WorkHolder;
import io.netty.channel.Channel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @author dalizu on 2019/2/26.
 * @version v1.0
 * @desc Master的上下文信息
 */
@Slf4j
@Data
public class MasterContext {

    //存储  所有注册到  master 的work 节点信息
    private Map<Channel,WorkHolder>workHolders=new ConcurrentHashMap<Channel,WorkHolder>();

    private static MasterContext INSTANCE = new MasterContext();

    //保存所有需要执行的任务信息
    private BlockingQueue<JobElement> scheduleQueue = new LinkedBlockingQueue<>(10000);


    private Map<Long, HostGroupVo> hostGroupCache;


    protected ScheduledExecutorService masterSchedule;

    //构建单列
    private MasterContext(){

        masterSchedule=Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r, "schedule-scan-queue");
                thread.setDaemon(true);
                return thread;
            }
        });

    }

    //构建单列
    public static MasterContext start() {
        return INSTANCE;
    }

    public synchronized void refreshHostGroupCache() {
        try {

            log.info("refresh host");

            HostGroupService hostGroupService=SpringHelper.popBean(HostGroupService.class);

            hostGroupCache=hostGroupService.getAllHostGroupInfo();

        } catch (Exception e) {
            log.info("refresh host group error");
        }
    }

}
