package com.zzsj.analysis.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzsj.analysis.base.pojo.query.PageQuery;
import com.zzsj.analysis.base.utils.enums.ResultEnum;
import com.zzsj.analysis.manage.mapper.calculate.QueryDataMapper;
import com.zzsj.analysis.manage.mapper.mysql.ReportManageMapper;
import com.zzsj.analysis.manage.pojo.query.DateQuery;
import com.zzsj.analysis.manage.pojo.query.ReportQuery;
import com.zzsj.analysis.manage.pojo.vo.ReportModelVo;
import com.zzsj.analysis.manage.pojo.vo.ReportTabVo;
import com.zzsj.analysis.manage.service.ReportManageService;
import com.zzsj.analysis.manage.utils.NumConvertUtil;
import com.zzsj.analysis.manage.utils.UserUtil;
import com.zzsj.analysis.manage.utils.UuidUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：zbya
 * @date ：Created in 2020/10/16 18:08
 * @description：报告管理
 */
@Service
public class ReportManageServiceImpl implements ReportManageService {
    @Autowired
    ReportManageMapper reportManageMapper;


    /**
     * Description: 模版查询
     *
     * @param pageQuery
     * @Date: 2020/10/17 16:14
     * @Author: zbya
     * @return: com.github.pagehelper.PageInfo
     */
    @Override
    public PageInfo getModel(PageQuery<ReportQuery> pageQuery) {
        if (!StringUtils.isEmpty(pageQuery.getQueryParams().getBeginDate())) {
            pageQuery.getQueryParams().setBeginDate(pageQuery.getQueryParams().getBeginDate() + " 00:00:00");
            pageQuery.getQueryParams().setEndDate(pageQuery.getQueryParams().getEndDate() + " 23:59:59");
        }
        return new PageInfo(PageHelper.startPage(pageQuery.getPageIndex(), pageQuery.getPageSize()).doSelectPage(() -> reportManageMapper.getModel(pageQuery.getQueryParams())).getResult());
    }



    /**
     * Description: 报告查询
     *
     * @param pageQuery
     * @Date: 2020/10/18 15:55
     * @Author: zbya
     * @return: com.github.pagehelper.PageInfo
     */
    @Override
    public PageInfo getReport(PageQuery<ReportQuery> pageQuery) {
        if (!StringUtils.isEmpty(pageQuery.getQueryParams().getBeginDate())) {
            pageQuery.getQueryParams().setBeginDate(pageQuery.getQueryParams().getBeginDate() + " 00:00:00");
            pageQuery.getQueryParams().setEndDate(pageQuery.getQueryParams().getEndDate() + " 23:59:59");
        }
        return new PageInfo(PageHelper.startPage(pageQuery.getPageIndex(), pageQuery.getPageSize()).doSelectPage(() -> reportManageMapper.getReport(pageQuery.getQueryParams())).getResult());
    }


    /**
     * Description: 模版删除
     *
     * @param id
     * @Date: 2020/10/17 16:14
     * @Author: zbya
     * @return: void
     */
    @Override
    public void deleteData(String id) {
        reportManageMapper.deleteData(id);
    }


    /**
     * Description: 模版送审审核
     *
     * @param reportModelVo
     * @Date: 2020/10/18 15:42
     * @Author: zbya
     * @return: void
     */
    @Override
    public String checkData(ReportTabVo reportModelVo) {

        Claims userClaims = UserUtil.userInfo();
        reportModelVo.setCheckDate(new Date());
        reportModelVo.setCheckUser(userClaims.get("userName").toString());
        reportModelVo.setCheckUnit(userClaims.get("dept").toString());
        reportManageMapper.CheckData(reportModelVo);
        return ResultEnum.CHECK_SUCCESS.getValue();
    }

    /**
     * Description: 查询报告
     *
     * @param id
     * @Date: 2020/10/18 15:51
     * @Author: zbya
     * @return: java.lang.String
     */
    @Override
    public String getData(String id) {
        return reportManageMapper.getData(id);
    }
}
