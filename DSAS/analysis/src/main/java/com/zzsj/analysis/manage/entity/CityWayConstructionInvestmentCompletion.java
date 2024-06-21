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
 * 各市（州）公路水路建设投资完成情况表
 *
 * @author zbya
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("CITY_WAY_CONSTRUCTION_INVESTMENT_COMPLETION")
public class CityWayConstructionInvestmentCompletion implements Serializable {

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
     * 市（州）
     */
        @TableField("CITY")
    private String city;

    /**
     * 合计-年度计划
     */
        @TableField("TOTAL_ANNUAL_PLAN")
    private BigDecimal totalAnnualPlan;

    /**
     * 合计-本月完成
     */
        @TableField("TOTAL_CURR_MONTH_FINISHED")
    private BigDecimal totalCurrMonthFinished;

    /**
     * 合计-累计完成
     */
        @TableField("TOTAL_FINISHED")
    private BigDecimal totalFinished;

    /**
     * 合计-完成率
     */
        @TableField("TOTAL_COMPLETION_RATE")
    private String totalCompletionRate;

    /**
     * 合计-完成率排名
     */
        @TableField("TOTAL_COMPLETION_RATE_RANKING")
    private Integer totalCompletionRateRanking;

    /**
     * 合计-相对上月排名变化
     */
        @TableField("TOTAL_MONTH_RANKING_CAHNGE")
    private String totalMonthRankingCahnge;

    /**
     * 完成投资-高速公路
     */
        @TableField("FINISHED_INVESTMENT_HIGHWAY")
    private BigDecimal finishedInvestmentHighway;

    /**
     * 完成投资-国省干线公路
     */
        @TableField("FINISHED_INVESTMENT_NP_HIGHWAY")
    private BigDecimal finishedInvestmentNpHighway;

    /**
     * 完成投资-农村公路
     */
        @TableField("FINISHED_INVESTMENT_RURAL_ROAD")
    private BigDecimal finishedInvestmentRuralRoad;

    /**
     * 完成投资-内河水运
     */
        @TableField("FINISHED_INVESTMENT_INLAND_WATERWAY")
    private BigDecimal finishedInvestmentInlandWaterway;

    /**
     * 完成投资-站点建设
     */
        @TableField("FINISHED_INVESTMENT_SITE_CONSTUCTION")
    private BigDecimal finishedInvestmentSiteConstuction;

    /**
     * 完成投资-养护及专项
     */
        @TableField("FINISHED_INVESTMENT_CONSERVATION")
    private BigDecimal finishedInvestmentConservation;

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

    public CityWayConstructionInvestmentCompletion() {
    }

    public CityWayConstructionInvestmentCompletion(Integer sort, String city) {
        this.sort = sort;
        this.city = city;
    }
}
