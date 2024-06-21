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
 * 全国公路水路固定资产投资完成情况表
 *
 * @author zbya
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("NATIONAL_HIGHWAY_WATERWAY_INVESTMENT_COMPLETION")
public class NationalHighwayWaterwayInvestmentCompletion implements Serializable {

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
     * 排名
     */
        @TableField("RANKING")
    private Integer ranking;

    /**
     * 省份
     */
        @TableField("PROVINCE")
    private String province;

    /**
     * 部下达目标
     */
        @TableField("DIVISION_TARGET")
    private BigDecimal divisionTarget;

    /**
     * 省内控目标
     */
        @TableField("PROVINCE_TARGET")
    private BigDecimal provinceTarget;

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

    public NationalHighwayWaterwayInvestmentCompletion(Integer sort, String province) {
        this.sort = sort;
        this.province = province;
    }
}
