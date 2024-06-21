package com.zzsj.dm.manage.service;

import com.github.pagehelper.PageInfo;
import com.zzsj.dm.base.pojo.query.PageQuery;
import com.zzsj.dm.manage.pojo.query.DataCountQuery; /**
 * @author ：zbya
 * @date ：Created in 2020/9/18 22:33
 * @description：数据统计
 */
public interface DataCountService {
    /**
     * Description: 获取时间范围内统计数据 
     * @Date: 2020/9/18 23:16
     * @Author: zbya 
     * 
     * @param pageQuery
     * @return: com.github.pagehelper.PageInfo
     */ 
    PageInfo getData(PageQuery<DataCountQuery> pageQuery);
}
