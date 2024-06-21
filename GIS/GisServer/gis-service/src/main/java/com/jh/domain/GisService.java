package com.jh.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 * gis_service
 * @author 
 */
public class GisService implements Serializable {
    /**
     * 服务ID
     */
    private Long id;

    /**
     * 图层名
     */
    private String name;

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 服务地址
     */
    private String serviceUrl;

    /**
     * 服务简介
     */
    private String serviceSummary;

    /**
     * 服务描述
     */
    private String serviceDesc;

    /**
     * 服务简介
     */
    private String serviceTags;

    /**
     * 创建用户ID
     */
    private Long createUser;

    /**
     * 创建用户名
     */
    private String createUsername;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**
     * 文件下载地址
     */
    private String sourceFile;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public String getCreateUsername() {
        return createUsername;
    }

    public void setCreateUsername(String createUsername) {
        this.createUsername = createUsername;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getServiceName()
    {
        return serviceName;
    }

    public void setServiceName(String serviceName)
    {
        this.serviceName = serviceName;
    }

    public String getServiceUrl()
    {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl)
    {
        this.serviceUrl = serviceUrl;
    }

    public String getServiceSummary()
    {
        return serviceSummary;
    }

    public void setServiceSummary(String serviceSummary)
    {
        this.serviceSummary = serviceSummary;
    }

    public String getServiceDesc()
    {
        return serviceDesc;
    }

    public void setServiceDesc(String serviceDesc)
    {
        this.serviceDesc = serviceDesc;
    }

    public String getServiceTags()
    {
        return serviceTags;
    }

    public void setServiceTags(String serviceTags)
    {
        this.serviceTags = serviceTags;
    }

    public String getSourceFile()
    {
        return sourceFile;
    }

    public void setSourceFile(String sourceFile)
    {
        this.sourceFile = sourceFile;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", id)
                .append("name", name)
                .append("serviceName", serviceName)
                .append("serviceUrl", serviceUrl)
                .append("serviceSummary", serviceSummary)
                .append("serviceDesc", serviceDesc)
                .append("serviceTags", serviceTags)
                .append("createUser", createUser)
                .append("createUsername", createUsername)
                .append("createTime", createTime)
                .append("sourceFile", sourceFile)
                .toString();
    }
}