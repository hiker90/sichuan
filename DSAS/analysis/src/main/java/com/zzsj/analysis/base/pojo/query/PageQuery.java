package com.zzsj.analysis.base.pojo.query;

import com.zzsj.analysis.base.utils.enums.PageEnum;
import lombok.Data;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/3 14:12
 * @description：查询参数
 */

@Data
public class PageQuery <T extends BaseParamQuery>{
    /**
     * 当前页码
     */
    private int pageIndex= PageEnum.PAGE_INDEX.getCode();
    /**
     * 分页大小
     */
    private int pageSize= PageEnum.PAGE_SIZE.getCode();
    /**
     * 其它
     */
    private T queryParams;
}
