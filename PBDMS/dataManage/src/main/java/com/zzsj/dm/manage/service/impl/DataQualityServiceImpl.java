package com.zzsj.dm.manage.service.impl;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.zzsj.dm.manage.mapper.oracle.DataQualityMapper;
import com.zzsj.dm.manage.pojo.vo.AcessNumVo;
import com.zzsj.dm.manage.service.DataQualityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/2 9:27
 * @description：数据质量
 */
@DS("oracle")
@Service
public class DataQualityServiceImpl implements DataQualityService {
    @Autowired
    DataQualityMapper dataQualityMapper;
    
    /**
     * Description: 数据质量-整体接入量信息 
     * @Date: 2020/9/7 15:13 
     * @Author: zbya 
     * 
     * @param 
     * @return: com.zzsj.dm.manage.pojo.vo.AcessNumVo
     */ 
    @Override
    public AcessNumVo getTotalNum() throws Exception {
        return dataQualityMapper.getTotalNum();
    }

}
