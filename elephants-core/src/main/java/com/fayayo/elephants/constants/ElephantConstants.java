package com.fayayo.elephants.constants;

/**
 * @author dalizu on 2019/2/27.
 * @version v1.0
 * @desc
 */
public class ElephantConstants {


    //zk注册 默认根路径
    public static final String ZOOKEEPER_REGISTRY_NAMESPACE = "/elephants";

    public static final String PATH_SEPARATOR = "/";

    public static final String MASTER_ADDRESS = ZOOKEEPER_REGISTRY_NAMESPACE+PATH_SEPARATOR+"master-address";


}
