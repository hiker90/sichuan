package com.zzsj.analysis.manage.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.zzsj.analysis.manage.constants.ReportCons;
import com.zzsj.analysis.manage.entity.FiveDistrictsInvestmentCompletionIndex;
import com.zzsj.analysis.manage.entity.ProvinceTransportationConstructionInvestment;
import com.zzsj.analysis.manage.mapper.calculate.QueryDataMapper;
import com.zzsj.analysis.manage.mapper.mysql.ReportManageMapper;
import com.zzsj.analysis.manage.pojo.query.DateQuery;
import com.zzsj.analysis.manage.pojo.vo.ReportModelVo;
import com.zzsj.analysis.manage.pojo.vo.ReportTabVo;
import com.zzsj.analysis.manage.service.EconomicOperAnalService;
import com.zzsj.analysis.manage.utils.NumConvertUtil;
import com.zzsj.analysis.manage.utils.UserUtil;
import com.zzsj.analysis.manage.utils.UuidUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ：zbya
 * @date ：Created in 2021/1/25 17:07
 * @description：经济运行分析
 */

@Service
public class EconomicOperAnalServiceImpl implements EconomicOperAnalService {

    @Autowired
    QueryDataMapper queryDataMapper;
    @Autowired
    ReportManageMapper reportManageMapper;

