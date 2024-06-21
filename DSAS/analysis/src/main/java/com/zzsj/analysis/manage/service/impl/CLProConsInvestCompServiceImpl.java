package com.zzsj.analysis.manage.service.impl;

import com.zzsj.analysis.base.exception.BusinessException;
import com.zzsj.analysis.base.utils.enums.ResultEnum;
import com.zzsj.analysis.manage.constants.EconomicRegion;
import com.zzsj.analysis.manage.entity.CityWayConstructionInvestmentCompletion;
import com.zzsj.analysis.manage.entity.CulRule;
import com.zzsj.analysis.manage.entity.FiveDistrictsInvestmentCompletionIndex;
import com.zzsj.analysis.manage.entity.ProvinceTransportationConstructionInvestment;
import com.zzsj.analysis.manage.mapper.calculate.ProConsInvestCompMapper;
import com.zzsj.analysis.manage.mapper.mysql.DataCalQueryMapper;
import com.zzsj.analysis.manage.mapper.mysql.ResultInsertMapper;
import com.zzsj.analysis.manage.pojo.query.DateQuery;
import com.zzsj.analysis.manage.pojo.vo.ProInvestOthersVo;
import com.zzsj.analysis.manage.service.CLProConsInvestCompService;
import com.zzsj.analysis.manage.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ：zbya
 * @date ：Created in 2021/1/5 15:16
 * @description：全省综合交通建设投资完成情况
 */

@Service
public class CLProConsInvestCompServiceImpl implements CLProConsInvestCompService {
    @Autowired
    ProConsInvestCompMapper proConsInvestCompMapper;
    @Autowired
    ResultInsertMapper resultInsertMapper;
    @Autowired
    DataCalQueryMapper dataCalQueryMapper;

