package com.fayayo.elephants.config;

import com.fayayo.elephants.zookeeper.ZkUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dalizu on 2019/2/27.
 * @version v1.0
 * @desc
 */
@Slf4j
@Configuration
public class ZkConfig {

    private static final int SECOND_MILLS = 1000;

    private static final int MINUTE_MILLS = 60 * SECOND_MILLS;

    public static final int SSIONTIMEOUT = 1 * MINUTE_MILLS;


    @Value("${work.zookeeper.address}")
    private String address;

    @Value("${work.zookeeper.timeout}")
    private int timeout;

    @Bean
    public ZkUtils zkUtils() {

        try {
            int sessionTimeout = SSIONTIMEOUT;
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 5);

            CuratorFramework client = CuratorFrameworkFactory.builder().connectString(address)
                    .sessionTimeoutMs(sessionTimeout).connectionTimeoutMs(timeout).retryPolicy(retryPolicy).build();
            client.start();
            return new ZkUtils(client);
        } catch (Exception e) {
            log.error("[ZookeeperRegistry] fail to connect zookeeper, cause: " + e.getMessage());
            throw e;
        }
    }

}
