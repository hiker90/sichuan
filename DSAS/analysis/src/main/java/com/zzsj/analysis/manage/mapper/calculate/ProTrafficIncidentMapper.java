package com.zzsj.analysis.manage.mapper.calculate;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.zzsj.analysis.manage.entity.ProvinceTrafficIncident;
import com.zzsj.analysis.manage.pojo.query.DateQuery;
import org.springframework.stereotype.Repository;

/**
 * @author ：zbya
 * @date ：Created in 2020/12/17 11:06
 * @description：全省交通运输安全生产事故统计表
 */

@DS("oracle")
@Repository
public interface ProTrafficIncidentMapper {
    
    /**
     * Description: 根据时间查询全省交通运输安全生产事故统计表
     * @Date: 2020/12/17 11:23 
     * @Author: zbya 
     * 
     * @param dateQuery
     * @return: com.zzsj.analysis.manage.entity.T10ProvinceTrafficIncident
     */
    ProvinceTrafficIncident getByDate(DateQuery dateQuery);

    /**
     * Description: 查询总数
     * @Date: 2021/1/5 11:27
     * @Author: zbya
     *
     * @param null
     * @return:
     */
    Integer getCountNum(DateQuery dateQuery);
}
