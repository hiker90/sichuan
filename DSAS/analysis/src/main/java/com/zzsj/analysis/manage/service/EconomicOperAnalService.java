package com.zzsj.analysis.manage.service;

import com.zzsj.analysis.manage.pojo.vo.ReportModelVo;

/**
 * @author ：zbya
 * @date ：Created in 2021/1/25 17:07
 * @description：经济运行分析
 */
public interface EconomicOperAnalService {
    /**
     * Description: 报告生成
     *
     * @param reportModelVo
     * @Date: 2021/1/19 14:37
     * @Author: zbya
     * @return: java.lang.String
     */
    void newReport(ReportModelVo reportModelVo) throws Exception;
}
