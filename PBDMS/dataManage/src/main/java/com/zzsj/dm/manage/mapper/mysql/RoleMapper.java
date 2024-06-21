package com.zzsj.dm.manage.mapper.mysql;

import com.zzsj.dm.base.pojo.vo.SysRole;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2020/12/3 15:53
 * @description：角色管理
 */

@Repository
public interface RoleMapper {
    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    public List<SysRole> selectRolesByUserId(Long userId);
}
