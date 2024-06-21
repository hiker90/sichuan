package com.zzsj.analysis.manage.mapper.calculate;

import com.zzsj.analysis.manage.pojo.query.DataCountQuery;
import org.springframework.stereotype.Service;

/**
 * @author ：zbya
 * @date ：Created in 2020/12/24 11:30
 * @description：数据计算
 */
@Service
public interface DataCalMapper {

    int deleteByDate(DataCountQuery dataCountQuery);
}
