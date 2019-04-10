package com.fayayo.elephants.zookeeper;

import com.fayayo.elephants.closable.ShutDownHook;
import com.fayayo.elephants.constants.ElephantConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import java.io.Closeable;
import java.io.IOException;

/**
 * @author dalizu on 2019/1/2.
 * @version v1.0
 * @desc 用于监听 master 切换
 */
@Slf4j
public class ZkUtils implements Closeable {

    private CuratorFramework zkClient;

    private NodeCache nodeCache;

    private WatchNotify watchNotify;

    public ZkUtils(CuratorFramework zkClient) {

        this.zkClient = zkClient;

        log.info("zk register success!");

        String parentPath = ElephantConstants.MASTER_ADDRESS;
        try {
            if (zkClient.checkExists().forPath(parentPath) == null) {
                log.info("init zookeeper registry namespace");

                zkClient.create().creatingParentsIfNeeded()
                        .withMode(CreateMode.PERSISTENT)
                        .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                        .forPath(parentPath);

            }

            ShutDownHook.registerShutdownHook(this);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Failed to subscribe zookeeper");
        }
    }


    public void subscribeDataChanges() {
        log.info("subscribe data changes");
        nodeCache = new NodeCache(zkClient, ElephantConstants.MASTER_ADDRESS);
        try {
            nodeCache.start(true);
            nodeCache.getListenable().addListener(new NodeCacheListener() {

                @Override
                public void nodeChanged() throws Exception {

                    String data = new String(nodeCache.getCurrentData().getData());
                    log.info(String.format("[ZkUtils] master address change: path=%s, currentData=%s",
                            ElephantConstants.MASTER_ADDRESS, data));

                    if (watchNotify != null) {
                        watchNotify.notify(newAddress(data));
                    }
                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getData(){

        try {
            byte[] result = zkClient.getData().forPath(ElephantConstants.MASTER_ADDRESS);
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("get address error!!!");
        }
        return null;
    }


    public void setWatchNotify(WatchNotify watchNotify) {
        this.watchNotify = watchNotify;
    }

    private String newAddress(String currentData) {

        return currentData;
    }

    @Override
    public void close() throws IOException {
        log.info("work zk close");

        CloseableUtils.closeQuietly(nodeCache);

        CloseableUtils.closeQuietly(zkClient);

    }

}
