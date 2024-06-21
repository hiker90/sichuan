package com.jh.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.jh.domain.*;
import com.jh.service.*;
import com.jh.shiro.ShiroUtils;
import com.jh.utils.CommonUtils;
import com.jh.utils.ajax.AjaxResult;
import com.jh.utils.file.FileUtils;
import com.jh.utils.string.StringUtils;
import com.jh.utils.uuid.IdUtils;
import com.jh.web.domain.ServiceFileModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

@RestController
@Slf4j
public class ShareController
{
    @Autowired
    ISysUserService userService;

    @Autowired
    IGisServiceService serviceService;

    @Autowired
    IGisFileService fileService;

    @Autowired
    IGisUserServiceService userServiceService;

    @Autowired
    IGisUserFileService userFileService;


    @GetMapping("/share/getuserlist")
    @RequiresPermissions("gis:map:share")
    public JSONObject getUserList() {
        List<SimpleSysUser> users = userService.selectUserSimpleList(new SysUser());
        SysUser user = ShiroUtils.getSysUser();

        for (SimpleSysUser u:users)
        {
            if (u.getUserId().equals(user.getUserId())){
                users.remove(u);
                break;
            }
        }

        JSONObject jo = new JSONObject();
        jo.put("retcode", 0);
        jo.put("msg", "");
        jo.put("users", users);
        return jo;
    }

    // 仅分享文件
    @PostMapping("/share/sharefile")
    @RequiresPermissions("gis:map:share")
    public String shareFile(@RequestBody JSONObject jsonParam) {
        Long fileId = jsonParam.getLong("fileId");
        String userIds = jsonParam.getString("userIds");

        // 检查是否有该service
        GisFile file = fileService.selectGisFileById(fileId);

        if (CommonUtils.isNull(file) || !file.getId().equals(fileId)) {
            return AjaxResult.error("待分享文件不存在，请确认后重试");
        }

        // 检查userid
        if (!Pattern.matches("^\\d+(,\\d+)*$", userIds)) {
            return AjaxResult.error("用户ID格式错误，请确认后重试");
        }

        if (userFileService.batchInsertGisUserFile(fileId, userIds)<=0) {
            return AjaxResult.error("保存失败，请稍后重试");
        }

        return AjaxResult.success();
    }

    // 仅分享服务
    @PostMapping("/share/shareservice")
    @RequiresPermissions("gis:map:share")
    public String shareService(@RequestBody JSONObject jsonParam) {

        Long serviceId = jsonParam.getLong("serviceId");
        String userIds = jsonParam.getString("userIds");

        // 检查是否有该service
        GisService service = serviceService.selectGisServiceById(serviceId);

        if (CommonUtils.isNull(service) || !service.getId().equals(serviceId)) {
            return AjaxResult.error("待分享服务不存在，请确认后重试");
        }

        // 检查userid
        if (!Pattern.matches("^\\d+(,\\d+)*$", userIds)) {
            return AjaxResult.error("用户ID格式错误，请确认后重试");
        }

        if (userServiceService.batchInsertGisUserService(serviceId, userIds)<=0) {
            return AjaxResult.error("保存失败，请稍后重试");
        }

        return AjaxResult.success();
    }

    // 分享文件和服务
    @PostMapping("/share/share")
    @RequiresPermissions("gis:map:share")
    public String share(@RequestBody JSONObject jsonParam) {
        Long serviceId = jsonParam.getLong("serviceId");
        String userIds = jsonParam.getString("userIds");

        // 检查是否有该service
        GisService service = serviceService.selectGisServiceById(serviceId);

        if (CommonUtils.isNull(service) || !service.getId().equals(serviceId)) {
            return AjaxResult.error("待分享服务不存在，请确认后重试");
        }

        GisFile file = fileService.selectGisFileByServiceId(serviceId);
        Long fileId;
        if (CommonUtils.isNull(file) || !file.getServiceId().equals(serviceId)) {
            fileId = -1L;
        } else {
            fileId = file.getId();
        }

        // 检查userid
        if (!Pattern.matches("^\\d+(,\\d+)*$", userIds)) {
            return AjaxResult.error("用户ID格式错误，请确认后重试");
        }

        if (userServiceService.batchInsertGisUserServiceAndFile(serviceId, fileId, userIds)<=0) {
            return AjaxResult.error("保存失败，请稍后重试");
        }

        return AjaxResult.success();
    }
}
