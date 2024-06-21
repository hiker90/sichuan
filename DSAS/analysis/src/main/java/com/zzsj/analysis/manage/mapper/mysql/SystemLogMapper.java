package com.zzsj.analysis.manage.mapper.mysql;

import com.zzsj.analysis.manage.pojo.query.SysLogQuery;
import com.zzsj.analysis.manage.pojo.vo.SysLogVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/15 15:24
 * @description：系统日志
 */

@Repository
public interface SystemLogMapper {
    /**
     * Description: 插入操作日志
     * @Date: 2020/9/15 15:36
     * @Author: zbya
     *
     * @param sysLogVo
     * @return: int
     */
    int insertSysLog(SysLogVo sysLogVo);

    /**
     * Description: 日志查询
     * @Date: 2020/9/15 15:45
     * @Author: zbya
     *
     * @param sysLogQuery
     * @return: List<SysLogVo>
     */
    List<SysLogVo> getSysLogList(SysLogQuery sysLogQuery);
}
