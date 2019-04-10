package com.fayayo.elephants.cluster;

/**
 * @author dalizu on 2019/2/26.
 * @version v1.0
 * @desc  管理 负载和Ha的功能
 */
public interface Cluster {

    //具体请求的方法
    void call();

}
