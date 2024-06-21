package com.jh.mapper;

import com.jh.domain.InterurbanCoachOd;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InterurbanCoachOdMapper {
    List<InterurbanCoachOd> selectInterurbanCoachOdStatisticsList(@Param("ocity") String ocity,
                                                                  @Param("start")Integer start,
                                                                  @Param("end")Integer end);
}