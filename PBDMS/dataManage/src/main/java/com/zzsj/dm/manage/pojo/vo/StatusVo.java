package com.zzsj.dm.manage.pojo.vo;

import lombok.Data;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/8 16:43
 * @description：状态实体
 */

@Data
public class StatusVo {
    /**
     * ID
     */
    private String id;
    /**
     * 状态 0启用，1停用
     */
    private Integer status;
}
