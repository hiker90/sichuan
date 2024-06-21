package com.jh.mapper;

import com.jh.domain.GisService;
import com.jh.domain.GisUserFile;
import com.jh.domain.GisUserService;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GisServiceMapper {
    /**
     * 插入服务信息
     *
     * @param service 服务信息
     * @return 结果
     */
    int insertGisService(GisService service);

    /**
     * 删除指定服务信息
     *
     * @param id 服务ID
     * @return 结果
     */
    int deleteGisServiceByID(Long id);

    /**
     * 查找指定服务信息
     *
     * @param id 服务ID
     * @return 结果
     */
    GisService selectGisServiceById(Long id);

    /**
     * 查找指定用户可见服务信息
     *
     * @param userId 用户ID
     * @return 结果
     */
    List<GisService> selectGisServiceByUserId(Long userId);

    /**
     * 查找指定用户创建的服务信息
     *
     * @param userId 用户ID
     * @return 结果
     */
    List<GisService> selectMyGisServiceByUserId(Long userId);

    /**
     * 查找指定用户他人分享的服务信息
     *
     * @param userId 用户ID
     * @return 结果
     */
    List<GisService> selectOtherGisServiceByUserId(Long userId);
}