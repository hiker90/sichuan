package com.zzsj.dm.manage.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.zzsj.dm.base.constants.ShiroConstants;
import com.zzsj.dm.base.constants.UserConstants;
import com.zzsj.dm.base.exception.BusinessException;
import com.zzsj.dm.base.pojo.vo.SysUser;
import com.zzsj.dm.base.utils.ServletUtils;
import com.zzsj.dm.base.utils.enums.UserStatus;
import com.zzsj.dm.manage.mapper.mysql.UserMapper;
import com.zzsj.dm.manage.service.LoginService;
import com.zzsj.dm.manage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author ：zbya
 * @date ：Created in 2020/12/10 9:58
 * @description：登录
 */
@DS("dms")
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;
    @Override
    public SysUser login(String username) throws Exception
    {
        // 验证码校验
        if (!StringUtils.isEmpty(ServletUtils.getRequest().getAttribute(ShiroConstants.CURRENT_CAPTCHA)))
        {
         //   AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new BusinessException("验证码错误");
        }
        // 用户名或密码为空 错误
        if (StringUtils.isEmpty(username))
        {
         //   AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("not.null")));
            throw new BusinessException("户名或密码为空");
        }

        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH)
        {
         //   AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
            throw new BusinessException("用户名不在指定范围内");
        }

        // 查询用户信息
        SysUser user = userMapper.selectUserByLoginName(username);

        if (user == null)
        {
         //   AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.not.exists")));
            throw new BusinessException("用户不存在");
        }

        if (UserStatus.DELETED.getCode().equals(user.getDelFlag()))
        {
        //    AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.delete")));
            throw new BusinessException("用户已被删除");
        }

        if (UserStatus.DISABLE.getCode().equals(user.getStatus()))
        {
         //   AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.blocked", user.getRemark())));
            throw new BusinessException("用户不可用");
        }

      //  AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        //更新redis没写了还
        return user;
    }



}
