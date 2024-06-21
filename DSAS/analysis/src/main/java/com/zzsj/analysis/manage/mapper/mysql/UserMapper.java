package com.zzsj.analysis.manage.mapper.mysql;

import com.zzsj.analysis.base.pojo.vo.SysUser;
import org.springframework.stereotype.Repository;

/**
 * @author ：zbya
 * @date ：Created in 2020/12/4 14:41
 * @description：用户
 */

@Repository
public interface UserMapper {
    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    public SysUser selectUserByLoginName(String userName);
}
