package com.fayayo.elephants.service.impl;

import com.fayayo.elephants.entity.WorkHost;
import com.fayayo.elephants.master.MasterContext;
import com.fayayo.elephants.params.WorkHostParams;
import com.fayayo.elephants.repository.WorkHostRepository;
import com.fayayo.elephants.service.WorkHostService;
import com.fayayo.elephants.vo.WorkHostVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dalizu on 2019/2/26.
 * @version v1.0
 * @desc
 */
@Service
@Slf4j
public class WorkHostServiceImpl implements WorkHostService{

    @Autowired
    private WorkHostRepository workHostRepository;


    @Override
    public List<WorkHost> list() {
        return workHostRepository.findAll();
    }

    @Override
    public WorkHost add(WorkHostParams workHostParams) {

        WorkHost workHost=new WorkHost();

        BeanUtils.copyProperties(workHostParams,workHost);

        workHost=workHostRepository.save(workHost);

        //刷新work 地址信息
        MasterContext.start().refreshHostGroupCache();

        return workHost;
    }


}
