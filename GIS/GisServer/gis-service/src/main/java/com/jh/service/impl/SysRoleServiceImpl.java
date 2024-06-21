package com.jh.service.impl;

import com.jh.annotation.DataSource;
import com.jh.datasource.DataSourceType;
import com.jh.domain.SysRole;
import com.jh.mapper.SysRoleMapper;
import com.jh.service.ISysRoleService;
import com.jh.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@DataSource(value = DataSourceType.SLAVE)
public class SysRoleServiceImpl implements ISysRoleService
{
    @Autowired
    private SysRoleMapper roleMapper;

    /**
     * 获取用户角色列表
     *
     * @return 用户角色列表
     */
    @Override
    public List<SysRole> selectRoleListByUserId(Long userid){
        List<SysRole> list = roleMapper.selectRoleListByUserId(userid);
        return list;
    }

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectRoleKeysByUserId(Long userId)
    {
        List<SysRole> perms = roleMapper.selectRoleListByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRole perm : perms)
        {
            if (CommonUtils.isNotNull(perm))
            {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }
}
