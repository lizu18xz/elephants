package com.fayayo.elephants.exception;

/**
 * @author dalizu on 2019/3/1.
 * @version v1.0
 * @desc 常见的框架服务异常
 */
public class ElephantsServiceException extends RuntimeException{

    public ElephantsServiceException(String message) {

        super(message);
    }

}
