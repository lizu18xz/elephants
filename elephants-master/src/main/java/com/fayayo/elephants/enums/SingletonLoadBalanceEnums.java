package com.fayayo.elephants.enums;

import com.fayayo.elephants.cluster.LoadBalance;
import com.fayayo.elephants.cluster.loadbalance.ConsistentHashLoadBalance;
import com.fayayo.elephants.cluster.loadbalance.RandomLoadBalance;
import com.fayayo.elephants.cluster.loadbalance.RoundRobinLoadBalance;
import lombok.Getter;

/**
 * @author dalizu on 2019/3/13.
 * @version v1.0
 * @desc
 */
@Getter
public enum  SingletonLoadBalanceEnums {

    RANDOM(1,new RandomLoadBalance()),
    ROUND_ROBIN(2,new RoundRobinLoadBalance()),
    CONSISTENT_HASH(3,new ConsistentHashLoadBalance())
    ;

    private Integer type;

    private LoadBalance loadBalance;


    SingletonLoadBalanceEnums(Integer type, LoadBalance loadBalance) {
        this.type = type;
        this.loadBalance = loadBalance;
    }

    public SingletonLoadBalanceEnums getValueByType(Integer type){

        for (SingletonLoadBalanceEnums singletonLoadBalanceEnums:values()){

            if(singletonLoadBalanceEnums.getType()==type){
                return singletonLoadBalanceEnums;
            }
        }

        throw new RuntimeException("find LoadBalance fail,please check the type of input");

    }


}
