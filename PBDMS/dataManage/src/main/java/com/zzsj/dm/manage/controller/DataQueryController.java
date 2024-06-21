package com.zzsj.dm.manage.controller;

import com.zzsj.dm.base.exception.BusinessException;
import com.zzsj.dm.base.json.AjaxJson;
import com.zzsj.dm.base.pojo.query.PageQuery;
import com.zzsj.dm.base.utils.enums.ResultEnum;
import com.zzsj.dm.manage.pojo.query.DataQueryQuery;
import com.zzsj.dm.manage.pojo.query.TabQuery;
import com.zzsj.dm.manage.service.DataQueryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/18 23:11
 * @description：数据查询
 */

@Slf4j
@RestController
@RequestMapping("/data-query")
public class DataQueryController {

    @Autowired
    DataQueryService dataQueryService;

    /**
     * Description: 数据查询查询数据
     * @Date: 2020/9/22 22:23
     * @Author: zbya
     *
     * @param pageQuery
     * @return: com.zzsj.dm.base.json.AjaxJson
     */
    @RequestMapping("get-data")
    @ResponseBody
    @RequiresPermissions("pbdms:query:gettabledata")
    @SysControllerLog(description = "数据查询-表详情")
    public AjaxJson getData(@RequestBody PageQuery<DataQueryQuery> pageQuery) throws Exception {
        try {
            return new AjaxJson(dataQueryService.getData(pageQuery));
        } catch (Exception e) {
            log.error("/data-query/get-data" + e);
            throw new BusinessException(ResultEnum.LIST_FAIL.getValue());
        }
    }
    
    /**
     * Description: 获取表信息 
     * @Date: 2020/9/23 15:24 
     * @Author: zbya 
     * 
     * @param pageQuery
     * @return: com.zzsj.dm.base.json.AjaxJson
     */ 
    @RequestMapping("get-table")
    @ResponseBody
    @SysControllerLog(description = "数据查询-表列表信息")
    @RequiresPermissions("pbdms:query:gettablelist")
    public AjaxJson getTable(@RequestBody PageQuery<TabQuery> pageQuery) throws Exception {
        try {
            return new AjaxJson(dataQueryService.getTable(pageQuery));
        } catch (Exception e) {
            log.error("/data-query/get-table" + e);
            throw new BusinessException(ResultEnum.LIST_FAIL.getValue());
        }
    }
    
    
    /**
     * Description: 获取指定表列信息 
     * @Date: 2020/9/23 16:10 
     * @Author: zbya 
     * 
     * @param tableEnName
     * @return: com.zzsj.dm.base.json.AjaxJson
     */ 
    @RequestMapping("get-col")
    @ResponseBody
    @RequiresPermissions("pbdms:query:gettablecols")
    public AjaxJson getColumn(String tableEnName) throws Exception{
        try {
            return new AjaxJson(dataQueryService.getColumn(tableEnName));
        } catch (Exception e) {
            log.error("/data-query/get-col" + e);
            throw new BusinessException(ResultEnum.LIST_FAIL.getValue());
        }
    }

}
