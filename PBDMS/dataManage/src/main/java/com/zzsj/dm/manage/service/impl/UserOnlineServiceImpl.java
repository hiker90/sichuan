package com.zzsj.dm.manage.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.zzsj.dm.base.pojo.vo.SysUserOnline;
import com.zzsj.dm.manage.mapper.mysql.UserOnlineMapper;
import com.zzsj.dm.manage.service.UserOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：zbya
 * @date ：Created in 2020/12/4 14:45
 * @description：在线用户
 */

@DS("dms")
@Service
public class UserOnlineServiceImpl implements UserOnlineService{
    @Autowired
    UserOnlineMapper userOnlineMapper;

    /**
     * 通过会话序号查询信息
     *
     * @param sessionId 会话ID
     * @return 在线用户信息
     */
    @Override
    public SysUserOnline selectOnlineById(String sessionId)
    {
        return userOnlineMapper.selectOnlineById(sessionId);
    }
}
