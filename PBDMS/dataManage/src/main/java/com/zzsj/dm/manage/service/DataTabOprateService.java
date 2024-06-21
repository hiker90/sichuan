package com.zzsj.dm.manage.service;

import com.github.pagehelper.PageInfo;
import com.zzsj.dm.base.pojo.query.PageQuery;
import com.zzsj.dm.base.pojo.vo.BaseVo;
import com.zzsj.dm.manage.pojo.query.StatusQuery;
import com.zzsj.dm.manage.pojo.query.TabQuery;
import com.zzsj.dm.manage.pojo.vo.ColListVo;
import com.zzsj.dm.manage.pojo.vo.ColTypesVo;
import com.zzsj.dm.manage.pojo.vo.TabListVo;
import com.zzsj.dm.manage.pojo.vo.TabVo;

import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/9 14:44
 * @description：数据表增删改查
 */

public interface DataTabOprateService {
    /**
     * Description: 查询自定义表集合 
     * @Date: 2020/9/10 17:51 
     * @Author: zbya 
     * 
     * @param pageQuery
     * @return: com.github.pagehelper.PageInfo
     */ 
    PageInfo getList(PageQuery pageQuery) throws Exception;
    /**
     * Description: 新建表 
     * @Date: 2020/9/10 17:51 
     * @Author: zbya 
     * 
     * @param tabVo
     * @return: int
     */
    String insertData(TabVo tabVo) throws Exception;
    
    /**
     * Description: 删除表信息 
     * @Date: 2020/9/10 17:51 
     * @Author: zbya 
     * 
     * @param tabQuery
     * @return: void
     */ 
    void deleteData(TabQuery tabQuery) throws Exception;

    /**
     * Description: 查询数据列可选类型
     * @Date: 2020/9/10 18:14
     * @Author: zbya 
     * 
     * @param 
     * @return: java.util.List<com.zzsj.dm.manage.pojo.vo.ColTypesVo>
     */ 
    List<ColTypesVo> getColTypes();
    
    /**
     * Description: 根据表ID查询列信息 
     * @Date: 2020/9/10 19:20
     * @Author: zbya 
     * 
     * @param id
     * @return: java.util.List<com.zzsj.dm.manage.pojo.vo.ColListVo>
     */ 
    List<ColListVo> getColList(String id);
}
