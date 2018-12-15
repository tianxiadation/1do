package com.demo.util;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.HttpKit;

public class JsonUtil {
	/*
	 2018年6月21日 方升群
	*/
	public static JSONObject getJSONObject(HttpServletRequest request) {
		String meg=HttpKit.readData(request);
		JSONObject json=JSON.parseObject(meg);
		return json;
	}
	public static LinkedHashMap<String, Object> getMap(int code,String message){
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();  
		 map.put("code", code);
		 map.put("message", message);
		 return map;
	}
	public static LinkedHashMap<String, Object> getMap(int code,boolean message){
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();  
		map.put("code", code);
		map.put("message", message);
		return map;
	}
	public static LinkedHashMap<String, Object> getMap(int code,int message){
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();  
		map.put("code", code);
		map.put("message", message);
		return map;
	}
}
