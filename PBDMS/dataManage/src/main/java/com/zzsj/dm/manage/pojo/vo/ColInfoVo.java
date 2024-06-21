package com.zzsj.dm.manage.pojo.vo;

import lombok.Data;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/7 17:13
 * @description：列信息
 */

@Data
public class ColInfoVo {
    /**
     * 表英文名称
     */
    private String tableName;
    /**
     * 列英文名称
     */
    private String columnName;
    /**
     * 列中文名称
     */
    private String comments;
}
