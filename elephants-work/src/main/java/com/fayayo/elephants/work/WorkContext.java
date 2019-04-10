package com.fayayo.elephants.work;

import com.fayayo.elephants.tools.RunShell;
import com.fayayo.elephants.utils.GlobalEnvironment;
import com.fayayo.elephants.utils.NetUtils;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author dalizu on 2019/2/27.
 * @version v1.0
 * @desc work 上下文信息
 */
@Slf4j
@Getter
@Setter
public class WorkContext {

    public static String host;//work 地址

    public static Integer cpuCoreNum;//cup core 数目

    private static final String loadStr = "cat /proc/cpuinfo |grep processor | wc -l";

    static {

        host = NetUtils.getLocalAddress();

        log.info("-----------------------------当前机器的IP为:{}-----------------------------", host);
        if (GlobalEnvironment.isLinuxSystem()) {
            RunShell shell = new RunShell(loadStr);
            Integer exitCode = shell.run();
            if (exitCode == 0) {
                try {
                    cpuCoreNum = Integer.parseInt(shell.getResult());
                } catch (IOException e) {
                    e.printStackTrace();
                    cpuCoreNum = 4;
                }
            }
        } else {
            cpuCoreNum = 4;
        }
        log.info("-----------------------------当前机器的cpuCore为:{}-----------------------------", cpuCoreNum);
    }

    private static WorkContext INSTANCE= new WorkContext();;

    //netty 客户端 和服务端 连接的通道
    private Channel serverChannel;

    //构建单列
    private WorkContext(){

    }

    //构建单列
    public static WorkContext start() {
        return INSTANCE;
    }




}
