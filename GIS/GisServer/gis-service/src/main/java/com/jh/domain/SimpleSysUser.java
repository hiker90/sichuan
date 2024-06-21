package com.jh.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 用户简要情报
 * @author wh
 */
public class SimpleSysUser implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    private Long userId;

    /** 用户名称 */
    private String userName;

    /** 部门名称 */
    private String deptName;

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getDeptName()
    {
        return deptName;
    }

    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("userId", userId)
                .append("userName", userName)
                .append("deptName", deptName)
                .toString();
    }
}