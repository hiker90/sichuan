package com.zzsj.dm.manage.service.impl;


import com.zzsj.dm.base.utils.CookieUtil;
import com.zzsj.dm.base.utils.JwtUtil;
import com.zzsj.dm.manage.controller.SysControllerLog;
import com.zzsj.dm.manage.mapper.mysql.SystemLogMapper;
import com.zzsj.dm.manage.pojo.vo.SysLogVo;
import com.zzsj.dm.manage.utils.IpUtils;
import com.zzsj.dm.manage.utils.UserUtil;
import com.zzsj.dm.manage.utils.UuidUtil;
import eu.bitwalker.useragentutils.UserAgent;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;


/**
 * @author ：zbya
 * @date ：Created in 2020/9/15 11:15
 * @description：切点类
 */
@Aspect
@Component
@Slf4j
@SuppressWarnings("all")
public class SystemLogAspect {
    //注入Service用于把日志保存数据库，实际项目入库采用队列做异步
      @Autowired
    SystemLogMapper systemLogMapper;

    //Service层切点
    @Pointcut("@annotation(com.zzsj.dm.manage.service.SysServiceLog)")
    public void serviceAspect() {
    }

    //Controller层切点
    @Pointcut("@annotation(com.zzsj.dm.manage.controller.SysControllerLog)")
    public void controllerAspect() {
    }


    /**
     * Description: 操作日志
     * @Date: 2020/9/27 16:22
     * @Author: zbya
     *
     * @param joinPoint
     * @param data
     * @return: void
     */
    @AfterReturning(value = "controllerAspect()",returning = "data")
    public void saveOperLog(JoinPoint joinPoint,Object data){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Claims userClaims= UserUtil.userInfo();
        //获取请求头中的User-Agent
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        // 从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点
        Method method=signature.getMethod();
        //获取操作
        SysControllerLog sysLog=method.getAnnotation(SysControllerLog.class);
        String ip = IpUtils.getIpAddr(request);
        SysLogVo sysLogVo=new SysLogVo();
        //主键
        sysLogVo.setId(UuidUtil.get());
        //操作
        sysLogVo.setOperate(sysLog.method());
        //操作人
        sysLogVo.setOperateUser(userClaims.get("userName").toString());
        //所属单位
        sysLogVo.setUnit(userClaims.get("dept").toString());
        //ip
        sysLogVo.setIp(ip);
        //请求参数
        if(Arrays.toString(joinPoint.getArgs()).length()<1000){
            sysLogVo.setQueryParam(Arrays.toString(joinPoint.getArgs()));
        }
        else{
            sysLogVo.setQueryParam("部分展示......"+Arrays.toString(joinPoint.getArgs()).substring(0,900));
        }
        //模块
        sysLogVo.setModule(sysLog.description());
        //请求方式
        sysLogVo.setQueryMethod(request.getMethod());
        //浏览器
        sysLogVo.setBrowser(userAgent.getBrowser().toString());
        //浏览器版本
        sysLogVo.setBrowserVersion(userAgent.getBrowserVersion().toString());
        //操作系统
        sysLogVo.setOperatingSystem(userAgent.getOperatingSystem().toString());
        //返回结果
        if(data.toString().length()<4000){
            sysLogVo.setReturnMes(data.toString());
        }
        else{
            sysLogVo.setReturnMes("部分展示......"+data.toString().substring(0,3900));
        }
        systemLogMapper.insertSysLog(sysLogVo);
    }
}
