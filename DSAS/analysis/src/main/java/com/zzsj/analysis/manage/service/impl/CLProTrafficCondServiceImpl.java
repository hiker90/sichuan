package com.zzsj.analysis.manage.service.impl;

import com.zzsj.analysis.base.exception.BusinessException;
import com.zzsj.analysis.base.utils.StringUtils;
import com.zzsj.analysis.base.utils.enums.ResultEnum;
import com.zzsj.analysis.manage.entity.CulRule;
import com.zzsj.analysis.manage.entity.ProvinceHighwayTrafficCondition;
import com.zzsj.analysis.manage.mapper.calculate.ProTrafficCondMapper;
import com.zzsj.analysis.manage.mapper.mysql.ResultInsertMapper;
import com.zzsj.analysis.manage.pojo.query.DateQuery;
import com.zzsj.analysis.manage.service.CLProTrafficCondService;
import com.zzsj.analysis.manage.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ：zbya
 * @date ：Created in 2020/12/24 9:10
 * @description：全省公路网运行情况统计表
 */

@Service
public class CLProTrafficCondServiceImpl implements CLProTrafficCondService {

    @Autowired
    ProTrafficCondMapper proTrafficCondMapper;

    @Autowired
    ResultInsertMapper resultInsertMapper;

    @Override
    public void provinceTrafficCondition(CulRule culRule) throws Exception {
        List<ProvinceHighwayTrafficCondition> output;
        //本月
        DateQuery dateQuery = new DateQuery(culRule.getYear(), culRule.getMonth());
        List<ProvinceHighwayTrafficCondition> datas = proTrafficCondMapper.getBydate(dateQuery);

        Map<Integer, ProvinceHighwayTrafficCondition> map;

        if (!datas.isEmpty()) {
            try {
                map = datas.stream().collect(Collectors.toMap(ProvinceHighwayTrafficCondition::getSort, v -> v));
            } catch (Exception e) {
                throw new BusinessException(dateQuery.toString()+ResultEnum.ILLEGAL_DATE.getValue());
            }
            if (map.get(6) != null) {
                if (!StringUtils.isEmpty(map.get(6).getFinishedCondition()) && !map.get(6).getFinishedCondition().contains("%")) {
                    map.get(6).setFinishedCondition((new BigDecimal(map.get(6).getFinishedCondition()).multiply(new BigDecimal(100))).stripTrailingZeros().toPlainString() + "%");
                }
            }

            if (map.get(1) == null) {
                map.put(1, new ProvinceHighwayTrafficCondition(UuidUtil.get(), 1, "高速公路", "高速路网出口交通量", "合计", "万辆/日"));
            }
            if (map.get(2) == null) {
                map.put(2, new ProvinceHighwayTrafficCondition(UuidUtil.get(), 2, "高速公路", "高速路网出口交通量", "客车", "万辆/日"));
            }
            if (map.get(3) == null) {
                map.put(3, new ProvinceHighwayTrafficCondition(UuidUtil.get(), 3, "高速公路", "高速路网出口交通量", "货车", "万辆/日"));
            }
            if (map.get(4) == null) {
                map.put(4, new ProvinceHighwayTrafficCondition(UuidUtil.get(), 4, "高速公路", "高速路网出口交通量", "客货比", ""));
            }
            if (map.get(5) == null) {
                map.put(5, new ProvinceHighwayTrafficCondition(UuidUtil.get(), 5, "高速公路", "ETC交通量情况", "ETC交通量", "万辆/日"));
            }
            if (map.get(6) == null) {
                map.put(6, new ProvinceHighwayTrafficCondition(UuidUtil.get(), 6, "高速公路", "ETC交通量情况", "占比", ""));
            }
            if (map.get(7) == null) {
                map.put(7, new ProvinceHighwayTrafficCondition(UuidUtil.get(), 7, "普通公路", "交通流量（pcu/日）", "普通公路", "pcu/日"));
            }
            if (map.get(8) == null) {
                map.put(8, new ProvinceHighwayTrafficCondition(UuidUtil.get(), 8, "普通公路", "交通流量（pcu/日）", "普通国道", "pcu/日"));
            }
            if (map.get(9) == null) {
                map.put(9, new ProvinceHighwayTrafficCondition(UuidUtil.get(), 9, "普通公路", "交通流量（pcu/日）", "普通省道", "pcu/日"));
            }
            if (map.get(10) == null) {
                map.put(10, new ProvinceHighwayTrafficCondition(UuidUtil.get(), 10, "累计通行费收入", "", "", "亿元"));
            }
            if (map.get(11) == null) {
                map.put(11, new ProvinceHighwayTrafficCondition(UuidUtil.get(), 11, "累计通行费减免", "", "", "亿元"));
            }
            if (map.get(12) == null) {
                map.put(12, new ProvinceHighwayTrafficCondition(UuidUtil.get(), 12, "ETC用户量", "", "", "亿元"));
            }
            output = map.values().stream().collect(Collectors.toList());
            resultInsertMapper.insertProTraCdt(output);
        }
    }
}
