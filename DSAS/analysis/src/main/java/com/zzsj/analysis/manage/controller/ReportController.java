package com.zzsj.analysis.manage.controller;

import com.zzsj.analysis.base.constants.OprLogConst;
import com.zzsj.analysis.base.exception.BusinessException;
import com.zzsj.analysis.base.json.AjaxJson;
import com.zzsj.analysis.base.pojo.query.PageQuery;
import com.zzsj.analysis.base.utils.enums.ResultEnum;
import com.zzsj.analysis.manage.entity.CulRule;
import com.zzsj.analysis.manage.pojo.query.DataQueryQuery;
import com.zzsj.analysis.manage.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2020/10/16 10:09
 * @description：报表管理
 */

@RestController
@Slf4j
@RequestMapping("report")
public class ReportController {
    @Autowired
    ReportService reportService;
    
    /**
     * Description: 获取结果表集合 
     * @Date: 2020/10/16 14:51 
     * @Author: zbya 
     * 
     * @param 
     * @return: com.zzsj.analysis.base.json.AjaxJson
     */ 
    @RequestMapping("all-table")
    @ResponseBody
    @RequiresPermissions("dsas:tables:getcatalog")
    @SysControllerLog(description = "报表管理")
    public AjaxJson getTabList() throws Exception{
        try {
            return new AjaxJson(reportService.getTabList());
        } catch (Exception e) {
            log.error("/report/all-table"+e);
            throw new BusinessException(ResultEnum.LIST_FAIL.getValue());
        }
    }

    /**
     * Description: 获取指定表列信息
     * @Date: 2020/10/23 16:10
     * @Author: zbya
     *
     * @param tableEnName
     * @return: com.zzsj.analysis.base.json.AjaxJson
     */
    @RequestMapping("get-col")
    @ResponseBody
    @RequiresPermissions("dsas:tables:getcols")
    public AjaxJson getColumn(String tableEnName) throws Exception{
        try {
            return new AjaxJson(reportService.getColumn(tableEnName));
        } catch (Exception e) {
            log.error("/report/get-col" + e);
            throw new BusinessException(ResultEnum.LIST_FAIL.getValue());
        }
    }

    /**
     * Description: 数据查询
     * @Date: 2020/10/16 14:56
     * @Author: zbya
     *
     * @param pageQuery
     * @return: com.zzsj.analysis.base.json.AjaxJson
     */
    @RequestMapping("get-data")
    @ResponseBody
    @RequiresPermissions("dsas:tables:getdata")
    @SysControllerLog(description = "报表管理-数据")
    public AjaxJson getData(@RequestBody PageQuery<DataQueryQuery> pageQuery) throws Exception {
        try {
            return new AjaxJson(reportService.getData(pageQuery));
        } catch (Exception e) {
            log.error("/report/get-data" + e);
            throw new BusinessException(ResultEnum.LIST_FAIL.getValue());
        }
    }

}
