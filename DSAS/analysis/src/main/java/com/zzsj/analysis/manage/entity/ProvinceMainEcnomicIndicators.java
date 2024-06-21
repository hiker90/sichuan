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
 * 全省主要经济指标
 *
 * @author zbya
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("PROVINCE_MAIN_ECNOMIC_INDICATORS")
public class ProvinceMainEcnomicIndicators implements Serializable {

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
     * 类别
     */
        @TableField("TYPE")
    private String type;

    /**
     * 本月完成
     */
        @TableField("CURR_MONTH_FINISHED")
    private String currMonthFinished;

    /**
     * 本月同比
     */
        @TableField("CURR_MONTH_YOY")
    private String currMonthYoy;

    /**
     * 累计完成
     */
        @TableField("TOTAL_FINISHED")
    private String totalFinished;

    /**
     * 累计增长
     */
        @TableField("TOTAL_INCREASE")
    private String totalIncrease;

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

    public ProvinceMainEcnomicIndicators() {
    }

    public ProvinceMainEcnomicIndicators(Integer sort, String type) {
        this.sort = sort;
        this.type = type;
    }
}
