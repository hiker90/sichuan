package com.jh.service.impl;

import com.jh.domain.InterurbanCoachOd;
import com.jh.mapper.InterurbanCoachOdMapper;
import com.jh.service.IInterurbanCoachOdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterurbanCoachOdServiceImpl implements IInterurbanCoachOdService
{
    @Autowired
    InterurbanCoachOdMapper interurbanCoachOdMapper;

    /**
     * 获取指定日期范围内数据统计
     *
     * @param ocity 出发城市
     * @param start 开始日期
     * @param end 结束日期
     * @return 结果
     */
    @Override
    public List<InterurbanCoachOd> selectInterurbanCoachOdStatisticsList(String ocity, Integer start, Integer end){
        return interurbanCoachOdMapper.selectInterurbanCoachOdStatisticsList(ocity, start, end);
    }
}
