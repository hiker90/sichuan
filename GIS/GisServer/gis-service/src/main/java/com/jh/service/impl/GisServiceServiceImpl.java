package com.jh.service.impl;

import com.jh.domain.GisService;
import com.jh.domain.GisUserService;
import com.jh.mapper.GisServiceMapper;
import com.jh.mapper.GisUserServiceMapper;
import com.jh.service.IGisServiceService;
import com.jh.service.IGisUserServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GisServiceServiceImpl implements IGisServiceService
{
    @Autowired
    GisServiceMapper serviceMapper;

    @Autowired
    GisUserServiceMapper userServiceMapper;


    /**
     * 插入服务信息
     *
     * @param service 服务信息
     * @return 结果
     */
    @Override
    @Transactional
    public GisService insertGisService(GisService service) {
        // 新增用户信息
        int rows = serviceMapper.insertGisService(service);

        if (rows > 0) {
            // 新增用户与角色管理
            insertUserService(service.getId(), service.getCreateUser());
        } else {
            service.setId(0L);
        }

        return service;
    }

    /**
     * 删除指定服务信息
     *
     * @param serviceId 服务ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteGisServiceByID(Long serviceId) {
        int rows = serviceMapper.deleteGisServiceByID(serviceId);

        // 删除用户服务信息
        userServiceMapper.deleteGisUserServiceByServiceID(serviceId);

        return rows;
    }

    /**
     * 查找指定服务信息
     *
     * @param id 服务ID
     * @return 结果
     */
    @Override
    public GisService selectGisServiceById(Long id) {
        return serviceMapper.selectGisServiceById(id);
    }

    /**
     * 查找指定用户可见服务信息
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public List<GisService> selectGisServiceByUserId(Long userId) {
        return serviceMapper.selectGisServiceByUserId(userId);
    }

    /**
     * 查找指定用户创建的服务信息
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public List<GisService> selectMyGisServiceByUserId(Long userId){
        return serviceMapper.selectMyGisServiceByUserId(userId);
    }

    /**
     * 查找指定用户他人分享的服务信息
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public List<GisService> selectOtherGisServiceByUserId(Long userId){
        return serviceMapper.selectOtherGisServiceByUserId(userId);
    }

    /**
     * 插入用户可见服务信息
     *
     * @param userId 用户ID
     * @param serviceId 服务ID
     */
    private void insertUserService(Long serviceId, Long userId) {
        GisUserService userService = new GisUserService();
        userService.setUserId(userId);
        userService.setServiceId(serviceId);

        userServiceMapper.insertGisUserService(userService);
    }
}
