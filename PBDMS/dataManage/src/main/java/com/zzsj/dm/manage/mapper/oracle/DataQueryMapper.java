package com.zzsj.dm.manage.mapper.oracle;

import com.zzsj.dm.manage.pojo.query.DataQueryQuery;
import com.zzsj.dm.manage.pojo.query.TabQuery;
import com.zzsj.dm.manage.pojo.vo.ColListVo;
import com.zzsj.dm.manage.pojo.vo.TabInfoVo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/18 23:15
 * @description：数据查询
 */

@Repository
public interface DataQueryMapper {
    /**
     * Description: 数据查询 
     * @Date: 2020/9/22 22:49
     * @Author: zbya 
     * 
     * @param queryParams
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */ 
    List<Map<String, Object>> getData(DataQueryQuery queryParams);

    /**
     * Description: 获取数据库表信息 
     * @Date: 2020/9/23 15:29 
     * @Author: zbya 
     * 
     * @param queryParams
     * @return: java.util.List<com.zzsj.dm.manage.pojo.vo.TabInfoVo>
     */ 
    List<TabInfoVo> getTable(TabQuery queryParams);
    
    /**
     * Description: 获取指定表列信息 
     * @Date: 2020/9/23 16:07
     * @Author: zbya 
     * 
     * @param tableEnName
     * @return: java.util.List<com.zzsj.dm.manage.pojo.vo.ColListVo>
     */ 
    List<ColListVo> getColumn(String tableEnName);
}
