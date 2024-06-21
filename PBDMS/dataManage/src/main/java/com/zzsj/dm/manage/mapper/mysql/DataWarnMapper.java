package com.zzsj.dm.manage.mapper.mysql;

import com.zzsj.dm.manage.pojo.query.WarnTypeQuery;
import com.zzsj.dm.manage.pojo.vo.DmWarningVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/4 21:23
 * @description：数据质量预警
 */

@Repository
public interface DataWarnMapper {
    /**
     * Description: 数据质量-预警情况
     * @Date: 2020/9/7 15:17
     * @Author: zbya
     *
     * @param warnTypeQuery
     * @return: java.util.List<com.zzsj.dm.manage.pojo.vo.DmWarningVo>
     */
    List<DmWarningVo> getWarningStates(WarnTypeQuery warnTypeQuery);

    /**
     * Description: 数据质量-预警情况处理
     * @Date: 2020/9/7 15:18
     * @Author: zbya
     *
     * @param name
     * @return: int
     */
    int dealWarning(String name);
    
    /**
     * Description: 数据质量-已处理
     * @Date: 2020/9/25 8:52
     * @Author: zbya 
     * 
     * @param queryParams
     * @return: java.util.List<com.zzsj.dm.manage.pojo.vo.DmWarningVo>
     */ 
    List<DmWarningVo> getprocessedWarning(WarnTypeQuery queryParams);

    /**
     * Description: 数据质量-历史记录
     * @Date: 2020/11/17 11:30
     * @Author: zbya
     *
     * @param queryParams
     * @return: java.util.List<com.zzsj.dm.manage.pojo.vo.DmWarningVo>
     */
    List<DmWarningVo> getWarningHistory(WarnTypeQuery queryParams);
}
