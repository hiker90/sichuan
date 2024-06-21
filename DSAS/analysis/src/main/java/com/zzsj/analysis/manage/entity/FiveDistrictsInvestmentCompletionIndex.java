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
 * 五区投资完成指标
 *
 * @author zbya
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("FIVE_DISTRICTS_INVESTMENT_COMPLETION_INDEX")
public class FiveDistrictsInvestmentCompletionIndex implements Serializable {

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
     * 区域
     */
        @TableField("REGION")
    private String region;

    /**
     * 年度目标（亿元）
     */
        @TableField("YEAR_TARGET")
    private BigDecimal yearTarget;

    /**
     * 本月完成
     */
        @TableField("CURR_MONTH_FINISHED")
    private BigDecimal currMonthFinished;

    /**
     * 累计完成（亿元）
     */
        @TableField("TOTAL_FINISHED")
    private BigDecimal totalFinished;

    /**
     * 完成占比
     */
        @TableField("FINISHED_PERCENT")
    private String finishedPercent;

    /**
     * 环比增速
     */
        @TableField("MOM_GROWTH_RATE")
    private String momGrowthRate;

    /**
     * 同比增速
     */
        @TableField("YOY_GROWTH_RATE")
    private String yoyGrowthRate;

    /**
     * 贡献率
     */
        @TableField("CONTRIBUTION_RATE")
    private String contributionRate;

    /**
     * 高速公路-年度目标（亿元）
     */
        @TableField("HIGHWAY_YEAR_GOAL")
    private BigDecimal highwayYearGoal;

    /**
     * 高速公路-本月完成
     */
        @TableField("HIGHWAY_CURR_MONTH_FINISHED")
    private BigDecimal highwayCurrMonthFinished;

    /**
     * 高速公路-累计完成（亿元）
     */
        @TableField("HIGHWAY_TOTAL_FINISHED")
    private BigDecimal highwayTotalFinished;

    /**
     * 高速公路-占比
     */
        @TableField("HIGHWAY_PERCENTAGE")
    private String highwayPercentage;

    /**
     * 高速公路-环比增速
     */
        @TableField("HIGHWAY_MOM")
    private String highwayMom;

    /**
     * 高速公路-同比增速
     */
        @TableField("HIGHWAY_YOY")
    private String highwayYoy;

    /**
     * 国省干线公路-年度目标（亿元）
     */
        @TableField("NP_HIGHWAY_YEAR_GOAL")
    private BigDecimal npHighwayYearGoal;

    /**
     * 国省干线公路-本月完成
     */
        @TableField("NP_HIGHWAY_CURR_MONTH_FINISHED")
    private BigDecimal npHighwayCurrMonthFinished;

    /**
     * 国省干线公路-累计完成（亿元）
     */
        @TableField("NP_HIGHWAY_TOTAL_FINISHED")
    private BigDecimal npHighwayTotalFinished;

    /**
     * 国省干线公路-占比
     */
        @TableField("NP_HIGHWAY_PERCENT")
    private String npHighwayPercent;

    /**
     * 国省干线公路-环比增速
     */
        @TableField("NP_HIGHWAY_MOM")
    private String npHighwayMom;

    /**
     * 国省干线公路-同比增速
     */
        @TableField("NP_HIGHWAY_YOY")
    private String npHighwayYoy;

    /**
     * 农村公路-年度目标（亿元）
     */
        @TableField("RURAL_ROAD_YEAR_GOAL")
    private BigDecimal ruralRoadYearGoal;

    /**
     * 农村公路-本月完成
     */
        @TableField("RURAL_ROAD_CURR_MONTH_FINISHED")
    private BigDecimal ruralRoadCurrMonthFinished;

    /**
     * 农村公路-累计完成（亿元）
     */
        @TableField("RURAL_ROAD_TOTAL_FINISHED")
    private BigDecimal ruralRoadTotalFinished;

    /**
     * 农村公路-占比
     */
        @TableField("RURAL_ROAD_PERCENT")
    private String ruralRoadPercent;

    /**
     * 农村公路-环比增速
     */
        @TableField("RURAL_ROAD_MOM")
    private String ruralRoadMom;

