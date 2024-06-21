package com.jh.web.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.web.multipart.MultipartFile;

public class ServiceFileModel
{
    private String name;

    private String serviceName;

    private String serviceUrl;

    private String serviceSummary;

    private String serviceDesc;

    private String serviceTags;

    private MultipartFile sourceFile;

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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
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

    public MultipartFile getSourceFile()
    {
        return sourceFile;
    }

    public void setSourceFile(MultipartFile sourceFile)
    {
        this.sourceFile = sourceFile;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("name", name)
                .append("serviceName", serviceName)
                .append("serviceUrl", serviceUrl)
                .append("serviceSummary", serviceSummary)
                .append("serviceDesc", serviceDesc)
                .append("serviceTags", serviceTags)
                .append("sourceFile", sourceFile)
                .toString();
    }
}
