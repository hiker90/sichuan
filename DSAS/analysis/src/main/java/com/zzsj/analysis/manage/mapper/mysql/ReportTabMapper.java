package com.zzsj.analysis.manage.mapper.mysql;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.zzsj.analysis.manage.entity.CulRule;
import com.zzsj.analysis.manage.pojo.query.DataQueryQuery;
import com.zzsj.analysis.manage.pojo.vo.TabListVo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author ：zbya
 * @date ：Created in 2020/10/16 14:24
 * @description：报表管理表
 */

@Repository
public interface ReportTabMapper {
    /**
     * Description: 获取结果表集合
     * @Date: 2020/10/16 14:46
     * @Author: zbya 
     * 
     * @param 
     * @return: java.util.List<com.zzsj.analysis.manage.pojo.vo.TabListVo>
     */ 
    List<CulRule> getTabList();

}
