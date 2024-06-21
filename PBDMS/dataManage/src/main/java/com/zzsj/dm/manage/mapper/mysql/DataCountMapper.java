package com.zzsj.dm.manage.mapper.mysql;

import com.zzsj.dm.manage.pojo.query.DataCountQuery;
import com.zzsj.dm.manage.pojo.vo.DataCountVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/18 22:35
 * @description：数据统计
 */

@Repository
public interface DataCountMapper {
    
    /**
     * Description: 获取时间范围内数据统计值 
     * @Date: 2020/9/18 23:05
     * @Author: zbya 
     * 
     * @param queryParams
     * @return: java.util.List<com.zzsj.dm.manage.pojo.vo.DataCountVo>
     */ 
    List<DataCountVo> getData(DataCountQuery queryParams);
}
