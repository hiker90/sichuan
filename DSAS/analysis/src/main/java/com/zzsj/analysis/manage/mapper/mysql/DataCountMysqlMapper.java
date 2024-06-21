package com.zzsj.analysis.manage.mapper.mysql;

import com.zzsj.analysis.manage.entity.CulRule;
import com.zzsj.analysis.manage.pojo.query.DataCountQuery;
import com.zzsj.analysis.manage.pojo.vo.CalTabInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2020/12/21 10:47
 * @description：数据统计Mysql
 */

@Repository
public interface DataCountMysqlMapper {
    /**
     * Description: 统计输出表数据
     * @Date: 2020/12/21 11:11
     * @Author: zbya
     *
     * @param culRule
     * @return: int
     */

    int getCountNum(CulRule culRule);
    /**
     * Description: 从输出表查询输入表名
     * @Date: 2020/12/21 13:50
     * @Author: zbya
     *
     * @param tabName
     * @return: java.util.List<java.lang.String>
     */
    List<CalTabInfo> getTabNames(String tabName);

    int deleteByDate(DataCountQuery dataCountQuery);
}
