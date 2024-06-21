package com.zzsj.analysis.manage.service;

import com.zzsj.analysis.manage.entity.CulRule; /**
 * @author ：zbya
 * @date ：Created in 2021/1/5 15:15
 * @description：全省综合交通建设投资完成情况
 */
public interface CLProConsInvestCompService {
    /**
     * Description: 各市（州）公路水路建设投资完成情况 
     * @Date: 2021/1/5 15:38 
     * @Author: zbya 
     * 
     * @param culRule
     * @return: void
     */
    void cityConsInvestComp(CulRule culRule) throws Exception;
    
    /**
     * Description: 五区投资完成指标 
     * @Date: 2021/1/5 15:38 
     * @Author: zbya 
     * 
     * @param culRule
     * @return: void
     */ 
    void distrInvestComp(CulRule culRule) throws Exception;
    /**
     * Description: 全省综合交通建设投资完成情况
     * @Date: 2021/1/5 15:39
     * @Author: zbya 
     * 
     * @param culRule
     * @return: void
     */ 
    void proConsInvestComp(CulRule culRule) throws Exception;
}
