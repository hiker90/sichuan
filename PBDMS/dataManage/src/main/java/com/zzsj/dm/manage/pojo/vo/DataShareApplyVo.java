package com.zzsj.dm.manage.pojo.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author ：zbya
 * @date ：Created in 2020/10/15 14:52
 * @description：数据共享申请表
 */

@Data
public class DataShareApplyVo {
    /**
     * 主键
     */
    private String id;

    /**
     * 申请人
     */
    private String applyUser;

    /**
     * 申请单位
     */
    private String applyUnit;

    /**
     * 申请时间
     */
    private Date applyDate;

    /**
     * 申请数据名称
     */
    private String applyDataName;

    /**
     * 审核状态(0:待审核,1.已通过,2.已驳回)
     */
    private Integer applyStatus;

    /**
     * 审核时间
     */
    private Date checkDate;

    /**
     * 审核人
     */
    private String checkUser;

    /**
     * 申请类型
     */
    private String applyType;

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
