package com.zzsj.dm.manage.mapper.oracle;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.zzsj.dm.manage.pojo.query.TabDataOprQuery;
import com.zzsj.dm.manage.pojo.vo.ColInfoVo;
import com.zzsj.dm.manage.pojo.vo.TabInfoVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/4 21:56
 * @description：oracle表信息
 */

@DS("oracle")
@Repository
public interface TabInfoMapper {
    /**
     * Description: 获取所有表信息
     * @Date: 2020/9/7 16:06 
     * @Author: zbya 
     * 
     * @param 
     * @return: java.util.List
     */ 
    List<TabInfoVo> getTabList() throws Exception;

    /**
     * Description: 获取表的列信息
     * @Date: 2020/9/7 16:06
     * @Author: zbya
     *
     * @param tableName
     * @return: java.util.List
     */
    List<ColInfoVo> getColList(String tableName) throws Exception;
    
    /**
     * Description: 查询用户自建表 
     * @Date: 2020/12/24 13:56 
     * @Author: zbya 
     * 
     * @param 
     * @return: java.util.List<com.zzsj.dm.manage.pojo.vo.TabInfoVo>
     */ 
    List<TabInfoVo> getUseTabList() throws Exception;
    
    /**
     * Description: 删除指定日期自建表
     * @Date: 2020/12/24 13:59
     * @Author: zbya 
     * 
     * @param dataCountQuery
     * @return: int
     */ 
    int deleteByDate(TabDataOprQuery dataCountQuery);
}
