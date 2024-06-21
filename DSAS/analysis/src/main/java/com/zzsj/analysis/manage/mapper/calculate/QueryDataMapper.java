package com.zzsj.analysis.manage.mapper.calculate;

import com.zzsj.analysis.manage.entity.FiveDistrictsInvestmentCompletionIndex;
import com.zzsj.analysis.manage.entity.ProvinceTransportationConstructionInvestment;
import com.zzsj.analysis.manage.pojo.query.DateQuery;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2021/1/19 17:12
 * @description：经济运行数据
 */

@Repository
public interface QueryDataMapper {
    //全省交通固定资产完成投资
    BigDecimal getFixInvest(DateQuery dateQuery);
    //养护及专项非固投完成投资
    BigDecimal getTotalConservation(DateQuery dateQuery);
    //厅年度力争目标
    BigDecimal getOfficeTarget(DateQuery dateQuery);
    //全省综合交通建设投资完成情况表
    List<ProvinceTransportationConstructionInvestment> getProInvestOthersData(DateQuery dateQuery);
    //养护三项指标
    FiveDistrictsInvestmentCompletionIndex getConserData(DateQuery dateQuery);
}
