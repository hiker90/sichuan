package com.zzsj.analysis.manage.mapper.calculate;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.zzsj.analysis.manage.entity.NationalHighwayWaterwayInvestmentCompletion;
import com.zzsj.analysis.manage.pojo.query.DateQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2021/1/18 14:08
 * @description：全国公路水路固定资产投资完成情况
 */

@DS("oracle")
@Repository
public interface NatnHigWatInvestCompleMapper {

    List<NationalHighwayWaterwayInvestmentCompletion> getProInvestData(DateQuery dateQuery);
}
