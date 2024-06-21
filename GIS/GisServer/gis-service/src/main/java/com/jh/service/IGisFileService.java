package com.jh.service;

import com.jh.domain.GisFile;

public interface IGisFileService
{
    /**
     * 插入文件情报
     *
     * @param file 文件情报
     * @return 结果
     */
    GisFile insertGisFile(GisFile file);

    /**
     * 删除指定文件情报
     *
     * @param id 文件ID
     * @return 结果
     */
    int deleteGisFileByID(Long id);

    /**
     * 查找指定文件信息
     *
     * @param id 文件ID
     * @return 结果
     */
    GisFile selectGisFileById(Long id);

    /**
     * 根据服务ID查找指定文件信息
     *
     * @param serviceId 服务ID
     * @return 结果
     */
    GisFile selectGisFileByServiceId(Long serviceId);
}
