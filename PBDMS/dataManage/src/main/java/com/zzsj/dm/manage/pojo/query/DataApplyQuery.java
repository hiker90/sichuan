package com.zzsj.dm.manage.pojo.query;

import com.zzsj.dm.base.pojo.query.BaseParamQuery;
import lombok.Data;

/**
 * @author ：zbya
 * @date ：Created in 2020/10/15 15:23
 * @description：数据共享申请
 */

@Data
public class DataApplyQuery extends BaseParamQuery {
    /**
     * 开始时间
     */
    private String beginDate;
    /**
     * 结束时间
     */
    private String endDate;
    /**
     * 审核状态
     */
    private int applyStatus;
    /**
     * 申请数据名称
     */
    private String applyDataName;
}
