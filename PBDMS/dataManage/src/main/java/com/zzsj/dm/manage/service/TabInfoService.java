package com.zzsj.dm.manage.service;

import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/4 21:54
 * @description：表信息
 */
public interface TabInfoService {
    /**
     * Description: 获取所有表信息
     * @Date: 2020/9/7 17:35 
     * @Author: zbya 
     * 
     * @param 
     * @return: java.util.List
     */ 
    List getTabList() throws Exception;
    
    /**
     * Description: 获取所有列信息
     * @Date: 2020/9/7 17:35 
     * @Author: zbya 
     * 
     * @param tableName
     * @return: java.util.List
     */ 
    List getColList(String tableName) throws Exception;
}
