package com.zzsj.dm.manage.pojo.vo;

import lombok.Data;


/**
 * @author ：zbya
 * @date ：Created in 2020/9/4 11:20
 * @description：数据对标字段
 */

@Data
public class DmCheckDetailVo {
    /**
     * ID
     */
    private String id;

    /**
     * 对标主表ID
     */
    private String checkMainId;

    /**
     * 规则
     */
    private String rule;

    /**
     * 对标字段英文名称
     */
    private String colEnName;

    /**
     * 对标字段中文名称
     */
    private String colCnName;

    /**
     * 排序
     */
    private String sort;

}
