package com.zzsj.analysis.manage.service;

import com.zzsj.analysis.manage.pojo.query.DataCountQuery; /**
 * @author ：zbya
 * @date ：Created in 2020/12/24 11:26
 * @description：数据计算
 */
public interface DataCalService {

    int deleteByDate(DataCountQuery dataCountQuery) throws Exception;
}
