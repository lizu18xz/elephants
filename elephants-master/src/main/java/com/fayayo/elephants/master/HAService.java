package com.fayayo.elephants.master;

import com.fayayo.elephants.Constants;
import com.fayayo.elephants.closable.ShutDownHook;
import com.fayayo.elephants.config.MaterConfiguration;
import com.fayayo.elephants.constants.ElephantConstants;
import com.fayayo.elephants.utils.NetUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author dalizu on 2019/2/25.
 * @version v1.0
 * @desc 通过zk来 实现选主的流程
 */
@Slf4j
public class HAService implements Closeable{

    private CuratorFramework client = null;

    private LeaderLatch leaderLatch;

    private MaterConfiguration materConfiguration;

    public HAService(MaterConfiguration materConfiguration) {
        this.materConfiguration= materConfiguration;
        init();
    }

    //初始化zk服务
    public void init(){
        if (client != null) {
            return;
        }

        //启动zk客户端
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 5);

        client = CuratorFrameworkFactory.builder().connectString(this.materConfiguration.getZkServer())
                .sessionTimeoutMs(10000).connectionTimeoutMs(10000).retryPolicy(retryPolicy).build();

        ShutDownHook.registerShutdownHook(this);//加入到hook事件
    }


    public void start() throws Exception {
        client.start();
        if(materConfiguration.isHaEnable()){
            // 创建选举实例
            leaderLatch = new LeaderLatch(client, Constants.LATCH_PATH, materConfiguration.getMasterId());
            // 添加选举监听
            leaderLatch.addListener(new LeaderLatchListener() {
                @Override
                public void isLeader() {
                    log.info("master id:{}",leaderLatch.getId());
                    // 如果成为master则触发
                    toActive();
                }

                @Override
                public void notLeader() {
                    log.info("slave id:{}",leaderLatch.getId());
                    // 如果从主节点变成从主节点则触发
                    toStandby();
                }
            });
            // 加入选举
            leaderLatch.start();

        }else {
            MasterService masterService=new MasterService();
            //启动server成功后,先注册,然后启动具体业务逻辑
            masterService.init(masterListener);
        }

    }

    private void toActive() {
        //启动server成功后,先注册,然后启动具体业务逻辑
        MasterService masterService=new MasterService();
        masterService.init(masterListener);
    }


    private void setMasterAddress() {
        //写入当前服务地址到zk,work对地址进行监听
        try {

            if (client.checkExists().forPath(ElephantConstants.MASTER_ADDRESS) == null) {
                client.create().creatingParentsIfNeeded()
                        .withMode(CreateMode.PERSISTENT)        // 节点类型：持久节点
                        .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)            // acl：匿名权限
                        .forPath(ElephantConstants.MASTER_ADDRESS);
            }

            log.info("set master address:【{}】",NetUtils.getLocalAddress());
            client.setData().forPath(ElephantConstants.MASTER_ADDRESS,NetUtils.getLocalAddress().getBytes());

        } catch (Exception e) {
            log.error("set master address error!!!");
            e.printStackTrace();
        }
    }

    private void toStandby() {
        //TODO 变为从节点后,关闭需要的服务

    }

    @Override
    public void close() throws IOException {
        log.info("master close......");
        if(leaderLatch!=null){
            leaderLatch.close();
        }

        if (client != null) {
           CloseableUtils.closeQuietly(client);
        }
    }


    private final MasterService.MasterListener masterListener=new MasterService.MasterListener() {
        @Override
        public void listener() {
            setMasterAddress();
        }
    };



}
