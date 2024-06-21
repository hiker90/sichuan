package com.jh.domain;

import java.io.Serializable;

/**
 * gis_user_service
 * @author 
 */
public class GisUserService implements Serializable {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 服务ID
     */
    private Long serviceId;

    private static final long serialVersionUID = 1L;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }
}