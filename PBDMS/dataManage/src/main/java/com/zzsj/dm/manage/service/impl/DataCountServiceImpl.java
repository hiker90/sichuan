package com.zzsj.dm.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzsj.dm.base.pojo.query.PageQuery;
import com.zzsj.dm.manage.mapper.mysql.DataCountMapper;
import com.zzsj.dm.manage.pojo.query.DataCountQuery;
import com.zzsj.dm.manage.service.DataCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/18 22:34
 * @description：数据统计
 */
@Service
public class DataCountServiceImpl implements DataCountService {
    @Autowired
    DataCountMapper dataCountMapper;
    
    /**
     * Description: 获取时间范围内数据统计值
     * @Date: 2020/9/18 22:57 
     * @Author: zbya 
     * 
     * @param pageQuery
     * @return: com.github.pagehelper.PageInfo
     */ 
    @Override
    public PageInfo getData(PageQuery<DataCountQuery> pageQuery) {
        return new PageInfo(PageHelper.startPage(pageQuery.getPageIndex(),pageQuery.getPageSize()).doSelectPage(()->dataCountMapper.getData(pageQuery.getQueryParams())).getResult());
    }
}
