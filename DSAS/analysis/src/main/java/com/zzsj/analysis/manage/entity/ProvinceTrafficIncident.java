package com.zzsj.analysis.manage.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 全省公路水路行业安全生产事故表
 *
 * @author zbya
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("PROVINCE_TRAFFIC_INCIDENT")
public class ProvinceTrafficIncident implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("ID")
    private String id;

    /**
     * 序号
     */
    @TableField("SORT")
    private Integer sort;

    /**
     * 类别
     */
    @TableField("TYPE")
    private String type;

    /**
     * 合计-事故数（起）
     * 合计-事故数（起）
     */
    @TableField("TOTAL_INCIDENT")
    private Integer totalIncident;

    /**
     * 合计-死亡人数（人）
     */
    @TableField("TOTAL_DIED")
    private Integer totalDied;

    /**
     * 道路运输-事故起数
     */
    @TableField("ROAD_TRANSPORT_INCIDENT")
    private Integer roadTransportIncident;

    /**
     * 道路运输-死亡人数
     */
    @TableField("ROAD_TRANSPORT_DIED")
    private Integer roadTransportDied;

    /**
     * 水上交通-事故起数
     */
    @TableField("WATERAGE_INCIDENT")
    private Integer waterageIncident;

    /**
     * 水上交通-死亡人数
     */
    @TableField("WATERAGE_DIED")
    private Integer waterageDied;

    /**
     * 交通建设-事故起数
     */
    @TableField("TRAFFIC_CONS_INCIDENT")
    private Integer trafficConsIncident;

    /**
     * 交通建设-死亡人数
     */
    @TableField("TRAFFIC_CONS_DIED")
    private Integer trafficConsDied;

    /**
     * 年份
     */
    @TableField("YEAR")
    private Integer year;

    /**
     * 月份
     */
    @TableField("MONTH")
    private Integer month;

    /**
     * 时间
     */
    @TableField("SYNCHRONIZE_TIME")
    private Date synchronizeTime;

    public ProvinceTrafficIncident() {
    }
}
