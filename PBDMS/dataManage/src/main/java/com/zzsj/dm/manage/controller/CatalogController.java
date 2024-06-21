package com.zzsj.dm.manage.controller;

import com.zzsj.dm.base.constants.OprLogConst;
import com.zzsj.dm.base.exception.BusinessException;
import com.zzsj.dm.base.json.AjaxJson;
import com.zzsj.dm.base.pojo.query.PageQuery;
import com.zzsj.dm.base.utils.enums.ResultEnum;
import com.zzsj.dm.manage.pojo.query.CatalogQuery;
import com.zzsj.dm.manage.pojo.query.DataCountQuery;
import com.zzsj.dm.manage.pojo.query.TabDataOprQuery;
import com.zzsj.dm.manage.pojo.vo.CatalogVo;
import com.zzsj.dm.manage.service.CatalogService;
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
 * @date ：Created in 2020/9/14 14:17
 * @description：数据目录管理
 */

@RestController
@Slf4j
@RequestMapping("/catalog-menu")
public class CatalogController {
    @Autowired
    CatalogService catalogService;
    
    /**
     * Description: 获取数据目录信息(暂不用)
     * @Date: 2020/9/14 14:34 
     * @Author: zbya 
     * 
     * @param pageQuery
     * @return: com.zzsj.dm.base.json.AjaxJson
     */ 
    @RequestMapping("get-catalog")
    @ResponseBody
    public AjaxJson getCatalog(@RequestBody PageQuery<CatalogQuery> pageQuery) throws Exception {
        try {
            return new AjaxJson(catalogService.getCatalog(pageQuery));
        } catch (Exception e) {
            log.error("/catalog-menu/get-catalog" + e);
            throw new BusinessException(ResultEnum.LIST_FAIL.getValue());
        }
    }
    
    /**
     * Description: 获取所有数据目录信息 
     * @Date: 2020/9/14 15:30 
     * @Author: zbya 
     * 
     * @param 
     * @return: com.zzsj.dm.base.json.AjaxJson
     */ 
    @RequestMapping("all-catalog")
    @ResponseBody
    @RequiresPermissions("pbdms:catalog:getcatalogtree")
    public AjaxJson getAllCatalog(String type) throws Exception{
        try {
            return new AjaxJson(catalogService.getAllCatalog(type));
        } catch (Exception e) {
            log.error("/catalog-menu/all-catalog" + e);
            throw new BusinessException(ResultEnum.LIST_FAIL.getValue());
        }
    }

    /**
     * Description: 删除目录信息
     * @Date: 2020/9/14 15:54
     * @Author: zbya
     *
     * @param id
     * @return: com.zzsj.dm.base.json.AjaxJson
     */
    @RequestMapping("delete-data")
    @ResponseBody
    @SysControllerLog(description = "数据目录",method= OprLogConst.DELETE)
    @RequiresPermissions("pbdms:catalog:delnode")
    public AjaxJson deleteData(String id) throws Exception{
        try {
            return new AjaxJson(ResultEnum.DELETE_SUCCESS.getValue(),catalogService.deleteData(id));
        } catch (Exception e) {
            log.error("/catalog-menu/delete-data"+e);
            throw new BusinessException(ResultEnum.DELETE_FAIL.getValue());
        }
    }

    /**
     * Description: 更新目录信息
     * @Date: 2020/9/14 15:54
     * @Author: zbya
     *
     * @param catalogVo
     * @return: com.zzsj.dm.base.json.AjaxJson
     */
    @RequestMapping("update-data")
    @ResponseBody
    @SysControllerLog(description = "数据目录",method= OprLogConst.UPDATE)
    @RequiresPermissions("pbdms:catalog:updatenode")
    public AjaxJson updateData(@RequestBody @Validated CatalogVo catalogVo, BindingResult bindingResult) throws Exception{
        try {
            if (bindingResult.getErrorCount() != 0) {
                return new AjaxJson(false, bindingResult.getAllErrors().get(0).toString());
            }
            return new AjaxJson(ResultEnum.SAVE_SUCCESS.getValue(),catalogService.updateData(catalogVo));
        } catch (Exception e) {
            log.error("/catalog-menu/update-data"+e);
            throw new BusinessException(ResultEnum.SAVE_FAIL.getValue());
        }
    }

    /**
     * Description: 插入目录信息
     * @Date: 2020/9/14 15:53
     * @Author: zbya
     *
     * @param
     * @return: com.zzsj.dm.base.json.AjaxJson
     */
    @RequestMapping("insert-data")
    @ResponseBody
    @SysControllerLog(description = "数据目录",method= OprLogConst.ADD)
    @RequiresPermissions("pbdms:catalog:addnode")
    public AjaxJson insertData(@RequestBody @Validated CatalogVo catalogVo, BindingResult bindingResult) throws Exception {

        try {
            if (bindingResult.getErrorCount() != 0) {
                return new AjaxJson(false, bindingResult.getAllErrors().get(0).toString());
            }
            return new AjaxJson(ResultEnum.SAVE_SUCCESS.getValue(),catalogService.insertData(catalogVo));
        } catch (Exception e) {
            log.error("/catalog-menu/insert-data"+e);
            throw new BusinessException(ResultEnum.SAVE_FAIL.getValue());
        }
    }
    
    /**
     * Description: 查询未插入数据目录的表
     * @Date: 2020/9/15 16:44
     * @Author: zbya 
     * 
     * @param 
     * @return: com.zzsj.dm.base.json.AjaxJson
     */ 
    @RequestMapping("tab-list")
    @ResponseBody
    @SysControllerLog(description = "数据目录")
    public AjaxJson getTabList() throws Exception{
        try {
            return new AjaxJson(catalogService.getTabList());
        } catch (Exception e) {
            log.error("/catalog-menu/tab-list"+e);
            throw new BusinessException(ResultEnum.LIST_FAIL.getValue());
        }
    }

    /**
     * Description: 删除指定日期输出表数据
     * @Date: 2020/12/24 11:18
     * @Author: zbya
     *
     * @param dataCountQuery
     * @return: com.zzsj.analysis.base.json.AjaxJson
     */
    @RequestMapping("del-data")
    @ResponseBody
    @RequiresPermissions("pbdms:tables:deletedata")
    @SysControllerLog(description = "数据导入-输入表",method= OprLogConst.DELETE)
    public AjaxJson delData(@RequestBody TabDataOprQuery dataCountQuery) throws Exception{
        try {
            catalogService.deleteByDate(dataCountQuery);
            return new AjaxJson();
        }
        catch (Exception e){
            log.error("/catalog-menu/del-data"+e);
            throw new BusinessException(ResultEnum.DELETE_FAIL.getValue());
        }
    }
}
