package com.zzsj.analysis.manage.mapper.calculate;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.zzsj.analysis.manage.entity.CityWayConstructionInvestmentCompletion;
import com.zzsj.analysis.manage.entity.FiveDistrictsInvestmentCompletionIndex;
import com.zzsj.analysis.manage.entity.ProvinceTransportationConstructionInvestment;
import com.zzsj.analysis.manage.pojo.query.DateQuery;
import com.zzsj.analysis.manage.pojo.vo.ProInvestOthersVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2021/1/5 16:19
 * @description：全省综合交通建设投资完成情况
 */

@DS("oracle")
@Repository
public interface ProConsInvestCompMapper {

    /**
     * Description: 全省综合交通建设投资完成情况
     * @Date: 2021/1/5 16:58
     * @Author: zbya
     *
     * @param dateQuery
     * @return: java.util.List<com.zzsj.analysis.manage.entity.ProvinceTransportationConstructionInvestment>
     */
    List<ProvinceTransportationConstructionInvestment> getProInvestData(DateQuery dateQuery);

    /**
     * Description: 全省综合交通建设投资完成情况-其他项目6~11
     * @Date: 2021/1/14 14:21
     * @Author: zbya
     *
     * @param null
     * @return:
     */
    ProInvestOthersVo getProInvestOthersData(DateQuery dateQuery);

    /**
     * Description: 全省综合交通建设投资完成情况-查询数据总量
     * @Date: 2021/1/14 14:56
     * @Author: zbya
     *
     * @param dateQuery
     * @return: int
     */
    Integer getCountNum(DateQuery dateQuery);
    /**
     * Description: 五区投资完成指标
     * @Date: 2021/1/6 10:49
     * @Author: zbya
     *
     * @param dateQuery
     * @return: java.util.List<com.zzsj.analysis.manage.entity.FiveDistrictsInvestmentCompletionIndex>
     */
    List<FiveDistrictsInvestmentCompletionIndex> getDistrInvestComp(DateQuery dateQuery);
    /**
     * Description: 各市（州）公路水路建设投资完成情况
     * @Date: 2021/1/6 10:49
     * @Author: zbya
     *
     * @param dateQuery
     * @return: java.util.List<com.zzsj.analysis.manage.entity.CityWayConstructionInvestmentCompletion>
     */
    List<CityWayConstructionInvestmentCompletion> getCityConsInvestComp(DateQuery dateQuery);

    /**
     * Description: 各市（州）公路水路建设投资完成情况-上月
     * @Date: 2021/1/8 13:51
     * @Author: zbya
     *
     * @param dateQuery
     * @return: java.util.List<com.zzsj.analysis.manage.entity.CityWayConstructionInvestmentCompletion>
     */
    List<CityWayConstructionInvestmentCompletion> getLastMonthCityConsInvestComp(DateQuery dateQuery);

}
