package com.zzsj.dm.manage.controller;

import com.zzsj.dm.base.constants.OprLogConst;
import com.zzsj.dm.base.exception.BusinessException;
import com.zzsj.dm.base.json.AjaxJson;
import com.zzsj.dm.base.pojo.query.PageQuery;
import com.zzsj.dm.base.utils.enums.ResultEnum;
import com.zzsj.dm.manage.pojo.query.WarnTypeQuery;
import com.zzsj.dm.manage.service.DataQualityService;
import com.zzsj.dm.manage.service.DataWarnService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/2 9:19
 * @description：数量质量控制器
 */

@Slf4j
@Validated
@RestController
@RequestMapping("/data-quality")
public class DataQualityController {
    @Autowired
    private DataQualityService dataQualityService;
    @Autowired
    private DataWarnService dataWarnService;

    /**
     * description:数据质量-整体接入量信息
     * date: 2020/9/2 16:03
     * author: zbya
     *
     * @param
     * @return com.zzsj.dm.base.json.AjaxJson
     */
    @RequestMapping("access-num")
    @ResponseBody
    @RequiresPermissions("pbdms:quality:getkpi")
    @SysControllerLog(description = "数据质量")
    public AjaxJson getTotalNum() throws Exception{
        try {
            return new AjaxJson(dataQualityService.getTotalNum());
        } catch (Exception e) {
            log.error("/data-quality/access-num",e);
            throw new BusinessException(ResultEnum.LIST_FAIL.getValue());
        }
    }

    /**
     * description: 数据质量-预警情况、处理情况查询
     * date: 2020/9/3 16:29
     * author: zbya
     *
     * @param pageQuery
     * @return com.zzsj.dm.base.json.AjaxJson
     */
    @RequestMapping("warn-states")
    @ResponseBody
    @RequiresPermissions("pbdms:quality:getmsg")
    public AjaxJson getWarningStates(@RequestBody  PageQuery<WarnTypeQuery> pageQuery) throws Exception{
        try {
            return new AjaxJson(dataWarnService.getWarningStates(pageQuery));
        } catch (Exception e) {
            log.error("/data-quality/warn-states",e);
            throw new BusinessException(ResultEnum.LIST_FAIL.getValue());
        }
    }

    /**
     * description: 数据质量-预警情况处理
     * date: 2020/9/3 16:29
     * author: zbya
     *
     * @param
     * @return com.zzsj.dm.base.json.AjaxJson
     */
    @RequestMapping("warn-deal")
    @ResponseBody
    @SysControllerLog(description = "数据质量-预警情况-状态",method= OprLogConst.UPDATE)
    @RequiresPermissions("pbdms:quality:handlemsg")
    public AjaxJson dealWarning(String name) throws Exception{
        try {
            return new AjaxJson(ResultEnum.DEAL_SUCCESS.getValue(),dataWarnService.dealWarning(name));
        } catch (Exception e) {
            log.error("/data-quality/warn-deal",e);
            throw new BusinessException(ResultEnum.DELETE_FAIL.getValue());
        }
    }
}
