package com.zzsj.analysis.manage.mapper.mysql;

import com.zzsj.analysis.manage.entity.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2020/12/15 14:07
 * @description：数据计算插入统计分析表
 */

@Repository
public interface ResultInsertMapper {
    /**
     * Description: 全省公路水路行业安全生产事故表 
     * @Date: 2020/12/17 15:58
     * @Author: zbya 
     * 
     * @param list
     * @return: int
     */ 
    int insertProTraIncd(List<ProvinceTrafficIncident> list);

    /**
     * Description: 全国公路客货运输量统计
     * @Date: 2020/12/23 10:21
     * @Author: zbya
     *
     * @param list
     * @return: int
     */
    int insertNatnTrafStatics(List<NationalHighwayTrafficStatistics> list);

    /**
     * Description: 全省公路网运行情况统计表
     * @Date: 2020/12/24 10:05
     * @Author: zbya
     *
     * @param list
     * @return: int
     */
    int insertProTraCdt(List<ProvinceHighwayTrafficCondition> list);

    /**
     * Description: 全省主要经济指标
     * @Date: 2020/12/29 17:38
     * @Author: zbya
     *
     * @param list
     * @return: int
     */
    int insertProMainEcnomic(List<ProvinceMainEcnomicIndicators> list);

    /**
     * Description: 全省综合交通建设投资完成情况
     * @Date: 2021/1/6 8:46
     * @Author: zbya
     *
     * @param list
     * @return: int
     */
    int insertProConsInvestComp(List<ProvinceTransportationConstructionInvestment> list);
    
    /**
     * Description: 各市（州）公路水路建设投资完成情况 
     * @Date: 2021/1/8 14:42
     * @Author: zbya 
     * 
     * @param list
     * @return: int
     */ 
    int insertCityConsInvestComp(List<CityWayConstructionInvestmentCompletion> list);

    /**
     * Description: 五区投资完成指标
     * @Date: 2021/1/12 15:43
     * @Author: zbya
     *
     * @param list
     * @return:
     */
    int insertDistrInvestComp(List<FiveDistrictsInvestmentCompletionIndex> list);
    
    /**
     * Description: 全国公路水路固定资产投资完成情况 
     * @Date: 2021/1/18 15:28 
     * @Author: zbya 
     * 
     * @param output
     * @return: int
     */ 
    int insertProInvestData(List<NationalHighwayWaterwayInvestmentCompletion> output);
}
