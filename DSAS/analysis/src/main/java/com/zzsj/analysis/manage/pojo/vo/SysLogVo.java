package com.zzsj.analysis.manage.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/15 15:32
 * @description：系统日志
 */

@Data
public class SysLogVo {

    /**
     * 主键
     */
    private String id;

    /**
     * 操作
     */
    @TableField("OPERATE")
    private Integer operate;

    /**
     * 操作人
     */
    @TableField("OPERATE_USER")
    private String operateUser;

    /**
     * 所属单位
     */
    @TableField("UNIT")
    private String unit;

    /**
     * 地址
     */
    @TableField("IP")
    private String ip;

    /**
     * 请求参数
     */
    @TableField("QUERY_PARAM")
    private String queryParam;

    /**
     * 模块
     */
    @TableField("MODULE")
    private String module;

    /**
     * 请求方式
     */
    @TableField("QUERY_METHOD")
    private String queryMethod;

    /**
     * 浏览器
     */
    @TableField("BROWSER")
    private String browser;

    /**
     * 浏览器版本
     */
    @TableField("BROWSER_VERSION")
    private String browserVersion;

    /**
     * 操作系统
     */
    @TableField("OPERATING_SYSTEM")
    private String operatingSystem;

    /**
     * 返回结果
     */
    @TableField("RETURN_MES")
    private String returnMes;

    /**
     * 数据标识
     */
    @TableField("FLAG")
    private Integer flag;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField("UPDATE_TIME")
    private Date updateTime;

}
