package com.zzsj.dm.manage.pojo.vo;

import lombok.Data;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/22 22:30
 * @description：数据查询条件
 */

@Data
public class DataQueryParamVo {
    /**
     * 列
     */
    private String colName;
    /**
     * 表达式
     */
    private  String expression;
    /**
     * 值
     */
    private String value;
    /**
     * 连接词
     */
    private String link;
}
