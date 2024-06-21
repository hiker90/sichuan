package com.zzsj.analysis.manage.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 报表模版表
 *
 * @author zbya
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("CUL_RULE")
public class CulRule implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 最小统计时间(0月,1季度,2年)
     */
    @TableField("DATE")
    private Integer date;

    /**
     * 序号
     */
    @TableField("SORT")
    private Integer sort;

    /**
     * 表名
     */
    @TableField("TABLE_NAME")
    private String tableName;

    /**
     * 表英文名
     */
    @TableField("TABLE_EN_NAME")
    private String tableEnName;

    /**
     * 年
     */
    @TableField("YEAR")
    private Integer year;

    /**
     * 季度
     */
    @TableField("QUARTER")
    private Integer quarter;

    /**
     * 月
     */
    @TableField("MONTH")
    private Integer month;

    /**
     * 报告名称
     */
    private String reportName;


    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField("UPDATE_TIME")
    private Date updateTime;


}
