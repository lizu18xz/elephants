package com.fayayo.elephants.repository;

import com.fayayo.elephants.entity.WorkHost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author dalizu on 2019/2/26.
 * @version v1.0
 * @desc
 */
public interface WorkHostRepository extends JpaRepository<WorkHost, Long> {


    List<WorkHost> findByHostGroupId(Long hostGroupId);


}
