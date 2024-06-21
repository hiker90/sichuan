package com.zzsj.dm.manage.pojo.query;

import com.zzsj.dm.base.pojo.query.BaseParamQuery;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/3 9:15
 * @description：预警管理
 */

@Data
public class WarnTypeQuery extends BaseParamQuery implements Serializable{
    /**
     *状态(0:未处理,1:已处理)
     */
    @NotNull(message = "状态不能为空")
    private int status;
    /**
     * 名称(表名或接口名)
     */
    private String name;
}
