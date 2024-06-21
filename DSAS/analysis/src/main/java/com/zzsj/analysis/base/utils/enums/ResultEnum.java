package com.zzsj.analysis.base.utils.enums;

/**
 * @Author: urtica
 * @Date: 2018/4/10 13:50.
 * Stay  hungry，Stay  foolish！
 * @Description:返回结果常量
 */
public enum ResultEnum {
    UNKONW_ERROR("Res-1", "未知错误"), SUCCESS("Res0", "成功"),
    LIST_FAIL("Res1", "查询时发生错误了，请联系管理员。"),
    SAVE_SUCCESS("Res2", "保存成功"),
    SAVE_FAIL("Res3", "保存时发生错误了，请联系管理员。"),
    DELETE_SUCCESS("Res4", "删除成功"),
    DELETE_FAIL("Res5", "删除时发生错误了，请联系管理员。"),
    NO_ENUM_ELEMENT_MATCHES("Res6","没有匹配的枚举常量"),
    DEAL_FIAL("Res7","处理失败，请联系管理员。"),
    DEAL_SUCCESS("Res8","处理成功。"),
    IMPORT_SUCCESS("Res9","导入成功"),
    IMPORT_FIAL("Res10","导入失败"),
    CHECK_SUCCESS("Res11","审核成功"),
    CHECK_FAIL("Res12","审核失败"),
    APPLY_SUCCESS("Res13","申请成功"),
    APPLY_FAIL("Res14","申请失败"),
    CALCULATE_FAIL("Res15","计算失败，请联系管理员"),
    ILLEGAL_OPRATION("Res16","非法操作"),
    ILLEGAL_DATE("Res17","存在重复数据，请删除后重新计算")
    ;
    private String code;
    private String value;

    ResultEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
