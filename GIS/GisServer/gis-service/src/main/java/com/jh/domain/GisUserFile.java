package com.jh.domain;

import java.io.Serializable;

/**
 * gis_user_file
 * @author 
 */
public class GisUserFile implements Serializable {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 文件ID
     */
    private Long fileId;

    private static final long serialVersionUID = 1L;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }
}