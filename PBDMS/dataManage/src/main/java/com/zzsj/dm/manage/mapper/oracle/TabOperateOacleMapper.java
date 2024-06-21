package com.zzsj.dm.manage.mapper.oracle;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/9 20:46
 * @description：数据库操作表
 */

@DS("oracle")
@Repository
public interface TabOperateOacleMapper {
    /**
     * Description: 根据表名删除表信息
     * @Date: 2020/9/10 9:38
     * @Author: zbya
     *
     * @param tableEnName
     * @return: void
     */
    void deleteTab(String tableEnName) throws Exception;

    /**
     * Description: insertData
     * @Date: 2020/9/10 15:44
     * @Author: zbya
     *
     * @param sql
     * @return: void
     */
    void insertData(String sql) throws Exception;

    /**
     * Description: 建表SQL
     * @Date: 2020/9/11 13:46
     * @Author: zbya
     *
     * @param s
     * @return: void
     */
    void createTab(String sql);
}
