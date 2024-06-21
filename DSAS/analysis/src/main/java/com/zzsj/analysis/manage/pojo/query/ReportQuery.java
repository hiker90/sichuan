package com.zzsj.analysis.manage.pojo.query;

import com.baomidou.mybatisplus.annotation.TableField;
import com.zzsj.analysis.base.pojo.query.BaseParamQuery;
import lombok.Data;

/**
 * @author ：zbya
 * @date ：Created in 2020/10/17 14:54
 * @description：模版管理查询
 */

@Data
public class ReportQuery extends BaseParamQuery {
    /**
     * 模版名称
     */
    private String modelName;
    /**
     *  开始时间
     */
    private String beginDate;
    /**
     *  结束时间
     */
    private String endDate;

    /**
     * 拟稿单位
     */
    private String checkUnit;
    /**
     *  报告状态
     */
    private String reportStatus;
    /**
     * 报告类型
     */
    private Integer reportType;
}
