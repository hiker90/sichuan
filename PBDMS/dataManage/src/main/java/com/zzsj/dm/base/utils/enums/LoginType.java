package com.zzsj.dm.base.utils.enums;

/**
 * 登录类型枚举类
 *
 * @author jh
 */
public enum LoginType
{
    /**
     * 密码登录
     */
    PASSWORD("password"),
    /**
     * 免密码登录
     */
    NOPASSWD("nopasswd");

    private String desc;

    LoginType(String desc)
    {
        this.desc = desc;
    }

    public String getDesc()
    {
        return desc;
    }
}