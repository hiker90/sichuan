package com.jh.shiro.filter;

import com.alibaba.fastjson.JSONObject;
import com.jh.enums.ErrorCode;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class MyPermissionsAuthorizationFilter extends PermissionsAuthorizationFilter
{

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException
    {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setStatus(200);
        httpServletResponse.setContentType("application/json;charset=utf-8");

        PrintWriter out = httpServletResponse.getWriter();
        JSONObject json = new JSONObject();
        json.put("retcode", ErrorCode.ERR_NEED_AUTH.ordinal());
        json.put("msg","无操作权限");
        out.println(json);
        out.flush();
        out.close();
        return false;
    }

}