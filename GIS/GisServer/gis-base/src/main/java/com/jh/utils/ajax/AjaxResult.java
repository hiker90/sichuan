package com.jh.utils.ajax;

import com.alibaba.fastjson.JSONObject;
import com.jh.enums.ErrorCode;

public class AjaxResult {
    public static String success() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("retcode", ErrorCode.SUCCESS.ordinal());
        jsonObject.put("msg", "");
        return jsonObject.toJSONString();
    }

    public static String error(String msg) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("retcode", ErrorCode.ERR_ERR.ordinal());
        jsonObject.put("msg", msg);
        return jsonObject.toJSONString();
    }

    public static String error(String msg, ErrorCode errorcode) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("retcode", errorcode.ordinal());
        jsonObject.put("msg", msg);
        return jsonObject.toJSONString();
    }
}
