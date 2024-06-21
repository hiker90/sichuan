package com.zzsj.analysis.manage.service;

import com.zzsj.analysis.manage.entity.CulRule;

/**
 * @author ：zbya
 * @date ：Created in 2020/12/18 15:00
 * @description：全省公路水路行业安全生产事故
 */
public interface CLProTrafIndService {

    void provinceTrafficIncident(CulRule culRule) throws Exception;
}
