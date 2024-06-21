package com.zzsj.analysis.manage.controller;

import com.zzsj.analysis.base.constants.OprLogConst;
import com.zzsj.analysis.base.exception.BusinessException;
import com.zzsj.analysis.base.json.AjaxJson;
import com.zzsj.analysis.base.utils.enums.ResultEnum;
import com.zzsj.analysis.manage.entity.CulRule;
import com.zzsj.analysis.manage.mapper.calculate.DataCalMapper;
import com.zzsj.analysis.manage.mapper.mysql.DataCountMysqlMapper;
import com.zzsj.analysis.manage.mapper.oracle.DataCountOracleMapper;
import com.zzsj.analysis.manage.pojo.query.DataCountQuery;
import com.zzsj.analysis.manage.pojo.vo.CalTabInfo;
import com.zzsj.analysis.manage.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2020/12/15 13:56
 * @description：输入表到输出表计算
 */

@RestController
@Slf4j
@RequestMapping("data-cal")
public class DataCalController {
    //tab 1  全省主要经济指标
    @Autowired
    CLProMainEcnomicService clProMainEcnomicService;
    //tab 2 全省综合交通建设投资完成情况
    @Autowired
    CLProConsInvestCompService clProConsInvestCompService;
    //tab 3 全国公路水路固定资产投资完成情况
    @Autowired
    CLNatnHigWatInvestComple clNatnHigWatInvestComple;
    //tab 11 全省交通运输安全生产事故统计表
    @Autowired
    CLProTrafIndService clProTrafIndService;
    //tab 10 全国公路客货运输量统计表
    @Autowired
    CLNatnTrafStaticsService clNatnTrafStaticsService;
    //tab 12 全省公路网运行情况统计表
    @Autowired
    CLProTrafficCondService clProTrafficCondService;

    @Autowired
    DataCountOracleMapper dataCountOracleMapper;
    @Autowired
    DataCountMysqlMapper dataCountMysqlMapper;
    @Autowired
    DataCalService dataCalService;
    @Autowired
    DataCalMapper dataCalMapper;

    @RequestMapping("/calculate")
    @ResponseBody
    @RequiresPermissions("dsas:tables:calculate")
    @SysControllerLog(description = "报表管理", method = OprLogConst.CAL)
    public AjaxJson calculate(@RequestBody List<CulRule> culRules) throws Exception {
        try {

            StringBuffer temp = new StringBuffer();

            boolean flag = false;

            //判断输出表对应输入表是否存在数据
            for (CulRule culRule : culRules) {
                if (!dataCountMysqlMapper.getTabNames(culRule.getTableEnName()).isEmpty()) {
                    for (CalTabInfo tab : dataCountMysqlMapper.getTabNames(culRule.getTableEnName())) {
                        if (dataCountOracleMapper.getCountNum(new DataCountQuery(culRule.getYear(), culRule.getMonth(), tab.getTableEnName())) == 0) {
                            temp.append(tab.getTableName() + " ");
                            flag = true;
                        }
                    }
                }
            }

            if (flag) {
                throw new BusinessException("所选月份输入表：" + temp + "无数据。");
            }
            //判断输出表中是否已经有了数据
            for (CulRule culRule : culRules) {
                if (dataCountMysqlMapper.getCountNum(culRule) != 0) {
                    dataCalMapper.deleteByDate(new DataCountQuery(culRule.getYear(), culRule.getMonth(), culRule.getTableEnName()));
                }
            }
            //计算
            calNum(culRules);

            return new AjaxJson();
        } catch (Exception e) {
            log.error("data-cal/calculate" + e);
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Description: 计算
     *
     * @param culRules
     * @Date: 2020/12/22 10:31
     * @Author: zbya
     * @return: void
     */
    public void calNum(List<CulRule> culRules) throws Exception {
        for (CulRule culRule : culRules) {
            switch (culRule.getSort()) {
                //表1
                case 1:
                    clProMainEcnomicService.provinceMainEcnomic(culRule);
                    break;
                //表2
                case 2:
                    clProConsInvestCompService.proConsInvestComp(culRule);
                    break;
                case 3:
                    clProConsInvestCompService.distrInvestComp(culRule);
                    break;
                case 4:
                    clProConsInvestCompService.cityConsInvestComp(culRule);
                    break;
                //表3
                case 5:
                    clNatnHigWatInvestComple.highwayWaterwayinvest(culRule);
                    break;
                case 11:
                    clProTrafIndService.provinceTrafficIncident(culRule);
                    break;
                case 10:
                    clNatnTrafStaticsService.nationalHighwayTrafficStatistics(culRule);
                    break;
                case 12:
                    clProTrafficCondService.provinceTrafficCondition(culRule);
                    break;
                default:
                    throw new BusinessException("计算逻辑暂无");
            }
        }
    }

    /**
     * Description: 删除指定日期输出表数据
     *
     * @param dataCountQuery
     * @Date: 2020/12/24 11:18
     * @Author: zbya
     * @return: com.zzsj.analysis.base.json.AjaxJson
     */
    @RequestMapping("/del-data")
    @ResponseBody
    @SysControllerLog(description = "报表管理-输出表", method = OprLogConst.DELETE)
    @RequiresPermissions("dsas:tables:deldata")
    public AjaxJson deleteData(@RequestBody DataCountQuery dataCountQuery) throws Exception {
        try {
            dataCalService.deleteByDate(dataCountQuery);
            return new AjaxJson();
        } catch (Exception e) {
            log.error("data-cal/del-data" + e);
            throw new BusinessException(ResultEnum.DELETE_FAIL.getValue());
        }
    }
}
