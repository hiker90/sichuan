package com.zzsj.dm.manage.controller;

import com.zzsj.dm.base.constants.OprLogConst;
import com.zzsj.dm.base.exception.BusinessException;
import com.zzsj.dm.base.json.AjaxJson;
import com.zzsj.dm.base.pojo.query.PageQuery;
import com.zzsj.dm.base.utils.enums.ResultEnum;
import com.zzsj.dm.manage.pojo.query.DataApplyQuery;
import com.zzsj.dm.manage.pojo.vo.DataShareApplyVo;
import com.zzsj.dm.manage.service.DataShareService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：zbya
 * @date ：Created in 2020/10/15 14:00
 * @description：数据共享管理
 */


@RestController
@Slf4j
@RequestMapping("data-share")
public class DataShareController {
    @Autowired
    DataShareService dataShareService;
    
    /**
     * Description: 共享交换-申请 
     * @Date: 2020/10/15 15:52 
     * @Author: zbya 
     * 
     * @param dataShareApplyVo
     * @return: com.zzsj.dm.base.json.AjaxJson
     */ 
    @RequestMapping("/data-apply")
    @ResponseBody
    @SysControllerLog(description = "共享交换-申请",method= OprLogConst.ADD)
    @RequiresPermissions("pbdms:share:applydata")
    public AjaxJson dataApply(@RequestBody DataShareApplyVo dataShareApplyVo) throws Exception{
        try {
            dataShareService.dataApply(dataShareApplyVo);
            return new AjaxJson(ResultEnum.APPLY_SUCCESS.getValue());
        } catch (Exception e) {
            log.error("/data-share/data-apply" + e);
            throw new BusinessException(ResultEnum.APPLY_FAIL.getValue());
        }
    }
    
    /**
     * Description: 共享交换-申请详情 
     * @Date: 2020/10/15 15:52 
     * @Author: zbya 
     * 
     * @param pageQuery
     * @return: com.zzsj.dm.base.json.AjaxJson
     */ 
    @RequestMapping("/get-list")
    @ResponseBody
    @SysControllerLog(description = "共享交换-申请详情")
    @RequiresPermissions("pbdms:share:getsharelist")
    public AjaxJson getApplyList(@RequestBody PageQuery<DataApplyQuery> pageQuery) throws Exception{
        try {
            return new AjaxJson(dataShareService.applyList(pageQuery));
        } catch (Exception e) {
            log.error("/data-share/get-list" + e);
            throw new BusinessException(ResultEnum.LIST_FAIL.getValue());
        }
    }
    
    /**
     * Description: 共享交换-审核 
     * @Date: 2020/10/15 15:52 
     * @Author: zbya 
     * 
     * @param dataShareApplyVo
     * @return: com.zzsj.dm.base.json.AjaxJson
     */ 
    @RequestMapping("/apply-check")
    @ResponseBody
    @SysControllerLog(description = "共享交换-审核",method= OprLogConst.CHECK)
    @RequiresPermissions("pbdms:share:checkapply")
    public AjaxJson applyCheck(@RequestBody DataShareApplyVo dataShareApplyVo) throws Exception{
        try {
            dataShareService.applyCheck(dataShareApplyVo);
            return new AjaxJson(ResultEnum.CHECK_SUCCESS.getValue());
        } catch (Exception e) {
            log.error("/data-share/apply-check" + e);
            throw new BusinessException(ResultEnum.CHECK_FAIL.getValue());
        }
    }
}
