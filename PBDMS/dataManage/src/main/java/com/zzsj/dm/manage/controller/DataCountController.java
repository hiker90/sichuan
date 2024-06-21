package com.zzsj.dm.manage.controller;

import com.zzsj.dm.base.exception.BusinessException;
import com.zzsj.dm.base.json.AjaxJson;
import com.zzsj.dm.base.pojo.query.PageQuery;
import com.zzsj.dm.base.utils.enums.ResultEnum;
import com.zzsj.dm.manage.pojo.query.DataCountQuery;
import com.zzsj.dm.manage.service.DataCountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/18 22:22
 * @description：数据统计
 */

@Slf4j
@RestController
@RequestMapping("/data-count")
public class DataCountController {
    @Autowired
    DataCountService dataCountService;

    /**
     * Description: 获取时间范围内统计数据
     * @Date: 2020/9/18 22:53
     * @Author: zbya
     *
     * @param pageQuery
     * @return: com.zzsj.dm.base.json.AjaxJson
     */
    @RequestMapping("get-data")
    @ResponseBody
    @SysControllerLog(description = "数据统计")
    @RequiresPermissions("pbdms:statistics:getlist")
    public AjaxJson getData(@RequestBody PageQuery<DataCountQuery> pageQuery) throws Exception {
        try {
            return new AjaxJson(dataCountService.getData(pageQuery));
        } catch (Exception e) {
            log.error("/data-count/get-data" + e);
            throw new BusinessException(ResultEnum.LIST_FAIL.getValue());
        }
    }

}
