package com.fayayo.elephants.controller;

import com.fayayo.elephants.entity.WorkHost;
import com.fayayo.elephants.params.WorkHostParams;
import com.fayayo.elephants.service.WorkHostService;
import com.fayayo.elephants.vo.WorkHostVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author dalizu on 2019/3/12.
 * @version v1.0
 * @desc  TODO  各种校验 都没有做,后期需要 完善很多  基础 的检验和判断
 */
@RestController
@RequestMapping("/workhost")
public class WorkHostController {


    @Autowired
    private WorkHostService workHostService;


    @GetMapping("/list")
    public List<WorkHost> list(){

        return workHostService.list();
    }


    @PostMapping("/add")
    public WorkHost add(@Valid WorkHostParams workHostParams, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            throw new RuntimeException("参数异常");
        }


        return workHostService.add(workHostParams);
    }

}
