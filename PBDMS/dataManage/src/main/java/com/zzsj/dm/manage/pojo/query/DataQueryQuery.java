package com.zzsj.dm.manage.pojo.query;

import com.zzsj.dm.base.pojo.query.BaseParamQuery;
import com.zzsj.dm.manage.pojo.vo.DataQueryParamVo;
import lombok.Data;

import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/22 22:22
 * @description：数据查询条件
 */

@Data
public class DataQueryQuery extends BaseParamQuery {
    /**
     * 表名
     */
    private String tabName;
    /**
     * 列名
     */
    private String colName;
    /**
     * 查询条件
     */
    private String queryParam;
    /**
     * 条件集合
     */
    private List<DataQueryParamVo> params;

}
