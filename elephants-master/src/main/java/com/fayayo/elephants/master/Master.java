package com.fayayo.elephants.master;

import com.fayayo.elephants.config.MaterConfiguration;
import com.fayayo.elephants.tools.SpringHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author dalizu on 2019/2/25.
 * @version v1.0
 * @desc master核心控制类
 */
@Slf4j
@Component
public class Master implements InitializingBean,ApplicationContextAware {

    @Autowired
    private MaterConfiguration materConfiguration;

    //启动master
    public void start() {

        //先设置master的主从
        try {
            log.info("enable ha service......");
            HAService haService = new HAService(materConfiguration);
            haService.start();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("master start error!!!");
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        start();
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringHelper.setApplicationContext(applicationContext);
    }
}
