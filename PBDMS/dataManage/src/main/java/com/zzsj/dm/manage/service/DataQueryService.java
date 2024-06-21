package com.zzsj.dm.manage.service;

import com.github.pagehelper.PageInfo;
import com.zzsj.dm.base.pojo.query.PageQuery;
import com.zzsj.dm.manage.pojo.query.DataQueryQuery;
import com.zzsj.dm.manage.pojo.query.TabQuery;
import com.zzsj.dm.manage.pojo.vo.ColListVo;

import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/18 23:13
 * @description：数据查询
 */
public interface DataQueryService {
    /**
     * Description: 数据查询 
     * @Date: 2020/9/22 22:24 
     * @Author: zbya 
     * 
     * @param pageQuery
     * @return: com.github.pagehelper.PageInfo
     */ 
    PageInfo getData(PageQuery<DataQueryQuery> pageQuery) throws Exception;
    
    /**
     * Description: 获取数据库表信息
     * @Date: 2020/9/23 15:29
     * @Author: zbya 
     * 
     * @param pageQuery
     * @return: com.github.pagehelper.PageInfo
     */ 
    PageInfo getTable(PageQuery<TabQuery> pageQuery) throws Exception;

    /**
     * Description: 获取指定表列信息
     * @Date: 2020/9/23 16:04
     * @Author: zbya
     *
     * @param tableEnName
     * @return: java.util.List<com.zzsj.dm.manage.pojo.vo.ColListVo>
     */
    List<ColListVo> getColumn(String tableEnName) throws Exception;
}
