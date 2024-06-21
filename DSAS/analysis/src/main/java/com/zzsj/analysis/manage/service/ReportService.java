package com.zzsj.analysis.manage.service;

import com.github.pagehelper.PageInfo;
import com.zzsj.analysis.base.pojo.query.PageQuery;
import com.zzsj.analysis.manage.entity.CulRule;
import com.zzsj.analysis.manage.pojo.query.DataQueryQuery;
import com.zzsj.analysis.manage.pojo.query.ReportQuery;
import com.zzsj.analysis.manage.pojo.vo.ColListVo;
import com.zzsj.analysis.manage.pojo.vo.ReportModelVo;
import com.zzsj.analysis.manage.pojo.vo.TabListVo;

import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2020/10/16 10:20
 * @description：报表管理
 */
public interface ReportService {
    /**
     * Description: 获取结果表集合
     * @Date: 2020/10/16 14:36
     * @Author: zbya
     *
     * @param
     * @return: java.util.List<com.zzsj.analysis.manage.pojo.vo.TabListVo>
     */
    List<CulRule> getTabList();

    /**
     * Description: 数据查询
     * @Date: 2020/9/22 22:24
     * @Author: zbya
     *
     * @param pageQuery
     * @return: com.github.pagehelper.PageInfo
     */
    PageInfo getData(PageQuery<DataQueryQuery> pageQuery) throws Exception;

    /**
     * Description: 获取指定表列信息
     * @Date: 2020/10/23 16:04
     * @Author: zbya
     *
     * @param tableEnName
     * @return: java.util.List<com.zzsj.analysis.manage.pojo.vo.ColListVo>
     */
    List<ColListVo> getColumn(String tableEnName) throws Exception;

}
