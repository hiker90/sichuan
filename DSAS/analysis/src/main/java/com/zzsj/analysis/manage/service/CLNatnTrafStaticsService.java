package com.zzsj.analysis.manage.service;

import com.zzsj.analysis.manage.entity.CulRule;

/**
 * @author ：zbya
 * @date ：Created in 2020/12/22 10:39
 * @description：全国公路客货运输量统计
 */
public interface CLNatnTrafStaticsService {

    void nationalHighwayTrafficStatistics (CulRule culRule) throws Exception;
}
