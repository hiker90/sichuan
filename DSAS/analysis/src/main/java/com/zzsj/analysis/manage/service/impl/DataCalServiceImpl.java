package com.zzsj.analysis.manage.service.impl;

import com.zzsj.analysis.base.exception.BusinessException;
import com.zzsj.analysis.base.utils.enums.ResultEnum;
import com.zzsj.analysis.manage.mapper.calculate.DataCalMapper;
import com.zzsj.analysis.manage.mapper.mysql.ReportTabMapper;
import com.zzsj.analysis.manage.pojo.query.DataCountQuery;
import com.zzsj.analysis.manage.service.DataCalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：zbya
 * @date ：Created in 2020/12/24 11:27
 * @description：数据计算
 */
@Service
public class DataCalServiceImpl implements DataCalService{
    @Autowired
    DataCalMapper dataCalMapper;
    @Autowired
    ReportTabMapper reportTabMapper;

    @Override
    public int deleteByDate(DataCountQuery dataCountQuery) throws Exception{
        List<String> tabList=reportTabMapper.getTabList().stream().map(culRule ->culRule.getTableEnName()).collect(Collectors.toList());
        if(!tabList.isEmpty()){
            if(tabList.contains(dataCountQuery.getTabEnName())){
                return dataCalMapper.deleteByDate(dataCountQuery);
            }
            else {
                throw new BusinessException(ResultEnum.ILLEGAL_OPRATION.getValue());
            }
        }
        return 0;
    }
}
