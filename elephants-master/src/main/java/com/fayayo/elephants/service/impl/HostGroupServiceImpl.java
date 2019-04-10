package com.fayayo.elephants.service.impl;

import com.fayayo.elephants.entity.HostGroup;
import com.fayayo.elephants.entity.WorkHost;
import com.fayayo.elephants.repository.HostGroupRepository;
import com.fayayo.elephants.service.HostGroupService;
import com.fayayo.elephants.service.WorkHostService;
import com.fayayo.elephants.vo.HostGroupVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dalizu on 2019/3/12.
 * @version v1.0
 * @desc
 */
@Service
@Slf4j
public class HostGroupServiceImpl implements HostGroupService{

    @Autowired
    private HostGroupRepository hostGroupRepository;

    @Autowired
    private WorkHostService workHostService;


    @Override
    public List<HostGroup> getAll() {
        return hostGroupRepository.findAll();
    }

    @Override
    public Map<Long, HostGroupVo> getAllHostGroupInfo() {
        List<HostGroup> groupList = this.getAll();

        Map<Long, HostGroupVo> hostGroupInfoMap = new HashMap<>(groupList.size());

        List<WorkHost> workHostList = workHostService.list();

        groupList.forEach(heraHostGroup -> {
            if(heraHostGroup.getEffective() == 1) {
                HostGroupVo vo = HostGroupVo.builder()
                        .id(String.valueOf(heraHostGroup.getId()))
                        .name(heraHostGroup.getName())
                        .description(heraHostGroup.getDescription())
                        .build();
                List<String> hosts = new ArrayList<>();
                workHostList.forEach(workHost -> {
                    if(workHost.getHostGroupId() == (heraHostGroup.getId())) {
                        hosts.add(workHost.getHost());
                    }
                });
                vo.setHosts(hosts);
                hostGroupInfoMap.put(heraHostGroup.getId(), vo);
            }
        });
        return hostGroupInfoMap;
    }
}
