package com.zzsj.dm.manage.mapper.mysql;

import com.zzsj.dm.manage.pojo.query.CatalogQuery;
import com.zzsj.dm.manage.pojo.vo.CatalogVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/14 14:19
 * @description：数据目录管理
 */

@Repository
public interface CatalogMapper {
    List<CatalogVo> selectneedChange();
    List<CatalogVo> selectParent();
    /**
     * Description: 获取数据目录结构信息
     * @Date: 2020/9/14 15:29
     * @Author: zbya
     *
     * @param queryParams
     * @return: java.util.List<com.zzsj.dm.manage.pojo.vo.CatalogVo>
     */
    List<CatalogVo> getCatalog(CatalogQuery queryParams);

    /**
     * Description: 获取所有数据目录信息
     * @Date: 2020/9/14 15:29
     * @Author: zbya
     *
     * @param
     * @return: java.util.List<com.zzsj.dm.manage.pojo.vo.CatalogVo>
     */
    List<CatalogVo> getAllCatalog();

    /**
     * Description: 查询自建表目录
     * @Date: 2020/9/25 16:03
     * @Author: zbya
     *
     * @param
     * @return: java.util.List<com.zzsj.dm.manage.pojo.vo.CatalogVo>
     */
    List<CatalogVo> getCatalogCh();

    /**
     * Description: 删除目录信息
     * @Date: 2020/9/14 16:04
     * @Author: zbya
     *
     * @param id
     * @return: int
     */
    int deleteData(String id);

    /**
     * Description: 更新目录信息
     * @Date: 2020/9/14 16:05
     * @Author: zbya
     *
     * @param catalogVo
     * @return: int
     */
    int updateData(CatalogVo catalogVo);

    /**
     * Description: 插入目录信息
     * @Date: 2020/9/14 16:05
     * @Author: zbya
     *
     * @param catalogVo
     * @return: int
     */
    int insertData(CatalogVo catalogVo);

    /**
     * Description: 查询有资源目录英文名
     * @Date: 2020/9/15 17:12
     * @Author: zbya
     *
     * @param
     * @return: java.util.List<java.lang.String>
     */
    List<String> selectTab();
    
    /**
     * Description: 根据表名删除目录信息
     * @Date: 2020/9/17 14:54
     * @Author: zbya 
     * 
     * @param tableEnName
     * @return: int
     */ 
    int deleteByEnName(String tableEnName);
}
