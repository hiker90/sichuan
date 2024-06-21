package com.zzsj.analysis.manage.service;

import com.zzsj.analysis.base.pojo.vo.SysUserOnline;

/**
 * @author ：zbya
 * @date ：Created in 2020/12/4 11:13
 * @description：在线用户
 */
public interface UserOnlineService {
    /**
     * 通过会话序号查询信息
     *
     * @param sessionId 会话ID
     * @return 在线用户信息
     */
    public SysUserOnline selectOnlineById(String sessionId);
}
