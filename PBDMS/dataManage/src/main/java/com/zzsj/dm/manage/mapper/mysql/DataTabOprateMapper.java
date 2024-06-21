package com.zzsj.dm.manage.mapper.mysql;

import com.zzsj.dm.manage.pojo.query.StatusQuery;
import com.zzsj.dm.manage.pojo.vo.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/9 14:47
 * @description：数据表增删改查
 */

@Repository
public interface DataTabOprateMapper {
    /**
     * Description: 查询自定义表集合
     * @Date: 2020/9/9 15:34
     * @Author: zbya
     *
     * @param
     * @return: java.util.List<com.zzsj.dm.manage.pojo.vo.TabListVo>
     */
    List<TabListVo> getList();
    /**
     * Description: 删除列集合表
     * @Date: 2020/9/9 16:17
     * @Author: zbya
     *
     * @param id
     * @return: int
     */
    int delColById(String id);
    /**
     * Description: 删除表集合对应数据
     * @Date: 2020/9/9 16:17
     * @Author: zbya
     *
     * @param id
     * @return: int
     */
    int delById(String id);
    
    /**
     * Description: 插入列集合
     * @Date: 2020/9/10 10:56 
     * @Author: zbya 
     * 
     * @param list
     * @return: int
     */ 
    int insertColList(List<ColListVo> list);

    /**
     * Description: 插入表信息
     * @Date: 2020/9/10 11:02
     * @Author: zbya
     *
     * @param tabListVo
     * @return: int
     */
    int insert(TabListVo tabListVo);

    /**
     * Description: 根据表名查询列集合
     * @Date: 2020/9/10 9:45
     * @Author: zbya
     *
     * @param id
     * @return: java.util.List<java.lang.String>
     */
    List<String> selectColsByTabId(String id);
    

    /**
     * Description: 查询可选数据类型 
     * @Date: 2020/9/10 18:13
     * @Author: zbya 
     * 
     * @param 
     * @return: java.util.List<com.zzsj.dm.manage.pojo.vo.ColTypesVo>
     */ 
    List<ColTypesVo> getColTypes();

    /**
     * Description: 根据ID查询列信息
     * @Date: 2020/9/10 19:12
     * @Author: zbya
     *
     * @param id
     * @return: java.util.List<com.zzsj.dm.manage.pojo.vo.ColListVo>
     */
    List<ColListVo> getColList(String id);

}
