package com.zzsj.dm.manage.service;

import com.zzsj.dm.base.pojo.vo.SysMenu;
import com.zzsj.dm.base.pojo.vo.SysUser;

import java.util.List;
import java.util.Set;

/**
 * @author ：zbya
 * @date ：Created in 2020/12/4 14:35
 * @description：用户
 */
public interface UserService {
    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    public SysUser selectUserByLoginName(String userName);


    /**
     * 根据用户ID查询权限
     *
     * @param loginName 用户name
     * @return 权限列表
     */
    public List<SysMenu> selectPermsByLoginName(String loginName, String roleKey,String type, String system);
}