    public static String increase="增长";
    public static String decrease="下降";
    /**
     * Description: 报告生成
     *
     * @param
     * @Date: 2021/1/19 10:50
     * @Author: zbya
     * @return: java.lang.String
     */
    @Override
    public void newReport(ReportModelVo reportModelVo) throws Exception {
        Map<String, Object> output = new HashMap();
        //1.时间
        String date = "";
        //2.全省交通固定资产完成投资
        BigDecimal finishedInvest;
        //3.部年度目标
        BigDecimal yearTarget = new BigDecimal("1380");
        //4.占部年度目标的？%
        BigDecimal yearTarPer;
        //5.省政府目标
        BigDecimal provTarget = new BigDecimal("1400");
        //6.省政府目标的？%
        BigDecimal provTargetPer;
        //7.厅力争目标
        BigDecimal officeTarget;
        //8 厅年度目标的%
        BigDecimal officeTargetPer;
        //9.调增力争目标
        BigDecimal increaseTarget = new BigDecimal("1817");
        //10.调增力争目标的？%
        BigDecimal increaseTargetPer;
        //11.同比增长%
        BigDecimal yoyIncrease;
        //全省交通固定资产完成投资-去年
        BigDecimal lastYearfinishedInvest;
        //12.养护及专项非固投完成投资
        BigDecimal totalConservation;
        //13.公路水路交通建设投资完成
        BigDecimal highWaterWayFinished;
        //14.公路水路同比
        BigDecimal highWaterWayYoy;
        //15.公路水路环比
        BigDecimal highWaterWayMom;
        //16.公路水路厅年度目标
        BigDecimal highWaterWayYearTarget;
        //17.公路水路完成？%
        BigDecimal highWaterWayFinPer;
        //18.高速公路投资
        BigDecimal highwayFinished;
        //19.高速公路力争目标
        BigDecimal highwayYearTarget;
        //20.高速公路力争目标的？%
        BigDecimal highwayFinPer;
        //21.高速公路 同比增长/下降
        BigDecimal highwayYoy;
        //22.高速公路 环比增长/下降
        BigDecimal highwayMom;
        //23.国省干线公路投资
        BigDecimal npHighwayFinished;
        //24.国省干线公路投资 力争目标
        BigDecimal npHighwayYearTarget;
        //25.国省干线公路投资 力争目标的？%
        BigDecimal npHighwayFinPer;
        //26.国省干线公路投资 同比？%
        BigDecimal npHighwayYoy;
        //27.国省干线公路投资 环比？%
        BigDecimal npHighwayMom;
        //28.农村公路投资
        BigDecimal ruralRoadFinished;
        //29.农村公路投资 力争目标
        BigDecimal ruralRoadYearTarget;
        //30.农村公路投资 力争目标？%
        BigDecimal ruralRoadFinPer;
        //31.农村公路投资 同比？%
        BigDecimal ruralRoadYoy;
        //32.农村公路投资 环比？%
        BigDecimal ruralRoadMom;
        //33.内河水运投资
        BigDecimal inlandWaterwayFinished;
        //34.内河水运投资 力争目标
        BigDecimal inlandWaterwayYearTarget;
        //35.内河水运投资 力争目标的？%
        BigDecimal inlandWaterwayFinPer;
        //36.内河水运投资 同比？%
        BigDecimal inlandWaterwayYoy;
        //37.内河水运投资 环比？%
        BigDecimal inlandWaterwayMom;
        //38.站点建设
        BigDecimal siteConsFinished;
        //39.站点建设 完成计划
        BigDecimal siteConsYearTarget;
        //40.站点建设 占完成计划的?%
        BigDecimal siteConsFinPer;
        //41.站点建设 环比
        BigDecimal siteConsYoy;
        //42.养护及其他专项投资
        BigDecimal conservationFinished;
        //43.养护及其他专项投资 计划
        BigDecimal conservationYearTarget;
        //44.养护及其他专项投资 占计划的？%
        BigDecimal conservationFinPer;
        //45.养护及其他专项投资 同比
        BigDecimal conservationYoy;
        //46.养护及其他专项投资 环比
        BigDecimal conservationMom;
        //47.养护及其他专项投资-养护投资(普通公路养护+高速公路养护)
        BigDecimal conservationOrdiHighway;
        //48.养护及其他专项投资-其他专项投资
        BigDecimal conservationOthers;
        //49.养护及其他专项投资-智慧交通
        BigDecimal conservationIntellegentTrans;
        //统计时间
        DateQuery dateQuery = new DateQuery();
        int year = 0;
        int month = 0;
        switch (reportModelVo.getDate()) {
            case 0:
                date = reportModelVo.getMonth() + "月";
                year = reportModelVo.getYear();
                month = reportModelVo.getMonth();
                break;
            case 1:
                date = "第"+NumConvertUtil.convert(reportModelVo.getQuarter()) + "季度";
                year = reportModelVo.getYear();
                month=reportModelVo.getQuarter()*3;
                break;
            case 2:
                date = reportModelVo.getYear() + "年";
                year = reportModelVo.getYear();
                month = 12;
                break;
        }
        //1.时间
        output.put("date", date);

        //3.年度目标
        output.put("yearTarget", yearTarget);

        String temp="";
        finishedInvest = queryDataMapper.getFixInvest(new DateQuery(year, month));
        officeTarget = queryDataMapper.getOfficeTarget(new DateQuery(year, month));
        if (finishedInvest != null) {
            //2.全省交通固定资产完成投资
            output.put("finishedInvest", finishedInvest.setScale(2, BigDecimal.ROUND_HALF_UP));
            //4.占年度目标的%
            if (yearTarget != null && yearTarget.compareTo(new BigDecimal("0")) != 0) {
                yearTarPer = finishedInvest.divide(yearTarget, 5, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                output.put("yearTarPer", yearTarPer);
            }
            //6.省政府目标的？%
            if (provTarget != null && provTarget.compareTo(new BigDecimal("0")) != 0) {
                provTargetPer = finishedInvest.divide(provTarget, 5, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                output.put("provTargetPer", provTargetPer);
            }
            //8 厅年度目标的%
            if (officeTarget != null && officeTarget.compareTo(new BigDecimal("0")) != 0) {
                officeTargetPer = finishedInvest.divide(officeTarget, 5, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                output.put("officeTargetPer", officeTargetPer);
            }
            //10.调增力争目标的？%
            if (increaseTarget != null && increaseTarget.compareTo(new BigDecimal("0")) != 0) {
                increaseTargetPer = finishedInvest.divide(increaseTarget, 5, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                output.put("increaseTargetPer", increaseTargetPer);
            }
            //11.同比增长
            lastYearfinishedInvest = queryDataMapper.getFixInvest(new DateQuery(year - 1, month));
            if (lastYearfinishedInvest != null && lastYearfinishedInvest.compareTo(new BigDecimal(0)) != 0) {
                yoyIncrease = finishedInvest.divide(lastYearfinishedInvest, 5, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal(1)).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                output.put("yoyIncrease", yoyIncrease.toString().contains("-")?yoyIncrease.toString().replace("-",decrease):increase+yoyIncrease.toString());
            }
        }
        //12.养护及专项非固投完成投资
        totalConservation = queryDataMapper.getTotalConservation(new DateQuery(year, month));
        if (totalConservation != null) {
            output.put("totalConservation", totalConservation.setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        //输出表13~45
        Map<Integer, ProvinceTransportationConstructionInvestment> mapdatas = new HashMap<>();
        try {
            mapdatas = queryDataMapper.getProInvestOthersData(new DateQuery(year, month)).stream().collect(Collectors.toMap(ProvinceTransportationConstructionInvestment::getSort, v -> v));
        } catch (Exception e) {

        }
        if (!mapdatas.isEmpty()) {
            //公路水路交通
            if (mapdatas.get(5) != null) {
                ProvinceTransportationConstructionInvestment highWaterWay = mapdatas.get(5);
                //13.公路水路交通建设投资完成
                if (highWaterWay.getTotalFinished() != null) {
                    output.put("highWaterWayFinished", highWaterWay.getTotalFinished());
                }
                //14.公路水路同比
                if (highWaterWay.getYoyGrowthRate() != null) {
                    temp=highWaterWay.getYoyGrowthRate().replace("%", "");
                    output.put("highWaterWayYoy", temp.contains("-")?temp.replace("-",decrease):increase+temp);
                }
                //15.公路水路环比
                if (highWaterWay.getCurrMonthMom() != null) {
                    temp=highWaterWay.getCurrMonthMom().replace("%", "");
                    output.put("highWaterWayMom", temp.contains("-")?temp.replace("-",decrease):increase+temp);
                }
                //16.公路水路厅年度目标
                if (highWaterWay.getYearTarget() != null) {
                    output.put("highWaterWayYearTarget", highWaterWay.getYearTarget());
                }
                //17.公路水路完成？%
                if (highWaterWay.getFinishedPercent() != null) {
                    output.put("highWaterWayFinPer", highWaterWay.getFinishedPercent().replace("%", ""));
                }
            }

            if (mapdatas.get(6) != null) {
                ProvinceTransportationConstructionInvestment highWaterWay = mapdatas.get(6);
                //18.高速公路投资
                if (highWaterWay.getTotalFinished() != null) {
                    output.put("highwayFinished", highWaterWay.getTotalFinished());
                }
                //19.高速公路力争目标
                if (highWaterWay.getYearTarget() != null) {
                    output.put("highwayYearTarget", highWaterWay.getYearTarget());
                }
                //20.高速公路力争目标的？%
                if (highWaterWay.getFinishedPercent() != null) {
                    output.put("highwayFinPer", highWaterWay.getFinishedPercent().replace("%", ""));
                }
                //21.高速公路 同比增长/下降
                if (highWaterWay.getYoyGrowthRate() != null) {
                    temp=highWaterWay.getYoyGrowthRate().replace("%", "");
                    output.put("highwayYoy", temp.contains("-")?temp.replace("-",decrease):increase+temp);
                }
                //22.高速公路 环比增长/下降
                if (highWaterWay.getCurrMonthMom() != null) {
                    temp=highWaterWay.getCurrMonthMom().replace("%", "");
                    output.put("highwayMom", temp.contains("-")?temp.replace("-",decrease):increase+temp);
                }
            }

            if (mapdatas.get(7) != null) {
                ProvinceTransportationConstructionInvestment highWaterWay = mapdatas.get(7);
                //23.国省干线公路投资
                if (highWaterWay.getTotalFinished() != null) {
                    output.put("npHighwayFinished", highWaterWay.getTotalFinished());
                }
                //24.国省干线公路投资 力争目标
                if (highWaterWay.getYearTarget() != null) {
                    output.put("npHighwayYearTarget", highWaterWay.getYearTarget());
                }
                //25.国省干线公路投资 力争目标的？%
                if (highWaterWay.getFinishedPercent() != null) {
                    output.put("npHighwayFinPer", highWaterWay.getFinishedPercent().replace("%", ""));
                }
                //26.国省干线公路投资 同比？%
                if (highWaterWay.getYoyGrowthRate() != null) {
                    temp=highWaterWay.getYoyGrowthRate().replace("%", "");
                    output.put("npHighwayYoy", temp.contains("-")?temp.replace("-",decrease):increase+temp);
                }
                //27.国省干线公路投资 环比？%
                if (highWaterWay.getCurrMonthMom() != null) {
                    temp=highWaterWay.getCurrMonthMom().replace("%", "");
                    output.put("npHighwayMom", temp.contains("-")?temp.replace("-",decrease):increase+temp);
                }
            }

            if (mapdatas.get(8) != null) {
                ProvinceTransportationConstructionInvestment highWaterWay = mapdatas.get(8);
                //28.农村公路投资
                if (highWaterWay.getTotalFinished() != null) {
                    output.put("ruralRoadFinished", highWaterWay.getTotalFinished());
                }
                //29.农村公路投资 力争目标
                if (highWaterWay.getYearTarget() != null) {
                    output.put("ruralRoadYearTarget", highWaterWay.getYearTarget());
                }
                //30.农村公路投资 力争目标？%
                if (highWaterWay.getFinishedPercent() != null) {
                    output.put("ruralRoadFinPer", highWaterWay.getFinishedPercent().replace("%", ""));
                }
                //31.农村公路投资 同比？%
                if (highWaterWay.getYoyGrowthRate() != null) {
                    temp=highWaterWay.getYoyGrowthRate().replace("%", "");
                    output.put("ruralRoadYoy", temp.contains("-")?temp.replace("-",decrease):increase+temp);
                }
                //32.农村公路投资 环比？%
                if (highWaterWay.getCurrMonthMom() != null) {
                    temp=highWaterWay.getCurrMonthMom().replace("%", "");
                    output.put("ruralRoadMom", temp.contains("-")?temp.replace("-",decrease):increase+temp);
                }
            }

            if (mapdatas.get(9) != null) {
                ProvinceTransportationConstructionInvestment highWaterWay = mapdatas.get(9);
                //33.内河水运投资
                if (highWaterWay.getTotalFinished() != null) {
                    output.put("inlandWaterwayFinished", highWaterWay.getTotalFinished());
                }
                //34.内河水运投资 力争目标
                if (highWaterWay.getYearTarget() != null) {
                    output.put("inlandWaterwayYearTarget", highWaterWay.getYearTarget());
                }
                //35.内河水运投资 力争目标的？%
                if (highWaterWay.getFinishedPercent() != null) {
                    output.put("inlandWaterwayFinPer", highWaterWay.getFinishedPercent().replace("%", ""));
                }
                //36.内河水运投资 同比？%
                if (highWaterWay.getYoyGrowthRate() != null) {
                    temp=highWaterWay.getYoyGrowthRate().replace("%", "");
                    output.put("inlandWaterwayYoy", temp.contains("-")?temp.replace("-",decrease):increase+temp);
                }
                //37.内河水运投资 环比？%
                if (highWaterWay.getCurrMonthMom() != null) {
                    temp=highWaterWay.getCurrMonthMom().replace("%", "");
                    output.put("inlandWaterwayMom", temp.contains("-")?temp.replace("-",decrease):increase+temp);
                }
            }

            if (mapdatas.get(10) != null) {
                ProvinceTransportationConstructionInvestment highWaterWay = mapdatas.get(10);
                //38.站点建设完成
                if (highWaterWay.getTotalFinished() != null) {
                    output.put("siteConsFinished", highWaterWay.getTotalFinished());
                }
                //39.站点建设 计划
                if (highWaterWay.getYearTarget() != null) {
                    output.put("siteConsYearTarget", highWaterWay.getYearTarget());
                }
                //40.站点建设 占完成计划的?%
                if (highWaterWay.getFinishedPercent() != null) {
                    output.put("siteConsFinPer", highWaterWay.getFinishedPercent().replace("%", ""));
                }
                //41.站点建设 环比
                if (highWaterWay.getYoyGrowthRate() != null) {
                    temp=highWaterWay.getYoyGrowthRate().replace("%", "");
                    output.put("siteConsYoy", temp.contains("-")?temp.replace("-",decrease):increase+temp);
                }
            }

            if (mapdatas.get(11) != null) {
                ProvinceTransportationConstructionInvestment highWaterWay = mapdatas.get(11);
                //42.养护及其他专项投资
                if (highWaterWay.getTotalFinished() != null) {
                    output.put("conservationFinished", highWaterWay.getTotalFinished());
                }
                //43.养护及其他专项投资 计划
                if (highWaterWay.getYearTarget() != null) {
                    output.put("conservationYearTarget", highWaterWay.getYearTarget());
                }
                //44.养护及其他专项投资 占计划的？%
                if (highWaterWay.getFinishedPercent() != null) {
                    output.put("conservationFinPer", highWaterWay.getFinishedPercent().replace("%", ""));
                }
                //45.养护及其他专项投资 同比
                if (highWaterWay.getYoyGrowthRate() != null) {
                    temp=highWaterWay.getYoyGrowthRate().replace("%", "");
                    output.put("conservationYoy", temp.contains("-")?temp.replace("-",decrease):increase+temp);
                }
                //46.养护及其他专项投资 环比
                if (highWaterWay.getCurrMonthMom() != null) {
                    temp=highWaterWay.getCurrMonthMom().replace("%", "");
                    output.put("conservationMom", temp.contains("-")?temp.replace("-",decrease):increase+temp);
                }
            }
        }

        FiveDistrictsInvestmentCompletionIndex conserData = queryDataMapper.getConserData(new DateQuery(year, month));
        if (conserData != null) {
            //47.养护及其他专项投资-养护投资(普通公路养护+高速公路养护)
            if (conserData.getConservationOrdinaryHighway() != null && conserData.getConservationHighway() != null) {
                output.put("conservationOrdiHighway", conserData.getConservationOrdinaryHighway().add(conserData.getConservationHighway()));
            } else if (conserData.getConservationOrdinaryHighway() != null && conserData.getConservationHighway() == null) {
                output.put("conservationOrdiHighway", conserData.getConservationOrdinaryHighway());
            } else if (conserData.getConservationOrdinaryHighway() == null && conserData.getConservationHighway() != null) {
                output.put("conservationOrdiHighway", conserData.getConservationHighway());
            }
            //48.养护及其他专项投资-其他专项投资
            if (conserData.getConservationOthers() != null) {
                output.put("conservationOthers", conserData.getConservationOthers());
            }
            // 49.养护及其他专项投资-智慧交通
            if (conserData.getConservationIntelligentTransport() != null) {
                output.put("conservationIntellegentTrans", conserData.getConservationIntelligentTransport());
            }
        }
        //5.省政府目标
        output.put("provTarget", provTarget);
        //7.厅力争目标
        if(officeTarget!=null){
            output.put("officeTarget", officeTarget);
        }
        //9.调增力争目标
        output.put("increaseTarget", increaseTarget);

        List<String> keys = output.keySet().stream().collect(Collectors.toList());
        for(String data:ReportCons.keys){
            if(!keys.contains(data)){
                output.put(data,"____");
            }
        }

        Claims userClaims = UserUtil.userInfo();
        ReportTabVo report = new ReportTabVo();

        report.setReportName(date+reportModelVo.getModelName()+"-"+LocalDateTime.now().toLocalDate().toString().replaceAll("-",""));
        report.setDraftUnit(reportModelVo.getDraftUnit());
        report.setVersion(reportModelVo.getVersion());
        report.setModel(JSONObject.toJSONString(output));
        report.setCheckUser(userClaims.get("userName").toString());
        report.setCheckDate(new Date());
        report.setReportStatus(1);
        report.setDraftUser(reportModelVo.getDraftUser());
        report.setCheckUnit(userClaims.get("dept").toString());
        report.setModelId(reportModelVo.getId());
        report.setModelName(reportModelVo.getModelName());
        report.setId(UuidUtil.get());
        report.setReportType(reportModelVo.getDate());
        reportManageMapper.newReport(report);
    }
    public  static void main(String []args){
        ReportModelVo reportModelVo=new ReportModelVo();
        System.out.println(reportModelVo.toString());
        System.out.println(LocalDateTime.now().toLocalDate().toString().replaceAll("-",""));
    }

}
