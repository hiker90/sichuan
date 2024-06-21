package com.zzsj.analysis.manage.mapper.mysql;

import com.zzsj.analysis.manage.entity.FiveDistrictsInvestmentCompletionIndex;
import com.zzsj.analysis.manage.entity.ProvinceTransportationConstructionInvestment;
import com.zzsj.analysis.manage.pojo.query.DateQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2021/1/11 17:09
 * @description：报表取所需数据
 */
@Repository
public interface DataCalQueryMapper {
    /**
     * Description: 五区投资完成指标
     * @Date: 2021/1/6 10:49
     * @Author: zbya
     *
     * @param dateQuery
     * @return: java.util.List<com.zzsj.analysis.manage.entity.FiveDistrictsInvestmentCompletionIndex>
     */
    List<FiveDistrictsInvestmentCompletionIndex> getHisDistrInvestComp(DateQuery dateQuery);

    /**
     * Description: 全省综合交通建设投资完成情况表 
     * @Date: 2021/1/15 9:09
     * @Author: zbya 
     * 
     * @param dateQuery
     * @return: java.util.List<com.zzsj.analysis.manage.entity.ProvinceTransportationConstructionInvestment>
     */     
    List<ProvinceTransportationConstructionInvestment> getHisProInvestOthersData(DateQuery dateQuery);
}
