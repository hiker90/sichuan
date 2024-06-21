package com.zzsj.analysis.manage.mapper.calculate;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.zzsj.analysis.manage.entity.ProvinceHighwayTrafficCondition;
import com.zzsj.analysis.manage.pojo.query.DateQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2020/12/24 9:23
 * @description：全省公路网运行情况统计表
 */

@DS("oracle")
@Repository
public interface ProTrafficCondMapper {

    List<ProvinceHighwayTrafficCondition> getBydate(DateQuery dateQuery);
}
