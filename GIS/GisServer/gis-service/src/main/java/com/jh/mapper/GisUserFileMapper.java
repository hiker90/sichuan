package com.jh.mapper;

import com.jh.domain.GisUserFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GisUserFileMapper {
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
     * @param userFileList 用户文件情报列表
     * @return 结果
     */
    int batchInsertGisUserFile(List<GisUserFile> userFileList);
}