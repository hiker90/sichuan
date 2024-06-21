package com.zzsj.dm.manage.pojo.vo;

import lombok.Data;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/18 22:25
 * @description：数据统计
 */

@Data
public class DataCountVo {
    /**
     * 数据单位
     */
    private String unit;
    /**
     * 总数据量
     */
    private int totalNum;
    /**
     * 范围新增量
     */
    private int num;
}
