package com.zzsj.dm.manage.pojo.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/4 10:29
 * @description：数据对标
 */

@Data
public class DmCheckMainVo {
    /**
     * 主键
     */
    private String id;

    /**
     * 方案名称
     */
    @Length(min=1,max = 20,message = "方案名称长度异常！")
    private String name;

    /**
     * 表中文名称
     */
    private String tabCnName;

    /**
     * 表英文名称
     */
    private String tabEnName;

    /**
     * 备注
     */
    @Length(min=1,max = 100,message = "备注长度异常！")
    private String comment;

    /**
     * 状态（0:启用,1:停用）
     */
    private Integer status;

    /**
     * 时间
     */
    private Date updateTime;

    public DmCheckMainVo() {
    }

    public DmCheckMainVo(String id) {
        this.id = id;
    }
}
