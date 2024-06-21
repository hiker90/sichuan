package com.zzsj.analysis.manage.service.impl;

import com.zzsj.analysis.base.exception.BusinessException;
import com.zzsj.analysis.base.utils.StringUtils;
import com.zzsj.analysis.base.utils.enums.ResultEnum;
import com.zzsj.analysis.manage.entity.CulRule;
import com.zzsj.analysis.manage.entity.ProvinceMainEcnomicIndicators;
import com.zzsj.analysis.manage.mapper.calculate.ProMainEcnomicMapper;
import com.zzsj.analysis.manage.mapper.mysql.ResultInsertMapper;
import com.zzsj.analysis.manage.pojo.query.DateQuery;
import com.zzsj.analysis.manage.service.CLProMainEcnomicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ：zbya
 * @date ：Created in 2020/12/29 16:29
 * @description：全省主要经济指标
 */

@Service
public class CLProMainEcnomicServiceImpl implements CLProMainEcnomicService{
    @Autowired
    ProMainEcnomicMapper proMainEcnomicMapper;

    @Autowired
    ResultInsertMapper resultInsertMapper;

    @Override
    public void provinceMainEcnomic(CulRule culRule) throws Exception {
        List<ProvinceMainEcnomicIndicators> output;
        //本月统计值
        DateQuery dateQuery = new DateQuery(culRule.getYear(), culRule.getMonth());
        //本月值
        List<ProvinceMainEcnomicIndicators> currMonth=proMainEcnomicMapper.getBydate(dateQuery);

        Map<Integer, ProvinceMainEcnomicIndicators> map;
        if(currMonth!=null){
            try {
            map=currMonth.stream().collect(Collectors.toMap(ProvinceMainEcnomicIndicators::getSort,v->v));
        } catch (Exception e) {
            throw  new BusinessException(dateQuery.toString()+ResultEnum.ILLEGAL_DATE.getValue());
        }
        }
        else{
            map=new HashMap<>();
        }
        if(map.get(1)==null){
            map.put(1,new ProvinceMainEcnomicIndicators(1,"地区生产总值（GDP)"));
        }
        if(map.get(2)==null){
            map.put(2,new ProvinceMainEcnomicIndicators(2,"规模以上工业增加值"));
        }
        if(map.get(3)==null){
            map.put(3,new ProvinceMainEcnomicIndicators(3,"全社会固定资产投资"));
        }
        if(map.get(4)==null){
            map.put(4,new ProvinceMainEcnomicIndicators(4,"社会消费品零售总额"));
        }
        if(map.get(5)==null){
            map.put(5,new ProvinceMainEcnomicIndicators(5,"进出口总额"));
        }
        if(map.get(6)==null){
            map.put(6,new ProvinceMainEcnomicIndicators(6,"居民消费价格"));
        }
        if(map.get(7)==null){
            map.put(7,new ProvinceMainEcnomicIndicators(7,"工业生产者出厂价格"));
        }
        if(map.get(8)==null){
            map.put(8,new ProvinceMainEcnomicIndicators(8,"工业生产者购进价格"));
        }

        output=map.values().stream().collect(Collectors.toList());
        output.stream().forEach(data->{
            if(!StringUtils.isEmpty(data.getCurrMonthYoy())&&!data.getCurrMonthYoy().contains("%")){
                data.setCurrMonthYoy((new BigDecimal(data.getCurrMonthYoy()).multiply(new BigDecimal(100))).stripTrailingZeros().toPlainString()+"%");
            }
            if(!StringUtils.isEmpty(data.getTotalIncrease())&&!data.getTotalIncrease().contains("%")){
                data.setTotalIncrease((new BigDecimal(data.getTotalIncrease()).multiply(new BigDecimal(100))).stripTrailingZeros().toPlainString()+"%");
            }
        });
        resultInsertMapper.insertProMainEcnomic(output);
    }
}
