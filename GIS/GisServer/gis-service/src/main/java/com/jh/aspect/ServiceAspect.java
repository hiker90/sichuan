//package com.jh.aspect;
//
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Order(1)
//@Slf4j
//@Component
//public class ServiceAspect
//{
//    @Pointcut("execution(* com.jh.service.impl..*.*(..)))")
//    public void dsPointCut()
//    {
//
//    }
//
//    @Around("dsPointCut()")
//    public Object doAround(ProceedingJoinPoint point) throws Throwable
//    {
//        log.debug("do Around begin");
//        //DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
//
//        try
//        {
//            return point.proceed();
//        }
//        finally
//        {
//            // 销毁数据源 在执行方法之后
//            //DynamicDataSourceContextHolder.clearDataSourceType();
//            log.debug("do Around end");
//        }
//    }
//}
