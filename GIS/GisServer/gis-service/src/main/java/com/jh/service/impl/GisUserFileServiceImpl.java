package com.jh.service.impl;

import com.jh.domain.GisUserFile;
import com.jh.mapper.GisUserFileMapper;
import com.jh.service.IGisUserFileService;
import com.jh.utils.string.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GisUserFileServiceImpl implements IGisUserFileService
{
    @Autowired
    GisUserFileMapper userFileMapper;

    /**
     * 插入用户文件情报
     *
     * @param userFile 用户文件情报
     * @return 结果
     */
    @Override
    public int insertGisUserFile(GisUserFile userFile) {
        return userFileMapper.insertGisUserFile(userFile);
    }

    /**
     * 删除用户文件情报
     *
     * @param userFile 用户文件情报
     * @return 结果
     */
    @Override
    public int deleteGisUserFile(GisUserFile userFile){
        return userFileMapper.deleteGisUserFile(userFile);
    }

    /**
     * 删除用户文件情报
     *
     * @param file_id 用户文件情报
     * @return 结果
     */
    @Override
    public int deleteGisUserFileByFileID(Long file_id) {
        return userFileMapper.deleteGisUserFileByFileID(file_id);
    }

    /**
     * 批量插入用户文件情报
     *
     * @param fileId 文件ID
     * @param userIds 用户ID列表
     * @return 结果
     */
    @Override
    public int batchInsertGisUserFile(Long fileId, String userIds) {
        String[] users = StringUtils.split(userIds, ",");
        List<GisUserFile> userFileList = new ArrayList<>();
        for (String user: users)
        {
            GisUserFile userFile = new GisUserFile();
            userFile.setFileId(fileId);
            userFile.setUserId(Long.parseLong(user));
            userFileList.add(userFile);
        }

        return userFileMapper.batchInsertGisUserFile(userFileList);
    }
}
