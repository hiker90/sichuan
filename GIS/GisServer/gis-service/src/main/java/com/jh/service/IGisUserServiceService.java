package com.jh.service;

import com.jh.domain.GisUserService;

import java.util.List;

public interface IGisUserServiceService
{
    /**
     * 用户服务情报
     *
     * @param userService 用户服务情报
     * @return 结果
     */
    int insertGisUserService(GisUserService userService);

    /**
     * 删除用户服务情报
     *
     * @param userService 用户服务情报
     * @return 结果
     */
    int deleteGisUserService(GisUserService userService);

    /**
     * 删除用户服务情报
     *
     * @param service_id 服务ID
     * @return 结果
     */
    int deleteGisUserServiceByServiceID(Long service_id);

    /**
     * 批量插入用户服务情报
     *
     * @param serviceId 服务Id
     * @param userIds 用户ID列表
     * @return 结果
     */
    int batchInsertGisUserService(Long serviceId, String userIds);

    /**
     * 批量插入用户服务及用户文件情报
     *
     * @param serviceId 服务Id
     * @param fileId    服务对应文件ID
     * @param userIds 用户ID列表
     * @return 结果
     */
    int batchInsertGisUserServiceAndFile(Long serviceId, Long fileId, String userIds);
}
