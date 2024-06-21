package com.zzsj.analysis.manage.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zzsj.analysis.manage.entity.CulRule;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author ：zbya
 * @date ：Created in 2020/10/16 17:47
 * @description：报告管理模版
 */

@Data
public class ReportModelVo extends CulRule {

    /**
     * 模版名称
     */
    @TableField("MODEL_NAME")
    private String modelName;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private String version;

    /**
     * 拟稿人
     */
    @TableField("DRAFT_USER")
    private String draftUser;

    /**
     * 拟稿单位
     */
    @TableField("DRAFT_UNIT")
    private String draftUnit;

    /**
     * 数据标识
     */
    @TableField("FLAG")
    private Integer flag;

}
