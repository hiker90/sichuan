package com.jh.exception;

import com.jh.enums.ErrorCode;
import com.jh.utils.ajax.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class MyCustomException
{
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public String HttpMediaTypeNotSupportedException(Exception ex) {
        log.warn(ex.getMessage());
        return AjaxResult.error("HTTTP请求异常！", ErrorCode.ERR_ERR);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public String HttpRequestMethodNotSupportedException(Exception ex) {
        log.warn(ex.getMessage());
        return AjaxResult.error("HTTTP请求异常！", ErrorCode.ERR_ERR);
    }
}
