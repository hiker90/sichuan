package com.zzsj.dm.manage.controller;

import com.zzsj.dm.base.constants.OprLogConst;
import com.zzsj.dm.base.exception.BusinessException;
import com.zzsj.dm.base.json.AjaxJson;
import com.zzsj.dm.base.pojo.query.PageQuery;
import com.zzsj.dm.base.pojo.vo.BaseVo;
import com.zzsj.dm.base.utils.enums.ResultEnum;
import com.zzsj.dm.manage.pojo.query.StatusQuery;
import com.zzsj.dm.manage.pojo.vo.TabListVo;
import com.zzsj.dm.manage.service.DataImportService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/8 21:51
 * @description：数据导入
 */

@Slf4j
@RequestMapping("/data-import")
@RestController
public class DataImportController {
    @Autowired
    DataImportService dataImportService;

    
    /**
     * Description: 导入文件格式EXCEL
     * @Date: 2020/9/10 10:38
     * @Author: zbya
     *
     * @param file
     * @param tabListVo
     * @return: com.zzsj.dm.base.json.AjaxJson
     */
    @RequestMapping("/excel")
    @ResponseBody
    public AjaxJson impData(MultipartFile file,TabListVo tabListVo) throws Exception{
        try {
            return new AjaxJson(ResultEnum.IMPORT_SUCCESS.getValue(),dataImportService.importData(file,tabListVo));
        } catch (Exception e) {
            log.error("/data-import/excel"+e);
            throw new BusinessException(ResultEnum.IMPORT_FIAL.getValue());
        }
    }
    
    /**
     * Description: 导入处理后JSON格式数据
     * @Date: 2020/9/10 18:24 
     * @Author: zbya 
     * 
     * @param tabListVo
     * @return: com.zzsj.dm.base.json.AjaxJson
     */ 
    @RequestMapping("/json-excel")
    @ResponseBody
    @SysControllerLog(description = "数据导入",method= OprLogConst.OHTER)
    @RequiresPermissions("pbdms:import:importdata")
    public AjaxJson impJsonExcel(@RequestBody TabListVo tabListVo) throws Exception{
        try {
            dataImportService.impJsonExcel(tabListVo);
            return new AjaxJson(ResultEnum.IMPORT_SUCCESS.getValue());
        } catch (Exception e) {
            log.error("/data-import/json-excel"+e);
            throw new BusinessException(ResultEnum.IMPORT_FIAL.getValue());
        }
    }

    /**
     * Description: 查询导入表日志
     *
     * @param pageQuery
     * @Date: 2020/9/10 17:54
     * @Author: zbya
     * @return: com.zzsj.dm.base.json.AjaxJson
     */
    @RequestMapping("imp-log")
    @ResponseBody
    @SysControllerLog(description = "数据导入-历史导入任务")
    @RequiresPermissions("pbdms:import:getimportlogs")
    public AjaxJson getImpLog(@RequestBody PageQuery<StatusQuery> pageQuery) throws Exception {
        try {
            return new AjaxJson(dataImportService.getImpLog(pageQuery));
        } catch (Exception e) {
            log.error("/tab-oprate/imp-log" + e);
            throw new BusinessException(ResultEnum.LIST_FAIL.getValue());
        }
    }

    /**
     * Description: 修改处理状态
     * @Date: 2020/9/8 16:47
     * @Author: zbya
     *
     * @param statusQuery
     * @return: com.zzsj.dm.base.json.AjaxJson
     */
    @RequestMapping("change-status")
    @ResponseBody
    @SysControllerLog(description = "数据导入-历史导入任务-状态",method= OprLogConst.UPDATE)
    @RequiresPermissions("pbdms:import:handlelog")
    public AjaxJson changStatus(@RequestBody StatusQuery statusQuery) throws Exception{
        try {
            return new AjaxJson(ResultEnum.SAVE_SUCCESS.getValue(),dataImportService.changeStatus(statusQuery));
        } catch (Exception e) {
            log.error("/tab-oprate/change-status"+e);
            throw new BusinessException(ResultEnum.SAVE_FAIL.getValue());
        }
    }

    /**
     * Description: 根据ID获取错误列信息
     * @Date: 2020/9/11 10:21
     * @Author: zbya
     *
     * @param id
     * @return: com.zzsj.dm.base.json.AjaxJson
     */
    @RequestMapping("wrong-mes")
    @ResponseBody
    @RequiresPermissions("pbdms:import:geterrorlog")
    public AjaxJson getWrongMes(String id) throws Exception {
        try {
            return new AjaxJson(dataImportService.getWrongMes(id));
        } catch (Exception e) {
            log.error("/tab-oprate/wrong-mes" + e);
            throw new BusinessException(ResultEnum.LIST_FAIL.getValue());
        }
    }
}
