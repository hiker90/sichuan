package com.jh.service;

import com.jh.domain.SimpleSysUser;
import com.jh.domain.SysUser;

import java.util.List;


public interface ISysUserService
{
    /**
     * 获取用户基本信息列表
     *
     * @return 用户基本信息列表
     */
    List<SimpleSysUser> selectUserSimpleList(SysUser user);

    /**
     * 通过身份证号码查询用户
     *
     * @param idcard 身份证号码
     * @return 用户对象信息
     */
    SysUser selectUserByIdcard(String idcard);

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    SysUser selectUserById(Long userId);

    /**
     * 通过登录名查询用户
     *
     * @param loginName 登录名
     * @return 用户对象信息
     */
    SysUser selectUserByLoginName(String loginName);
}
