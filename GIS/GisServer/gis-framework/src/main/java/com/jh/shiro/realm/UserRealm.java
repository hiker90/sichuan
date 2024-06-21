package com.jh.shiro.realm;

import com.jh.config.JwtConfig;
import com.jh.constant.Constants;
import com.jh.domain.JwtToken;
import com.jh.domain.SysUser;
import com.jh.exception.UserNotExistsException;
import com.jh.jwt.utils.JwtUtil;
import com.jh.redis.utils.RedisUtil;
import com.jh.service.ISysMenuService;
import com.jh.service.ISysRoleService;
import com.jh.service.ISysUserService;
import com.jh.shiro.service.ShiroLoginService;
import com.jh.utils.CommonUtils;
import com.jh.utils.string.StringUtils;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.context.annotation.Lazy;

import java.util.HashSet;
import java.util.Set;

/**
 * 自定义Realm 处理登录 权限
 */
@Slf4j
public class UserRealm extends AuthorizingRealm
{
    @Autowired
    @Lazy
    private ISysMenuService menuService;

    @Autowired
    @Lazy
    private ISysRoleService roleService;

    @Autowired
    @Lazy
    private ShiroLoginService loginService;

    @Autowired
    @Lazy
    private RedisUtil redisUtil;

    /**
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)
    {
        SysUser user = null;

        if (principals != null) {
            user = (SysUser) principals.getPrimaryPrincipal();
        } else {
            return new SimpleAuthorizationInfo();
        }

        // 角色列表
        Set<String> roles = new HashSet<String>();
        // 功能列表
        Set<String> menus = new HashSet<String>();

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 管理员拥有所有权限
        if (user.isAdmin())
        {
            info.addRole("admin");
            info.addStringPermission("*:*:*");
        }
        else
        {
            roles = roleService.selectRoleKeysByUserId(user.getUserId());
            menus = menuService.selectPermsByUserId(user.getUserId());

            // 角色加入AuthorizationInfo认证对象
            info.setRoles(roles);
            // 权限加入AuthorizationInfo认证对象
            info.setStringPermissions(menus);
        }
        return info;
    }

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException
    {
        // 获取token
        String token = (String) auth.getCredentials();
        if (StringUtils.isEmpty(token)) {
            throw new AuthenticationException("token为空!");
        }

        // 获取loginname
        String loginname = JwtUtil.getLoginname(token);
        if (StringUtils.isEmpty(loginname)) {
            throw new AuthenticationException("token中账号为空!");
        }

        SysUser user = null;
        String userkey = Constants.PREFIX_SHIRO_USER_CACHE + loginname;
        if (redisUtil.hasKey(userkey)){
            Object o = redisUtil.get(userkey);
            user = (SysUser) o;
        } else {
            try
            {
                user = loginService.login(loginname);
            } catch (UserNotExistsException e)
            {
                throw new AuthenticationException("用户不存在！");
            }

            if (CommonUtils.isNull(user)) {
                throw new AuthenticationException("用户不存在！");
            }

            redisUtil.set(userkey, user, JwtConfig.getShiroCacheExpireTime());
        }

        // 开始认证，要AccessToken认证通过，且Redis中存在token
        String salt = JwtUtil.getClaimSalt(token);
        String tokenkey = Constants.PREFIX_SHIRO_TOKEN + loginname + "_" + salt;

        if (JwtUtil.verify(token) && redisUtil.hasKey(tokenkey)) {
            // 刷新token过期时间
            redisUtil.expire(tokenkey, JwtConfig.getAccessTokenExpireTime());
            //user.setSalt(salt);
            return new SimpleAuthenticationInfo(user, token, getName());
        }
        throw new AuthenticationException("token已过期或无效！");
    }

    /**
     * 清理缓存权限
     */
    public void clearCachedAuthorizationInfo()
    {
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }
}