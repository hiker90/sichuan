package com.jh.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 * gis_file
 * @author 
 */
public class GisFile implements Serializable {
    /**
     * 服务ID
     */
    private Long id;

    /**
     * 关联GIS服务ID
     */
    private Long serviceId;

    /**
     * 文件UUID
     */
    private String uuid;

    /**
     * 文件原名
     */
    private String filename;

    /**
     * 文件大小
     */
    private Long filesize;

    /**
     * 上传用户ID
     */
    private Long createUser;

    /**
     * 上传用户名
     */
    private String createUsername;

    /**
     * 上传时间
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Long getFilesize() {
        return filesize;
    }

    public void setFilesize(Long filesize) {
        this.filesize = filesize;
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

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", id)
                .append("serviceId", serviceId)
                .append("uuid", uuid)
                .append("filename", filename)
                .append("filesize", filesize)
                .append("createUser", createUser)
                .append("createUsername", createUsername)
                .append("createTime", createTime)
                .toString();
    }
}