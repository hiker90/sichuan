package com.zzsj.dm.manage.controller;

import com.zzsj.dm.base.exception.BusinessException;
import com.zzsj.dm.base.json.AjaxJson;
import com.zzsj.dm.base.shiro.UserToken;
import com.zzsj.dm.base.utils.CookieUtil;
import com.zzsj.dm.base.utils.JwtUtil;
import com.zzsj.dm.base.utils.enums.LoginType;
import com.zzsj.dm.base.utils.enums.ResultEnum;
import com.zzsj.dm.manage.service.UserService;
import com.zzsj.dm.manage.utils.UserUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：zbya
 * @date ：Created in 2020/12/8 9:08
 * @description：登录管理
 */
@Slf4j
@RestController
public class LoginController {

    @Autowired
    UserService userService;

    @RequestMapping("/login")
    @ResponseBody
    //@SysControllerLog(description = "用户登录")
    public AjaxJson login(String token,HttpServletRequest request, HttpServletResponse response) throws Exception {
        Claims userClaims= UserUtil.userInfo(token);
        UserToken userToken = new UserToken((String)userClaims.get("loginName"),LoginType.NOPASSWD);
        Subject subject = SecurityUtils.getSubject();
        try
        {
            subject.login(userToken);
            Map map=new HashMap();
            map.put("userName",userClaims.get("userName"));
            map.put("dept",userClaims.get("dept"));
            map.put("catalog",userService.selectPermsByLoginName((String)userClaims.get("loginName"),(String)userClaims.get("roleKey"),"C","pbdms"));
            map.put("button",userService.selectPermsByLoginName((String)userClaims.get("loginName"),(String)userClaims.get("roleKey"),"F","pbdms"));

            return new AjaxJson(map);
        }
        catch (Exception e)
        {
            log.error("login" + e);
            throw new BusinessException(e.getMessage());
        }
    }

    @RequestMapping("/notlogin")
    @ResponseBody
    //@SysControllerLog(description = "用户登录失败")
    public AjaxJson notlogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try
        {
            return new AjaxJson(false,"nopermission");
        }
        catch (Exception e)
        {
            log.error("notlogin" + e);
            throw new BusinessException(e.getMessage());
        }
    }
}
