package com.fayayo.elephants.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author dalizu on 2019/2/20.
 * @version v1.0
 * @desc 排除作用的主键
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface IgnoreResponseAdvice {
}
