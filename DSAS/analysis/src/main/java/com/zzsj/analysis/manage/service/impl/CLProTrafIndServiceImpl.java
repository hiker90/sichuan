package com.zzsj.analysis.manage.service.impl;

import com.zzsj.analysis.base.exception.BusinessException;
import com.zzsj.analysis.base.utils.enums.ResultEnum;
import com.zzsj.analysis.manage.entity.CulRule;
import com.zzsj.analysis.manage.entity.ProvinceTrafficIncident;
import com.zzsj.analysis.manage.mapper.calculate.ProTrafficIncidentMapper;
import com.zzsj.analysis.manage.mapper.mysql.ResultInsertMapper;
import com.zzsj.analysis.manage.pojo.query.DateQuery;
import com.zzsj.analysis.manage.service.CLProTrafIndService;
import com.zzsj.analysis.manage.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2020/12/18 15:01
 * @description：全省公路水路行业安全生产事故
 */

@Service
public class CLProTrafIndServiceImpl implements CLProTrafIndService{
    @Autowired
    ProTrafficIncidentMapper proTrafficIncidentMapper;
    @Autowired
    ResultInsertMapper resultInsertMapper;
    /**
     * Description: 全省公路水路行业安全生产事故表
     * @Date: 2020/12/17 13:49
     * @Author: zbya
     *
     * @param culRule
     * @return: void
     */

    @Override
    public void provinceTrafficIncident(CulRule culRule) throws Exception{

        List<ProvinceTrafficIncident> output=new ArrayList<>();
        //本月
        DateQuery dateQuery=new DateQuery(culRule.getYear(),culRule.getMonth());
        if(proTrafficIncidentMapper.getCountNum(dateQuery)>1){
            throw new BusinessException(dateQuery.toString()+ResultEnum.ILLEGAL_DATE.getValue());
        }
        ProvinceTrafficIncident currMonth=proTrafficIncidentMapper.getByDate(dateQuery);
        if(currMonth==null){
            currMonth=new ProvinceTrafficIncident();
        }
        currMonth.setSort(3);
        currMonth.setType("本月");
        output.add(currMonth);
        //上一年
        dateQuery=new DateQuery(culRule.getYear()-1);
        if(proTrafficIncidentMapper.getCountNum(dateQuery)>1){
            throw new BusinessException(dateQuery.toString()+ResultEnum.ILLEGAL_DATE.getValue());
        }
        ProvinceTrafficIncident lastYear=proTrafficIncidentMapper.getByDate(dateQuery);
        if(lastYear==null){
            lastYear=new ProvinceTrafficIncident();
        }
        lastYear.setSort(1);
        lastYear.setType("上一年");
        output.add(lastYear);
        //年初累计
        dateQuery=new DateQuery(culRule.getYear());
        if(proTrafficIncidentMapper.getCountNum(dateQuery)>1){
            throw new BusinessException(dateQuery.toString()+ResultEnum.ILLEGAL_DATE.getValue());
        }
        ProvinceTrafficIncident currYear=proTrafficIncidentMapper.getByDate(dateQuery);
        if(currYear==null){
            currYear=new ProvinceTrafficIncident();
        }
        currYear.setSort(4);
        currYear.setType("年初累计");
        output.add(currYear);
        //上一月
        ProvinceTrafficIncident lastMonth;
        if(culRule.getMonth()!=1){
            dateQuery=new DateQuery(culRule.getYear(),culRule.getMonth()-1);
        }
        else{
            dateQuery=new DateQuery(culRule.getYear()-1,12);

        }
        if(proTrafficIncidentMapper.getCountNum(dateQuery)>1){
            throw new BusinessException(dateQuery.toString()+ResultEnum.ILLEGAL_DATE.getValue());
        }
        lastMonth=proTrafficIncidentMapper.getByDate(dateQuery);
        if(lastMonth==null){
            lastMonth=new ProvinceTrafficIncident();
        }
        lastMonth.setSort(2);
        lastMonth.setType("上一月");
        output.add(lastMonth);
        output.stream().forEach(data->{
            data.setId(UuidUtil.get());
            data.setMonth(culRule.getMonth());
            data.setYear(culRule.getYear());
        });

        resultInsertMapper.insertProTraIncd(output);

    }
}
