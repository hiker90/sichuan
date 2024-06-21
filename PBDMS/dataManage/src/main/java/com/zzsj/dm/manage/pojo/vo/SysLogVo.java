package com.zzsj.dm.manage.pojo.vo;

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
    private Integer operate;

    /**
     * 操作人
     */
    private String operateUser;

    /**
     * 所属单位
     */
    private String unit;

    /**
     * 地址
     */
    private String ip;

    /**
     * 请求参数
     */
    private String queryParam;

    /**
     * 模块
     */
    private String module;

    /**
     * 请求方式
     */
    private String queryMethod;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 浏览器版本
     */
    private String browserVersion;

    /**
     * 操作系统
     */
    private String operatingSystem;

    /**
     * 返回结果
     */
    private String returnMes;

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
