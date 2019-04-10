package com.fayayo.elephants.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author dalizu on 2019/2/27.
 * @version v1.0
 * @desc Master相关的配置
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "master")
@Configuration
public class MaterConfiguration {

    private boolean haEnable;


    private String masterId;

    private String zkServer;

    private Integer connectPort;


    private float maxMemRate;


    private float maxCpuLoadPerCore;

    //系统本身占用
    private float systemMemUsed;


    //每个任务分配的内存
    private float perTaskUseMem;

}
