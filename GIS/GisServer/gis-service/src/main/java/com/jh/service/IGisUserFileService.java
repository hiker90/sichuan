package com.jh.service;

import com.jh.domain.GisUserFile;

import java.util.List;

public interface IGisUserFileService
{
    /**
     * 插入用户文件情报
     *
     * @param userFile 用户文件情报
     * @return 结果
     */
    int insertGisUserFile(GisUserFile userFile);

    /**
     * 删除用户文件情报
     *
     * @param userFile 用户文件情报
     * @return 结果
     */
    int deleteGisUserFile(GisUserFile userFile);

    /**
     * 删除用户文件情报
     *
     * @param file_id 用户文件情报
     * @return 结果
     */
    int deleteGisUserFileByFileID(Long file_id);

    /**
     * 批量插入用户文件情报
     *
     * @param fileId 文件ID
     * @param userIds 用户ID列表
     * @return 结果
     */
    int batchInsertGisUserFile(Long fileId, String userIds);
}
