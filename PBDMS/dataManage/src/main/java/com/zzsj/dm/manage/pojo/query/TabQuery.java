package com.zzsj.dm.manage.pojo.query;

import com.zzsj.dm.base.pojo.query.BaseParamQuery;
import lombok.Data;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/9 20:49
 * @description：表信息操作
 */

@Data
public class TabQuery extends BaseParamQuery {
    /**
     * 主键
     */
    private String id;

    /**
     * 英文名
     */
    private String tableEnName;

    /**
     * 模糊查询条件
     */
    private String tableQueryParam;
}
