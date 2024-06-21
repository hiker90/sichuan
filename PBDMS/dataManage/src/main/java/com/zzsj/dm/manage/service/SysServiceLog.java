package com.zzsj.dm.manage.service;

import java.lang.annotation.*;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/15 10:52
 * @description：自定义注解，拦截service
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysServiceLog {
    String description() default "";
}
