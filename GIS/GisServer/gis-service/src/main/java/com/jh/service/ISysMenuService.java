package com.jh.service;

import com.jh.domain.SysMenu;

import java.util.List;
import java.util.Set;

public interface ISysMenuService
{
    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    Set<String> selectPermsByUserId(Long userId);

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    List<SysMenu> selectMenuAllByUserId(Long userId);

}
