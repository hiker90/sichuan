package com.zzsj.analysis.manage.mapper.oracle;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.zzsj.analysis.manage.pojo.query.DataQueryQuery;
import com.zzsj.analysis.manage.pojo.vo.ColInfoVo;
import com.zzsj.analysis.manage.pojo.vo.ColListVo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author ：zbya
 * @date ：Created in 2020/10/16 10:24
 * @description：报表管理
 */

@Repository
public interface ReportMapper {
    /**
     * Description: 数据查询
     * @Date: 2020/10/16 15:03
     * @Author: zbya
     *
     * @param queryParams
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> getData(DataQueryQuery queryParams);

    /**
     * Description: 获取表的列信息
     * @Date: 2020/10/16 15:13
     * @Author: zbya
     *
     * @param tableName
     * @return: java.util.List<ColInfoVo>
     */
    List<ColInfoVo> getColList(String tableName) throws Exception;

    /**
     * Description: 获取指定表列信息
     * @Date: 2020/10/23 16:07
     * @Author: zbya
     *
     * @param tableEnName
     * @return: java.util.List<com.zzsj.analysis.manage.pojo.vo.ColListVo>
     */
    List<ColListVo> getColumn(String tableEnName);
}
