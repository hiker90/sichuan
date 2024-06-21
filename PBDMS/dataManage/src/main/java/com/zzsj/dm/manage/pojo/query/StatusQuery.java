package com.zzsj.dm.manage.pojo.query;

import com.zzsj.dm.base.pojo.query.BaseParamQuery;
import lombok.Data;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/9 19:17
 * @description：状态查询
 */

@Data
public class StatusQuery extends BaseParamQuery {
    /**
   * ID
   */
    private String id;
    /**
     * 状态 0启用，1停用
     */
    private Integer status;
}
