package com.jh.service.impl;

import com.jh.domain.GisFile;
import com.jh.domain.GisUserFile;
import com.jh.mapper.GisFileMapper;
import com.jh.mapper.GisUserFileMapper;
import com.jh.service.IGisFileService;
import com.jh.service.IGisUserFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GisFileServiceImpl implements IGisFileService
{
    @Autowired
    GisUserFileMapper userFileMapper;

    @Autowired
    GisFileMapper fileMapper;

    /**
     * 插入文件情报
     *
     * @param file 文件情报
     * @return 结果
     */
    @Override
    public GisFile insertGisFile(GisFile file) {
        int row = fileMapper.insertGisFile(file);
        if (row > 0) {
            insertGisUserFile(file.getCreateUser(), file.getId());
        } else {
            file.setId(0L);
        }

        return file;
    }

    /**
     * 删除指定文件情报
     *
     * @param file_id 文件情报
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteGisFileByID(Long file_id) {
        int rows = fileMapper.deleteGisFileByID(file_id);

        userFileMapper.deleteGisUserFileByFileID(file_id);

        return rows;
    }

    /**
     * 查找指定文件信息
     *
     * @param id 文件ID
     * @return 结果
     */
    @Override
    public GisFile selectGisFileById(Long id) {
        return fileMapper.selectGisFileById(id);
    }

    /**
     * 根据服务ID查找指定文件信息
     *
     * @param serviceId 服务ID
     * @return 结果
     */
    @Override
    public GisFile selectGisFileByServiceId(Long serviceId){
        return fileMapper.selectGisFileByServiceId(serviceId);
    }

    /**
     * 插入用户可见文件信息
     *
     * @param user_id 用户ID
     * @param file_id 文件ID
     */
    private void insertGisUserFile(Long user_id, Long file_id) {
        GisUserFile userFile = new GisUserFile();
        userFile.setUserId(user_id);
        userFile.setFileId(file_id);
        userFileMapper.insertGisUserFile(userFile);
    }
}
