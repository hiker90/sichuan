package com.zzsj.dm.manage.pojo.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/14 11:08
 * @description：数据目录
 */

@Data
public class CatalogVo {
    /**
     * ID
     */
    private String id;
    /**
     * 名称
     */
    @Length(min=1,max = 128,message = "目录名称长度异常！")
    private String menuName;
    /**
     * 上级ID
     */
    private String parentId;
    /**
     * 类型：0，目录，1表名
     */
    private int type;
    /**
     * 菜单顺序
     */
    private int sort;
    /**
     * 英文名称
     */
    private String enName;
    /**
     * 子目录
     */
    private List<CatalogVo> children;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    private int flag;

}
