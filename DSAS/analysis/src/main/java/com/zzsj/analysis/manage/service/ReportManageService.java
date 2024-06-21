package com.zzsj.analysis.manage.service;

import com.github.pagehelper.PageInfo;
import com.zzsj.analysis.base.pojo.query.PageQuery;
import com.zzsj.analysis.manage.entity.CulRule;
import com.zzsj.analysis.manage.pojo.query.ReportQuery;
import com.zzsj.analysis.manage.pojo.vo.ReportModelVo;
import com.zzsj.analysis.manage.pojo.vo.ReportTabVo;

/**
 * @author ：zbya
 * @date ：Created in 2020/10/16 18:06
 * @description：报告管理
 */
public interface ReportManageService {

    /**
     * Description: 模版查询
     *
     * @param pageQuery
     * @Date: 2020/10/17 16:14
     * @Author: zbya
     * @return: com.github.pagehelper.PageInfo
     */
    PageInfo getModel(PageQuery<ReportQuery> pageQuery);

    /**
     * Description: 报告查询
     *
     * @param pageQuery
     * @Date: 2020/10/18 15:55
     * @Author: zbya
     * @return: com.github.pagehelper.PageInfo
     */
    PageInfo getReport(PageQuery<ReportQuery> pageQuery);



    /**
     * Description: 模版送审审核
     *
     * @param reportModelVo
     * @Date: 2020/10/18 15:33
     * @Author: zbya
     * @return: void
     */
    String checkData(ReportTabVo reportModelVo);

    /**
     * Description: 报告删除
     *
     * @param id
     * @Date: 2020/10/17 16:14
     * @Author: zbya
     * @return: void
     */
    void deleteData(String id);
    /**
     * Description: 查询报告
     *
     * @param id
     * @Date: 2020/10/18 15:36
     * @Author: zbya
     * @return: java.lang.String
     */
    String getData(String id);
}
