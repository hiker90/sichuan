package com.zzsj.analysis.manage.constants;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2021/1/26 16:11
 * @description：经济报告指标
 */
public class ReportCons {
    public static List<String> keys=new ArrayList<>();

    static {
        //1.时间
        keys.add("date");
        //2.全省交通固定资产完成投资
        keys.add("finishedInvest");
        //3.部年度目标
        keys.add("yearTarget");
        //4.占部年度目标的？%
        keys.add("yearTarPer");
        //5.省政府目标
        keys.add("provTarget");
        //6.省政府目标的？%
        keys.add("provTargetPer");
        //7.厅力争目标
        keys.add("officeTarget");
        //8 厅年度目标的%
        keys.add("officeTargetPer");
        //9.调增力争目标
        keys.add("increaseTarget");
        //10.调增力争目标的？%
        keys.add("increaseTargetPer");
        //11.同比增长%
        keys.add("yoyIncrease");
        //全省交通固定资产完成投资-去年
        keys.add("lastYearfinishedInvest");
        //12.养护及专项非固投完成投资
        keys.add("totalConservation");
        //13.公路水路交通建设投资完成
        keys.add("highWaterWayFinished");
        //14.公路水路同比
        keys.add("highWaterWayYoy");
        //15.公路水路环比
        keys.add("highWaterWayMom");
        //16.公路水路厅年度目标
        keys.add("highWaterWayYearTarget");
        //17.公路水路完成？%
        keys.add("highWaterWayFinPer");
        //18.高速公路投资
        keys.add("highwayFinished");
        //19.高速公路力争目标
        keys.add("highwayYearTarget");
        //20.高速公路力争目标的？%
        keys.add("highwayFinPer");
        //21.高速公路 同比增长/下降
        keys.add("highwayYoy");
        //22.高速公路 环比增长/下降
        keys.add("highwayMom");
        //23.国省干线公路投资
        keys.add("npHighwayFinished");
        //24.国省干线公路投资 力争目标
        keys.add("npHighwayYearTarget");
        //25.国省干线公路投资 力争目标的？%
        keys.add("npHighwayFinPer");
        //26.国省干线公路投资 同比？%
        keys.add("npHighwayYoy");
        //27.国省干线公路投资 环比？%
        keys.add("npHighwayMom");
        //28.农村公路投资
        keys.add("ruralRoadFinished");
        //29.农村公路投资 力争目标
        keys.add("ruralRoadYearTarget");
        //30.农村公路投资 力争目标？%
        keys.add("ruralRoadFinPer");
        //31.农村公路投资 同比？%
        keys.add("ruralRoadYoy");
        //32.农村公路投资 环比？%
        keys.add("ruralRoadMom");
        //33.内河水运投资
        keys.add("inlandWaterwayFinished");
        //34.内河水运投资 力争目标
        keys.add("inlandWaterwayYearTarget");
        //35.内河水运投资 力争目标的？%
        keys.add("inlandWaterwayFinPer");
        //36.内河水运投资 同比？%
        keys.add("inlandWaterwayYoy");
        //37.内河水运投资 环比？%
        keys.add("inlandWaterwayMom");
        //38.站点建设
        keys.add("siteConsFinished");
        //39.站点建设 完成计划
        keys.add("siteConsYearTarget");
        //40.站点建设 占完成计划的?%
        keys.add("siteConsFinPer");
        //41.站点建设 环比
        keys.add("siteConsYoy");
        //42.养护及其他专项投资
        keys.add("conservationFinished");
        //43.养护及其他专项投资 计划
        keys.add("conservationYearTarget");
        //44.养护及其他专项投资 占计划的？%
        keys.add("conservationFinPer");
        //45.养护及其他专项投资 同比
        keys.add("conservationYoy");
        //46.养护及其他专项投资 环比
        keys.add("conservationMom");
        //47.养护及其他专项投资-养护投资(普通公路养护+高速公路养护)
        keys.add("conservationOrdiHighway");
        //48.养护及其他专项投资-其他专项投资
        keys.add("conservationOthers");
        //49.养护及其他专项投资-智慧交通
        keys.add("conservationIntellegentTrans");
    }
}
