package com.jh.mapper;

import com.jh.domain.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper
{
    /**
     * 获取用户角色列表
     *
     * @return 用户角色列表
     */
    public List<SysRole> selectRoleListByUserId(Long userId);

    /**
     * 获取角色信息
     *
     * @return 角色信息
     */
    public SysRole selectRoleByRoleId(Long roleId);
}