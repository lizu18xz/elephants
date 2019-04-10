package com.fayayo.elephants.advice;

import com.fayayo.elephants.annotation.IgnoreResponseAdvice;
import com.fayayo.elephants.vo.Response;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author dalizu on 2019/2/20.
 * @version v1.0
 * @desc 拦截Controller方法默认返回参数
 */
@RestControllerAdvice
public class ResponseDataAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter,
                            Class<? extends HttpMessageConverter<?>> aClass) {

        //拦截 类和方法

        if(methodParameter.getDeclaringClass().isAnnotationPresent(IgnoreResponseAdvice.class)){
            return false;
        }

        if (methodParameter.getMethod().isAnnotationPresent(
                IgnoreResponseAdvice.class
        )) {
            return false;
        }

        return true;
    }

    @Nullable
    @Override
    public Object beforeBodyWrite(@Nullable Object o, MethodParameter methodParameter,
                                  MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        Response <Object>response=new Response<Object>(0,"");

        if (null == o) {
            return response;
        } else if (o instanceof Response) {
            response = (Response<Object>) o;
        } else {
            response.setData(o);
        }
        return response;
    }

}
