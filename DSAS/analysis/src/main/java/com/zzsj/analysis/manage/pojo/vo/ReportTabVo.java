package com.zzsj.analysis.manage.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author ：zbya
 * @date ：Created in 2021/1/21 15:46
 * @description：报告表
 */

@Data
public class ReportTabVo {
    /**
     * 主键
     */
    @TableId("ID")
    private String id;

    /**
     * 报告名称
     */
    @TableField("REPORT_NAME")
    private String reportName;

    /**
     * 拟稿单位
     */
    @TableField("DRAFT_UNIT")
    private String draftUnit;

    /**
     * 模版名称
     */
    @TableField("MODEL_NAME")
    private String modelName;

    /**
     * 模版ID
     */
    @TableField("MODEL_ID")
    private String modelId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private String version;

    /**
     * 模版
     */
    @TableField("MODEL")
    private String model;

    /**
     * 审核单位
     */
    @TableField("CHECK_UNIT")
    private String checkUnit;

    /**
     * 审核人
     */
    @TableField("CHECK_USER")
    private String checkUser;

    /**
     * 审核时间
     */
    @TableField("CHECK_DATE")
    private Date checkDate;

    /**
     * 报告状态
     */
    @TableField("REPORT_STATUS")
    private Integer reportStatus;

    /**
     * 拟稿人
     */
    @TableField("DRAFT_USER")
    private String draftUser;

    /**
     * 数据标识
     */
    @TableField("FLAG")
    private Integer flag;

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

    /**
     * 报告类型
     */
    private Integer reportType;
}
