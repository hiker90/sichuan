package com.zzsj.analysis.manage.service;

import com.zzsj.analysis.base.pojo.vo.SysUser;

/**
 * @author ：zbya
 * @date ：Created in 2020/12/10 9:57
 * @description：登录
 */
public interface LoginService {
    SysUser login(String username) throws Exception;
}
