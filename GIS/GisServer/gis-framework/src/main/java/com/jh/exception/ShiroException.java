package com.jh.exception;

import com.jh.enums.ErrorCode;
import com.jh.utils.ajax.AjaxResult;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ShiroException
{
    /**
     * 权限校验失败 如果请求为ajax返回json，普通请求跳转页面
     */
    @ExceptionHandler(AuthorizationException.class)
    public String AuthorizationException(Exception ex) {
        return AjaxResult.error("权限不足！", ErrorCode.ERR_NEED_AUTH);
    }
}
