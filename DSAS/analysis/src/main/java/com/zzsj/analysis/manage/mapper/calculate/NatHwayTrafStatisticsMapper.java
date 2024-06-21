package com.zzsj.analysis.manage.mapper.calculate;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.zzsj.analysis.manage.entity.NationalHighwayTrafficStatistics;
import com.zzsj.analysis.manage.pojo.query.DateQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2020/12/22 11:34
 * @description：全国公路客货运输量统计表
 */
@DS("oracle")
@Repository
public interface NatHwayTrafStatisticsMapper {
    /**
     * Description: 根据日期查询运输量
     * @Date: 2020/12/22 11:35
     * @Author: zbya
     *
     * @param dateQuery
     * @return: com.zzsj.analysis.manage.entity.NationalHighwayTrafficStatistics
     */
    List<NationalHighwayTrafficStatistics> getByDate(DateQuery dateQuery);

    /**
     * Description: 根据日期查询总计运输量
     * @Date: 2020/12/22 17:37
     * @Author: zbya
     *
     * @param dateQuery
     * @return: java.util.List<com.zzsj.analysis.manage.entity.NationalHighwayTrafficStatistics>
     */
    NationalHighwayTrafficStatistics getCountNumByDate(DateQuery dateQuery);
}
