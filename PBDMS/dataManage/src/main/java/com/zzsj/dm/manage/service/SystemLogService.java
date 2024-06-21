package com.zzsj.dm.manage.service;

import com.github.pagehelper.PageInfo;
import com.zzsj.dm.base.pojo.query.PageQuery;
import com.zzsj.dm.manage.pojo.query.SysLogQuery; /**
 * @author ：zbya
 * @date ：Created in 2020/9/15 15:57
 * @description：系统操作日志
 */
public interface SystemLogService {
    /**
     * Description: 查询系统操作日志
     * @Date: 2020/9/15 16:02
     * @Author: zbya
     *
     * @param pageQuery
     * @return: com.github.pagehelper.PageInfo
     */
    PageInfo getCatalog(PageQuery<SysLogQuery> pageQuery);
}
