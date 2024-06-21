package com.zzsj.analysis.manage.controller;

import com.zzsj.analysis.base.constants.OprLogConst;

import java.lang.annotation.*;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/15 10:47
 * @description：自定义注解，拦截controller
 */


@Target({ElementType.PARAMETER, ElementType.METHOD})//作用在参数和方法上
@Retention(RetentionPolicy.RUNTIME)//运行时注解
@Documented//表明这个注解应该被 javadoc工具记录
public @interface SysControllerLog {
    String description() default "";
    int method() default OprLogConst.SELECT;
}
