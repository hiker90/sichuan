package com.zzsj.analysis.manage.mapper.oracle;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.zzsj.analysis.manage.entity.CulRule;
import com.zzsj.analysis.manage.pojo.query.DataCountQuery;
import org.springframework.stereotype.Repository;

/**
 * @author ：zbya
 * @date ：Created in 2020/12/21 10:47
 * @description：数据统计
 */

@DS("oracle")
@Repository
public interface DataCountOracleMapper {
    /**
     * Description: 统计输入表数据
     * @Date: 2020/12/21 11:07
     * @Author: zbya
     *
     * @param query
     * @return: int
     */
    int getCountNum(DataCountQuery query);
}
