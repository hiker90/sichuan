package com.zzsj.dm.manage.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.zzsj.dm.base.utils.StringUtils;
import com.zzsj.dm.manage.mapper.mysql.MenuMapper;
import com.zzsj.dm.manage.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author ：zbya
 * @date ：Created in 2020/12/3 15:49
 * @description：权限管理
 */

@DS("dms")
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    MenuMapper menuMapper;

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
}
