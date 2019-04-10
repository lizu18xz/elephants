package com.fayayo.elephants.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author dalizu on 2019/2/20.
 * @version v1.0
 * @desc
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer{

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        //清空
        converters.clear();

        //加入 java 转 json  消息转换器
        converters.add(new MappingJackson2HttpMessageConverter());

    }
}
