package com.zzsj.dm.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzsj.dm.base.pojo.query.PageQuery;
import com.zzsj.dm.manage.mapper.mysql.DataWarnMapper;
import com.zzsj.dm.manage.pojo.query.WarnTypeQuery;
import com.zzsj.dm.manage.service.DataWarnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/4 21:20
 * @description：数据质量预警
 */
@Service
public class DataWarnServiceImpl implements DataWarnService{
    @Autowired
    DataWarnMapper dataWarnMapper;
    
    /**
     * Description: 数据质量-预警情况、处理情况查询 
     * @Date: 2020/9/7 15:13 
     * @Author: zbya 
     * 
     * @param pageQuery
     * @return: com.github.pagehelper.PageInfo
     */ 
    @Override
    public PageInfo getWarningStates(PageQuery<WarnTypeQuery> pageQuery) throws Exception {
        //预警
        if(pageQuery.getQueryParams().getStatus()==0){
            return new PageInfo<>(PageHelper.startPage(pageQuery.getPageIndex(),pageQuery.getPageSize()).doSelectPage(()->dataWarnMapper.getWarningStates(pageQuery.getQueryParams())).getResult());
        }
        //处理情况
        else if(pageQuery.getQueryParams().getStatus()==1){
            return new PageInfo<>(PageHelper.startPage(pageQuery.getPageIndex(),pageQuery.getPageSize()).doSelectPage(()->dataWarnMapper.getprocessedWarning(pageQuery.getQueryParams())).getResult());
        }
        //待处理历史情况
        else if(pageQuery.getQueryParams().getStatus()==2){
            return new PageInfo<>(PageHelper.startPage(pageQuery.getPageIndex(),pageQuery.getPageSize()).doSelectPage(()->dataWarnMapper.getWarningHistory(pageQuery.getQueryParams())).getResult());
        }
        return null;
    }
    
    /**
     * Description: 数据质量-预警情况处理 
     * @Date: 2020/9/7 15:14 
     * @Author: zbya 
     * 
     * @param name
     * @return: int
     */ 
    @Override
    public int dealWarning(String name) {
        return dataWarnMapper.dealWarning(name);
    }
}
