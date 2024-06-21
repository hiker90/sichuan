package com.zzsj.analysis.manage.service;

import com.zzsj.analysis.manage.entity.CulRule;

/**
 * @author ：zbya
 * @date ：Created in 2020/12/24 9:10
 * @description：全省公路网运行情况统计
 */
public interface CLProTrafficCondService {

    void provinceTrafficCondition(CulRule culRule) throws Exception;
}
