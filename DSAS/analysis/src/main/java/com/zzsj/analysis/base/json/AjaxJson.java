package com.zzsj.analysis.base.json;


import com.zzsj.analysis.base.utils.enums.ResultEnum;

import java.util.Map;

/**
 * $.ajax后需要接受的JSON
 * 
 * @author
 * 
 */
public class AjaxJson {
	/**
	 * 是否成功
	 */
	private boolean success = true;
	/**
	 * 提示信息
	 */
	private String msg = ResultEnum.SUCCESS.getValue();
	/**
	 * 状态
	 */
	private String code="00";
	/**
	 * 信息
	 */
	private Object data = null;
	/**
	 * 其他参数
	 */
	private Map<String, Object> attributes;

	public AjaxJson() {
	}

	public AjaxJson(boolean success, String msg) {
		this.success = success;
		this.msg = msg;
	}

	public AjaxJson(boolean success, ResultEnum resultEnum){
		this.success=success;
		this.msg=resultEnum.getValue();
		this.code=resultEnum.getCode();
	}



	public AjaxJson(Object data) {
		this.data = data;
	}

	public AjaxJson(String msg, Object data) {
		this.msg = msg;
		this.data = data;
	}

	public AjaxJson(String msg) {
		this.msg = msg;
	}

	public AjaxJson(boolean success, String msg, String code , Object data){

		this.success = success;
		this.msg = msg;
		this.code=code;
		this.data = data;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/*public String getJsonStr(){
		JSONObject obj = new JSONObject();
		obj.put("success", this.isSuccess());
		obj.put("msg", this.getMsg());
		obj.put("code", this.getCode());
		obj.put("data", this.data);
		obj.put("attributes", this.attributes);
		return obj.toJSONString();
	}*/

	@Override
	public String toString() {
		return "AjaxJson{success=" + this.success + ", msg=\'" + this.msg + '\'' + ", code=\'" + this.code + '\'' + ", data=" + this.data + ", attributes=" + this.attributes + '}';
	}

}
