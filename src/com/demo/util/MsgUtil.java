package com.demo.util;


import com.demo.common.model.Msg;

/**
 * @author gery
 * @date 2018年5月3日
 * @content 返回值枚举
 */
public class MsgUtil {
	
	/**
	 * @content 成功登陆后的返回值
	 * @param data 返回数据
	 * @return Msg
	 */
	public static Msg successMsg(Object data) {
		return new Msg(data);
	}
	
	/**
	 * @content 异常返回错误
	 * @param message 错误信息
	 * @return Msg
	 */
	public static Msg errorMsg(String message) {
		return new Msg(500,message);
	}

	/**
	 * 未登录
	 */
	public static Msg LoginErrorMsg = new Msg(1, "Not Login");
	
	/**
	 * 非法传参
	 */
	public static Msg illegalPara = new Msg(2,"Illegal Transfer Parameter");
	
	/**
	 * 无结果返回
	 */
	public static Msg NoResult = new Msg(3,"No Result");
}
