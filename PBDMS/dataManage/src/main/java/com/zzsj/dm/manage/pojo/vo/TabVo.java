package com.zzsj.dm.manage.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/10 10:48
 * @description：自定义表
 */

@Data
public class TabVo extends TabListVo {
    /**
     * 表字段集合
     */
    private List<ColListVo> colList;
}
