package com.jh.shiro;

import com.jh.domain.SysUser;
import com.jh.utils.CommonUtils;
import com.jh.utils.bean.BeanUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;

/**
 * shiro 工具类
 *
 * @author ruoyi
 */
public class ShiroUtils
{
    public static Subject getSubject()
    {
        return SecurityUtils.getSubject();
    }

    public static SysUser getSysUser()
    {
        SysUser user = null;
        Object obj = getSubject().getPrincipal();
        if (CommonUtils.isNotNull(obj))
        {
            user = new SysUser();
            BeanUtils.copyBeanProp(user, obj);
        }
        return user;
    }

    public static void setSysUser(SysUser user)
    {
        Subject subject = getSubject();
        PrincipalCollection principalCollection = subject.getPrincipals();
        String realmName = principalCollection.getRealmNames().iterator().next();
        PrincipalCollection newPrincipalCollection = new SimplePrincipalCollection(user, realmName);
        // 重新加载Principal
        subject.runAs(newPrincipalCollection);
    }
}