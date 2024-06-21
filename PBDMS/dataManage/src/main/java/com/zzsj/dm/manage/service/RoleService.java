package com.zzsj.dm.manage.service;

import java.util.Set;

/**
 * @author ：zbya
 * @date ：Created in 2020/12/3 15:47
 * @description：角色管理
 */
public interface RoleService {
    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    public Set<String> selectRoleKeys(Long userId);
}
