package com.zzsj.dm.manage.controller;

import com.zzsj.dm.base.constants.OprLogConst;
import com.zzsj.dm.base.exception.BusinessException;
import com.zzsj.dm.base.json.AjaxJson;
import com.zzsj.dm.base.pojo.query.PageQuery;
import com.zzsj.dm.base.utils.enums.ResultEnum;
import com.zzsj.dm.manage.pojo.query.TabQuery;
import com.zzsj.dm.manage.pojo.vo.TabVo;
import com.zzsj.dm.manage.service.DataTabOprateService;
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
 * @date ：Created in 2020/9/9 14:38
 * @description：数据表增删改查及操作日志查看
 */

@Slf4j
@RestController
@RequestMapping("/tab-operate")
public class DataTabOprateController {
    @Autowired
    DataTabOprateService dataTabOprateService;

    /**
     * Description: 查询可新建列集合
     *
     * @param
     * @Date: 2020/9/10 18:23
     * @Author: zbya
     * @return: com.zzsj.dm.base.json.AjaxJson
     */
    @RequestMapping("col-types")
    @ResponseBody
    @RequiresPermissions("pbdms:import:getcoloptions")
    public AjaxJson getColTypes() throws Exception {
        try {
            return new AjaxJson(dataTabOprateService.getColTypes());
        } catch (Exception e) {
            log.error("/tab-operate/col-types" + e);
            throw new BusinessException(ResultEnum.LIST_FAIL.getValue());
        }
    }

    /**
     * Description: 查询自定义表集合
     *
     * @param pageQuery
     * @Date: 2020/9/10 17:54
     * @Author: zbya
     * @return: com.zzsj.dm.base.json.AjaxJson
     */
    @RequestMapping("get-list")
    @ResponseBody
    @SysControllerLog(description = "数据导入-目录")
    @RequiresPermissions("")
    public AjaxJson getList(PageQuery pageQuery) throws Exception {

        try {
            return new AjaxJson(dataTabOprateService.getList(pageQuery));
        } catch (Exception e) {
            log.error("/tab-operate/get-list" + e);
            throw new BusinessException(ResultEnum.LIST_FAIL.getValue());
        }
    }

    /**
     * Description: 新建表
     *
     * @param tabVo
     * @Date: 2020/9/10 17:54
     * @Author: zbya
     * @return: com.zzsj.dm.base.json.AjaxJson
     */
    @RequestMapping("insert-data")
    @ResponseBody
    @SysControllerLog(description = "数据导入-表",method= OprLogConst.ADD)
    @RequiresPermissions("pbdms:import:addtable")
    public AjaxJson insertData(@RequestBody @Validated TabVo tabVo, BindingResult bindingResult) throws Exception {

        try {
            if (bindingResult.getErrorCount() != 0) {
                return new AjaxJson(false, bindingResult.getAllErrors().get(0).toString());
            }
            return new AjaxJson(ResultEnum.SAVE_SUCCESS.getValue(), dataTabOprateService.insertData(tabVo));
        } catch (Exception e) {
            log.error("/tab-operate/insert-data" + e);
            throw new BusinessException(ResultEnum.SAVE_FAIL.getValue());
        }
    }


    /**
     * Description: 删除表信息
     *
     * @param tabQuery
     * @Date: 2020/9/10 17:54
     * @Author: zbya
     * @return: com.zzsj.dm.base.json.AjaxJson
     */
    @RequestMapping("delete-data")
    @ResponseBody
    @SysControllerLog(description = "数据导入-表",method= OprLogConst.DELETE)
    @RequiresPermissions("pbdms:import:deltable")
    public AjaxJson deleteData(@RequestBody TabQuery tabQuery) throws Exception {
        try {
            dataTabOprateService.deleteData(tabQuery);
            return new AjaxJson(ResultEnum.DELETE_SUCCESS.getValue());
        } catch (Exception e) {
            log.error("/tab-operate/delete-data" + e);
            throw new BusinessException(ResultEnum.DELETE_FAIL.getValue());
        }
    }

//    /**
//     * Description: 根据表名查询列信息
//     *
//     * @param id
//     * @Date: 2020/9/10 19:08
//     * @Author: zbya
//     * @return: com.zzsj.dm.base.json.AjaxJson
//     */
//    @RequestMapping("col-list")
//    @ResponseBody
//    @RequiresPermissions("")
//    public AjaxJson getColList(String id) throws Exception {
//        try {
//            return new AjaxJson(dataTabOprateService.getColList(id));
//        } catch (Exception e) {
//            log.error("/tab-operate/col-list" + e);
//            throw new BusinessException(ResultEnum.LIST_FAIL.getValue());
//        }
//    }

}
