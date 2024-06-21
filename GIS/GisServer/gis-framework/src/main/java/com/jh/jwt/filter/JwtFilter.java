package com.jh.jwt.filter;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.jh.constant.Constants;
import com.jh.domain.JwtToken;
import com.jh.enums.ErrorCode;
import com.jh.utils.ajax.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 鉴权登录拦截器
 **/
@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter
{
    /**
     * 执行登录认证
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        try {
            executeLogin(request, response);
            return true;
        } catch (Exception e) {
            // 认证出现异常，传递错误信息msg
            String msg = e.getMessage();
            // 获取应用异常(该Cause是导致抛出此throwable(异常)的throwable(异常))
            Throwable throwable = e.getCause();
            if (throwable instanceof SignatureVerificationException) {
                // 该异常为JWT的AccessToken认证失败(Token或者密钥不正确)
                msg = "token或者密钥不正确(" + throwable.getMessage() + ")";
            } else {
                // 应用异常不为空
                if (throwable != null) {
                    // 获取应用异常msg
                    msg = throwable.getMessage();
                }
            }
            // 直接返回Response信息
            this.response401(request, response, msg);
        }
        return false;
    }

    /**
     *
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader(Constants.ACCESS_TOKEN);

        JwtToken jwtToken = new JwtToken(token);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(jwtToken);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers",
                httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个OPTIONS请求，这里我们给OPTIONS请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    /**
     * 无需转发，直接返回Response信息
     */
    private void response401(ServletRequest req, ServletResponse resp, String msg) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        try (PrintWriter out = httpServletResponse.getWriter())
        {
            String data = AjaxResult.error(msg, ErrorCode.ERR_NEED_LOGIN);
            out.append(data);
        } catch (IOException e)
        {
            log.error("系统异常:" + e.getMessage());
        }
    }
}