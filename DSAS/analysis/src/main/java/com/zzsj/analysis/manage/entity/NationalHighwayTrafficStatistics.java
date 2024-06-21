package com.zzsj.analysis.manage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 全国公路客货运输量统计表
 *
 * @author zbya
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("NATIONAL_HIGHWAY_TRAFFIC_STATISTICS")
public class NationalHighwayTrafficStatistics implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
            @TableId("ID")
    private String id;

    /**
     * 区域
     */
        @TableField("REGION")
    private String region;

    public NationalHighwayTrafficStatistics(Integer sort) {
        this.sort = sort;
    }

    public NationalHighwayTrafficStatistics() {
    }

    /**
     * 序号
     */
        @TableField("SORT")
    private Integer sort;

    /**
     * 省份
     */
        @TableField("PROVINCE")
    private String province;

    /**
     * 旅客周转量-完成量（万人公里）
     */
        @TableField("TOUR_WEEK_TRANS_CPLETD_NUM")
    private Integer tourWeekTransCpletdNum;

    /**
     * 旅客周转量-同比增速
     */
        @TableField("TOUR_WEEK_TRANS_YEAR_GROWTH")
    private String tourWeekTransYearGrowth;

    /**
     * 旅客周转量-排名
     */
        @TableField("TOUR_WEEK_TRANS_RANKING")
    private Integer tourWeekTransRanking;

    /**
     * 货物周转量-完成量（万吨公里）
     */
        @TableField("GOODS_WEEK_TRANS_CPLETD_NUM")
    private Integer goodsWeekTransCpletdNum;

    /**
     * 货物周转量-同比增速
     */
        @TableField("GOODS_WEEK_TRANS_YEAR_GROWTH")
    private String goodsWeekTransYearGrowth;

    /**
     * 货物周转量-排名
     */
        @TableField("GOODS_WEEK_TRANS_RANKING")
    private Integer goodsWeekTransRanking;

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

    private Integer inputOrder;

}
