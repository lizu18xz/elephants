package com.fayayo.elephants.closable;

import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author zhanran
 * Date: 2017/5/24
 * add a shutDownHook to close some global resources
 */
@Slf4j
public class ShutDownHook extends Thread {
    //Smaller the priority is,earlier the resource is to be closed,default Priority is 20
    private static final int defaultPriority = 20;
    //only global resource should be register to ShutDownHook,don't register connections to it.
    private static ShutDownHook instance;
    private ArrayList<closableObject> resourceList = new ArrayList<closableObject>();

    private ShutDownHook() {
    }


    private static void init() {
        if (instance == null) {
            instance = new ShutDownHook();
            log.info("ShutdownHook is initialized");
        }
    }

    @Override
    public void run() {
        closeAll();
    }

    public static void runHook(boolean sync) {
        if (instance != null) {
            if (sync)
                instance.run();
            else
                instance.start();
        }
    }

    //synchronized method to close all the resources in the list
    private synchronized void closeAll() {
        Collections.sort(resourceList);
        log.info("Start to close global resource due to priority");
        for (closableObject resource : resourceList) {
            try {
                resource.closable.close();
            } catch (Exception e) {
                log.error("Failed to close " + resource.closable.getClass(), e);
            }
            log.info("Success to close " + resource.closable.getClass());
        }
        log.info("Success to close all the resource!");
        resourceList.clear();
    }

    public static void registerShutdownHook(Closeable closable) {
        registerShutdownHook(closable, defaultPriority);
    }

    public static synchronized void registerShutdownHook(Closeable closable, int priority) {
        if (instance == null) {
            init();
        }
        instance.resourceList.add(new closableObject(closable, priority));
        log.info("add resource " + closable.getClass() + " to list");
    }

    private static class closableObject implements Comparable<closableObject> {
        Closeable closable;
        int priority;

        public closableObject(Closeable closable, int priority) {
            this.closable = closable;
            this.priority = priority;
        }

        @Override
        public int compareTo(closableObject o) {
            if (this.priority > o.priority) return -1;
            else if (this.priority == o.priority) return 0;
            else return 1;
        }
    }
}
