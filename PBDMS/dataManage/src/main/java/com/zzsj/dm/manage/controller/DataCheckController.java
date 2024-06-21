package com.zzsj.dm.manage.controller;

import com.zzsj.dm.base.constants.OprLogConst;
import com.zzsj.dm.base.exception.BusinessException;
import com.zzsj.dm.base.json.AjaxJson;
import com.zzsj.dm.base.pojo.query.PageQuery;
import com.zzsj.dm.base.utils.enums.ResultEnum;
import com.zzsj.dm.manage.pojo.query.CheckQuery;
import com.zzsj.dm.manage.pojo.query.StatusQuery;
import com.zzsj.dm.manage.pojo.vo.DmCheckVo;
import com.zzsj.dm.manage.service.DataCheckService;
import com.zzsj.dm.manage.service.TabInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/4 9:37
 * @description：数据表预警相关信息
 */
@Slf4j
@RestController
@RequestMapping("/data-check")
public class DataCheckController {
    @Autowired
    private DataCheckService dataCheckService;
    @Autowired
    private TabInfoService tabInfoService;

    /**
     * Description: 获取所有表信息
     * @Date: 2020/9/7 17:20
     * @Author: zbya
     *
     * @param
     * @return: com.zzsj.dm.base.json.AjaxJson
     */
    @RequestMapping("all-table")
    @ResponseBody
    @RequiresPermissions("pbdms:dataconfig:gettablelist")
    public AjaxJson getTabList() throws Exception{
        try {
            return new AjaxJson(tabInfoService.getTabList());
        } catch (Exception e) {
            log.error("/data-check/all-table"+e);
            throw new BusinessException(ResultEnum.LIST_FAIL.getValue());
        }
    }

    /**
     * Description: 获取所有列信息
     * @Date: 2020/9/7 17:20
     * @Author: zbya
     *
     * @param tabName
     * @return: com.zzsj.dm.base.json.AjaxJson
     */
    @RequestMapping("col-list")
    @ResponseBody
    @RequiresPermissions("pbdms:dataconfig:gettablecols")
    public AjaxJson getColList(String tabName) throws Exception{
        try {
            return new AjaxJson(tabInfoService.getColList(tabName));
        } catch (Exception e) {
            log.error("/data-check/col-list"+e);
            throw new BusinessException(ResultEnum.LIST_FAIL.getValue());
        }
    }

    /**
     * description: 表对标规则展示
     * date: 2020/9/4 9:55
     * author: zbya
     *
     * @param pageQuery
     * @return com.zzsj.dm.base.json.AjaxJson
     */
    @RequestMapping("table-list")
    @ResponseBody
    @SysControllerLog(description = "表预警规则")
    @RequiresPermissions("pbdms:dataconfig:getrules")
    public AjaxJson getCheckList(@RequestBody PageQuery<CheckQuery> pageQuery) throws Exception{
        try {
            return new AjaxJson(dataCheckService.getCheckList(pageQuery));
        } catch (Exception e) {
            log.error("/data-check/table-list"+e);
            throw new BusinessException(ResultEnum.LIST_FAIL.getValue());
        }
    }
    
    /**
     * Description: 插入表预警信息 
     * @Date: 2020/9/7 18:14 
     * @Author: zbya 
     * 
     * @param dmCheckVo
     * @return: com.zzsj.dm.base.json.AjaxJson
     */ 
    @RequestMapping("insert-data")
    @ResponseBody
    @SysControllerLog(description = "表预警规则",method= OprLogConst.ADD)
    @RequiresPermissions("pbdms:dataconfig:addrules")
    public AjaxJson insertData(@RequestBody @Validated DmCheckVo dmCheckVo,BindingResult bindingResult) throws Exception {

        try {
            if (bindingResult.getErrorCount() != 0) {
                return new AjaxJson(false, bindingResult.getAllErrors().get(0).toString());
            }

            return new AjaxJson(ResultEnum.SAVE_SUCCESS.getValue(),dataCheckService.insertData(dmCheckVo));
        } catch (Exception e) {
            log.error("/data-check/insert-data"+e);
            throw new BusinessException(ResultEnum.SAVE_FAIL.getValue());
        }
    }
    
    /**
     * Description: 删除表预警信息，字段信息 
     * @Date: 2020/9/7 22:59 
     * @Author: zbya 
     * 
     * @param id
     * @return: com.zzsj.dm.base.json.AjaxJson
     */ 
    @RequestMapping("delete-data")
    @ResponseBody
    @SysControllerLog(description = "表预警规则",method= OprLogConst.DELETE)
    @RequiresPermissions("pbdms:dataconfig:delrules")
    public AjaxJson deleteData(String id) throws Exception{
        try {
            return new AjaxJson(ResultEnum.DELETE_SUCCESS.getValue(),dataCheckService.deleteData(id));
        } catch (Exception e) {
            log.error("/data-check/delete-data"+e);
            throw new BusinessException(ResultEnum.DELETE_FAIL.getValue());
        }
    }
    
    /**
     * Description: 更新表预警信息，字段信息  
     * @Date: 2020/9/7 23:00 
     * @Author: zbya 
     * 
     * @param 
     * @return: com.zzsj.dm.base.json.AjaxJson
     */ 
    @RequestMapping("update-data")
    @ResponseBody
    @SysControllerLog(description = "表预警规则",method= OprLogConst.UPDATE)
    @RequiresPermissions("pbdms:dataconfig:updaterules")
    public AjaxJson updateData(@RequestBody @Validated DmCheckVo dmCheckVo,BindingResult bindingResult) throws Exception{
        try {
            if (bindingResult.getErrorCount() != 0) {
                return new AjaxJson(false, bindingResult.getAllErrors().get(0).toString());
            }
            dataCheckService.updateData(dmCheckVo);
            return new AjaxJson(ResultEnum.SAVE_SUCCESS.getValue());
        } catch (Exception e) {
            log.error("/data-check/update-data"+e);
            throw new BusinessException(ResultEnum.SAVE_FAIL.getValue());
        }
    }
    
    /**
     * Description: 根据表ID查询列集合信息 
     * @Date: 2020/9/7 23:01 
     * @Author: zbya 
     * 
     * @param id
     * @return: com.zzsj.dm.base.json.AjaxJson
     */
    @RequestMapping("check-col")
    @ResponseBody
    @RequiresPermissions("pbdms:dataconfig:getruledetail")
    public AjaxJson getCheckColList(String id) throws Exception{
        try {
            return new AjaxJson(dataCheckService.getColList(id));
        } catch (Exception e) {
            log.error("/data-check/check-col"+e);
            throw new BusinessException(ResultEnum.LIST_FAIL.getValue());
        }
    }

    /**
     * Description: 修改预警状态
     * @Date: 2020/9/8 16:47
     * @Author: zbya
     *
     * @param statusQuery
     * @return: com.zzsj.dm.base.json.AjaxJson
     */
    @RequestMapping("change-status")
    @ResponseBody
    @SysControllerLog(description = "表预警规则-状态",method= OprLogConst.UPDATE)
    @RequiresPermissions("pbdms:dataconfig:handlerules")
    public AjaxJson changStatus(@RequestBody StatusQuery statusQuery) throws Exception{
        try {
            return new AjaxJson(ResultEnum.SAVE_SUCCESS.getValue(),dataCheckService.changeStatus(statusQuery));
        } catch (Exception e) {
            log.error("/data-check/change-status"+e);
            throw new BusinessException(ResultEnum.SAVE_FAIL.getValue());
        }
    }
}
