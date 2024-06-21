package com.zzsj.dm.manage.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.zzsj.dm.base.pojo.query.PageQuery;
import com.zzsj.dm.manage.pojo.query.WarnTypeQuery;
import com.zzsj.dm.manage.pojo.vo.AcessNumVo;
import com.zzsj.dm.manage.pojo.vo.DmWarningVo;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/2 9:22
 * @description：数据质量
 */
public interface DataQualityService {
    /**
     * Description: 数据质量-整体接入量信息
     * @Date: 2020/9/7 15:02
     * @Author: zbya 
     * 
     * @param 
     * @return: com.zzsj.dm.manage.pojo.vo.AcessNumVo
     */ 
    AcessNumVo getTotalNum() throws Exception;

}
