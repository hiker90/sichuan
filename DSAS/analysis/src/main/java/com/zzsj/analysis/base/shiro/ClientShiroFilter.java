package com.zzsj.analysis.base.shiro;

import com.zzsj.analysis.base.utils.CookieUtil;
import com.zzsj.analysis.base.utils.RedisUtil;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ：zbya
 * @date ：Created in 2020/12/2 15:26
 * @description：过滤器
 */
public class ClientShiroFilter extends AuthenticationFilter {
    @Autowired
    RedisUtil redisUtil;

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse response1) throws Exception {

        return true;
    }
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse response, Object mappedValue) {

        return true;
    }
}
