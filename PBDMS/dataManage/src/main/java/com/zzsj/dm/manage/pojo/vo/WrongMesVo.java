package com.zzsj.dm.manage.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/23 14:13
 * @description：导入失败所需信息
 */

@Data
public class WrongMesVo {
    /**
     * 数据信息
     */
    List<WrongSqlVo> dataList;
    /**
     * 列信息
     */
    List<String> col;

    public WrongMesVo() {
    }

    public WrongMesVo(List<WrongSqlVo> dataList, List<String> col) {
        this.dataList = dataList;
        this.col = col;
    }
}
