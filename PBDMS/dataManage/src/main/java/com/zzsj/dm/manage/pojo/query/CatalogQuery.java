package com.zzsj.dm.manage.pojo.query;

import com.zzsj.dm.base.pojo.query.BaseParamQuery;
import lombok.Data;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/14 14:28
 * @description：数据目录
 */

@Data
public class CatalogQuery extends BaseParamQuery {
    /**
     * 名称
     */
    private String menuName;
}
