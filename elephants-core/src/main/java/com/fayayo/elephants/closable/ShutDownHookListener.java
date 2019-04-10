package com.fayayo.elephants.closable;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author dalizu on 2019/1/3.
 * @version v1.0
 * @desc 为了关闭在tomcat中运行的 server（运行tomcat的shutdown.sh关闭而不是手动kill pid），需要在web.xml中添加ShutDownHookListener
 */
public class ShutDownHookListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
