package com.zzsj.analysis.manage.utils;

import com.zzsj.analysis.base.utils.CookieUtil;
import com.zzsj.analysis.base.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：zbya
 * @date ：Created in 2020/12/14 10:42
 * @description：获取人员信息工具
 */
public class UserUtil {
    public static Claims userInfo(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = CookieUtil.getValue(request, "token");
        return JwtUtil.parseToken(token);
    }

    public static Claims userInfo(String token){
        return JwtUtil.parseToken(token);
    }

    public static String getToken(){
        return CookieUtil.getValue(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest(), "token");
    }
}
