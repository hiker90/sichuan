package com.zzsj.dm.manage.pojo.vo;

import lombok.Data;


/**
 * @author ：zbya
 * @date ：Created in 2020/9/10 18:02
 * @description：列类型
 */

@Data
public class ColTypesVo {
    /**
     * 主键
     */
    private String id;

    /**
     * 数据类型
     */
    private String dataType;

    /**
     * 是否可选长度
     */
    private Integer exLength;

    /**
     * 是否可选小数精度
     */
    private Integer exDecimal;

    /**
     * 最长可选长度
     */
    private Integer maxLength;

    /**
     * 最长小数
     */
    private Integer maxDecimal;

    /**
     * 最短小数
     */
    private Integer minDecimal;

    /**
     * 备注
     */
    private String comments;

}