    /**
     * 农村公路-同比增速
     */
        @TableField("RURAL_ROAD_YOY")
    private String ruralRoadYoy;

    /**
     * 内河水运-年度目标（亿元）
     */
        @TableField("INLAND_WATERWAY_YEAR_GOAL")
    private BigDecimal inlandWaterwayYearGoal;

    /**
     * 内河水运-本月完成
     */
        @TableField("INLAND_WATERWAY_CURR_MONTH_FINISHED")
    private BigDecimal inlandWaterwayCurrMonthFinished;

    /**
     * 内河水运-累计完成（亿元）
     */
        @TableField("INLAND_WATERWAY_TOTAL_FINISHED")
    private BigDecimal inlandWaterwayTotalFinished;

    /**
     * 内河水运-占比
     */
        @TableField("INLAND_WATERWAY_PERCENT")
    private String inlandWaterwayPercent;

    /**
     * 内河水运-环比增速
     */
        @TableField("INLAND_WATERWAY_MOM")
    private String inlandWaterwayMom;

    /**
     * 内河水运-同比增速
     */
        @TableField("INLAND_WATERWAY_YOY")
    private String inlandWaterwayYoy;

    /**
     * 站点建设-年度目标（亿元）
     */
        @TableField("SITE_CONSTUCTION_YEAR_GOAL")
    private BigDecimal siteConstuctionYearGoal;

    /**
     * 站点建设-本月完成
     */
        @TableField("SITE_CONSTUCTION_CURR_MONTH_FINISHED")
    private BigDecimal siteConstuctionCurrMonthFinished;

    /**
     * 站点建设-累计完成（亿元）
     */
        @TableField("SITE_CONSTUCTION_TOTAL_FINISHED")
    private BigDecimal siteConstuctionTotalFinished;

    /**
     * 站点建设-占比
     */
        @TableField("SITE_CONSTUCTION_PERCENT")
    private String siteConstuctionPercent;

    /**
     * 站点建设-环比增速
     */
        @TableField("SITE_CONSTUCTION_MOM")
    private String siteConstuctionMom;

    /**
     * 站点建设-同比增速
     */
        @TableField("SITE_CONSTUCTION_YOY")
    private String siteConstuctionYoy;

    /**
     * 养护及其它专项-年度目标（亿元）
     */
        @TableField("CONSERVATION_YEAR_GOAL")
    private BigDecimal conservationYearGoal;

    /**
     * 养护及其它专项-本月完成
     */
        @TableField("CONSERVATION_CURR_MONTH_FINISHED")
    private BigDecimal conservationCurrMonthFinished;

    /**
     * 养护及其它专项-累计完成（亿元）
     */
        @TableField("CONSERVATION_TOTAL_FINISHED")
    private BigDecimal conservationTotalFinished;

    /**
     * 养护及其它专项-其他专项
     */
        @TableField("CONSERVATION_OTHERS")
    private BigDecimal conservationOthers;

    /**
     * 养护及其它专项-普通公路养护
     */
        @TableField("CONSERVATION_ORDINARY_HIGHWAY")
    private BigDecimal conservationOrdinaryHighway;

    /**
     * 养护及其它专项-高速公路养护
     */
        @TableField("CONSERVATION_HIGHWAY")
    private BigDecimal conservationHighway;

    /**
     * 养护及其它专项-智慧交通
     */
        @TableField("CONSERVATION_INTELLIGENT_TRANSPORT")
    private BigDecimal conservationIntelligentTransport;

    /**
     * 养护及其它专项-占比
     */
        @TableField("CONSERVATION_PERCENT")
    private String conservationPercent;

    /**
     * 养护及其它专项-环比增速
     */
        @TableField("CONSERVATION_MOM")
    private String conservationMom;

    /**
     * 养护及其它专项-同比增速
     */
        @TableField("CONSERVATION_YOY")
    private String conservationYoy;

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

    public FiveDistrictsInvestmentCompletionIndex() {
    }

    public FiveDistrictsInvestmentCompletionIndex(Integer sort, String region) {
        this.sort = sort;
        this.region = region;
    }
}
