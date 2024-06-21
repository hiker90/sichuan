package com.zzsj.dm.manage.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/2 10:17
 * @description：数据接入量Vo
 */

@Data
public class AcessNumVo {
    /**
     * 接入数据量
     */
    private BigDecimal dataNum;
    /**
     * 数据接口量
     */
    private int interfaceNum;
    /**
     * 接入表量
     */
    private int tableNum;
}
