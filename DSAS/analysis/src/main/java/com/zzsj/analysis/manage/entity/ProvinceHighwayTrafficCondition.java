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
 * 全省公路网运行情况统计表
 *
 * @author zbya
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("PROVINCE_HIGHWAY_TRAFFIC_CONDITION")
public class ProvinceHighwayTrafficCondition implements Serializable {

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
     * 项目
     */
        @TableField("PROJECT_NAME")
    private String projectName;

    /**
     * 项目-说明1
     */
        @TableField("PROJECT_ILLUSTRATION1")
    private String projectIllustration1;

    /**
     * 项目-说明2
     */
        @TableField("PROJECT_ILLUSTRATION2")
    private String projectIllustration2;

    /**
     * 单位
     */
        @TableField("UNIT")
    private String unit;

    /**
     * 完成情况
     */
        @TableField("FINISHED_CONDITION")
    private String finishedCondition;

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

    public ProvinceHighwayTrafficCondition() {
    }

    public ProvinceHighwayTrafficCondition(String id, Integer sort, String projectName, String projectIllustration1, String projectIllustration2, String unit) {
        this.id = id;
        this.sort = sort;
        this.projectName = projectName;
        this.projectIllustration1 = projectIllustration1;
        this.projectIllustration2 = projectIllustration2;
        this.unit = unit;
    }

    public ProvinceHighwayTrafficCondition(String id, Integer sort, String projectName, String projectIllustration1, String projectIllustration2, String unit, String finishedCondition) {
        this.id = id;
        this.sort = sort;
        this.projectName = projectName;
        this.projectIllustration1 = projectIllustration1;
        this.projectIllustration2 = projectIllustration2;
        this.unit = unit;
        this.finishedCondition = finishedCondition;
    }
}
