package com.zzsj.dm.manage.pojo.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/10 16:56
 * @description：导入日志信息
 */

@Data
public class ImportLogVo {
    /**
     * 主键
     */
    private String id;

    /**
     * 状态（0:正在导入,1:导入完成）
     */
    private Integer status;

    /**
     * 总条数
     */
    private Integer totalNum;

    /**
     * 成功条数
     */
    private Integer successNum;

    /**
     * 失败条数
     */
    private Integer failNum;

    /**
     * 表名
     */
    private String tableName;
    /**
     * 表ID
     */
    private String tableId;
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

    public ImportLogVo(String id, Integer totalNum, String tableName,String tableId) {
        this.id = id;
        this.totalNum = totalNum;
        this.tableName = tableName;
        this.tableId=tableId;
    }

    public ImportLogVo(String id, Integer status, Integer successNum, Integer failNum) {
        this.id = id;
        this.status = status;
        this.successNum = successNum;
        this.failNum = failNum;
    }

    public ImportLogVo() {
    }
}
