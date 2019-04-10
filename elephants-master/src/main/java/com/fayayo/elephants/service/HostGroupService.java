package com.fayayo.elephants.service;

import com.fayayo.elephants.entity.HostGroup;
import com.fayayo.elephants.vo.HostGroupVo;

import java.util.List;
import java.util.Map;

/**
 * @author dalizu on 2019/3/12.
 * @version v1.0
 * @desc
 */
public interface HostGroupService {

    List<HostGroup> getAll();

    Map<Long, HostGroupVo> getAllHostGroupInfo();

}
