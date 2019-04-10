package com.fayayo.elephants.controller;

import com.fayayo.elephants.master.MasterContext;
import com.fayayo.elephants.params.ScheduleJobParams;
import com.fayayo.elephants.queue.JobElement;
import com.fayayo.elephants.service.ScheduleCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dalizu on 2019/3/12.
 * @version v1.0
 * @desc TODO 暂时测试使用
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleCenterController {


    @Autowired
    private ScheduleCenterService scheduleCenterService;

     /**
       *@描述 开始调度一个任务任务,获取页面配置的任务的信息,保存到数据库,然后加入到quartz中。 quartz定时执行的时候,获取任务信息加入到队列。
      * 执行完一个任务后,检查是否有依赖。TODO  暂时不支持依赖
     */
    @GetMapping("/scheduleJob")
    public void scheduleJob(ScheduleJobParams scheduleJobParams){






        /*JobElement jobElement=new JobElement();
        jobElement.setJobId("9987654321");
        jobElement.setHostGroupId(1L);

        try {
            MasterContext.start().getScheduleQueue().put(jobElement);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

    }

}
