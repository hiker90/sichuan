package com.zzsj.analysis.manage.service.impl;

import com.zzsj.analysis.base.exception.BusinessException;
import com.zzsj.analysis.base.utils.StringUtils;
import com.zzsj.analysis.base.utils.enums.ResultEnum;
import com.zzsj.analysis.manage.entity.CulRule;
import com.zzsj.analysis.manage.entity.NationalHighwayTrafficStatistics;
import com.zzsj.analysis.manage.mapper.calculate.NatHwayTrafStatisticsMapper;
import com.zzsj.analysis.manage.mapper.mysql.ResultInsertMapper;
import com.zzsj.analysis.manage.pojo.query.DateQuery;
import com.zzsj.analysis.manage.service.CLNatnTrafStaticsService;
import com.zzsj.analysis.manage.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ：zbya
 * @date ：Created in 2020/12/22 10:41
 * @description：全国公路客货运输量统计
 */

@Service
public class CLNatnTrafStaticsServiceImpl implements CLNatnTrafStaticsService {

    @Autowired
    NatHwayTrafStatisticsMapper natHwayTrafStatisticsMapper;

    @Autowired
    ResultInsertMapper resultInsertMapper;

    @Override
    public void nationalHighwayTrafficStatistics(CulRule culRule) throws Exception {
        List<NationalHighwayTrafficStatistics> output;
        //本月统计值
        DateQuery dateQuery = new DateQuery(culRule.getYear(), culRule.getMonth());
        NationalHighwayTrafficStatistics totalMonth;
        try {
            totalMonth = natHwayTrafStatisticsMapper.getCountNumByDate(dateQuery);
        } catch (Exception e) {
            throw new BusinessException(dateQuery.toString()+ResultEnum.ILLEGAL_DATE.getValue());
        }
        if (totalMonth == null) {
            totalMonth = new NationalHighwayTrafficStatistics();
        }
        totalMonth.setRegion("总计");
        totalMonth.setSort(1);
        totalMonth.setId(UuidUtil.get());
        totalMonth.setYear(culRule.getYear());
        totalMonth.setMonth(culRule.getMonth());

        //本月各项值
        List<NationalHighwayTrafficStatistics> currMonth = natHwayTrafStatisticsMapper.getByDate(dateQuery);

        Map<Integer, NationalHighwayTrafficStatistics> map;
        if (currMonth != null) {
            try {
                map = currMonth.stream().collect(Collectors.toMap(NationalHighwayTrafficStatistics::getInputOrder, v -> v));
            } catch (Exception e) {
                throw new BusinessException(dateQuery.toString()+ResultEnum.ILLEGAL_DATE.getValue());
            }
        } else {
            map = new HashMap<>();
        }

        for (int k = 2; k < 39; k++) {
            if (k == 2 || k == 8 || k == 12 || k == 20 || k == 27 || k == 33) {
                continue;
            } else {
                if (map.get(k) == null) {
                    map.put(k, new NationalHighwayTrafficStatistics(k));
                }
                map.get(k).setYear(culRule.getYear());
                map.get(k).setMonth(culRule.getMonth());
                map.get(k).setId(UuidUtil.get());
                if (map.get(k).getInputOrder() >= 3 && map.get(k).getInputOrder() <= 7) {
                    map.get(k).setRegion("华北地区");
                } else if (map.get(k).getInputOrder() >= 9 && map.get(k).getInputOrder() <= 11) {
                    map.get(k).setRegion("东北地区");
                } else if (map.get(k).getInputOrder() >= 13 && map.get(k).getInputOrder() <= 19) {
                    map.get(k).setRegion("华东地区");
                } else if (map.get(k).getInputOrder() >= 17 && map.get(k).getInputOrder() <= 19) {
                    map.get(k).setRegion("华中地区");
                } else if (map.get(k).getInputOrder() >= 21 && map.get(k).getInputOrder() <= 26) {
                    map.get(k).setRegion("华南地区");
                } else if (map.get(k).getInputOrder() >= 28 && map.get(k).getInputOrder() <= 32) {
                    map.get(k).setRegion("西南地区");
                } else if (map.get(k).getInputOrder() >= 34 && map.get(k).getInputOrder() <= 38) {
                    map.get(k).setRegion("西北地区");
                }

                if (map.get(k).getInputOrder() == 3) {
                    map.get(k).setSort(27);
                } else if (map.get(k).getInputOrder() == 4) {
                    map.get(k).setSort(26);
                } else if (map.get(k).getInputOrder() == 5) {
                    map.get(k).setSort(23);
                } else if (map.get(k).getInputOrder() == 6) {
                    map.get(k).setSort(24);
                } else if (map.get(k).getInputOrder() == 7) {
                    map.get(k).setSort(25);
                } else if (map.get(k).getInputOrder() == 9) {
                    map.get(k).setSort(7);
                } else if (map.get(k).getInputOrder() == 10) {
                    map.get(k).setSort(8);
                } else if (map.get(k).getInputOrder() == 11) {
                    map.get(k).setSort(9);
                } else if (map.get(k).getInputOrder() == 13) {
                    map.get(k).setSort(16);
                } else if (map.get(k).getInputOrder() == 14) {
                    map.get(k).setSort(11);
                } else if (map.get(k).getInputOrder() == 15) {
                    map.get(k).setSort(14);
                } else if (map.get(k).getInputOrder() == 16) {
                    map.get(k).setSort(12);
                } else if (map.get(k).getInputOrder() == 17) {
                    map.get(k).setSort(15);
                } else if (map.get(k).getInputOrder() == 18) {
                    map.get(k).setSort(13);
                } else if (map.get(k).getInputOrder() == 19) {
                    map.get(k).setSort(10);
                } else if (map.get(k).getInputOrder() == 21) {
                    map.get(k).setSort(17);
                } else if (map.get(k).getInputOrder() == 22) {
                    map.get(k).setSort(19);
                } else if (map.get(k).getInputOrder() == 23) {
                    map.get(k).setSort(18);
                } else if (map.get(k).getInputOrder() == 24) {
                    map.get(k).setSort(20);
                } else if (map.get(k).getInputOrder() == 25) {
                    map.get(k).setSort(21);
                } else if (map.get(k).getInputOrder() == 26) {
                    map.get(k).setSort(22);
                } else if (map.get(k).getInputOrder() == 28) {
                    map.get(k).setSort(4);
                } else if (map.get(k).getInputOrder() == 29) {
                    map.get(k).setSort(2);
                } else if (map.get(k).getInputOrder() == 30) {
                    map.get(k).setSort(5);
                } else if (map.get(k).getInputOrder() == 31) {
                    map.get(k).setSort(3);
                } else if (map.get(k).getInputOrder() == 32) {
                    map.get(k).setSort(6);
                } else if (map.get(k).getInputOrder() == 34) {
                    map.get(k).setSort(28);
                } else if (map.get(k).getInputOrder() == 35) {
                    map.get(k).setSort(29);
                } else if (map.get(k).getInputOrder() == 36) {
                    map.get(k).setSort(32);
                } else if (map.get(k).getInputOrder() == 37) {
                    map.get(k).setSort(31);
                } else if (map.get(k).getInputOrder() == 38) {
                    map.get(k).setSort(30);
                }
            }
        }
        output = map.values().stream().collect(Collectors.toList());
        output.add(totalMonth);
        output.stream().forEach(data -> {
            if (!StringUtils.isEmpty(data.getTourWeekTransYearGrowth()) && !data.getTourWeekTransYearGrowth().contains("%")) {
                data.setTourWeekTransYearGrowth(data.getTourWeekTransYearGrowth() + "%");
            }
            if (!StringUtils.isEmpty(data.getGoodsWeekTransYearGrowth()) && !data.getGoodsWeekTransYearGrowth().contains("%")) {
                data.setGoodsWeekTransYearGrowth(data.getGoodsWeekTransYearGrowth() + "%");
            }
        });
        resultInsertMapper.insertNatnTrafStatics(output);
    }

}
