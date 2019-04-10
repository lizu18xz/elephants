package com.fayayo.elephants.advice;

import com.fayayo.elephants.exception.ElephantsCommonException;
import com.fayayo.elephants.vo.Response;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author dalizu on 2019/2/20.
 * @version v1.0
 * @desc 异常处理
 */
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(value = ElephantsCommonException.class)
    public Response<String> handlerAdException(HttpServletRequest req,
                                               ElephantsCommonException ex) {
        Response<String> response = new Response<>(-1,
                "business error");
        response.setData(ex.getMessage());
        return response;
    }


}