    /**
     * Description: 全省综合交通建设投资完成情况
     *
     * @param culRule
     * @Date: 2021/1/5 15:41
     * @Author: zbya
     * @return: void
     */
    @Override
    public void proConsInvestComp(CulRule culRule) throws Exception {
        List<ProvinceTransportationConstructionInvestment> output;
        //统计时间
        DateQuery dateQuery = new DateQuery(culRule.getYear(), culRule.getMonth());

        List<ProvinceTransportationConstructionInvestment> datas = proConsInvestCompMapper.getProInvestData(dateQuery);

        Map<Integer, ProvinceTransportationConstructionInvestment> map;
        if (datas != null) {
            try {
                map = datas.stream().collect(Collectors.toMap(ProvinceTransportationConstructionInvestment::getSort, v -> v));
            } catch (Exception e) {
                throw new BusinessException(dateQuery.toString() + ResultEnum.ILLEGAL_DATE.getValue());
            }
        } else {
            map = new HashMap<>();
        }
        if (map.get(1) == null) {
            map.put(1, new ProvinceTransportationConstructionInvestment(1, "总投资"));
        }
        if (map.get(2) == null) {
            map.put(2, new ProvinceTransportationConstructionInvestment(2, "（一）民航投资"));
        }
        if (map.get(3) == null) {
            map.put(3, new ProvinceTransportationConstructionInvestment(3, "（二）铁路投资"));
        }
        if (map.get(4) == null) {
            map.put(4, new ProvinceTransportationConstructionInvestment(4, "（三）城市轨道交通投资"));
        }
        map.values().stream().forEach(data->{
            //完成比例
            if(data.getFinishedPercent()!=null&&!data.getFinishedPercent().contains("%")){
                data.setFinishedPercent(new BigDecimal(data.getFinishedPercent()).multiply(new BigDecimal("100")).setScale(2,BigDecimal.ROUND_HALF_UP)+"%");
            }
            //本月环比
            if(data.getCurrMonthMom()!=null&&!data.getCurrMonthMom().contains("%")){
                data.setCurrMonthMom(new BigDecimal(data.getCurrMonthMom()).multiply(new BigDecimal("100")).setScale(2,BigDecimal.ROUND_HALF_UP)+"%");
            }
            //同比增速
            if(data.getYoyGrowthRate()!=null&&!data.getYoyGrowthRate().contains("%")){
                data.setYoyGrowthRate(new BigDecimal(data.getYoyGrowthRate()).multiply(new BigDecimal("100")).setScale(2,BigDecimal.ROUND_HALF_UP)+"%");
            }
        });
        //其他 5-11
        //本月
        if (proConsInvestCompMapper.getCountNum(dateQuery) != 0) {
            throw new BusinessException(dateQuery.toString() + ResultEnum.ILLEGAL_DATE.getValue());
        }

        ProInvestOthersVo others = proConsInvestCompMapper.getProInvestOthersData(dateQuery);
        Map<Integer, ProvinceTransportationConstructionInvestment> tempMap = new HashMap<>();
        ProvinceTransportationConstructionInvestment waterway=new ProvinceTransportationConstructionInvestment();
        ProvinceTransportationConstructionInvestment highway=new ProvinceTransportationConstructionInvestment();
        ProvinceTransportationConstructionInvestment npHighway=new ProvinceTransportationConstructionInvestment();
        ProvinceTransportationConstructionInvestment ruralRoad=new ProvinceTransportationConstructionInvestment();
        ProvinceTransportationConstructionInvestment inlandWaterway=new ProvinceTransportationConstructionInvestment();
        ProvinceTransportationConstructionInvestment siteConstuction=new ProvinceTransportationConstructionInvestment();
        ProvinceTransportationConstructionInvestment conservation=new ProvinceTransportationConstructionInvestment();
        if(others!=null){
            //（四）公路水路投资
            if( others.getRoadWaterwayYearGoal()!=null){
                waterway.setYearTarget(others.getRoadWaterwayYearGoal());
            }
            if(others.getRoadWaterwayTotalFinished()!=null){
                waterway.setTotalFinished(others.getRoadWaterwayTotalFinished());
            }
            //其中：高速公路投资
            if( others.getHighwayYearGoal()!=null){
                highway.setYearTarget(others.getHighwayYearGoal());
            }
            if(others.getHighwayTotalFinished()!=null){
                highway.setTotalFinished(others.getHighwayTotalFinished());
            }
            //国省干线投资
            if( others.getNpHighwayYearGoal()!=null){
                npHighway.setYearTarget(others.getNpHighwayYearGoal());
            }
            if(others.getNpHighwayTotalFinished()!=null){
                npHighway.setTotalFinished(others.getNpHighwayTotalFinished());
            }
            //农村公路投资
            if( others.getRuralRoadYearGoal()!=null){
                ruralRoad.setYearTarget(others.getRuralRoadYearGoal());
            }
            if(others.getRuralRoadTotalFinished()!=null){
                ruralRoad.setTotalFinished(others.getRuralRoadTotalFinished());
            }
            //水运投资
            if( others.getInlandWaterwayYearGoal()!=null){
                inlandWaterway.setYearTarget(others.getInlandWaterwayYearGoal());
            }
            if(others.getInlandWaterwayTotalFinished()!=null){
                inlandWaterway.setTotalFinished(others.getInlandWaterwayTotalFinished());
            }
            //站点投资
            if( others.getSiteConstuctionYearGoal()!=null){
                siteConstuction.setYearTarget(others.getSiteConstuctionYearGoal());
            }
            if(others.getSiteConstuctionTotalFinished()!=null){
                siteConstuction.setTotalFinished(others.getSiteConstuctionTotalFinished());
            }
            //养护及其它投资
            if( others.getConservationYearGoal()!=null){
                conservation.setYearTarget(others.getConservationYearGoal());
            }
            if(others.getConservationTotalFinished()!=null){
                conservation.setTotalFinished(others.getConservationTotalFinished());
            }
        }
        tempMap.put(5, waterway);
        tempMap.put(6, highway);
        tempMap.put(7, npHighway);
        tempMap.put(8, ruralRoad);
        tempMap.put(9, inlandWaterway);
        tempMap.put(10, siteConstuction);
        tempMap.put(11, conservation);
        //上月数据
        if (culRule.getMonth() != 1) {
            dateQuery = new DateQuery(culRule.getYear(), culRule.getMonth() - 1);
        } else {
            dateQuery = new DateQuery(culRule.getYear() - 1, 12);
        }
        if (proConsInvestCompMapper.getCountNum(dateQuery) != 0) {
            throw new BusinessException(dateQuery.toString() + ResultEnum.ILLEGAL_DATE.getValue());
        }
        ProInvestOthersVo lastMonthOthers = proConsInvestCompMapper.getProInvestOthersData(dateQuery);
        //上月完成数据
        Map<Integer, ProvinceTransportationConstructionInvestment> lastMonthFinished = dataCalQueryMapper.getHisProInvestOthersData(dateQuery).stream().collect(Collectors.toMap(ProvinceTransportationConstructionInvestment::getSort, v -> v));
        //上一年数据
        dateQuery = new DateQuery(culRule.getYear() - 1, culRule.getMonth());
        if (proConsInvestCompMapper.getCountNum(dateQuery) != 0) {
            throw new BusinessException(dateQuery.toString() + ResultEnum.ILLEGAL_DATE.getValue());
        }
        ProInvestOthersVo lastYearOthers = proConsInvestCompMapper.getProInvestOthersData(dateQuery);

        for (Map.Entry<Integer, ProvinceTransportationConstructionInvestment> data : tempMap.entrySet()) {
            ProvinceTransportationConstructionInvestment temp = data.getValue();
            //完成占比
            if (temp.getTotalFinished() != null) {
                if (temp.getYearTarget() != null && temp.getYearTarget().compareTo(new BigDecimal("0")) != 0) {
                    temp.setFinishedPercent(temp.getTotalFinished().divide(temp.getYearTarget(), 5, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
                }
                //5.（四）公路水路投资
                if (data.getKey() == 5) {
                    //本月完成
                    if (lastMonthOthers != null) {
                        if (lastMonthOthers.getRoadWaterwayTotalFinished() != null) {
                            temp.setCurrMonthFinished(temp.getTotalFinished().subtract(lastMonthOthers.getRoadWaterwayTotalFinished()).setScale(2, BigDecimal.ROUND_HALF_UP));
                        }
                    }
                    //同比增速
                    if (lastYearOthers != null) {
                        if (lastMonthOthers.getRoadWaterwayTotalFinished() != null) {
                            temp.setYoyGrowthRate(temp.getTotalFinished().divide(lastYearOthers.getRoadWaterwayTotalFinished(), 5, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal("1")).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
                        }
                    }
                    //本月环比
                    if (lastMonthFinished.get(data.getKey())!=null){
                        if (lastMonthFinished.get(data.getKey()).getCurrMonthFinished() != null) {
                            temp.setCurrMonthMom(temp.getCurrMonthFinished().divide(lastMonthFinished.get(data.getKey()).getCurrMonthFinished(), 5, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal("1")).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
                        }
                    }
                    continue;
                }

                //6.其中：高速公路投资
                if (data.getKey() == 6) {
                    //本月完成
                    if (lastMonthOthers != null) {
                        if (lastMonthOthers.getHighwayTotalFinished() != null) {
                            temp.setCurrMonthFinished(temp.getTotalFinished().subtract(lastMonthOthers.getHighwayTotalFinished()).setScale(2, BigDecimal.ROUND_HALF_UP));
                        }
                    }
                    //同比增速
                    if (lastYearOthers != null) {
                        if (lastMonthOthers.getHighwayTotalFinished() != null) {
                            temp.setYoyGrowthRate(temp.getTotalFinished().divide(lastYearOthers.getHighwayTotalFinished(), 5, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal("1")).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
                        }
                    }

                    if (lastMonthFinished.get(data.getKey())!=null){
                        //本月环比
                        if (lastMonthFinished.get(data.getKey()).getCurrMonthFinished() != null) {
                            temp.setCurrMonthMom(temp.getCurrMonthFinished().divide(lastMonthFinished.get(data.getKey()).getCurrMonthFinished(), 5, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal("1")).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
                        }
                    }
                    continue;
                }

                //7.国省干线投资
                if (data.getKey() == 7) {
                    //本月完成
                    if (lastMonthOthers != null) {
                        if (lastMonthOthers.getNpHighwayTotalFinished() != null) {
                            temp.setCurrMonthFinished(temp.getTotalFinished().subtract(lastMonthOthers.getNpHighwayTotalFinished()).setScale(2, BigDecimal.ROUND_HALF_UP));
                        }
                    }
                    //同比增速
                    if (lastYearOthers != null) {
                        if (lastMonthOthers.getNpHighwayTotalFinished() != null) {
                            temp.setYoyGrowthRate(temp.getTotalFinished().divide(lastYearOthers.getNpHighwayTotalFinished(), 5, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal("1")).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
                        }
                    }
                    //本月环比
                    if (lastMonthFinished.get(data.getKey())!=null){
                        if (lastMonthFinished.get(data.getKey()).getCurrMonthFinished() != null) {
                            temp.setCurrMonthMom(temp.getCurrMonthFinished().divide(lastMonthFinished.get(data.getKey()).getCurrMonthFinished(), 5, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal("1")).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
                        }
                    }
                    continue;
                }

                //8.农村公路投资
                if (data.getKey() == 8) {
                    //本月完成
                    if (lastMonthOthers != null) {
                        if (lastMonthOthers.getRuralRoadTotalFinished() != null) {
                            temp.setCurrMonthFinished(temp.getTotalFinished().subtract(lastMonthOthers.getRuralRoadTotalFinished()).setScale(2, BigDecimal.ROUND_HALF_UP));
                        }
                    }
                    //同比增速
                    if (lastYearOthers != null) {
                        if (lastMonthOthers.getRuralRoadTotalFinished() != null) {
                            temp.setYoyGrowthRate(temp.getTotalFinished().divide(lastYearOthers.getRuralRoadTotalFinished(), 5, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal("1")).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
                        }
                    }
                    //本月环比

                    if (lastMonthFinished.get(data.getKey())!=null){
                        if (lastMonthFinished.get(data.getKey()).getCurrMonthFinished() != null) {
                            temp.setCurrMonthMom(temp.getCurrMonthFinished().divide(lastMonthFinished.get(data.getKey()).getCurrMonthFinished(), 5, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal("1")).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
                        }
                    }
                    continue;
                }

                //9.水运投资
                if (data.getKey() == 9) {
                    //本月完成
                    if (lastMonthOthers != null) {
                        if (lastMonthOthers.getInlandWaterwayTotalFinished() != null) {
                            temp.setCurrMonthFinished(temp.getTotalFinished().subtract(lastMonthOthers.getInlandWaterwayTotalFinished()).setScale(2, BigDecimal.ROUND_HALF_UP));
                        }
                    }
                    //同比增速
                    if (lastYearOthers != null) {
                        if (lastMonthOthers.getInlandWaterwayTotalFinished() != null) {
                            temp.setYoyGrowthRate(temp.getTotalFinished().divide(lastYearOthers.getInlandWaterwayTotalFinished(), 5, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal("1")).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
                        }
                    }
                    //本月环比

                    if (lastMonthFinished.get(data.getKey())!=null){
                        if (lastMonthFinished.get(data.getKey()).getCurrMonthFinished() != null) {
                            temp.setCurrMonthMom(temp.getCurrMonthFinished().divide(lastMonthFinished.get(data.getKey()).getCurrMonthFinished(), 5, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal("1")).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
                        }
                    }
                    continue;
                }

                //10.站点投资
                if (data.getKey() == 10) {
                    //本月完成
                    if (lastMonthOthers != null) {
                        if (lastMonthOthers.getSiteConstuctionTotalFinished() != null) {
                            temp.setCurrMonthFinished(temp.getTotalFinished().subtract(lastMonthOthers.getSiteConstuctionTotalFinished()).setScale(2, BigDecimal.ROUND_HALF_UP));
                        }
                    }
                    //同比增速
                    if (lastYearOthers != null) {
                        if (lastMonthOthers.getSiteConstuctionTotalFinished() != null) {
                            temp.setYoyGrowthRate(temp.getTotalFinished().divide(lastYearOthers.getSiteConstuctionTotalFinished(), 5, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal("1")).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
                        }
                    }
                    //本月环比

                    if (lastMonthFinished.get(data.getKey())!=null){
                        if (lastMonthFinished.get(data.getKey()).getCurrMonthFinished() != null) {
                            temp.setCurrMonthMom(temp.getCurrMonthFinished().divide(lastMonthFinished.get(data.getKey()).getCurrMonthFinished(), 5, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal("1")).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
                        }
                    }
                    continue;
                }

                //11.养护及其它投资
                if (data.getKey() == 11) {
                    //本月完成
                    if (lastMonthOthers != null) {
                        if (lastMonthOthers.getConservationTotalFinished() != null) {
                            temp.setCurrMonthFinished(temp.getTotalFinished().subtract(lastMonthOthers.getConservationTotalFinished()).setScale(2, BigDecimal.ROUND_HALF_UP));
                        }
                    }
                    //同比增速
                    if (lastYearOthers != null) {
                        if (lastMonthOthers.getConservationTotalFinished() != null) {
                            temp.setYoyGrowthRate(temp.getTotalFinished().divide(lastYearOthers.getConservationTotalFinished(), 5, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal("1")).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
                        }
                    }
                    //本月环比

                    if (lastMonthFinished.get(data.getKey())!=null){
                        if (lastMonthFinished.get(data.getKey()).getCurrMonthFinished() != null) {
                            temp.setCurrMonthMom(temp.getCurrMonthFinished().divide(lastMonthFinished.get(data.getKey()).getCurrMonthFinished(), 5, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal("1")).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
                        }
                    }
                    continue;
                }

            }


        }
        map.putAll(tempMap);
        output = map.values().stream().collect(Collectors.toList());
        output.stream().forEach(data -> {
            data.setId(UuidUtil.get());
            data.setYear(culRule.getYear());
            data.setMonth(culRule.getMonth());
        });
        resultInsertMapper.insertProConsInvestComp(output);
    }


    /**
     * Description: 五区投资完成指标
     *
     * @param culRule
     * @Date: 2021/1/5 15:41
     * @Author: zbya
     * @return: void
     */
    @Override
    public void distrInvestComp(CulRule culRule) throws Exception {
        //输出结果
        Map<String, FiveDistrictsInvestmentCompletionIndex> output = new HashMap<>();
        output.put("成都平原经济区", new FiveDistrictsInvestmentCompletionIndex(1, "成都平原经济区"));
        output.put("川南经济区", new FiveDistrictsInvestmentCompletionIndex(2, "川南经济区"));
        output.put("川东北经济区", new FiveDistrictsInvestmentCompletionIndex(3, "川东北经济区"));
        output.put("川西北生态示范区", new FiveDistrictsInvestmentCompletionIndex(4, "川西北生态示范区"));
        output.put("攀西经济区", new FiveDistrictsInvestmentCompletionIndex(5, "攀西经济区"));
        //统计时间
        DateQuery dateQuery = new DateQuery(culRule.getYear(), culRule.getMonth());
        //当前月份数据
        Map<String, FiveDistrictsInvestmentCompletionIndex> map;
        try {
            map = proConsInvestCompMapper.getDistrInvestComp(dateQuery).stream().collect(Collectors.toMap(FiveDistrictsInvestmentCompletionIndex::getRegion, v -> v));
        } catch (Exception e) {
            throw new BusinessException(dateQuery.toString() + ResultEnum.ILLEGAL_DATE.getValue());
        }

        //上月数据
        if (culRule.getMonth() != 1) {
            dateQuery = new DateQuery(culRule.getYear(), culRule.getMonth() - 1);
        } else {
            dateQuery = new DateQuery(culRule.getYear() - 1, 12);
        }
        Map<String, FiveDistrictsInvestmentCompletionIndex> lastMonthMap;
        try {
            if (!dataCalQueryMapper.getHisDistrInvestComp(dateQuery).isEmpty()) {
                lastMonthMap = dataCalQueryMapper.getHisDistrInvestComp(dateQuery).stream().collect(Collectors.toMap(FiveDistrictsInvestmentCompletionIndex::getRegion, v -> v));
            } else {
                lastMonthMap = new HashMap<>();
                lastMonthMap.put("成都平原经济区", new FiveDistrictsInvestmentCompletionIndex(1, "成都平原经济区"));
                lastMonthMap.put("川南经济区", new FiveDistrictsInvestmentCompletionIndex(2, "川南经济区"));
                lastMonthMap.put("川东北经济区", new FiveDistrictsInvestmentCompletionIndex(3, "川东北经济区"));
                lastMonthMap.put("川西北生态示范区", new FiveDistrictsInvestmentCompletionIndex(4, "川西北生态示范区"));
                lastMonthMap.put("攀西经济区", new FiveDistrictsInvestmentCompletionIndex(5, "攀西经济区"));
            }
        } catch (Exception e) {
            throw new BusinessException(dateQuery.toString() + ResultEnum.ILLEGAL_DATE.getValue());
        }
        //上年数据
        dateQuery = new DateQuery(culRule.getYear() - 1, culRule.getMonth());

        Map<String, FiveDistrictsInvestmentCompletionIndex> lastYearMap;
        try {
            if (!dataCalQueryMapper.getHisDistrInvestComp(dateQuery).isEmpty()) {
                lastYearMap = dataCalQueryMapper.getHisDistrInvestComp(dateQuery).stream().collect(Collectors.toMap(FiveDistrictsInvestmentCompletionIndex::getRegion, v -> v));
            } else {
                lastYearMap = new HashMap<>();
                lastYearMap.put("成都平原经济区", new FiveDistrictsInvestmentCompletionIndex(1, "成都平原经济区"));
                lastYearMap.put("川南经济区", new FiveDistrictsInvestmentCompletionIndex(2, "川南经济区"));
                lastYearMap.put("川东北经济区", new FiveDistrictsInvestmentCompletionIndex(3, "川东北经济区"));
                lastYearMap.put("川西北生态示范区", new FiveDistrictsInvestmentCompletionIndex(4, "川西北生态示范区"));
                lastYearMap.put("攀西经济区", new FiveDistrictsInvestmentCompletionIndex(5, "攀西经济区"));
            }
        } catch (Exception e) {
            throw new BusinessException(dateQuery.toString() + ResultEnum.ILLEGAL_DATE.getValue());
        }
        Map<String, String> regionMap = EconomicRegion.map;
        //累计完成
        BigDecimal total = new BigDecimal("0");
        //
        FiveDistrictsInvestmentCompletionIndex tempRegion = new FiveDistrictsInvestmentCompletionIndex();
        for (Map.Entry<String, FiveDistrictsInvestmentCompletionIndex> data : map.entrySet()) {
            for (Map.Entry<String, String> citys : regionMap.entrySet()) {
                if (data.getKey().equals(citys.getKey())) {
                    //累计完成和
                    total = total.add(data.getValue().getTotalFinished());
                    tempRegion = output.get(regionMap.get(data.getKey()));
                    //1.年度目标
                    if (tempRegion.getYearTarget() != null) {
                        tempRegion.setYearTarget(tempRegion.getYearTarget().add(data.getValue().getYearTarget()));
                    } else {
                        tempRegion.setYearTarget(data.getValue().getYearTarget());
                    }

                    //3.累计完成
                    if (data.getValue().getTotalFinished() != null) {
                        if (tempRegion.getTotalFinished() != null) {
                            tempRegion.setTotalFinished(tempRegion.getTotalFinished().add(data.getValue().getTotalFinished()));
                        } else {
                            tempRegion.setTotalFinished(data.getValue().getTotalFinished());
                        }
                    }
                    //8.高速公路-年度目标（亿元）
                    if (data.getValue().getHighwayYearGoal() != null) {
                        if (tempRegion.getHighwayYearGoal() != null) {
                            tempRegion.setHighwayYearGoal(tempRegion.getHighwayYearGoal().add(data.getValue().getHighwayYearGoal()));
                        } else {
                            tempRegion.setHighwayYearGoal(data.getValue().getHighwayYearGoal());
                        }
                    }
                    //10高速公路-累计完成（亿元）
                    if (data.getValue().getHighwayTotalFinished() != null) {
                        if (tempRegion.getHighwayTotalFinished() != null) {
                            tempRegion.setHighwayTotalFinished(tempRegion.getHighwayTotalFinished().add(data.getValue().getHighwayTotalFinished()));
                        } else {
                            tempRegion.setHighwayTotalFinished(data.getValue().getHighwayTotalFinished());
                        }
                    }
                    //14.国省干线-年度目标（亿元）
                    if (data.getValue().getNpHighwayYearGoal() != null) {
                        if (tempRegion.getNpHighwayYearGoal() != null) {
                            tempRegion.setNpHighwayYearGoal(tempRegion.getNpHighwayYearGoal().add(data.getValue().getNpHighwayYearGoal()));
                        } else {
                            tempRegion.setNpHighwayYearGoal(data.getValue().getNpHighwayYearGoal());
                        }
                    }
                    //17.国省干线-累计完成（亿元）
                    if (data.getValue().getNpHighwayTotalFinished() != null) {
                        if (tempRegion.getNpHighwayTotalFinished() != null) {
                            tempRegion.setNpHighwayTotalFinished(tempRegion.getNpHighwayTotalFinished().add(data.getValue().getNpHighwayTotalFinished()));
                        } else {
                            tempRegion.setNpHighwayTotalFinished(data.getValue().getNpHighwayTotalFinished());
                        }
                    }
                    //20.农村公路-年度目标（亿元）
                    if (data.getValue().getRuralRoadYearGoal() != null) {
                        if (tempRegion.getRuralRoadYearGoal() != null) {
                            tempRegion.setRuralRoadYearGoal(tempRegion.getRuralRoadYearGoal().add(data.getValue().getRuralRoadYearGoal()));
                        } else {
                            tempRegion.setRuralRoadYearGoal(data.getValue().getRuralRoadYearGoal());
                        }
                    }
                    //22.农村公路-累计完成（亿元）
                    if (data.getValue().getRuralRoadTotalFinished() != null) {
                        if (tempRegion.getRuralRoadTotalFinished() != null) {
                            tempRegion.setRuralRoadTotalFinished(tempRegion.getRuralRoadTotalFinished().add(data.getValue().getRuralRoadTotalFinished()));
                        } else {
                            tempRegion.setRuralRoadTotalFinished(data.getValue().getRuralRoadTotalFinished());
                        }
                    }
                    //26.内河水运-年度目标（亿元）
                    if (data.getValue().getInlandWaterwayYearGoal() != null) {
                        if (tempRegion.getInlandWaterwayYearGoal() != null) {
                            tempRegion.setInlandWaterwayYearGoal(tempRegion.getInlandWaterwayYearGoal().add(data.getValue().getInlandWaterwayYearGoal()));
                        } else {
                            tempRegion.setInlandWaterwayYearGoal(data.getValue().getInlandWaterwayYearGoal());
                        }
                    }
                    //28.内河水运-累计完成（亿元）
                    if (data.getValue().getInlandWaterwayTotalFinished() != null) {
                        if (tempRegion.getInlandWaterwayTotalFinished() != null) {
                            tempRegion.setInlandWaterwayTotalFinished(tempRegion.getInlandWaterwayTotalFinished().add(data.getValue().getInlandWaterwayTotalFinished()));
                        } else {
                            tempRegion.setInlandWaterwayTotalFinished(data.getValue().getInlandWaterwayTotalFinished());
                        }
                    }
                    //32.站点建设-年度目标（亿元）
                    if (data.getValue().getSiteConstuctionYearGoal() != null) {
                        if (tempRegion.getSiteConstuctionYearGoal() != null) {
                            tempRegion.setSiteConstuctionYearGoal(tempRegion.getSiteConstuctionYearGoal().add(data.getValue().getSiteConstuctionYearGoal()));
                        } else {
                            tempRegion.setSiteConstuctionYearGoal(data.getValue().getSiteConstuctionYearGoal());
                        }
                    }
                    //34.站点建设-累计完成（亿元）
                    if (data.getValue().getSiteConstuctionTotalFinished() != null) {
                        if (tempRegion.getSiteConstuctionTotalFinished() != null) {
                            tempRegion.setSiteConstuctionTotalFinished(tempRegion.getSiteConstuctionTotalFinished().add(data.getValue().getSiteConstuctionTotalFinished()));
                        } else {
                            tempRegion.setSiteConstuctionTotalFinished(data.getValue().getSiteConstuctionTotalFinished());
                        }
                    }
                    //38.养护及其它专项-年度目标（亿元）
                    if (data.getValue().getConservationYearGoal() != null) {
                        if (tempRegion.getConservationYearGoal() != null) {
                            tempRegion.setConservationYearGoal(tempRegion.getConservationYearGoal().add(data.getValue().getConservationYearGoal()));
                        } else {
                            tempRegion.setConservationYearGoal(data.getValue().getConservationYearGoal());
                        }
                    }
                    //40.养护及其它专项-累计完成（亿元）
                    if (data.getValue().getConservationTotalFinished() != null) {
                        if (tempRegion.getConservationTotalFinished() != null) {
                            tempRegion.setConservationTotalFinished(tempRegion.getConservationTotalFinished().add(data.getValue().getConservationTotalFinished()));
                        } else {
                            tempRegion.setConservationTotalFinished(data.getValue().getConservationTotalFinished());
                        }
                    }
                    //44.养护及其它专项-其他专项
                    if (tempRegion.getConservationOthers() != null) {
                        tempRegion.setConservationOthers(tempRegion.getConservationOthers().add(data.getValue().getConservationOthers()));
                    } else {
                        tempRegion.setConservationOthers(data.getValue().getConservationOthers());
                    }
                    //45.养护及其它专项-普通公路养护
                    if (tempRegion.getConservationOrdinaryHighway() != null) {
                        tempRegion.setConservationOrdinaryHighway(tempRegion.getConservationOrdinaryHighway().add(data.getValue().getConservationOrdinaryHighway()));
                    } else {
                        tempRegion.setConservationOrdinaryHighway(data.getValue().getConservationOrdinaryHighway());
                    }
                    //46.养护及其它专项-高速公路养护
                    if (tempRegion.getConservationHighway() != null) {
                        tempRegion.setConservationHighway(tempRegion.getConservationHighway().add(data.getValue().getConservationHighway()));
                    } else {
                        tempRegion.setConservationHighway(data.getValue().getConservationHighway());
                    }
                    //47.养护及其它专项-智慧交通
                    if (tempRegion.getConservationIntelligentTransport() != null) {
                        tempRegion.setConservationIntelligentTransport(tempRegion.getConservationIntelligentTransport().add(data.getValue().getConservationIntelligentTransport()));
                    } else {
                        tempRegion.setConservationIntelligentTransport(data.getValue().getConservationIntelligentTransport());
                    }

                    continue;
                }


            }
        }
        BigDecimal totalFinish = new BigDecimal(total.toString());
        output.values().stream().forEach(data -> {
            data.setId(UuidUtil.get());
            data.setYear(culRule.getYear());
            data.setMonth(culRule.getMonth());
            //1.年度目标
            if (data.getYearTarget() != null) {
                data.setYearTarget(data.getYearTarget().setScale(2, BigDecimal.ROUND_HALF_UP));
            }
            //2.本月完成
            if (data.getTotalFinished() != null && lastMonthMap.get(data.getRegion()).getTotalFinished() != null) {
                data.setCurrMonthFinished(data.getTotalFinished().subtract(lastMonthMap.get(data.getRegion()).getTotalFinished()).setScale(2, BigDecimal.ROUND_HALF_UP));
            }
            //3.累计完成
            if (data.getTotalFinished() != null) {
                data.setTotalFinished(data.getTotalFinished().setScale(2, BigDecimal.ROUND_HALF_UP));
            }
            //4.完成占比
            if (data.getTotalFinished() != null) {
                if (data.getYearTarget() != null && data.getYearTarget().compareTo(new BigDecimal(0)) != 0) {
                    data.setFinishedPercent(data.getTotalFinished().divide(data.getYearTarget(), 5, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
                }
            }
            //5.环比增速
            if (data.getCurrMonthFinished() != null && lastMonthMap.get(data.getRegion()).getCurrMonthFinished() != null && lastMonthMap.get(data.getRegion()).getCurrMonthFinished().compareTo(new BigDecimal(0)) != 0) {
                data.setMomGrowthRate(data.getCurrMonthFinished().divide(lastMonthMap.get(data.getRegion()).getCurrMonthFinished(), 5, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal(1).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP)) + "%");
            }
            //6.同比增速
            if (data.getTotalFinished() != null && lastYearMap.get(data.getRegion()).getTotalFinished() != null && lastYearMap.get(data.getRegion()).getTotalFinished().compareTo(new BigDecimal(0)) != 0) {
                data.setYoyGrowthRate(data.getTotalFinished().divide(lastYearMap.get(data.getRegion()).getTotalFinished(), 5, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal(1).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP)) + "%");
            }
            //7.贡献率
            if (data.getTotalFinished() != null && totalFinish.compareTo(new BigDecimal("0")) != 0) {
                data.setContributionRate(data.getTotalFinished().divide(totalFinish, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
            }
            //8.高速公路-年度目标（亿元）
            if (data.getHighwayYearGoal() != null) {
                data.setHighwayYearGoal(data.getHighwayYearGoal().setScale(2, BigDecimal.ROUND_HALF_UP));
            }
            //9.高速公路-本月完成
            if (data.getHighwayTotalFinished() != null && lastMonthMap.get(data.getRegion()).getHighwayTotalFinished() != null) {
                data.setHighwayCurrMonthFinished(data.getHighwayTotalFinished().subtract(lastMonthMap.get(data.getRegion()).getHighwayTotalFinished()));
            }
            //10.高速公路-累计完成（亿元）
            if (data.getHighwayTotalFinished() != null) {
                data.setHighwayTotalFinished(data.getHighwayTotalFinished().setScale(2, BigDecimal.ROUND_HALF_UP));
            }
            //11.高速公路-占比
            if (data.getHighwayTotalFinished() != null && data.getHighwayYearGoal() != null && data.getHighwayYearGoal().compareTo(new BigDecimal(0)) != 0) {
                data.setHighwayPercentage(data.getHighwayTotalFinished().divide(data.getHighwayYearGoal(), 5, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
            }
            //12.高速公路-环比增速
            if (data.getHighwayCurrMonthFinished() != null && lastMonthMap.get(data.getRegion()).getHighwayCurrMonthFinished() != null && lastMonthMap.get(data.getRegion()).getHighwayCurrMonthFinished().compareTo(new BigDecimal(0)) != 0) {
                data.setHighwayMom(data.getHighwayCurrMonthFinished().divide(lastMonthMap.get(data.getRegion()).getHighwayCurrMonthFinished(), 5, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal(1)).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
            }
            //13.高速公路-同比增速
            if (data.getHighwayTotalFinished() != null && lastYearMap.get(data.getRegion()).getHighwayTotalFinished() != null && lastYearMap.get(data.getRegion()).getHighwayTotalFinished().compareTo(new BigDecimal(0)) != 0) {
                data.setHighwayYoy(data.getHighwayTotalFinished().divide(lastYearMap.get(data.getRegion()).getHighwayTotalFinished(), 5, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal(1)).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
            }

            //14.国省干线公路-年度目标（亿元）
            if (data.getNpHighwayYearGoal() != null) {
                data.setNpHighwayYearGoal(data.getNpHighwayYearGoal().setScale(2, BigDecimal.ROUND_HALF_UP));
            }
            //15.国省干线-本月完成
            if (data.getNpHighwayTotalFinished() != null && lastMonthMap.get(data.getRegion()).getNpHighwayTotalFinished() != null) {
                data.setNpHighwayCurrMonthFinished(data.getNpHighwayTotalFinished().subtract(lastMonthMap.get(data.getRegion()).getNpHighwayTotalFinished()));
            }
            //16.国省干线公路-累计完成（亿元）
            if (data.getNpHighwayTotalFinished() != null) {
                data.setNpHighwayTotalFinished(data.getNpHighwayTotalFinished().setScale(2, BigDecimal.ROUND_HALF_UP));
            }
            //17.国省干线公路-占比
            if (data.getNpHighwayTotalFinished() != null && data.getNpHighwayYearGoal() != null && data.getNpHighwayYearGoal().compareTo(new BigDecimal(0)) != 0) {
                data.setNpHighwayPercent(data.getNpHighwayTotalFinished().divide(data.getNpHighwayYearGoal(), 5, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
            }
            //18.国省干线公路-环比增速
            if (data.getNpHighwayCurrMonthFinished() != null && lastMonthMap.get(data.getRegion()).getNpHighwayCurrMonthFinished() != null && lastMonthMap.get(data.getRegion()).getNpHighwayCurrMonthFinished().compareTo(new BigDecimal(0)) != 0) {
                data.setNpHighwayMom(data.getNpHighwayCurrMonthFinished().divide(lastMonthMap.get(data.getRegion()).getNpHighwayCurrMonthFinished(), 5, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal(1)).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
            }
            //19.国省干线公路-同比增速
            if (data.getNpHighwayTotalFinished() != null && lastYearMap.get(data.getRegion()).getNpHighwayTotalFinished() != null && lastYearMap.get(data.getRegion()).getNpHighwayTotalFinished().compareTo(new BigDecimal(0)) != 0) {
                data.setNpHighwayYoy(data.getNpHighwayTotalFinished().divide(lastYearMap.get(data.getRegion()).getNpHighwayTotalFinished(), 5, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal(1)).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
            }

            //20.农村公路-年度目标（亿元）
            if (data.getRuralRoadYearGoal() != null) {
                data.setRuralRoadYearGoal(data.getRuralRoadYearGoal().setScale(2, BigDecimal.ROUND_HALF_UP));
            }
            //21.农村公路-本月完成
            if (data.getRuralRoadTotalFinished() != null && lastMonthMap.get(data.getRegion()).getRuralRoadTotalFinished() != null) {
                data.setRuralRoadCurrMonthFinished(data.getRuralRoadTotalFinished().subtract(lastMonthMap.get(data.getRegion()).getRuralRoadTotalFinished()));
            }
            //22.农村公路-累计完成（亿元）
            if (data.getRuralRoadTotalFinished() != null) {
                data.setRuralRoadTotalFinished(data.getRuralRoadTotalFinished().setScale(2, BigDecimal.ROUND_HALF_UP));
            }
            //23.农村公路-占比
            if (data.getRuralRoadTotalFinished() != null && data.getRuralRoadYearGoal() != null && data.getRuralRoadYearGoal().compareTo(new BigDecimal(0)) != 0) {
                data.setRuralRoadPercent(data.getRuralRoadTotalFinished().divide(data.getRuralRoadYearGoal(), 5, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
            }
            //24.农村公路-环比增速
            if (data.getRuralRoadCurrMonthFinished() != null && lastMonthMap.get(data.getRegion()).getRuralRoadCurrMonthFinished() != null && lastMonthMap.get(data.getRegion()).getRuralRoadCurrMonthFinished().compareTo(new BigDecimal(0)) != 0) {
                data.setRuralRoadMom(data.getRuralRoadCurrMonthFinished().divide(lastMonthMap.get(data.getRegion()).getRuralRoadCurrMonthFinished(), 5, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal(1)).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
            }
            //25.农村公路-同比增速
            if (data.getRuralRoadTotalFinished() != null && lastYearMap.get(data.getRegion()).getRuralRoadTotalFinished() != null && lastYearMap.get(data.getRegion()).getRuralRoadTotalFinished().compareTo(new BigDecimal(0)) != 0) {
                data.setRuralRoadYoy(data.getRuralRoadTotalFinished().divide(lastYearMap.get(data.getRegion()).getRuralRoadTotalFinished(), 5, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal(1)).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
            }

            //26.内河水运-年度目标（亿元）
            if (data.getInlandWaterwayYearGoal() != null) {
                data.setInlandWaterwayYearGoal(data.getInlandWaterwayYearGoal().setScale(2, BigDecimal.ROUND_HALF_UP));
            }
            //27.内河水运-本月完成
            if (data.getInlandWaterwayTotalFinished() != null && lastMonthMap.get(data.getRegion()).getInlandWaterwayTotalFinished() != null) {
                data.setInlandWaterwayCurrMonthFinished(data.getInlandWaterwayTotalFinished().subtract(lastMonthMap.get(data.getRegion()).getInlandWaterwayTotalFinished()));
            }
            //28.内河水运-累计完成（亿元）
            if (data.getInlandWaterwayTotalFinished() != null) {
                data.setInlandWaterwayTotalFinished(data.getInlandWaterwayTotalFinished().setScale(2, BigDecimal.ROUND_HALF_UP));
            }
            //29.内河水运-占比
            if (data.getInlandWaterwayTotalFinished() != null && data.getInlandWaterwayYearGoal() != null && data.getInlandWaterwayYearGoal().compareTo(new BigDecimal(0)) != 0) {
                data.setInlandWaterwayPercent(data.getInlandWaterwayTotalFinished().divide(data.getInlandWaterwayYearGoal(), 5, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
            }
            //30.内河水运-环比增速
            if (data.getInlandWaterwayCurrMonthFinished() != null && lastMonthMap.get(data.getRegion()).getInlandWaterwayCurrMonthFinished() != null && lastMonthMap.get(data.getRegion()).getInlandWaterwayCurrMonthFinished().compareTo(new BigDecimal(0)) != 0) {
                data.setInlandWaterwayMom(data.getInlandWaterwayCurrMonthFinished().divide(lastMonthMap.get(data.getRegion()).getInlandWaterwayCurrMonthFinished(), 5, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal(1)).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
            }
            //31.内河水运-同比增速
            if (data.getInlandWaterwayTotalFinished() != null && lastYearMap.get(data.getRegion()).getInlandWaterwayTotalFinished() != null && lastYearMap.get(data.getRegion()).getInlandWaterwayTotalFinished().compareTo(new BigDecimal(0)) != 0) {
                data.setInlandWaterwayYoy(data.getInlandWaterwayTotalFinished().divide(lastYearMap.get(data.getRegion()).getInlandWaterwayTotalFinished(), 5, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal(1)).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
            }

            //32.站点建设-年度目标（亿元）
            if (data.getSiteConstuctionYearGoal() != null) {
                data.setSiteConstuctionYearGoal(data.getSiteConstuctionYearGoal().setScale(2, BigDecimal.ROUND_HALF_UP));
            }
            //33.站点建设-本月完成
            if (data.getSiteConstuctionTotalFinished() != null && lastMonthMap.get(data.getRegion()).getSiteConstuctionTotalFinished() != null) {
                data.setSiteConstuctionCurrMonthFinished(data.getSiteConstuctionTotalFinished().subtract(lastMonthMap.get(data.getRegion()).getSiteConstuctionTotalFinished()));
            }
            //34.站点建设-累计完成（亿元）
            if (data.getSiteConstuctionTotalFinished() != null) {
                data.setSiteConstuctionTotalFinished(data.getSiteConstuctionTotalFinished().setScale(2, BigDecimal.ROUND_HALF_UP));
            }
            //35.站点建设-占比
            if (data.getSiteConstuctionTotalFinished() != null && data.getSiteConstuctionYearGoal() != null && data.getSiteConstuctionYearGoal().compareTo(new BigDecimal(0)) != 0) {
                data.setSiteConstuctionPercent(data.getSiteConstuctionTotalFinished().divide(data.getSiteConstuctionYearGoal(), 5, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
            }
            //36.站点建设-环比增速
            if (data.getSiteConstuctionCurrMonthFinished() != null && lastMonthMap.get(data.getRegion()).getSiteConstuctionCurrMonthFinished() != null && lastMonthMap.get(data.getRegion()).getSiteConstuctionCurrMonthFinished().compareTo(new BigDecimal(0)) != 0) {
                data.setSiteConstuctionMom(data.getSiteConstuctionCurrMonthFinished().divide(lastMonthMap.get(data.getRegion()).getSiteConstuctionCurrMonthFinished(), 5, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal(1)).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
            }
            //37.站点建设-同比增速
            if (data.getSiteConstuctionTotalFinished() != null && lastYearMap.get(data.getRegion()).getSiteConstuctionTotalFinished() != null && lastYearMap.get(data.getRegion()).getSiteConstuctionTotalFinished().compareTo(new BigDecimal(0)) != 0) {
                data.setSiteConstuctionYoy(data.getSiteConstuctionTotalFinished().divide(lastYearMap.get(data.getRegion()).getSiteConstuctionTotalFinished(), 5, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal(1)).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
            }

            //38.养护及其它专项-年度目标（亿元）
            if (data.getConservationYearGoal() != null) {
                data.setConservationYearGoal(data.getConservationYearGoal().setScale(2, BigDecimal.ROUND_HALF_UP));
            }
            //39.养护及其它专项-本月完成
            if (data.getConservationTotalFinished() != null && lastMonthMap.get(data.getRegion()).getConservationTotalFinished() != null) {
                data.setConservationCurrMonthFinished(data.getConservationTotalFinished().subtract(lastMonthMap.get(data.getRegion()).getConservationTotalFinished()));
            }
            //40.养护及其它专项-累计完成（亿元）
            if (data.getConservationTotalFinished() != null) {
                data.setConservationTotalFinished(data.getConservationTotalFinished().setScale(2, BigDecimal.ROUND_HALF_UP));
            }
            //41.养护及其它专项-占比
            if (data.getConservationTotalFinished() != null && data.getConservationYearGoal() != null && data.getConservationYearGoal().compareTo(new BigDecimal(0)) != 0) {
                data.setConservationPercent(data.getConservationTotalFinished().divide(data.getConservationYearGoal(), 5, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
            }
            //42.养护及其它专项-环比增速
            if (data.getConservationCurrMonthFinished() != null && lastMonthMap.get(data.getRegion()).getConservationCurrMonthFinished() != null && lastMonthMap.get(data.getRegion()).getConservationCurrMonthFinished().compareTo(new BigDecimal(0)) != 0) {
                data.setConservationMom(data.getConservationCurrMonthFinished().divide(lastMonthMap.get(data.getRegion()).getConservationCurrMonthFinished(), 5, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal(1)).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
            }
            //43.养护及其它专项-同比增速
            if (data.getConservationCurrMonthFinished() != null && lastYearMap.get(data.getRegion()).getConservationCurrMonthFinished() != null && lastYearMap.get(data.getRegion()).getConservationCurrMonthFinished().compareTo(new BigDecimal(0)) != 0) {
                data.setConservationYoy(data.getConservationCurrMonthFinished().divide(lastYearMap.get(data.getRegion()).getConservationCurrMonthFinished(), 5, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal(1)).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
            }
            //44.养护及其它专项-其他专项
            if (data.getConservationOthers() != null) {
                data.setConservationOthers(data.getConservationOthers().setScale(2, BigDecimal.ROUND_HALF_UP));
            }
            //45.养护及其它专项-普通公路养护
            if (data.getConservationOrdinaryHighway() != null) {
                data.setConservationOrdinaryHighway(data.getConservationOrdinaryHighway().setScale(2, BigDecimal.ROUND_HALF_UP));
            }
            //46.养护及其它专项-高速公路养护
            if (data.getConservationHighway() != null) {
                data.setConservationHighway(data.getConservationHighway().setScale(2, BigDecimal.ROUND_HALF_UP));
            }
            //47.养护及其它专项-智慧交通
            if (data.getConservationIntelligentTransport() != null) {
                data.setConservationIntelligentTransport(data.getConservationIntelligentTransport().setScale(2, BigDecimal.ROUND_HALF_UP));
            }
        });

        resultInsertMapper.insertDistrInvestComp(output.values().

                stream().

                collect(Collectors.toList()));
    }

    /**
     * Description: 各市（州）公路水路建设投资完成情况
     *
     * @param culRule
     * @Date: 2021/1/5 15:40
     * @Author: zbya
     * @return: void
     */
    @Override
    public void cityConsInvestComp(CulRule culRule) throws Exception {
        List<CityWayConstructionInvestmentCompletion> output;
        //统计时间
        DateQuery dateQuery = new DateQuery(culRule.getYear(), culRule.getMonth());

        List<CityWayConstructionInvestmentCompletion> datas = proConsInvestCompMapper.getCityConsInvestComp(dateQuery);

        Map<String, CityWayConstructionInvestmentCompletion> map;
        if (datas != null) {
            try {
                map = datas.stream().collect(Collectors.toMap(CityWayConstructionInvestmentCompletion::getCity, v -> v));
            } catch (Exception e) {
                throw new BusinessException(dateQuery.toString() + ResultEnum.ILLEGAL_DATE.getValue());
            }
        } else {
            map = new HashMap<>();
        }
        if (map.get("总计") == null) {
            map.put("总计", new CityWayConstructionInvestmentCompletion(1, "总计"));
        }
        if (map.get("广元市") == null) {
            map.put("广元市", new CityWayConstructionInvestmentCompletion(2, "广元市"));
        }
        if (map.get("眉山市") == null) {
            map.put("眉山市", new CityWayConstructionInvestmentCompletion(3, "眉山市"));
        }
        if (map.get("攀枝花市") == null) {
            map.put("攀枝花市", new CityWayConstructionInvestmentCompletion(4, "攀枝花市"));
        }
        if (map.get("雅安市") == null) {
            map.put("雅安市", new CityWayConstructionInvestmentCompletion(5, "雅安市"));
        }
        if (map.get("资阳市") == null) {
            map.put("资阳市", new CityWayConstructionInvestmentCompletion(6, "资阳市"));
        }
        if (map.get("南充市") == null) {
            map.put("南充市", new CityWayConstructionInvestmentCompletion(7, "南充市"));
        }
        if (map.get("绵阳市") == null) {
            map.put("绵阳市", new CityWayConstructionInvestmentCompletion(8, "绵阳市"));
        }
        if (map.get("成都市") == null) {
            map.put("成都市", new CityWayConstructionInvestmentCompletion(9, "成都市"));
        }
        if (map.get("自贡市") == null) {
            map.put("自贡市", new CityWayConstructionInvestmentCompletion(10, "自贡市"));
        }
        if (map.get("遂宁市") == null) {
            map.put("遂宁市", new CityWayConstructionInvestmentCompletion(11, "遂宁市"));
        }
        if (map.get("乐山市") == null) {
            map.put("乐山市", new CityWayConstructionInvestmentCompletion(12, "乐山市"));
        }
        if (map.get("宜宾市") == null) {
            map.put("宜宾市", new CityWayConstructionInvestmentCompletion(13, "宜宾市"));
        }
        if (map.get("广安市") == null) {
            map.put("广安市", new CityWayConstructionInvestmentCompletion(14, "广安市"));
        }
        if (map.get("巴中市") == null) {
            map.put("巴中市", new CityWayConstructionInvestmentCompletion(15, "巴中市"));
        }
        if (map.get("德阳市") == null) {
            map.put("德阳市", new CityWayConstructionInvestmentCompletion(16, "德阳市"));
        }
        if (map.get("泸州市") == null) {
            map.put("泸州市", new CityWayConstructionInvestmentCompletion(17, "泸州市"));
        }
        if (map.get("甘孜州") == null) {
            map.put("甘孜州", new CityWayConstructionInvestmentCompletion(18, "甘孜州"));
        }
        if (map.get("达州市") == null) {
            map.put("达州市", new CityWayConstructionInvestmentCompletion(19, "达州市"));
        }
        if (map.get("阿坝州") == null) {
            map.put("阿坝州", new CityWayConstructionInvestmentCompletion(20, "阿坝州"));
        }
        if (map.get("内江市") == null) {
            map.put("内江市", new CityWayConstructionInvestmentCompletion(21, "内江市"));
        }
        if (map.get("凉山州") == null) {
            map.put("凉山州", new CityWayConstructionInvestmentCompletion(22, "凉山州"));
        }
        if (map.get("厅直有关单位和高速公路项目公司（信息化项目）") == null) {
            map.put("厅直有关单位和高速公路项目公司（信息化项目）", new CityWayConstructionInvestmentCompletion(23, "厅直有关单位和高速公路项目公司（信息化项目）"));
        }
        datas = map.values().stream().collect(Collectors.toList());
        //上月值
        if (dateQuery.getMonth() != 1) {
            dateQuery = new DateQuery(dateQuery.getYear(), dateQuery.getMonth() - 1);
        } else {
            dateQuery = new DateQuery(dateQuery.getYear() - 1, 12);
        }
        Map<String, CityWayConstructionInvestmentCompletion> lastMonth;
        try {
            lastMonth = proConsInvestCompMapper.getCityConsInvestComp(dateQuery).stream().collect(Collectors.toMap(CityWayConstructionInvestmentCompletion::getCity, v -> v));
        } catch (Exception e) {
            throw new BusinessException(dateQuery.toString() + ResultEnum.ILLEGAL_DATE.getValue());
        }
        //名次变化
        int rankChange;
        for (CityWayConstructionInvestmentCompletion data : datas) {
            //合计本月完成计算
            boolean flag = false;
            if (data.getTotalFinished() != null) {
                flag = lastMonth.get(data.getCity()) == null ? false : true;
                if (dateQuery.getMonth() == 1) {
                    data.setTotalCurrMonthFinished(data.getTotalFinished());
                } else {
                    if (flag && lastMonth.get(data.getCity()).getTotalFinished() != null) {
                        data.setTotalCurrMonthFinished(data.getTotalFinished().subtract(lastMonth.get(data.getCity()).getTotalFinished()));
                    }
                }
            }
            //合计相对于上月排名变化计算
            if (data.getTotalCompletionRate() != null) {
                if (flag && lastMonth.get(data.getCity()).getTotalCompletionRate() != null) {
                    rankChange = lastMonth.get(data.getCity()).getTotalCompletionRateRanking() - data.getTotalCompletionRateRanking();
                    if (rankChange == 0) {
                        data.setTotalMonthRankingCahnge("—");
                    } else if (rankChange > 0) {
                        data.setTotalMonthRankingCahnge("↑" + rankChange);
                    } else {
                        data.setTotalMonthRankingCahnge("↓" + Math.abs(rankChange));
                    }
                }
            } else {
                data.setTotalCompletionRate(null);
            }
            data.setId(UuidUtil.get());
            data.setYear(culRule.getYear());
            data.setMonth(culRule.getMonth());
        }
        resultInsertMapper.insertCityConsInvestComp(datas);
    }
}
