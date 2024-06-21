package com.zzsj.dm.manage.mapper.oracle;
import com.zzsj.dm.manage.pojo.query.WarnTypeQuery;
import com.zzsj.dm.manage.pojo.vo.AcessNumVo;
import com.zzsj.dm.manage.pojo.vo.DmWarningVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/2 10:26
 * @description：数据质量
 */

@Repository
public interface DataQualityMapper {
    /**
     * Description: 数据质量-整体接入量信息
     * @Date: 2020/9/7 10:39
     * @Author: zbya 
     * 
     * @param 
     * @return: com.zzsj.dm.manage.pojo.vo.AcessNumVo
     */ 
    AcessNumVo getTotalNum() throws Exception;
}
