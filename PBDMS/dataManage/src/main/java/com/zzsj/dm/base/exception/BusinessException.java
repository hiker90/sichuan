package com.zzsj.dm.base.exception;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/2 10:07
 * @description：业务异常
 */
public class BusinessException extends Exception {

    public BusinessException(String message) {
        super(message);
    }
}
