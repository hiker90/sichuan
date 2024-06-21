package com.jh.service;

import com.jh.domain.SysRole;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface ISysRoleService
{
    /**
     * 获取用户角色列表
     *
     * @return 用户角色列表
     */
    public List<SysRole> selectRoleListByUserId(Long userid);

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    public Set<String> selectRoleKeysByUserId(Long userId);
}
