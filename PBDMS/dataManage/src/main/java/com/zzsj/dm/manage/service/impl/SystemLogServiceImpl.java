package com.zzsj.dm.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzsj.dm.base.pojo.query.PageQuery;
import com.zzsj.dm.manage.mapper.mysql.SystemLogMapper;
import com.zzsj.dm.manage.pojo.query.SysLogQuery;
import com.zzsj.dm.manage.service.SystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
