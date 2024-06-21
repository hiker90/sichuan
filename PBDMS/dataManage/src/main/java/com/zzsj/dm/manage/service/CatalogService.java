package com.zzsj.dm.manage.service;

import com.github.pagehelper.PageInfo;
import com.zzsj.dm.base.pojo.query.PageQuery;
import com.zzsj.dm.manage.pojo.query.CatalogQuery;
import com.zzsj.dm.manage.pojo.query.TabDataOprQuery;
import com.zzsj.dm.manage.pojo.vo.CatalogVo;

import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/14 14:18
 * @description：数据目录管理
 */
public interface CatalogService {
    /**
     * Description: 获取数据目录结构信息
     *
     * @param pageQuery
     * @Date: 2020/9/14 14:36
     * @Author: zbya
     * @return: PageInfo
     */
    PageInfo getCatalog(PageQuery<CatalogQuery> pageQuery) throws Exception;

    /**
     * Description: 获取所有数据目录结构信息
     *
     * @param
     * @Date: 2020/9/14 15:41
     * @Author: zbya
     * @return: java.util.List<com.zzsj.dm.manage.pojo.vo.CatalogVo>
     */
    List<CatalogVo> getAllCatalog(String type) throws Exception;

    /**
     * Description: 删除目录信息
     *
     * @param id
     * @Date: 2020/9/14 15:57
     * @Author: zbya
     * @return: int
     */
    int deleteData(String id) throws Exception;

    /**
     * Description: 更新目录信息
     *
     * @param catalogVo
     * @Date: 2020/9/14 15:58
     * @Author: zbya
     * @return: int
     */
    int updateData(CatalogVo catalogVo) throws Exception;

    /**
     * Description: 插入目录信息
     *
     * @param
     * @Date: 2020/9/14 15:58
     * @Author: zbya
     * @return: int
     */
    int insertData(CatalogVo catalogVo) throws Exception;

    /**
     * Description: 获取没有建目录的所有表
     * @Date: 2020/9/15 17:32
     * @Author: zbya 
     * 
     * @param 
     * @return: java.util.List
     */ 
    List getTabList() throws Exception;

    /**
     * Description: 自检表数据删除
     * @Date: 2020/12/24 13:56
     * @Author: zbya
     *
     * @param dataCountQuery
     * @return: int
     */
    int deleteByDate(TabDataOprQuery dataCountQuery) throws Exception;
}
