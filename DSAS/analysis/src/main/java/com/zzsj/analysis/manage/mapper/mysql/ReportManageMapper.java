package com.zzsj.analysis.manage.mapper.mysql;

import com.github.pagehelper.PageInfo;
import com.zzsj.analysis.base.pojo.query.PageQuery;
import com.zzsj.analysis.manage.entity.CulRule;
import com.zzsj.analysis.manage.pojo.query.ReportQuery;
import com.zzsj.analysis.manage.pojo.vo.ReportModelVo;
import com.zzsj.analysis.manage.pojo.vo.ReportTabVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2020/10/16 18:09
 * @description：报告管理
 */

@Repository
public interface ReportManageMapper {

    /**
     * Description: 报告生成
     * @Date: 2021/1/19 14:37
     * @Author: zbya
     *
     * @param report
     * @return: java.lang.String
     */
    int newReport(ReportTabVo report);

    /**
     * Description: 模版查询
     * @Date: 2020/10/17 16:14
     * @Author: zbya
     *
     * @param reportQuery
     * @return: com.github.pagehelper.PageInfo
     */
    List<ReportModelVo> getModel(ReportQuery reportQuery);
    
    /**
     * Description: 报告查询 
     * @Date: 2020/10/18 15:54 
     * @Author: zbya 
     * 
     * @param reportQuery
     * @return: java.util.List<com.zzsj.analysis.manage.pojo.vo.ReportModelVo>
     */ 
    List<ReportTabVo> getReport(ReportQuery reportQuery);
     
    /**
     * Description: 报告删除
     * @Date: 2020/10/17 16:14
     * @Author: zbya
     *
     * @param id
     * @return: void
     */
    int deleteData(String id);


    /**
     * Description: 模版送审审核
     * @Date: 2020/10/18 15:42
     * @Author: zbya
     *
     * @param reportModelVo
     * @return: int
     */
    int CheckData(ReportTabVo reportModelVo);
    
    /**
     * Description: 查询报告 
     * @Date: 2020/10/18 15:51 
     * @Author: zbya 
     * 
     * @param id
     * @return: java.lang.String
     */ 
    String getData(String id);
}
