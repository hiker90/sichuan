package com.zzsj.analysis.manage.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.zzsj.analysis.base.pojo.vo.SysRole;
import com.zzsj.analysis.base.utils.StringUtils;
import com.zzsj.analysis.manage.mapper.mysql.RoleMapper;
import com.zzsj.analysis.manage.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author ：zbya
 * @date ：Created in 2020/12/3 15:49
 * @description：角色管理
 */

@DS("dms")
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleMapper roleMapper;

    @Override
    public Set<String> selectRoleKeys(Long userId) {
        List<SysRole> perms = roleMapper.selectRolesByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRole perm : perms)
        {
            if (StringUtils.isNotNull(perm))
            {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }
}
