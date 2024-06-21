package com.zzsj.dm.base.shiro;

import com.zzsj.dm.base.exception.BusinessException;
import com.zzsj.dm.base.pojo.vo.SysUser;
import com.zzsj.dm.base.utils.CookieUtil;
import com.zzsj.dm.base.utils.JwtUtil;
import com.zzsj.dm.base.utils.RedisUtil;
import com.zzsj.dm.base.utils.enums.LoginType;
import com.zzsj.dm.manage.service.*;
import com.zzsj.dm.manage.utils.UserUtil;
import io.jsonwebtoken.Claims;
import io.netty.util.internal.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ：zbya
 * @date ：Created in 2020/12/2 15:10
 * @description：shiro配置
 */

@Slf4j
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private LoginService loginService;

    @Autowired
    RedisUtil redisUtil;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) throws AuthenticationException {
        if(!ObjectUtils.allNotNull(UserUtil.userInfo().get("loginName"))){
            throw new AuthenticationException("nouser");
        }
        String token=UserUtil.getToken();
            // 判断是否登录状态
            if (redisUtil.get(token)!=null) {
                SimpleAuthorizationInfo s = (SimpleAuthorizationInfo) redisUtil.get(token);
                redisUtil.expire(token, 60 * 60*4);
                return (SimpleAuthorizationInfo) redisUtil.get(token);
            }
            else {
                throw new AuthenticationException("timeout");
            }
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UserToken upToken = (UserToken) authenticationToken;
        LoginType type = upToken.getType();
        String username = upToken.getUsername();
        String password = "";
        if (upToken.getPassword() != null)
        {
            password = new String(upToken.getPassword());
        }

        SysUser user = null;
        try
        {

             if (LoginType.NOPASSWD.equals(type))
            {
                user = loginService.login(username);
            }
        }
        catch (Exception e)
        {
            log.info("对用户[" + username + "]进行登录验证..验证未通过{}", e.getMessage());
            throw new AuthenticationException(e.getMessage(), e);
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
        return info;
    }

    /**
     * 清理缓存权限
     */
    public void clearCachedAuthorizationInfo() {
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }
}
