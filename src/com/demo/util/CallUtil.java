package com.demo.util;

public class CallUtil {
	 /*
	 2018年8月6日下午3:38:11 coco
	*/
	/*public void getsessionName() {
		JSONObject json1=JsonUtil.getJSONObject(getRequest());
		JSONObject json= new JSONObject();
		JSONObject json2= new JSONObject();
		JSONObject json3= new JSONObject();
		JSONObject json4= new JSONObject();
		json.put("appName", "launchr");
		json.put("appToken", "verify-code");
		json.put("userName", "NO6lZyJjYRCAKd9R");
		json.put("userToken", json1.getString("LoginToken"));
		json.put("userName", json1.getString("loginName"));
		json.put("sessionName", json1.getString("GROUPID"));
		json2.put("param", json);
		json3.put("body", json2);
		json4.put("async",false);
		json4.put("authToken","NhHCGqeKtkK0dFnznZxP9FxeTF8=");
		json4.put("companyCode","xcgov");
		json4.put("companyShowID","b5WJZ1bRLDCb7x2B");
		json4.put("language","zh-cn");
		json4.put("loginName","NO6lZyJjYRCAKd9R");
		json4.put("resourceUri","/Chat-Module/chat/session");
		json4.put("type","POST");
		json4.put("userName","boyd");
		json3.put("header",json4);
	   String str=HttpUtil.doPost1("http://xcgovapi.hzxc.gov.cn/Chat-Module/chat/session", json3.toString());
	   JSONObject json33 = JSON.parseObject(str);
		String Data1=json33.getString("Body");
		JSONObject json22 = JSON.parseObject(Data1);
		String Data2=json22.getString("response");
		JSONObject json11 = JSON.parseObject(Data2);
		String Data=json11.getString("Data");
		JSONObject json333 = JSON.parseObject(Data);
		String Data22=json333.getString("data");
		renderJson(Data22);
	}*/
}
