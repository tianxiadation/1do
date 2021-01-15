package com.luqi.util;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.LogKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class JsonUtil {
	private static Logger log=Logger.getLogger(JsonUtil.class);

	/*
	 2018年6月21日 方升群
	*/
	public static JSONObject getJSONObject(HttpServletRequest request) {
		String meg=HttpKit.readData(request);
		JSONObject json=JSON.parseObject(meg);
		LogKit.info("Request parameters:" + json);
		return json;
	}
	public static LinkedHashMap<String, Object> getMap(int code,String message){
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		 map.put("code", code);
		 map.put("message", message);
		 log.error(map);
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
	/*
	 2019年4月28日 coco 注解：
	*/
	public static String getJsonString(String value) {
		JSONObject json=new JSONObject();
		json.put("text", value);
		return json.toString();
	}
	
	public static void main(String[] args) {
		String[] name={"a","b","c"};
		String[] age={"1","2","3"};
		System.out.println(Arrays.deepToString(name));
	}
	/**   
	　* 描述：   打标签接口参数
	　* 创建人：coco   
	　* 创建时间：2019年9月26日 下午4:56:00         
	*/
	public static JSONObject getSegwordParameter(String content) {
		String label=HttpUtil.doPost(UrlUtil.segword_api+"/Aot/getToken", getTokenParameter().toString());		
		 JSONObject json=JSON.parseObject(label);
		 String authtoken="";
			if(json.getInteger("code")==2000) {
				JSONObject json1=json.getJSONObject("data");
				JSONObject arr=json1.getJSONObject("result");
				
				authtoken= arr.getString("authtoken");
			}		
		JSONObject paramJson=new JSONObject();
		paramJson.put("client", 0);
		paramJson.put("validate", 0);
		paramJson.put("authtoken", authtoken);
		JSONObject paramJson1=new JSONObject();
		paramJson1.put("text", content);
		paramJson1.put("businessId", null);
		paramJson1.put("organizationId", null);
		paramJson.put("params", paramJson1);
		return paramJson;
	}
	/**   
	 * 描述：   打标签接口参数
	 * 创建人：coco   
	 * 创建时间：2019年9月26日 下午4:56:00         
	 */
	public static JSONObject getTokenParameter() {
		JSONObject paramJson=new JSONObject();
		
		paramJson.put("userName", "fangshengqun");
		paramJson.put("source", "1do");
		return paramJson;
	}
	
}