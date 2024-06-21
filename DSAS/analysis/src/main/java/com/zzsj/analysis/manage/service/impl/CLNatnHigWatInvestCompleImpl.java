package com.zzsj.analysis.manage.service.impl;

import com.zzsj.analysis.base.exception.BusinessException;
import com.zzsj.analysis.base.utils.enums.ResultEnum;
import com.zzsj.analysis.manage.entity.CulRule;
import com.zzsj.analysis.manage.entity.NationalHighwayWaterwayInvestmentCompletion;
import com.zzsj.analysis.manage.mapper.calculate.NatnHigWatInvestCompleMapper;
import com.zzsj.analysis.manage.mapper.mysql.ResultInsertMapper;
import com.zzsj.analysis.manage.pojo.query.DateQuery;
import com.zzsj.analysis.manage.service.CLNatnHigWatInvestComple;
import com.zzsj.analysis.manage.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ：zbya
 * @date ：Created in 2021/1/18 13:50
 * @description：全国公路水路固定资产投资完成情况
 */

@Service
public class CLNatnHigWatInvestCompleImpl implements CLNatnHigWatInvestComple {
    @Autowired
    NatnHigWatInvestCompleMapper natnHigWatInvestCompleMapper;
    @Autowired
    ResultInsertMapper resultInsertMapper;
    @Override
    public void highwayWaterwayinvest(CulRule culRule) throws Exception {
        List<NationalHighwayWaterwayInvestmentCompletion> output;
        //统计时间
        DateQuery dateQuery = new DateQuery(culRule.getYear(), culRule.getMonth());
        List<NationalHighwayWaterwayInvestmentCompletion> datas = natnHigWatInvestCompleMapper.getProInvestData(dateQuery);
        Map<Integer, NationalHighwayWaterwayInvestmentCompletion> map;
        if (datas != null) {
            try {
                map = datas.stream().collect(Collectors.toMap(NationalHighwayWaterwayInvestmentCompletion::getSort, v -> v));
            } catch (Exception e) {
                throw new BusinessException(dateQuery.toString() + ResultEnum.ILLEGAL_DATE.getValue());
            }
        } else {
            map = new HashMap<>();
        }
        if (map.get(1) == null) {
            map.put(1, new NationalHighwayWaterwayInvestmentCompletion(1, "北京"));
        }
        if (map.get(2) == null) {
            map.put(2, new NationalHighwayWaterwayInvestmentCompletion(2, "天津"));
        }
        if (map.get(3) == null) {
            map.put(3, new NationalHighwayWaterwayInvestmentCompletion(3, "河北"));
        }
        if (map.get(4) == null) {
            map.put(4, new NationalHighwayWaterwayInvestmentCompletion(4, "山西"));
        }
        if (map.get(5) == null) {
            map.put(5, new NationalHighwayWaterwayInvestmentCompletion(5, "内蒙古"));
        }
        if (map.get(6) == null) {
            map.put(6, new NationalHighwayWaterwayInvestmentCompletion(6, "辽宁"));
        }
        if (map.get(7) == null) {
            map.put(7, new NationalHighwayWaterwayInvestmentCompletion(7, "吉林"));
        }
        if (map.get(8) == null) {
            map.put(8, new NationalHighwayWaterwayInvestmentCompletion(8, "黑龙江"));
        }
        if (map.get(9) == null) {
            map.put(9, new NationalHighwayWaterwayInvestmentCompletion(9, "上海"));
        }
        if (map.get(10) == null) {
            map.put(10, new NationalHighwayWaterwayInvestmentCompletion(10, "江苏"));
        }
        if (map.get(11) == null) {
            map.put(11, new NationalHighwayWaterwayInvestmentCompletion(11, "浙江"));
        }
        if (map.get(12) == null) {
            map.put(12, new NationalHighwayWaterwayInvestmentCompletion(12, "安徽"));
        }
        if (map.get(13) == null) {
            map.put(13, new NationalHighwayWaterwayInvestmentCompletion(13, "福建"));
        }
        if (map.get(14) == null) {
            map.put(14, new NationalHighwayWaterwayInvestmentCompletion(14, "江西"));
        }
        if (map.get(15) == null) {
            map.put(15, new NationalHighwayWaterwayInvestmentCompletion(15, "山东"));
        }
        if (map.get(16) == null) {
            map.put(16, new NationalHighwayWaterwayInvestmentCompletion(16, "河南"));
        }
        if (map.get(17) == null) {
            map.put(17, new NationalHighwayWaterwayInvestmentCompletion(17, "湖北"));
        }
        if (map.get(18) == null) {
            map.put(18, new NationalHighwayWaterwayInvestmentCompletion(18, "湖南"));
        }
        if (map.get(19) == null) {
            map.put(19, new NationalHighwayWaterwayInvestmentCompletion(19, "广东"));
        }
        if (map.get(20) == null) {
            map.put(20, new NationalHighwayWaterwayInvestmentCompletion(20, "广西"));
        }
        if (map.get(21) == null) {
            map.put(21, new NationalHighwayWaterwayInvestmentCompletion(21, "海南"));
        }
        if (map.get(22) == null) {
            map.put(22, new NationalHighwayWaterwayInvestmentCompletion(22, "重庆"));
        }
        if (map.get(23) == null) {
            map.put(23, new NationalHighwayWaterwayInvestmentCompletion(23, "四川"));
        }
        if (map.get(24) == null) {
            map.put(24, new NationalHighwayWaterwayInvestmentCompletion(24, "贵州"));
        }
        if (map.get(25) == null) {
            map.put(25, new NationalHighwayWaterwayInvestmentCompletion(25, "云南"));
        }
        if (map.get(26) == null) {
            map.put(26, new NationalHighwayWaterwayInvestmentCompletion(26, "西藏"));
        }
        if (map.get(27) == null) {
            map.put(27, new NationalHighwayWaterwayInvestmentCompletion(27, "陕西"));
        }
        if (map.get(28) == null) {
            map.put(28, new NationalHighwayWaterwayInvestmentCompletion(28, "甘肃"));
        }
        if (map.get(29) == null) {
            map.put(29, new NationalHighwayWaterwayInvestmentCompletion(29, "青海"));
        }
        if (map.get(30) == null) {
            map.put(30, new NationalHighwayWaterwayInvestmentCompletion(30, "宁夏"));
        }
        if (map.get(31) == null) {
            map.put(31, new NationalHighwayWaterwayInvestmentCompletion(31, "新疆"));
        }
        if (map.get(32) == null) {
            map.put(32, new NationalHighwayWaterwayInvestmentCompletion(32, "#兵团"));
        }
        output = map.values().stream().collect(Collectors.toList());

        output.stream().forEach(data -> {
            data.setId(UuidUtil.get());
            data.setYear(culRule.getYear());
            data.setMonth(culRule.getMonth());
        });

        resultInsertMapper.insertProInvestData(output);
    }
}
