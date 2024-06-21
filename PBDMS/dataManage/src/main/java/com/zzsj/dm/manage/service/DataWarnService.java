package com.zzsj.dm.manage.service;

import com.github.pagehelper.PageInfo;
import com.zzsj.dm.base.pojo.query.PageQuery;
import com.zzsj.dm.manage.pojo.query.WarnTypeQuery;
import com.zzsj.dm.manage.pojo.vo.DmWarningVo;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/4 21:19
 * @description：数据质量预警
 */
public interface DataWarnService {

    /**
     * Description: 数据质量-预警情况、处理情况查询
     * @Date: 2020/9/7 14:57
     * @Author: zbya
     *
     * @param pageQuery
     * @return: com.github.pagehelper.PageInfo
     */
    PageInfo getWarningStates(PageQuery<WarnTypeQuery> pageQuery) throws Exception;

    /**
     * Description: 数据质量-预警情况处理
     * @Date: 2020/9/7 14:58
     * @Author: zbya
     *
     * @param id
     * @return: int
     */
    int dealWarning(String name);
}
