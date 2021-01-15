package com.demo.common.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class Temp {
	private String before_modification;//修改前
	private String 	after_modification;//修改后
	public String getBefore_modification() {
		return before_modification;
	}
	public void setBefore_modification(String before_modification) {
		this.before_modification = before_modification;
	}
	public String getAfter_modification() {
		return after_modification;
	}
	public void setAfter_modification(String after_modification) {
		this.after_modification = after_modification;
	}
	@Override
	public String toString() {
		
		//return "{\"before_modification\":" + before_modification + ", \"after_modification\":" + after_modification + "}";
		return  JSON.toJSON(this).toString();
	}
	public Temp(String before_modification, String after_modification) {
		super();
		this.before_modification = before_modification;
		this.after_modification = after_modification;
	}
	public static void main(String[] args) {
		System.out.println(new Temp("sss","sssss").toString());
	}

}
