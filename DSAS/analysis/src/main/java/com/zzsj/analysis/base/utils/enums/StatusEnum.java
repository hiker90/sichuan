package com.zzsj.analysis.base.utils.enums;

/**
 * @author ：zbya
 * @date ：Created in 2020/4/28 18:38
 * @description：状态码
 */
public enum StatusEnum {
    DATA_FLAG_NORMAL(0, "正常的数据标识"),
    DATA_FLAG_DELETE(1, "删除的数据标识"),
    DATA_FLAG_ZERO(0, "数据标识0"),
    DATA_FLAG_ONE(1, "数据标识1"),
    DATA_FLAG_TWO(2, "数据标识2"),
    DATA_FLAG_THREE(3, "数据标识3"),
    DATA_FLAG_FOUR(4, "数据标识4"),
    DATA_FLAG_UN_NORMAL(-1, "不正常的数据标识"),
    ;
    private int code;
    private String value;

    StatusEnum(int code) {
        this.code = code;
    }

    StatusEnum(int code, String value) {
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
