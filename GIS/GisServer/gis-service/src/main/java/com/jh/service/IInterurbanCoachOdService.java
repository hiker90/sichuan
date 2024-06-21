package com.jh.service;

import com.jh.domain.InterurbanCoachOd;

import java.util.List;

public interface IInterurbanCoachOdService
{
    /**
     * 获取指定日期范围内数据统计
     *
     * @param ocity 出发城市
     * @param start 开始日期
     * @param end 结束日期
     * @return 结果
     */
    List<InterurbanCoachOd> selectInterurbanCoachOdStatisticsList(String ocity, Integer start, Integer end);
}
