package com.zzsj.dm.manage.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/3 9:54
 * @description：预警情况
 */

@Data
public class DmWarningVo {
    /**
     *主键
     */
    private String id;
    /**
     * 预警情况
     */
    private String warningState;

    /**
     * 状态(0:未处理,1:已处理)
     */
    private BigDecimal status;
    /**
     * 预警类型(1:接口,2:表)
     */
    private Integer warningType;
    /**
     * 名称(表名或接口名)
     */
    private String name;

    /**
     * 时间
     */
    private Date updateTime;
}
