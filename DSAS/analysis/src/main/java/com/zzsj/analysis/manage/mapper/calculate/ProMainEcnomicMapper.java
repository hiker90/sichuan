package com.zzsj.analysis.manage.mapper.calculate;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.zzsj.analysis.manage.entity.ProvinceMainEcnomicIndicators;
import com.zzsj.analysis.manage.pojo.query.DateQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2020/12/29 16:36
 * @description：全省主要经济指标
 */

@DS("oracle")
@Repository
public interface ProMainEcnomicMapper {

    List<ProvinceMainEcnomicIndicators> getBydate(DateQuery dateQuery);
}
