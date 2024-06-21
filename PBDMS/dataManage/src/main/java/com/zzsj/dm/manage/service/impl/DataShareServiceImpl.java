package com.zzsj.dm.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzsj.dm.base.pojo.query.PageQuery;
import com.zzsj.dm.manage.mapper.mysql.DataShareMapper;
import com.zzsj.dm.manage.pojo.query.DataApplyQuery;
import com.zzsj.dm.manage.pojo.vo.DataShareApplyVo;
import com.zzsj.dm.manage.service.DataShareService;
import com.zzsj.dm.manage.utils.UserUtil;
import com.zzsj.dm.manage.utils.UuidUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author ：zbya
 * @date ：Created in 2020/10/15 14:05
 * @description：数据共享
 */

@Service
public class DataShareServiceImpl implements DataShareService {
    @Autowired
    DataShareMapper dataShareMapper;

    /**
     * Description: 共享交换-申请
     * @Date: 2020/10/15 15:53
     * @Author: zbya
     *
     * @param dataShareApplyVo
     * @return: java.lang.String
     */
    @Override
    public void dataApply(DataShareApplyVo dataShareApplyVo) throws Exception {
        dataShareApplyVo.setId(UuidUtil.get());
        Claims userInfo= UserUtil.userInfo();
        dataShareApplyVo.setApplyUnit(userInfo.get("userName").toString());
        dataShareApplyVo.setApplyUser(userInfo.get("dept").toString());
        dataShareMapper.dataApply(dataShareApplyVo);
    }

    /**
     * Description: 共享交换-申请详情
     * @Date: 2020/10/15 15:53
     * @Author: zbya
     *
     * @param pageQuery
     * @return: com.github.pagehelper.PageInfo
     */
    @Override
    public PageInfo applyList(PageQuery<DataApplyQuery> pageQuery) throws Exception{
        if(!StringUtils.isEmpty(pageQuery.getQueryParams().getBeginDate())) {
            pageQuery.getQueryParams().setBeginDate(pageQuery.getQueryParams().getBeginDate() + " 00:00:00");
            pageQuery.getQueryParams().setEndDate(pageQuery.getQueryParams().getEndDate() + " 23:59:59");
        }
        return new PageInfo(PageHelper.startPage(pageQuery.getPageIndex(),pageQuery.getPageSize()).doSelectPage(()->dataShareMapper.applyList(pageQuery.getQueryParams())).getResult());
    }

    /**
     * Description:  共享交换-审核
     * @Date: 2020/10/15 15:53
     * @Author: zbya
     *
     * @param dataShareApplyVo
     * @return: java.lang.String
     */
    @Override
    public void applyCheck(DataShareApplyVo dataShareApplyVo) throws Exception {
        dataShareApplyVo.setCheckDate(new Date());
        Claims userInfo= UserUtil.userInfo();
        dataShareApplyVo.setCheckUser(userInfo.get("userName").toString());
        dataShareMapper.applyCheck(dataShareApplyVo);
    }
}
