package com.jh.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jh.config.JwtConfig;
import com.jh.config.SystemConfig;
import com.jh.constant.Constants;
import com.jh.controller.BaseController;
import com.jh.domain.SysMenu;
import com.jh.domain.SysUser;
import com.jh.enums.ErrorCode;
import com.jh.jwt.utils.JwtUtil;
import com.jh.redis.utils.RedisUtil;
import com.jh.service.ISysMenuService;
import com.jh.service.ISysUserService;
import com.jh.shiro.ShiroUtils;
import com.jh.utils.CommonUtils;
import com.jh.utils.ajax.AjaxResult;
import com.jh.utils.string.StringUtils;
import com.jh.web.domain.SSOUserModel;
import com.jh.web.exception.SSOUserFetchErrorException;
import com.jh.web.service.SSOLoginService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

@Slf4j
@RestController
public class LoginController extends BaseController
{
    @Autowired
    ISysUserService userService;

    @Autowired
    ISysMenuService menuService;

    @Autowired
    RedisUtil redisUtil;

    @PostMapping("/login")
    public String doLogin(@RequestBody JSONObject jsonParam, HttpServletResponse response) {
        // 参数检查
        String username = jsonParam.getString("username");
        String password = jsonParam.getString("password");

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return AjaxResult.error("参数错误！");
        }

        // SSO登录
        SSOUserModel userModel;
        try
        {
            userModel = SSOLoginService.login(username, password);
        } catch (RestClientException | SSOUserFetchErrorException e) {
            return AjaxResult.error("系统异常，请稍后重试！");
        }

        if (CommonUtils.isNull(userModel)) {
            return AjaxResult.error("用户名密码错误，请确认后重试！");
        }

        // 通过IDCARD获取用户信息
        SysUser user = userService.selectUserByIdcard(userModel.getIdCardNum());

        if (CommonUtils.isNull(user) || !user.getIdcard().equals(userModel.getIdCardNum())) {
            return AjaxResult.error("用户不存在！");
        }

        boolean hasPerms = false;
        List<String> gismenus = new LinkedList<String>();
        if (user.isAdmin()) {
            gismenus.add("*:*:*");
            hasPerms = true;
        }
        else
        {
            // 查询用户权限
            List<SysMenu> menuList = menuService.selectMenuAllByUserId(user.getUserId());

            for (SysMenu menu : menuList)
            {
                if (menu.getMenuId() == 2) {
                    hasPerms = true;
                }
                if (menu.getPerms().startsWith("gis:map:"))
                {
                    gismenus.add(menu.getPerms());
                }
            }
        }
        if (!hasPerms) {
            return AjaxResult.error("权限不足！", ErrorCode.ERR_NEED_AUTH);
        }

        // 生成token并保存在redis中，随后返回给用户
        String salt = JwtUtil.generalSalt();
        String token = JwtUtil.sign(user.getLoginName(), salt);

        String key = Constants.PREFIX_SHIRO_TOKEN + user.getLoginName() + "_" + salt;
        redisUtil.set(key, token, JwtConfig.getAccessTokenExpireTime());

        response.setHeader("Authorization", token);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");

        JSONObject jo = JSONObject.parseObject(AjaxResult.success());
        jo.put("username", user.getUserName());
        jo.put("dept", user.getDept().getDeptName());
        jo.put("perms", gismenus.toArray());
        return jo.toJSONString();
    }

    @CrossOrigin
    @GetMapping("/login/{token}")
    public String doLogin(@PathVariable String token) {
        // 参数检查
        String getuser_url = SystemConfig.getGlurl() + token;

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(getuser_url, String.class);

        JSONObject jo = JSON.parseObject(result);

        if (Integer.parseInt(jo.get("code").toString()) != 0) {
            log.error("登录失败：" + result);
            return AjaxResult.error("登录失败！");
        }

        // 通过IDCARD获取用户信息
        SysUser user = userService.selectUserById(Long.parseLong(jo.getString("userid")));

        if (CommonUtils.isNull(user)
            || !user.getUserId().equals(Long.parseLong(jo.getString("userid")))
            || !user.getUserName().equals(jo.getString("username"))
        ) {
            log.error("登录失败：" + result + ",user:" + user.toString());
            return AjaxResult.error("登录失败！");
        }

        boolean hasPerms = false;
        List<String> gismenus = new LinkedList<String>();
        if (user.isAdmin()) {
            gismenus.add("*:*:*");
            hasPerms = true;
        }
        else
        {
            // 查询用户权限
            List<SysMenu> menuList = menuService.selectMenuAllByUserId(user.getUserId());

            for (SysMenu menu : menuList)
            {
                if (menu.getMenuId() == 2) {
                    hasPerms = true;
                }
                if (menu.getPerms().startsWith("gis:map:"))
                {
                    gismenus.add(menu.getPerms());
                }
            }
        }
        if (!hasPerms) {
            return AjaxResult.error("权限不足！", ErrorCode.ERR_NEED_AUTH);
        }
        jo.put("permsarray", gismenus.toArray());

        // 生成token并保存在redis中，随后返回给用户
        String salt = JwtUtil.generalSalt();
        String auth_token = JwtUtil.sign(user.getLoginName(), salt);

        String key = Constants.PREFIX_SHIRO_TOKEN + user.getLoginName() + "_" + salt;
        redisUtil.set(key, token, JwtConfig.getAccessTokenExpireTime());

        HttpServletResponse response = getResponse();
        response.setHeader("Authorization", auth_token);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");

        return jo.toJSONString();
    }

    @CrossOrigin
    @GetMapping("/logout")
    public String doLogout() {
        SysUser user = ShiroUtils.getSysUser();
        String salt = JwtUtil.getClaimSalt(getRequest().getHeader(Constants.ACCESS_TOKEN));

        // 删除token
        redisUtil.del(Constants.PREFIX_SHIRO_TOKEN + user.getLoginName() + "_" + salt);

        // 登出
        Subject subject = ShiroUtils.getSubject();
        subject.logout();

        return AjaxResult.success();
    }
}
