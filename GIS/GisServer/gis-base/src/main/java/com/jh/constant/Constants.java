package com.jh.constant;

/**
 * 通用常量信息
 *
 */
public class Constants
{
    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * 通用成功标识
     */
    public static final String SUCCESS = "0";

    /**
     * 通用失败标识
     */
    public static final String FAIL = "1";

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 注册
     */
    public static final String REGISTER = "Register";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 当前记录起始索引
     */
    public static final String PAGE_NUM = "pageNum";

    /**
     * 每页显示记录数
     */
    public static final String PAGE_SIZE = "pageSize";

    /**
     * 排序列
     */
    public static final String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    public static final String IS_ASC = "isAsc";

    /**
     * HEAD中鉴权TOKEN
     */
    public static final String ACCESS_TOKEN = "Authorization";


    /**
     * redis-key-前缀-shiro:cache:
     * 用于储存用户AuthorizationInfo，权限信息
     */
    public static final String PREFIX_SHIRO_CACHE = "shiro:cache:";

    /**
     * redis-key-前缀-shiro:user:
     * 用于储存用户信息
     */
    public static final String PREFIX_SHIRO_USER_CACHE = "shiro:user:";

    /**
     * redis-key-前缀-shiro:token:
     * 用于储存用户token及过期时间
     */
    public static final String PREFIX_SHIRO_TOKEN = "shiro:token:";

    public static final String CLAIM_LOGINNAME = "loginname";

    public static final String CLAIM_SALT = "salt";
}
