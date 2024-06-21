package com.zzsj.dm.manage.mapper.oracle;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2020/10/15 17:08
 * @description：oracle更新数据条数
 */
@DS("oracle")
@Repository
public interface DbOracleUpdateMapper {
    /**
     * Description: 获取所有表名
     * @Date: 2020/9/17 14:14
     * @Author: zbya
     *
     * @param
     * @return: java.util.List<java.lang.String>
     */
    List<String> getTabList();

    /**
     * Description: 更新表数据
     * @Date: 2020/9/17 15:32
     * @Author: zbya
     *
     * @param sql
     * @return: void
     */
    void updateOracleRowNum(String sql);
}
