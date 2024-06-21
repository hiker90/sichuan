package com.zzsj.dm.manage.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.zzsj.dm.manage.mapper.oracle.TabInfoMapper;
import com.zzsj.dm.manage.service.TabInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/4 21:55
 * @description：查询oracle接入表信息
 */
@DS("oracle")
@Slf4j
@Service
public class TabInfoServiceImpl implements TabInfoService {
    @Autowired
    TabInfoMapper tabInfoMapper;
    /**
     * Description: 获取所有列信息 
     * @Date: 2020/9/7 17:36 
     * @Author: zbya 
     * 
     * @param 
     * @return: java.util.List
     */ 
    @Override
    public List getTabList() throws Exception {
        return tabInfoMapper.getTabList();
    }
    
    /**
     * Description: 表对标规则展示 
     * @Date: 2020/9/7 18:17
     * @Author: zbya
     * 
     * @param tableName
     * @return: java.util.List
     */ 
    @Override
    public List getColList(String tableName) throws Exception {
        return tabInfoMapper.getColList(tableName);
    }
}
