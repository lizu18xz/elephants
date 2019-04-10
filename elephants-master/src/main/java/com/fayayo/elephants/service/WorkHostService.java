package com.fayayo.elephants.service;

import com.fayayo.elephants.entity.WorkHost;
import com.fayayo.elephants.params.WorkHostParams;

import java.util.List;

/**
 * @author dalizu on 2019/2/26.
 * @version v1.0
 * @desc
 */
public interface WorkHostService {


    List<WorkHost>list();

    WorkHost add( WorkHostParams workHostParams);

}
