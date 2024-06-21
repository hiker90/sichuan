package com.jh.service.impl;

import com.jh.domain.GisUserFile;
import com.jh.domain.GisUserService;
import com.jh.mapper.GisUserFileMapper;
import com.jh.mapper.GisUserServiceMapper;
import com.jh.service.IGisUserServiceService;
import com.jh.utils.string.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

@Service
public class GisUserServiceServiceImpl implements IGisUserServiceService
{
    @Autowired
    GisUserServiceMapper userServiceMapper;

    @Autowired
    GisUserFileMapper userFileMapper;

    /**
     * 用户服务情报
     *
     * @param userService 用户服务情报
     * @return 结果
     */
    @Override
    public int insertGisUserService(GisUserService userService)
    {
        return userServiceMapper.insertGisUserService(userService);
    }

    /**
     * 删除用户服务情报
     *
     * @param userService 用户服务情报
     * @return 结果
     */
    @Override
    public int deleteGisUserService(GisUserService userService) {
        return userServiceMapper.deleteGisUserService(userService);
    }

    /**
     * 删除用户服务情报
     *
     * @param service_id 服务ID
     * @return 结果
     */
    @Override
    public int deleteGisUserServiceByServiceID(Long service_id) {
        return userServiceMapper.deleteGisUserServiceByServiceID(service_id);
    }

    /**
     * 批量插入用户服务情报
     *
     * @param serviceId 服务Id
     * @param userIds 用户ID列表
     * @return 结果
     */
    @Override
    public int batchInsertGisUserService(Long serviceId, String userIds) {
        String[] users = StringUtils.split(userIds, ",");
        List<GisUserService> userServiceList = new ArrayList<>();
        for (String user: users)
        {
            GisUserService userService = new GisUserService();
            userService.setServiceId(serviceId);
            userService.setUserId(Long.parseLong(user));
            userServiceList.add(userService);
        }

        return userServiceMapper.batchInsertGisUserService(userServiceList);
    }

    /**
     * 批量插入用户服务及用户文件情报
     *
     * @param serviceId 服务Id
     * @param fileId    服务对应文件ID
     * @param userIds 用户ID列表
     * @return 结果
     */
    @Override
    @Transactional
    public int batchInsertGisUserServiceAndFile(Long serviceId, Long fileId, String userIds) {
        String[] users = StringUtils.split(userIds, ",");
        List<GisUserService> userServiceList = new ArrayList<>();
        List<GisUserFile> userFileList = new ArrayList<>();
        for (String user: users)
        {
            GisUserService userService = new GisUserService();
            userService.setServiceId(serviceId);
            userService.setUserId(Long.parseLong(user));
            userServiceList.add(userService);
            GisUserFile userFile = new GisUserFile();
            userFile.setUserId(Long.parseLong(user));
            userFile.setFileId(fileId);
            userFileList.add(userFile);
        }

        // 插入用户服务记录
        int rows = userServiceMapper.batchInsertGisUserService(userServiceList);

        // 插入用户文件记录
        if (fileId > 0)
        {
            userFileMapper.batchInsertGisUserFile(userFileList);
        }

        return rows;
    }
}
