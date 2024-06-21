package com.zzsj.dm.base.controller;

import com.zzsj.dm.base.exception.BusinessException;
import com.zzsj.dm.base.json.AjaxJson;
import com.zzsj.dm.base.pojo.query.PageQuery;
import com.zzsj.dm.base.pojo.vo.BaseVo;
import com.zzsj.dm.base.service.BaseService;
import com.zzsj.dm.base.utils.enums.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/4 16:00
 * @description：模版
 */

@Slf4j
@RestController
@RequestMapping("")
public class BaseController {

    @Autowired
    private BaseService baseService;

    @RequestMapping("get-list")
    @ResponseBody
    public AjaxJson getList(PageQuery<BaseVo> pageQuery) throws Exception {

        try {
            return new AjaxJson(baseService.getList(pageQuery));
        } catch (Exception e) {
            log.error("/get-list"+e);
            throw new BusinessException(ResultEnum.LIST_FAIL.getValue());
        }
    }

    @RequestMapping("insert-data")
    @ResponseBody
    public AjaxJson insertData() throws Exception {

        try {
            return new AjaxJson(ResultEnum.SAVE_SUCCESS.getValue(),baseService.insertData());
        } catch (Exception e) {
            log.error("/insert-data"+e);
            throw new BusinessException(ResultEnum.SAVE_FAIL.getValue());
        }
    }

    @RequestMapping("delete-data")
    @ResponseBody
    public AjaxJson deleteData(String id) throws Exception{
        try {
            return new AjaxJson(ResultEnum.DELETE_SUCCESS.getValue(),baseService.deleteData(id));
        } catch (Exception e) {
            log.error("/delete-data"+e);
            throw new BusinessException(ResultEnum.DELETE_FAIL.getValue());
        }
    }

    @RequestMapping("update-data")
    @ResponseBody
    public AjaxJson updateData() throws Exception{
        try {
            return new AjaxJson(ResultEnum.SAVE_SUCCESS.getValue(),baseService.updateData());
        } catch (Exception e) {
            log.error("/update-data"+e);
            throw new BusinessException(ResultEnum.SAVE_FAIL.getValue());
        }
    }
}
