package com.jh.mapper;

import com.jh.domain.GisUserFile;
import com.jh.domain.GisUserService;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GisUserServiceMapper {
    /**
     * 插入用户服务情报
     *
     * @param userFile 用户服务情报
     * @return 结果
     */
    int insertGisUserService(GisUserService userFile);

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
     * @param userServiceList 用户服务情报列表
     * @return 结果
     */
    int batchInsertGisUserService(List<GisUserService> userServiceList);
}