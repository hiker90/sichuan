package com.jh.shiro.service;

import com.jh.domain.SysUser;
import com.jh.exception.UserPasswordNotMatchException;
import com.jh.exception.UserNotExistsException;

import com.jh.service.ISysUserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShiroLoginService {
    @Autowired
    private ISysUserService userService;

    public SysUser login(String loginname, String password) throws UserNotExistsException, UserPasswordNotMatchException
    {
        SysUser user = userService.selectUserByLoginName(loginname);

        if (user == null)
        {
            throw new UserNotExistsException();
        }

        // 检查用户名密码
        if (!user.getPassword().equals(encryptPassword(loginname, password, user.getSalt()))) {
            throw new UserPasswordNotMatchException();
        }

        return user;
    }

    public SysUser login(String loginname) throws UserNotExistsException
    {
        SysUser user = userService.selectUserByLoginName(loginname);

        if (user == null)
        {
            throw new UserNotExistsException();
        }

        return user;
    }

    private String encryptPassword(String username, String password, String salt)
    {
        return new Md5Hash(username + password + salt).toHex();
    }
}
