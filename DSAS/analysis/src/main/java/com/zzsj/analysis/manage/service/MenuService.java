package com.zzsj.analysis.manage.service;

import java.util.Set;

/**
 * @author ：zbya
 * @date ：Created in 2020/12/3 15:46
 * @description：权限管理
 */
public interface MenuService {
    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    public Set<String> selectPermsByUserId(Long userId);
}
