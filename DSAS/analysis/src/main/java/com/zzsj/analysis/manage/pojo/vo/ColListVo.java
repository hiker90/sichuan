package com.zzsj.analysis.manage.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/10 10:50
 * @description：列集合
 */

@Data
public class ColListVo {
    /**
     * 主键
     */
    @TableId("ID")
    private String id;

    /**
     * 表ID
     */
    @TableField("TABLE_ID")
    private String tableId;

    /**
     * 排序
     */
    @TableField("SORT")
    private Integer sort;

    /**
     * 列中文名
     */
    @Length(min=1,max = 128,message = "列中文名称长度异常！")
    @TableField("COL_CN_NAME")
    private String colCnName;

    /**
     * 列英文名
     */
    @Length(min=1,max = 128,message = "列中文名称长度异常！")
    @TableField("COL_EN_NAME")
    private String colEnName;

    /**
     * 数据类型
     */
    @TableField("DATA_TYPE")
    private String dataType;

    /**
     * 长度
     */
    @TableField("LENGTH")
    private Integer length;

    /**
     * 小数精度
     */
    @TableField("DECIMAL_LENGTH")
    private Integer decimalLength;

    /**
     * 日期格式
     */
    @TableField("DATE_TYPE")
    private String dateType;

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
}
