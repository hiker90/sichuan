package com.zzsj.dm.manage.pojo.vo;

import lombok.Data;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/7 17:10
 * @description：表信息
 */

@Data
public class TabInfoVo {
    /**
     * 表英文名称
     */
    private String tableName;
    /**
     * 表中文名称
     */
    private String comments;
}
