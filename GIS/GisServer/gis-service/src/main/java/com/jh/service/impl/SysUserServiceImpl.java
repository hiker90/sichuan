package com.jh.service.impl;

import com.jh.annotation.DataSource;
import com.jh.datasource.DataSourceType;
import com.jh.domain.SimpleSysUser;
import com.jh.domain.SysUser;
import com.jh.mapper.SysUserMapper;
import com.jh.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@DataSource(value = DataSourceType.SLAVE)
public class SysUserServiceImpl implements ISysUserService
{
    @Autowired
    private SysUserMapper userMapper;

    @Override
    public List<SimpleSysUser> selectUserSimpleList(SysUser user)
    {
        List<SimpleSysUser> list = userMapper.selectUserSimpleList(user);
        return list;
    }

    @Override
    public SysUser selectUserByIdcard(String idcard)
    {
        SysUser user = userMapper.selectUserByIdcard(idcard);
        return user;
    }

    @Override
    public SysUser selectUserById(Long userId) {
        SysUser user = userMapper.selectUserById(userId);
        return user;
    }

    /**
     * 通过登录名查询用户
     *
     * @param loginName 登录名
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByLoginName(String loginName) {
        SysUser user = userMapper.selectUserByLoginName(loginName);
        return user;
    }
}
