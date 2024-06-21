package com.zzsj.analysis.base.shiro;


import com.zzsj.analysis.base.utils.CookieUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.Serializable;

/**
 * @author ：zbya
 * @date ：Created in 2020/12/28 16:21
 * @description：自定义sessionId生成器
 */
public class CustomsSessionIdGenerator implements SessionIdGenerator {

    @Override
    public Serializable generateId(Session session) {
        return CookieUtil.getValue(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest(), "token");
    }
}
