package com.zzsj.dm.manage.pojo.query;

import lombok.Data;

/**
 * @author ：zbya
 * @date ：Created in 2020/12/24 13:52
 * @description：自建表信息
 */
@Data
public class TabDataOprQuery {
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
}
