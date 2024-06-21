package com.zzsj.analysis.manage.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.zzsj.analysis.base.pojo.vo.SysMenu;
import com.zzsj.analysis.base.pojo.vo.SysUser;
import com.zzsj.analysis.manage.mapper.mysql.MenuMapper;
import com.zzsj.analysis.manage.mapper.mysql.UserMapper;
import com.zzsj.analysis.manage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2020/12/4 14:38
 * @description：用户
 */

@DS("dms")
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserMapper userMapper;
    @Autowired
    MenuMapper menuMapper;

    @Override
    public SysUser selectUserByLoginName(String userName) {

        return userMapper.selectUserByLoginName(userName);
    }

    @Override
    public List<SysMenu> selectPermsByLoginName(String loginName,String roleKey ,String type, String system) {
        if(!roleKey.equals("admin")){
            return menuMapper.selectPermsByLoginName(loginName,type,system);
        }

        else{
            return menuMapper.selectSystemPerms(type,system);
        }

    }
}
