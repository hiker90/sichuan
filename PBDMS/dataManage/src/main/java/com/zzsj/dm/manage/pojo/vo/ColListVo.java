package com.zzsj.dm.manage.pojo.vo;

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
    private String id;

    /**
     * 表ID
     */
    private String tableId;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 列中文名
     */
    @Length(min=1,max = 128,message = "列中文名称长度异常！")
    private String colCnName;

    /**
     * 列英文名
     */
    @Length(min=1,max = 128,message = "列中文名称长度异常！")
    private String colEnName;

    /**
     * 数据类型
     */
    private String dataType;

    /**
     * 长度
     */
    private Integer length;

    /**
     * 小数精度
     */
    private Integer decimalLength;

    /**
     * 日期格式
     */
    private String dateType;

    /**
     * 数据标识
     */
    private Integer flag;
    
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;
}
