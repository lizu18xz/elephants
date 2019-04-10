package com.fayayo.elephants.cluster.support;

import com.fayayo.elephants.cluster.HaStrategy;
import com.fayayo.elephants.cluster.LoadBalance;
import com.fayayo.elephants.cluster.ha.FailfastHaStrategy;
import com.fayayo.elephants.cluster.loadbalance.RoundRobinLoadBalance;
import com.fayayo.elephants.enums.SingletonLoadBalanceEnums;
import com.fayayo.elephants.master.MasterContext;
import com.fayayo.elephants.queue.JobElement;
import com.fayayo.elephants.vo.HostGroupVo;
import com.fayayo.elephants.work.Referer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author dalizu on 2019/2/26.
 * @version v1.0
 * @desc
 */
@Slf4j
public class ClusterSupport {

    private JobElement jobElement;

    public ClusterSupport(JobElement jobElement) {
        this.jobElement = jobElement;
    }

    public ClusterService buildCluster(){

        //构造 Ha和LoadBalance
        LoadBalance loadBalance= SingletonLoadBalanceEnums.ROUND_ROBIN.getLoadBalance();
        loadBalance.onRefresh(referers());

        HaStrategy haStrategy=new FailfastHaStrategy();

        ClusterService clusterService=new ClusterService(haStrategy,loadBalance);
        clusterService.setJobElement(jobElement);

        return clusterService;
    }


    private List<Referer> referers(){

        Map<Long, HostGroupVo>hostGroupCache=MasterContext.start().getHostGroupCache();

        HostGroupVo hostGroupVo=hostGroupCache.get(this.jobElement.getHostGroupId());
        if(hostGroupVo==null){
            log.info("~~获取不到当前组内的work ip地址~~");
            return null;
        }
        List<Referer> referers=new ArrayList<>();

        List<String>hosts= hostGroupVo.getHosts();
        if(!CollectionUtils.isEmpty(hosts)){
            Referer referer=null;
            for (String host:hosts){
                referer=new Referer(host);
                referers.add(referer);
            }
        }
        return referers;
    }

}
