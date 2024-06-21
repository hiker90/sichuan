package com.zzsj.analysis.manage.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ：zbya
 * @date ：Created in 2021/1/14 15:16
 * @description：全省综合交通建设投资完成情况表其他
 */

@Data
public class ProInvestOthersVo {
    /**
     * 公路水路投资-年度目标（亿元）
     */
    private BigDecimal roadWaterwayYearGoal;
    /**
     * 公路水路投资-累计完成（亿元）
     */
    private BigDecimal roadWaterwayTotalFinished;
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
}
