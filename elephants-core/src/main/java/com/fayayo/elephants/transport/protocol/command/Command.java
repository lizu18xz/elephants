package com.fayayo.elephants.transport.protocol.command;

/**
 * @author dalizu on 2019/2/26.
 * @version v1.0
 * @desc 指令
 */
public interface Command {

    //work  心跳信息发送
    Byte HEART_BEAT_REQUEST = 1;

    Byte HEART_BEAT_RESPONSE = 2;


    //发送请求执行 任务

    Byte SCHEDULE_REQUEST = 3;

    Byte SCHEDULE_RESPONSE = 4;

}
