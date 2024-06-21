package com.zzsj.dm.manage.mapper.mysql;

import com.zzsj.dm.base.pojo.vo.SysUserOnline;
import org.springframework.stereotype.Repository;

/**
 * @author ：zbya
 * @date ：Created in 2020/12/4 14:47
 * @description：在线用户
 */

@Repository
public interface UserOnlineMapper {
    /**
     * 通过会话序号查询信息
     *
     * @param sessionId 会话ID
     * @return 在线用户信息
     */
    public SysUserOnline selectOnlineById(String sessionId);
}
