package com.fayayo.elephants.work;

import com.fayayo.elephants.config.WorkConfiguration;
import com.fayayo.elephants.request.HeartBeatRequest;
import com.fayayo.elephants.client.NettyClient;
import com.fayayo.elephants.tools.SpringHelper;
import com.fayayo.elephants.zookeeper.WatchNotify;
import com.fayayo.elephants.zookeeper.ZkUtils;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dalizu on 2019/2/27.
 * @version v1.0
 * @desc work的控制类
 */
@Slf4j
@Component
public class Work implements WatchNotify, InitializingBean, ApplicationContextAware {


    @Autowired
    private ZkUtils zkUtils;

    @Autowired
    private WorkConfiguration workConfiguration;

    public ScheduledThreadPoolExecutor workSchedule;

    {
        workSchedule = new ScheduledThreadPoolExecutor(1, new WorkThreadFactory("work-schedule"));
        workSchedule.setKeepAliveTime(5, TimeUnit.MINUTES);
        workSchedule.allowCoreThreadTimeOut(true);
    }

    /**
     * work启动入口, 向master 进行 netty  连接,注册。
     */

    public void start(String host) {

        NettyClient nettyClient = new NettyClient();

        Channel channel = nettyClient.connect(host, workConfiguration.getConnectPort());
        if (channel == null) {
            log.error("connect server failed ");
            return;
        }
        //连接成功设置到上下文中
        WorkContext workContext = WorkContext.start();
        workContext.setServerChannel(channel);

        //启动定时任务,获取work机器信息,发送给master
        HeartBeatRequest heartBeatRequest = new HeartBeatRequest();
        workSchedule.schedule(new Runnable() {

            //发送失败的次数
            private int failCount = 0;

            @Override
            public void run() {

                try {
                    boolean idSend = heartBeatRequest.send(workContext);
                    if (idSend) {
                        failCount = 0;
                        log.info("send heartBeat success. remoteAddress:{}", workContext.getServerChannel().remoteAddress());
                    } else {
                        failCount++;
                        log.info("send heartBeat error,failCount:{}", failCount);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    workSchedule.schedule(this, (failCount + 1) * workConfiguration.getHeartBeat(), TimeUnit.SECONDS);
                }
            }

        }, workConfiguration.getHeartBeat(), TimeUnit.SECONDS);

    }


    //当获取到主节点信息后后就启动
    @Override
    public void notify(String currentData) {
        log.info("master change, now start reconnect......{}", currentData);
        start(currentData);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        //启动work,启动监听
        start(zkUtils.getData());
        zkUtils.setWatchNotify(this);
        zkUtils.subscribeDataChanges();
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringHelper.setApplicationContext(applicationContext);
    }


    static class WorkThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        WorkThreadFactory(String namePrefix) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            this.namePrefix = namePrefix;
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }

}
