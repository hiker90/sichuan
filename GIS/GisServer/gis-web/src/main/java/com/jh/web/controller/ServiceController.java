package com.jh.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.jh.config.SystemConfig;
import com.jh.controller.BaseController;
import com.jh.domain.GisFile;
import com.jh.domain.GisService;
import com.jh.domain.SysUser;
import com.jh.service.IGisFileService;
import com.jh.service.IGisServiceService;
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
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class ServiceController extends BaseController
{
    @Autowired
    IGisServiceService gisServiceService;

    @Autowired
    IGisFileService gisFileService;


    @PostMapping("/publish/service")
    @RequiresPermissions("gis:map:publish")
    public String publishService(@ModelAttribute ServiceFileModel model) {
        SysUser user = ShiroUtils.getSysUser();
        if (CommonUtils.isNull(user)) {
            return AjaxResult.error("系统异常");
        }

        // 参数检查
        if (StringUtils.isEmpty(model.getServiceName())
            || StringUtils.isEmpty(model.getServiceUrl())
            || !(model.getServiceUrl().startsWith("http://") || model.getServiceUrl().startsWith("https://"))
        ) {
            return AjaxResult.error("参数错误");
        }

        GisService service = new GisService();
        service.setName(model.getName());
        service.setServiceName(model.getServiceName());
        service.setServiceUrl(model.getServiceUrl());
        service.setServiceSummary(model.getServiceSummary());
        service.setServiceDesc(model.getServiceDesc());
        service.setServiceTags(model.getServiceTags());
        service.setCreateUser(user.getUserId());
        service.setCreateUsername(user.getUserName());

        try
        {
            service = gisServiceService.insertGisService(service);
            if (service.getId() == 0)
            {
                return AjaxResult.error("保存服务失败，请稍后重试");
            }
        }catch (Exception e) {
            log.error(e.getMessage());
            return AjaxResult.error("保存服务失败，请稍后重试");
        }

        return AjaxResult.success();
    }

    @PostMapping("/publish/service-file")
    @RequiresPermissions("gis:map:publish")
    public String publishServiceAndFile(@ModelAttribute ServiceFileModel model) {
        SysUser user = ShiroUtils.getSysUser();
        if (CommonUtils.isNull(user)) {
            return AjaxResult.error("系统异常");
        }

        // 参数检查
        if (StringUtils.isEmpty(model.getName())
            || StringUtils.isEmpty(model.getServiceUrl())
            || !(model.getServiceUrl().startsWith("http://") || model.getServiceUrl().startsWith("https://"))
            || CommonUtils.isNull(model.getSourceFile())
        ) {
            return AjaxResult.error("参数错误");
        }

        // 上传文件名
        String fileName = IdUtils.fastSimpleUUID();

        // 上传文件
        Boolean rst = FileUtils.fileUpload(model.getSourceFile(), fileName);
        if (!rst) {
            log.error("文件保存服务失败，"+ user.getUserId() +",parameter:" + model.toString());
            return AjaxResult.error("文件保存失败，请稍后重试！");
        }

        GisService service = new GisService();
        service.setName(model.getName());
        service.setServiceName(model.getServiceName());
        service.setServiceUrl(model.getServiceUrl());
        service.setServiceSummary(model.getServiceSummary());
        service.setServiceDesc(model.getServiceDesc());
        service.setServiceTags(model.getServiceTags());
        service.setCreateUser(user.getUserId());
        service.setCreateUsername(user.getUserName());

        try
        {
            service = gisServiceService.insertGisService(service);
            if (service.getId() == 0)
            {
                log.error("保存服务信息失败，service:" + service.toString());
                //删除文件
                FileUtils.deleteFile(fileName);
                return AjaxResult.error("保存服务失败，请稍后重试");
            }
        }catch (Exception e) {
            log.error(e.getMessage());
            return AjaxResult.error("保存服务失败，请稍后重试");
        }

        GisFile file = new GisFile();
        file.setServiceId(service.getId());
        file.setFilename(model.getSourceFile().getOriginalFilename());
        file.setFilesize(model.getSourceFile().getSize());
        file.setCreateUser(user.getUserId());
        file.setCreateUsername(user.getUserName());
        file.setUuid(fileName);

        try {
            file = gisFileService.insertGisFile(file);
            if (file.getId() == 0)
            {
                log.error("保存文件信息失败，service:" + service.toString());
                // 删除服务
                int rows = gisServiceService.deleteGisServiceByID(service.getId());
                if (rows <= 0) {
                    log.error("删除服务失败，service:" + service.toString());
                }

                // 文件删除
                FileUtils.deleteFile(fileName);
                return AjaxResult.error("保存文件失败，请稍后重试");
            }
        } catch (Exception e) {
            log.error(e.getMessage());

            // 文件删除
            FileUtils.deleteFile(fileName);

            return AjaxResult.error("保存文件失败，请稍后重试");
        }

        return AjaxResult.success();
    }

    @PostMapping("/publish/file")
    @RequiresPermissions("gis:map:publish")
    public String publishFile(@ModelAttribute ServiceFileModel model) {
        SysUser user = ShiroUtils.getSysUser();
        if (CommonUtils.isNull(user)) {
            return AjaxResult.error("系统异常");
        }

        // 参数检查
        if (CommonUtils.isNull(model.getSourceFile())) {
            return AjaxResult.error("参数错误");
        }

        // 上传文件名
        String fileName = IdUtils.fastSimpleUUID();

        // 上传文件
        Boolean rst = FileUtils.fileUpload(model.getSourceFile(), fileName);
        if (!rst) {
            return AjaxResult.error("文件保存失败，请稍后重试！");
        }

        GisFile file = new GisFile();
        file.setServiceId(0L);
        file.setFilename(model.getSourceFile().getOriginalFilename());
        file.setFilesize(model.getSourceFile().getSize());
        file.setCreateUser(user.getUserId());
        file.setCreateUsername(user.getUserName());
        file.setUuid(fileName);

        try {
            file = gisFileService.insertGisFile(file);
            if (file.getId() == 0)
            {
                // 文件删除
                FileUtils.deleteFile(fileName);
                return AjaxResult.error("保存文件失败，请稍后重试");
            }

        } catch (Exception e) {
            log.error(e.getMessage());

            // 文件删除
            FileUtils.deleteFile(fileName);

            return AjaxResult.error("保存文件失败，请稍后重试");
        }

        return AjaxResult.success();
    }

    @GetMapping("/service/list")
    @RequiresPermissions("gis:map:view")
    public JSONObject listService() {
        JSONObject jo = new JSONObject();
        SysUser user = ShiroUtils.getSysUser();
        if (CommonUtils.isNull(user)) {
            jo.put("retcode", 1);
            jo.put("msg", "系统异常");
            return jo;
        }
        String downloadurl = SystemConfig.getDownloadurlprefix();

        startPage();
        List<GisService> serviceList = gisServiceService.selectGisServiceByUserId(user.getUserId());

        for (GisService service:serviceList)
        {
            service.setSourceFile(downloadurl + service.getSourceFile());
        }

        jo.put("retcode", 0);
        jo.put("msg", "");
        jo.put("services", getDataTable(serviceList));

        return jo;
    }

    @GetMapping("/file/download/{id}")
    @RequiresPermissions("gis:map:download")
    public String downloadService(@PathVariable Long id, HttpServletResponse response) throws IOException
    {
        SysUser user = ShiroUtils.getSysUser();
        if (CommonUtils.isNull(user)) {
            return AjaxResult.error("系统异常");
        }

        // 查询文件
        GisFile file = gisFileService.selectGisFileById(id);

        if (CommonUtils.isNull(file) || !file.getId().equals(id)) {
            return AjaxResult.error("文件不存在");
        }

        // 文件地址
        String fileName = SystemConfig.getFilepath() + "/" + file.getUuid();

        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        FileUtils.setAttachmentResponseHeader(response, file.getFilename());
        response.setContentLength(file.getFilesize().intValue());

        FileUtils.writeBytes(fileName, response.getOutputStream());

        return AjaxResult.success();
    }

    @GetMapping("/service/mylist")
    @RequiresPermissions("gis:map:view")
    public JSONObject myServiceList()
    {
        JSONObject jo = new JSONObject();
        SysUser user = ShiroUtils.getSysUser();
        if (CommonUtils.isNull(user)) {
            jo.put("retcode", 1);
            jo.put("msg", "系统异常");
            return jo;
        }

        String downloadurl = SystemConfig.getDownloadurlprefix();

        startPage();
        List<GisService> serviceList = gisServiceService.selectMyGisServiceByUserId(user.getUserId());

        for (GisService service:serviceList)
        {
            service.setSourceFile(downloadurl + service.getSourceFile());
        }

        jo.put("retcode", 0);
        jo.put("msg", "");
        jo.put("services", getDataTable(serviceList));

        return jo;
    }

    @GetMapping("/service/sharelist")
    @RequiresPermissions("gis:map:view")
    public JSONObject shareServiceList()
    {
        JSONObject jo = new JSONObject();
        SysUser user = ShiroUtils.getSysUser();
        if (CommonUtils.isNull(user)) {
            jo.put("retcode", 1);
            jo.put("msg", "系统异常");
            return jo;
        }
        String downloadurl = SystemConfig.getDownloadurlprefix();

        startPage();
        List<GisService> serviceList = gisServiceService.selectOtherGisServiceByUserId(user.getUserId());

        for (GisService service:serviceList)
        {
            service.setSourceFile(downloadurl + service.getSourceFile());
        }

        jo.put("retcode", 0);
        jo.put("msg", "");
        jo.put("services", getDataTable(serviceList));

        return jo;
    }

    @GetMapping("/delete/service-file/{id}")
    @RequiresPermissions("gis:map:delete")
    public String deleteServiceFile(@PathVariable Long id)
    {
        // 查询服务
        GisService service = gisServiceService.selectGisServiceById(id);
        if (CommonUtils.isNull(service) || !service.getId().equals(id)) {
            log.info("服务({})不存在！", id);
            return AjaxResult.success();
        }

        // 判断该服务是否为用户本人创建
        SysUser user = ShiroUtils.getSysUser();
        if (!user.getUserId().equals(service.getCreateUser())) {
            log.error("用户({}){}试图删除非本人创建服务({})！", user.getUserId(), user.getUserName(), id);
            return AjaxResult.success();
        }

        int rows = gisServiceService.deleteGisServiceByID(id);
        if (rows <= 0) {
            log.error("服务({})删除失败！", id);
            return AjaxResult.error("删除失败，请稍后重试！");
        }

        // 删除文件记录
        GisFile file = gisFileService.selectGisFileByServiceId(id);
        if (CommonUtils.isNull(file) || !file.getServiceId().equals(id)) {
            log.info("服务({})关联文件不存在！", id);
        }
        rows = gisFileService.deleteGisFileByID(file.getId());
        if (rows <= 0) {
            log.info("服务({})关联文件({})删除失败！", id, file.getId());
            return AjaxResult.error("删除失败，请稍后重试！");
        }

        // 删除文件
        FileUtils.deleteFile(file.getUuid());
        return AjaxResult.success();
    }
}
