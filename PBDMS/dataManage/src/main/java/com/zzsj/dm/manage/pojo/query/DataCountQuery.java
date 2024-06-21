package com.zzsj.dm.manage.pojo.query;

import com.zzsj.dm.base.pojo.query.BaseParamQuery;
import lombok.Data;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/18 22:48
 * @description：
 */

@Data
public class DataCountQuery extends BaseParamQuery {
    /**
     * 开始时间
     */
    private String beginTime;
    /**
     * 结束时间
     */
    private String endTime;
}
