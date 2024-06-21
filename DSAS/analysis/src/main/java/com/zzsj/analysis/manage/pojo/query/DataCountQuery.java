package com.zzsj.analysis.manage.pojo.query;

import lombok.Data;

/**
 * @author ：zbya
 * @date ：Created in 2020/12/21 11:10
 * @description：数据统计查询
 */

@Data
public class DataCountQuery {
    /**
     * 年
     */
    private int year;
    /**
     * 月
     */
    private int month;
    /**
     * 表英文名
     */
    private String tabEnName;

    public DataCountQuery(int year, int month, String tabEnName) {
        this.year = year;
        this.month = month;
        this.tabEnName = tabEnName;
    }

    public DataCountQuery() {
    }
}
