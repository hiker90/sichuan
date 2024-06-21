package com.zzsj.dm.manage.pojo.query;

import com.zzsj.dm.base.pojo.query.BaseParamQuery;
import lombok.Data;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/4 9:50
 * @description：
 */

@Data
public class CheckQuery extends BaseParamQuery {
    /**
     *0:启用，1：未启用
     */
    private int status;
}
