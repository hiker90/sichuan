package com.zzsj.analysis.manage.pojo.query;

import lombok.Data;

/**
 * @author ：zbya
 * @date ：Created in 2020/12/17 11:17
 * @description：riqi
 */
@Data
public class DateQuery {
    /**
     * 年
     */
    private int year;
    /**
     * 月
     */
    private int month;
    /**
     * 表名称
     */
    private String tabName;
    /**
     * 列名称
     */
    private String colName;

    public DateQuery(int year, int month) {
        this.year = year;
        this.month = month;
    }

    public DateQuery() {
    }

    public DateQuery(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return year + "年" + month + "月";
    }

    public static void main(String[] args) {
        System.out.println(new DateQuery(2020, 1).toString());
    }
}
