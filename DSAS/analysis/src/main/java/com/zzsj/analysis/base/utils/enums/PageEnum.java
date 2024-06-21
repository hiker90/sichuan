package com.zzsj.analysis.base.utils.enums;

/**
 * @Author: urtica
 * @Date: 2018/4/10 13:55.
 * Stay  hungry，Stay  foolish！
 * @Description:分页功能常量枚举
 */
public enum PageEnum {
     PAGE_DEFAULT(0,"分页参数默认值为ZERO")
    ,PAGE_INDEX(1,"当前页码")
    ,PAGE_SIZE(10,"分页大小")
    ,RECORD_COUNT(0, "总记录数")
    ,PAGE_COUNT(0, "总页数")
    ,PAGE_CHANGE_DEFAULT(0,"SQL执行语句默认修改条数")
    ,PAGE_START(0,"查询下标开始值")
    ;
    private int code;
    private String value;

    PageEnum(int code) {
        this.code = code;
    }

    PageEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
