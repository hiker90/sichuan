package com.zzsj.analysis.manage.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author ：zbya
 * @date ：Created in 2020/10/16 14:28
 * @description：表集合
 */


@Data
public class TabListVo {
    /**
     * 表名
     */
    private String tableCnName;

    /**
     * 英文名
     */
    private String tableEnName;
}
