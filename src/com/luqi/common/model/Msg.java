package com.luqi.common.model;

import com.jfinal.kit.JsonKit;

/**
 * @author gery
 * @date 2018年5月3日
 * @content 返回前端的信息统一化,包含:
 * 	code	状态
 * 	message	信息
 * 	data	数据
 */
public class Msg {
	public int code = 200;
	public String message = "Success";
	public Object data;

	/**
	 * @param dataSql
	 * @content 自定义状态与信息,不返回数据
	 */
	public Msg(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	
	/**
	 * @param data
	 * @content 默认正常返回并传递数据
	 */
	public Msg(Object data) {
		this.data = data;
	}
	public Msg(Object data,String message) {
		this.data = data;
		this.message = message;
	}

	public int getcode() {
		return code;
	}

	public void setcode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		//return "Msg [code=" + code + ", message=" + message + ", data=" + data + "]";
		return "{\"code\":"+ code +",\"message\":" + message + ",\"data\":" + data + "}";
	}
	/*
	 2018年11月26日 coco 注解：
	*/
	public void action() {
		//JsonKit.toJson()
	}

}
