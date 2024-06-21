package com.zzsj.analysis.manage.controller;

import com.zzsj.analysis.base.exception.BusinessException;
import com.zzsj.analysis.base.json.AjaxJson;
import com.zzsj.analysis.base.shiro.UserToken;
import com.zzsj.analysis.base.utils.CookieUtil;
import com.zzsj.analysis.base.utils.JwtUtil;
import com.zzsj.analysis.base.utils.enums.LoginType;
import com.zzsj.analysis.base.utils.enums.ResultEnum;
import com.zzsj.analysis.manage.service.UserService;
import com.zzsj.analysis.manage.utils.UserUtil;
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

    @RequestMapping("login")
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
            map.put("catalog",userService.selectPermsByLoginName((String)userClaims.get("loginName"),(String)userClaims.get("roleKey"),"C","dsas"));
            map.put("button",userService.selectPermsByLoginName((String)userClaims.get("loginName"),(String)userClaims.get("roleKey"),"F","dsas"));

            return new AjaxJson(map);
        }
        catch (Exception e)
        {
            log.error("login" + e);
            throw new BusinessException(e.getMessage());
        }
    }
}
