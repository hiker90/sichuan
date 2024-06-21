package com.zzsj.analysis.manage.controller;

import com.zzsj.analysis.base.exception.BusinessException;
import com.zzsj.analysis.base.json.AjaxJson;
import com.zzsj.analysis.base.pojo.query.PageQuery;
import com.zzsj.analysis.base.utils.enums.ResultEnum;
import com.zzsj.analysis.manage.pojo.query.SysLogQuery;
import com.zzsj.analysis.manage.service.SystemLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/15 15:30
 * @description：系统操作日志
 */

@RequestMapping("/sys-log")
@RestController
@Slf4j
public class SystemLogController {
    @Autowired
    SystemLogService systemLogService;

    /**
     * Description: 查询系统日志
     * @Date: 2020/9/15 16:02
     * @Author: zbya
     *
     * @param pageQuery
     * @return: com.zzsj.analysis.base.json.AjaxJson
     */
    @RequestMapping("log-list")
    @ResponseBody
    @RequiresPermissions("dsas:logs:getlist")
    public AjaxJson getSysLog(@RequestBody PageQuery<SysLogQuery> pageQuery) throws Exception {
        try {
            return new AjaxJson(systemLogService.getCatalog(pageQuery));
        } catch (Exception e) {
            log.error("/sys-log/log-list" + e);
            throw new BusinessException(ResultEnum.LIST_FAIL.getValue());
        }
    }
}
