package com.zzsj.dm.manage.pojo.vo;

import lombok.Data;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/10 15:49
 * @description：错误信息
 */

@Data
public class WrongSqlVo {
    /**
     * ID
     */
    private String id;
    /**
     * LOG_ID
     */
    private String logId;
    /**
     * 数据集合 ‘，’分隔
     */
    private String data;
    /**
     *异常信息
     */
    private String exceptionMessage;

    public WrongSqlVo(String id, String logId, String data, String exceptionMessage) {
        this.id = id;
        this.logId = logId;
        this.data = data;
        this.exceptionMessage = exceptionMessage;
    }
}
