package com.zzsj.dm.manage.pojo.vo;

import com.zzsj.dm.base.pojo.query.BaseParamQuery;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Date;


/**
 * @author ：zbya
 * @date ：Created in 2020/9/9 15:12
 * @description：表集合
 */

@Data
public class TabListVo extends BaseParamQuery{
    private Object excel;
    /**
     * 主键
     */
    private String id;

    /**
     * 表名
     */
    @Length(min=1,max = 128,message = "表中文名称长度异常！")
    private String tableCnName;

    /**
     * 英文名
     */
    @Length(min=1,max = 128,message = "表英文名称长度异常！")
    private String tableEnName;

    /**
     * 数据标识
     */
    private Integer flag;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;
}
