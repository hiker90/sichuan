package com.jh.mapper;

import com.jh.domain.SimpleSysUser;
import com.jh.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserMapper
{
    /**
     * 获取用户基本信息列表
     *
     * @return 用户基本信息列表
     */
    public List<SimpleSysUser> selectUserSimpleList(SysUser user);

    /**
     * 通过身份证号码查询用户
     *
     * @param idcard 身份证号码
     * @return 用户对象信息
     */
    public SysUser selectUserByIdcard(String idcard);

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    public SysUser selectUserById(Long userId);

    /**
     * 通过登录名查询用户
     *
     * @param loginName 登录名
     * @return 用户对象信息
     */
    public SysUser selectUserByLoginName(String loginName);
}