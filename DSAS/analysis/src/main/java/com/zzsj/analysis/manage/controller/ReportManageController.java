package com.zzsj.analysis.manage.controller;

import com.sun.net.httpserver.Authenticator;
import com.zzsj.analysis.base.constants.OprLogConst;
import com.zzsj.analysis.base.exception.BusinessException;
import com.zzsj.analysis.base.json.AjaxJson;
import com.zzsj.analysis.base.pojo.query.PageQuery;
import com.zzsj.analysis.base.utils.enums.ResultEnum;
import com.zzsj.analysis.manage.entity.CulRule;
import com.zzsj.analysis.manage.pojo.query.ReportQuery;
import com.zzsj.analysis.manage.pojo.vo.ReportModelVo;
import com.zzsj.analysis.manage.pojo.vo.ReportTabVo;
import com.zzsj.analysis.manage.service.EconomicOperAnalService;
import com.zzsj.analysis.manage.service.ReportManageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：zbya
 * @date ：Created in 2020/10/16 15:27
 * @description：报告管理
 */

@Slf4j
@RequestMapping("report-manage")
@RestController
public class ReportManageController {
    @Autowired
    ReportManageService reportService;
    @Autowired
    EconomicOperAnalService economicOperAnalService;
    /**
     * Description: 模版查询
     *
     * @param pageQuery
     * @Date: 2020/10/17 15:37
     * @Author: zbya
     * @return: com.zzsj.analysis.base.json.AjaxJson
     */
    @RequestMapping("get-model")
    @ResponseBody
    @SysControllerLog(description = "模版管理")
    @RequiresPermissions("dsas:models:getlist")
    public AjaxJson getModel(@RequestBody PageQuery<ReportQuery> pageQuery) throws Exception {
        try {
            return new AjaxJson(reportService.getModel(pageQuery));
        } catch (Exception e) {
            log.error("/report-manage/get-model" + e);
            throw new BusinessException(ResultEnum.LIST_FAIL.getValue());
        }
    }

    /**
     * Description: 模版生成报告
     *
     * @param
     * @Date: 2020/10/18 15:32
     * @Author: zbya
     * @return: com.zzsj.analysis.base.json.AjaxJson
     */
    @RequestMapping("new-report")
    @ResponseBody
    @RequiresPermissions("dsas:tables:newreport")
    @SysControllerLog(description = "生成报告",method = OprLogConst.CAL)
    public AjaxJson newReport(@RequestBody ReportModelVo reportModelVo) throws Exception{
        try {
            switch (reportModelVo.getId()){
                case "0053f57125bf4c98ac340d362e1c3709":
                    economicOperAnalService.newReport(reportModelVo);
                    return new AjaxJson();
            }
           return new AjaxJson();
        } catch (Exception e) {
            log.error("/report/new-report" + e);
            throw new BusinessException(ResultEnum.UNKONW_ERROR.getValue());
        }
    }


    /**
     * Description: 报告管理
     *
     * @param pageQuery
     * @Date: 2020/10/17 15:37
     * @Author: zbya
     * @return: com.zzsj.analysis.base.json.AjaxJson
     */
    @RequestMapping("get-report")
    @ResponseBody
    @SysControllerLog(description = "报告管理")
    @RequiresPermissions("dsas:reports:getlist")
    public AjaxJson getReport(@RequestBody PageQuery<ReportQuery> pageQuery) throws Exception {
        try {
            return new AjaxJson(reportService.getReport(pageQuery));
        } catch (Exception e) {
            log.error("/report-manage/get-report" + e);
            throw new BusinessException(ResultEnum.LIST_FAIL.getValue());
        }
    }

    /**
     * Description: 报告删除
     *
     * @param id
     * @Date: 2020/10/17 15:38
     * @Author: zbya
     * @return: com.zzsj.analysis.base.json.AjaxJson
     */
    @RequestMapping("delete-reportdata")
    @ResponseBody
    @SysControllerLog(description = "报告", method = OprLogConst.DELETE)
    @RequiresPermissions("dsas:reports:delreport")
    public AjaxJson deleteReportData(String id) throws Exception {
        try {
            reportService.deleteData(id);
            return new AjaxJson(ResultEnum.DELETE_SUCCESS.getValue());
        } catch (Exception e) {
            log.error("/report-manage/delete-reportdata" + e);
            throw new BusinessException(ResultEnum.DELETE_FAIL.getValue());
        }
    }

    /**
     * Description: 报告审核
     *
     * @param reportModelVo
     * @Date: 2020/10/18 15:32
     * @Author: zbya
     * @return: com.zzsj.analysis.base.json.AjaxJson
     */
    @RequestMapping("check-data")
    @ResponseBody
    @SysControllerLog(description = "报告审核", method = OprLogConst.CHECK)
    @RequiresPermissions("dsas:models:checkreport")
    public AjaxJson checkData(@RequestBody ReportTabVo reportModelVo) throws Exception {
        try {

            return new AjaxJson(reportService.checkData(reportModelVo));
        } catch (Exception e) {
            log.error("/report-manage/check-data" + e);
            throw new BusinessException(ResultEnum.CHECK_FAIL.getValue());
        }
    }

    /**
     * Description: 归档
     *
     * @param reportModelVo
     * @Date: 2020/10/18 15:32
     * @Author: zbya
     * @return: com.zzsj.analysis.base.json.AjaxJson
     */
    @RequestMapping("archive-data")
    @ResponseBody
    @SysControllerLog(description = "报告归档", method = OprLogConst.CHECK)
    @RequiresPermissions("dsas:models:archivereport")
    public AjaxJson archiveReportData(@RequestBody ReportTabVo reportModelVo) throws Exception {
        try {
            return new AjaxJson(reportService.checkData(reportModelVo));
        } catch (Exception e) {
            log.error("/report-manage/archive-data" + e);
            throw new BusinessException(ResultEnum.CHECK_FAIL.getValue());
        }
    }





    /**
     * Description: 查询报告
     *
     * @param id
     * @Date: 2020/10/18 15:36
     * @Author: zbya
     * @return: com.zzsj.analysis.base.json.AjaxJson
     */
    @RequestMapping("get-reportdata")
    @ResponseBody
    @SysControllerLog(description = "报告")
    @RequiresPermissions("dsas:reports:getdetail")
    public AjaxJson getReportData(String id) throws Exception {
        try {
            return new AjaxJson(ResultEnum.SUCCESS.getValue(),reportService.getData(id));
        } catch (Exception e) {
            log.error("/report-manage/get-reportdata" + e);
            throw new BusinessException(ResultEnum.LIST_FAIL.getValue());
        }
    }

}
