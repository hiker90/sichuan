package com.zzsj.analysis.manage.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 全省综合交通建设投资完成情况表
 *
 * @author zbya
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("PROVINCE_TRANSPORTATION_CONSTRUCTION_INVESTMENT")
public class ProvinceTransportationConstructionInvestment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
            @TableId("ID")
    private String id;

    /**
     * 序号
     */
        @TableField("SORT")
    private Integer sort;

    /**
     * 指标
     */
        @TableField("QUOTA")
    private String quota;

    /**
     * 年度目标
     */
        @TableField("YEAR_TARGET")
    private BigDecimal yearTarget;

    /**
     * 本月完成
     */
        @TableField("CURR_MONTH_FINISHED")
    private BigDecimal currMonthFinished;

    /**
     * 累计完成
     */
        @TableField("TOTAL_FINISHED")
    private BigDecimal totalFinished;

    /**
     * 完成比例
     */
        @TableField("FINISHED_PERCENT")
    private String finishedPercent;

    /**
     * 本月环比
     */
        @TableField("CURR_MONTH_MOM")
    private String currMonthMom;

    /**
     * 同比增速
     */
        @TableField("YOY_GROWTH_RATE")
    private String yoyGrowthRate;

    /**
     * 年份
     */
        @TableField("YEAR")
    private Integer year;

    /**
     * 月份
     */
        @TableField("MONTH")
    private Integer month;

    /**
     * 时间
     */
        @TableField("SYNCHRONIZE_TIME")
    private LocalDateTime synchronizeTime;

    public ProvinceTransportationConstructionInvestment() {
    }

    public ProvinceTransportationConstructionInvestment(Integer sort, String quota) {
        this.sort = sort;
        this.quota = quota;
    }

    public ProvinceTransportationConstructionInvestment(Integer sort, String quota, BigDecimal yearTarget, BigDecimal totalFinished) {
        this.sort = sort;
        this.quota = quota;
        this.yearTarget = yearTarget;
        this.totalFinished = totalFinished;
    }
}
