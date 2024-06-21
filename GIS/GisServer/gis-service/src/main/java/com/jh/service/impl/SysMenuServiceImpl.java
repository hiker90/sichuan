package com.jh.service.impl;

import com.jh.annotation.DataSource;
import com.jh.datasource.DataSourceType;
import com.jh.domain.SysMenu;
import com.jh.mapper.SysMenuMapper;
import com.jh.service.ISysMenuService;
import com.jh.utils.string.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@DataSource(value = DataSourceType.SLAVE)
public class SysMenuServiceImpl implements ISysMenuService
{
    @Autowired
    private SysMenuMapper menuMapper;

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectPermsByUserId(Long userId) {
        List<String> perms = menuMapper.selectPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms)
        {
            if (StringUtils.isNotEmpty(perm))
            {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public List<SysMenu> selectMenuAllByUserId(Long userId) {
        return menuMapper.selectMenuAllByUserId(userId);
    }
}
