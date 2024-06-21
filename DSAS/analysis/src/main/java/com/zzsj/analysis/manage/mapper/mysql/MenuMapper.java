package com.zzsj.analysis.manage.mapper.mysql;

import com.zzsj.analysis.base.pojo.vo.SysMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2020/12/3 15:53
 * @description：权限管理
 */

@Repository
public interface MenuMapper {
    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    public List<String> selectPermsByUserId(Long userId);

    /**
     * 根据用户LoginName查询权限
     *
     * @param loginName 用户name
     * @return 权限列表
     */
    public List<SysMenu> selectPermsByLoginName(@Param("loginName")String loginName, @Param("type")String type, @Param("system")String system);

    /**
     * 根据用户admin查询权限
     *
     * @param
     * @return 权限列表
     */
    public List<SysMenu> selectSystemPerms(@Param("type") String type, @Param("system")String system);
}
