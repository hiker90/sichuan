package com.zzsj.analysis.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzsj.analysis.base.pojo.query.PageQuery;
import com.zzsj.analysis.manage.mapper.mysql.SystemLogMapper;
import com.zzsj.analysis.manage.pojo.query.SysLogQuery;
import com.zzsj.analysis.manage.service.SystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/15 15:57
 * @description：系统操作日志
 */
@Service
public class SystemLogServiceImpl implements SystemLogService {
    @Autowired
    SystemLogMapper systemLogMapper;
    
    /**
     * Description: 获取系统操作日志 
     * @Date: 2020/9/15 16:28
     * @Author: zbya 
     * 
     * @param pageQuery
     * @return: com.github.pagehelper.PageInfo
     */ 
    @Override
    public PageInfo getCatalog(PageQuery<SysLogQuery> pageQuery) {
        return new PageInfo(PageHelper.startPage(pageQuery.getPageIndex(),pageQuery.getPageSize()).doSelectPage(()->systemLogMapper.getSysLogList(pageQuery.getQueryParams())).getResult());
    }
}
