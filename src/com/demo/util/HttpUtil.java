package com.demo.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.demo.common.model.T1doBase;
import com.jfinal.plugin.activerecord.Record;


public class HttpUtil {
	public static  String doPost12(String url){
		return url;
		/*OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
		  .url("http://172.16.8.18:8080/1call/getSchemeStart?id=69&schemeStart=3&examineTime=1111111111")
		  .post(null)
		  .addHeader("Cache-Control", "no-cache")
		  .addHeader("Postman-Token", "19c70b29-1fb8-4358-af60-347b32a9f91f")
		  .build();

		Response response = client.newCall(request).execute();   */                          
	      
	    }
	public static  String doPost11(String url){
		HttpClient httpClient = HttpClients.custom().build();
		HttpPost httpPost=new HttpPost(url);
		String result="";
		try {
			StringEntity entity=new StringEntity("","utf8");
	    		entity.setContentType("application/json");
	    		httpPost.setEntity(entity);
			HttpResponse response=httpClient.execute(httpPost);
			
			
			if(response.getStatusLine().getStatusCode()==200){
				//HttpResponseProxy{HTTP/1.1 200 OK [Content-Length: 172, Content-Type: application/json; charset=utf-8, Server: Microsoft-IIS/8.0, X-Powered-By: ASP.NET, Date: Fri, 19 Jan 2018 03:41:44 GMT] ResponseEntityProxy{[Content-Type: application/json; charset=utf-8,Content-Length: 172,Chunked: false]}}
				result=EntityUtils.toString(response.getEntity(),"UTF-8");
				
			}        
		}catch (Exception e){
			e.printStackTrace();
			//System.out.println(url+"--interface Exception");
		}finally{
			
			
		}
		return   result;                                              
		
	}
	public static String doGet(String url,String LoginToken,String UserName){
		HttpClientBuilder builder = HttpClients.custom();
		CloseableHttpClient httpClient = builder.build();
		HttpGet get=new HttpGet(url);
        //httpPost.addHeader("Cookie", "LoginToken="+LoginToken+";UserName="+UserName);
		get.addHeader("Cookie", "LoginToken="+LoginToken+";UserName="+UserName);
		String result="";
		try {
			CloseableHttpResponse response = httpClient.execute(get);
			try{
				if(response.getStatusLine().getStatusCode()==200){
					HttpEntity entity = response.getEntity();
					result=EntityUtils.toString(entity,"utf8");
				}
			}finally{
				response.close();
			}
				
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				httpClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
		
		
	}
	public static String doGet11(String url){
		HttpClientBuilder builder = HttpClients.custom();
		CloseableHttpClient httpClient = builder.build();
		HttpGet get=new HttpGet(url);
		String result="";
		try {
			CloseableHttpResponse response = httpClient.execute(get);
			try{
				if(response.getStatusLine().getStatusCode()==200){
					HttpEntity entity = response.getEntity();
					result=EntityUtils.toString(entity,"utf8");
				}
			}finally{
				response.close();
			}
			
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				httpClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
		
		
	}
	
	public static JSONObject doGet1(String url,String LoginToken,String UserName){
		HttpClientBuilder builder = HttpClients.custom();
		CloseableHttpClient httpClient = builder.build();
		HttpGet get=new HttpGet(url);
		//httpPost.addHeader("Cookie", "LoginToken="+LoginToken+";UserName="+UserName);
		get.addHeader("Cookie", "LoginToken="+LoginToken+";UserName="+UserName);
		String result="";
		try {
			CloseableHttpResponse response = httpClient.execute(get);
			try{
				if(response.getStatusLine().getStatusCode()==200){
					HttpEntity entity = response.getEntity();
					result=EntityUtils.toString(entity,"utf8");
				}
			}finally{
				response.close();
			}
			
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				httpClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		JSONObject json33 = JSON.parseObject(result);
		String Data1=json33.getString("Body");
		JSONObject json22 = JSON.parseObject(Data1);
		String Data2=json22.getString("response");
		JSONObject json11 = JSON.parseObject(Data2);
		String Data=json11.getString("Data");
		JSONObject json1 = JSON.parseObject(Data);
		return json1;
		//return result;		
	}
	public static String getParameter(JSONObject json,String url,int isContainChildDeptMember,String parentId){//T1doBase t
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();  
		 map.put("async", false);
		 map.put("authToken", json.getString("LoginToken"));
		 map.put("companyCode", "xcgov");
		 map.put("companyShowID", "b5WJZ1bRLDCb7x2B");
		 map.put("language", "zh-cn");
		 map.put("loginName", json.getString("loginName"));
		 map.put("resourceUri", url);
		 map.put("type", "GET");
		 map.put("userName", json.getString("username"));
		 LinkedHashMap<String, Object> map1 = new LinkedHashMap<String, Object>();  
		// map1.put("size","100");
		 map1.put("isContainChildDeptMember",isContainChildDeptMember);
		 map1.put("parentId",parentId);
         LinkedHashMap<String, Object> map2 = new LinkedHashMap<String, Object>();  
		 map2.put("param",map1);
		 JSONObject map3 = new JSONObject();  
		 map3.put("header",map);
		 map3.put("body",map2);
		 
		return map3.toString();
		
	}
	public static String getParameter1(JSONObject json,String url,int isContainChildDeptMember,String deptId){//T1doBase t
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();  
		map.put("async", false);
		map.put("authToken", json.getString("LoginToken"));
		map.put("companyCode", "xcgov");
		map.put("companyShowID", "b5WJZ1bRLDCb7x2B");
		map.put("language", "zh-cn");
		map.put("loginName", json.getString("loginName"));
		map.put("resourceUri", url);
		map.put("type", "GET");
		map.put("userName", json.getString("username"));
		LinkedHashMap<String, Object> map1 = new LinkedHashMap<String, Object>();  
		// map1.put("size","100");
		//String str=HttpUtil.doGet("http://xcgov.hzxc.gov.cn/Base-Module/CompanyUser/GetList?isContainChildDeptMember="+json2.getIntValue("isContainChildDeptMember")+"&sortColumn=U_DEPT_SORT&sortAscending=true&deptId="+json2.getString("deptId"),json.getString("LoginToken"),json.getString("loginName"));

		map1.put("isContainChildDeptMember",isContainChildDeptMember);
		map1.put("deptId",deptId);
		map1.put("sortColumn","U_DEPT_SORT");
		map1.put("sortAscending",true);
		LinkedHashMap<String, Object> map2 = new LinkedHashMap<String, Object>();  
		map2.put("param",map1);
		JSONObject map3 = new JSONObject();  
		map3.put("header",map);
		map3.put("body",map2);
		
		return map3.toString();
		
	}
	public static String getParameter1(JSONObject json,String url,int isContainChildDeptMember,int createPage,int pageSize,String searchKey){//T1doBase t
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();  
		map.put("async", false);
		map.put("authToken", json.getString("LoginToken"));
		map.put("companyCode", "xcgov");
		map.put("companyShowID", "b5WJZ1bRLDCb7x2B");
		map.put("language", "zh-cn");
		map.put("loginName", json.getString("loginName"));
		map.put("resourceUri", url);
		map.put("type", "GET");
		map.put("userName", json.getString("username"));
		LinkedHashMap<String, Object> map1 = new LinkedHashMap<String, Object>();  
		//String str=HttpUtil.getParameter1(json, "/Base-Module/CompanyUser/GetList",json2.getIntValue("isContainChildDeptMember"),json2.getString("deptId"),json2.getIntValue("createPage"),json2.getIntValue("pageSize"),json2.getString("searchKey"));

		map1.put("isContainChildDeptMember",isContainChildDeptMember);

		map1.put("sortColumn","U_DEPT_SORT");
		map1.put("createPage",createPage);
		map1.put("pageSize",pageSize);
		map1.put("searchKey",searchKey);
				LinkedHashMap<String, Object> map2 = new LinkedHashMap<String, Object>();  
		map2.put("param",map1);
		JSONObject map3 = new JSONObject();  
		map3.put("header",map);
		map3.put("body",map2);
		
		return map3.toString();
		
	}
	public static String getParameter1(JSONObject json,String url,String SHOW_ID){//T1doBase t
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();  
		map.put("async", false);
		map.put("authToken", json.getString("LoginToken"));
		map.put("companyCode", "xcgov");
		map.put("companyShowID", "b5WJZ1bRLDCb7x2B");
		map.put("language", "zh-cn");
		map.put("loginName", json.getString("loginName"));
		map.put("resourceUri", url);
		map.put("type", "GET");
		map.put("userName", json.getString("username"));
		LinkedHashMap<String, Object> map1 = new LinkedHashMap<String, Object>();  
		//String str=HttpUtil.getParameter1(json, "/Base-Module/CompanyUser/GetList",json2.getIntValue("isContainChildDeptMember"),json2.getString("deptId"),json2.getIntValue("createPage"),json2.getIntValue("pageSize"),json2.getString("searchKey"));
		
	
		map1.put("showId",SHOW_ID);
		LinkedHashMap<String, Object> map2 = new LinkedHashMap<String, Object>();  
		map2.put("param",map1);
		JSONObject map3 = new JSONObject();  
		map3.put("header",map);
		map3.put("body",map2);
		
		return map3.toString();
		
	}
	public static String getParameter(JSONObject json,String url){//T1doBase t
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();  
		 map.put("async", false);
		 map.put("authToken", json.getString("LoginToken"));
		 map.put("companyCode", "xcgov");
		 map.put("companyShowID", "b5WJZ1bRLDCb7x2B");
		 map.put("language", "zh-cn");
		 map.put("loginName", json.getString("loginName"));
		 map.put("resourceUri", url);
		 map.put("type", "GET");
		 map.put("userName", json.getString("username"));
		 LinkedHashMap<String, Object> map1 = new LinkedHashMap<String, Object>();  
		 map1.put("size","100");
         LinkedHashMap<String, Object> map2 = new LinkedHashMap<String, Object>();  
		 map2.put("param",map1);
		 JSONObject map3 = new JSONObject();  
		 map3.put("header",map);
		 map3.put("body",map2);
		 
		return map3.toString();
		
	}
	public static String getParameter(T1doBase t,JSONObject json,List<String> list,List<String> list1){//T1doBase t
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();  
		 map.put("async", false);
		 map.put("authToken", json.getString("LoginToken"));
		 map.put("companyCode", "xcgov");
		 map.put("companyShowID", "b5WJZ1bRLDCb7x2B");
		 map.put("language", "zh-cn");
		 map.put("loginName", json.getString("loginName"));
		 map.put("resourceUri", "/Base-Module/Message");
		 map.put("type", "PUT");
		 map.put("userName", json.getString("trueName"));
		 LinkedHashMap<String, Object> map1 = new LinkedHashMap<String, Object>();  
		 map1.put("M_FROM","System_Auto");
         map1.put("M_FROM_NAME","System_Auto");
         map1.put("M_TO",list);
         map1.put("M_TO_NAME",list1);
         map1.put("A_SHOW_ID","yKPjKzWZPPFGyzK9");
         map1.put("M_TITLE",t.getOTitle());
         
         //map1.put("M_CONTENT",t.getODescribe());
         map1.put("M_CONTENT",t);
       //  map1.put("RM_SHOW_ID",t.getShowId());
       //  map1.put("RM_DATA",t.getShowId());
           map1.put("RM_SHOW_ID",t.getShowId());
           map1.put("RM_DATA","");
         map1.put("M_READ_STATUS", 0);
         map1.put("M_HANDLE_STATUS", 0);
         map1.put("M_TYPE","0");
         map1.put("M_APP_TYPE","SEND");
         map1.put("M_TRANS_TYPE","");
         map1.put("M_REMARK","");
         map1.put("M_GET_DETAIL_STATUS",0);
         map1.put("IS_NOT_PUSH",0);
         LinkedHashMap<String, Object> map2 = new LinkedHashMap<String, Object>();  
		 map2.put("param",map1);
		 JSONObject map3 = new JSONObject();  
		 map3.put("header",map);
		 map3.put("body",map2);
		 
		return map3.toString();
		
	}
	public static String getParameter1(String showid,String title,JSONObject json,String loginName,String trueName,JSONObject record){//T1doBase t
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();  
		/*json.put("LoginToken", token);
	        json.put("UserName", userId);
	        json.put("cShowId", cShowId);*/
		map.put("async", false);
		map.put("authToken", json.getString("LoginToken"));
		map.put("companyCode", "xcgov");
		map.put("companyShowID", "b5WJZ1bRLDCb7x2B");
		map.put("language", "zh-cn");
		map.put("loginName", json.getString("loginName"));
		map.put("resourceUri", "/Base-Module/Message");
		map.put("type", "PUT");
		map.put("userName", json.getString("trueName"));
		LinkedHashMap<String, Object> map1 = new LinkedHashMap<String, Object>();  
		map1.put("M_FROM","System_Auto");
		map1.put("M_FROM_NAME","System_Auto");
		// JSONObject json1=HttpUtil.loginIm1(t.getOExecutor());
		 List<String> list=new ArrayList<String>();
		 list.add(loginName);
		map1.put("M_TO",list);
		 List<String> list1=new ArrayList<String>();
		  list1.add(trueName);
		map1.put("M_TO_NAME",list1);
		map1.put("A_SHOW_ID","yKPjKzWZPPFGyzK9");
		map1.put("M_TITLE","1do");
		map1.put("M_CONTENT",record.toString());
		map1.put("RM_SHOW_ID",showid);
		map1.put("RM_DATA","");
		map1.put("M_READ_STATUS", 0);
		map1.put("M_HANDLE_STATUS", 0);
		map1.put("M_TYPE","0");
		map1.put("M_APP_TYPE","SEND");
		map1.put("M_TRANS_TYPE","");
		map1.put("M_REMARK","");
		map1.put("M_GET_DETAIL_STATUS",0);
		map1.put("IS_NOT_PUSH",0);
		LinkedHashMap<String, Object> map2 = new LinkedHashMap<String, Object>();  
		map2.put("param",map1);
		JSONObject map3 = new JSONObject();  
		map3.put("header",map);
		map3.put("body",map2);
		
		return map3.toString();
		
	}
	public static String getParameter2(String showid,String loginName,String trueName,JSONObject record,String title){//T1doBase t
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();  
		map.put("async", false);
		map.put("authToken", "NhHCGqeKtkK0dFnznZxP9FxeTF8=");
		map.put("companyCode", "xcgov");
		map.put("companyShowID", "b5WJZ1bRLDCb7x2B");
		map.put("language", "zh-cn");
		//map.put("loginName", "NO6lZyJjYRCAKd9R");
		map.put("loginName", loginName);
		map.put("resourceUri", "/Base-Module/Message");
		map.put("type", "PUT");
		//map.put("userName", "boyd");
		map.put("userName", trueName);
		LinkedHashMap<String, Object> map1 = new LinkedHashMap<String, Object>();  
		//map1.put("M_FROM","System_Auto");
		//map1.put("M_FROM_NAME","System_Auto");
		map1.put("M_FROM",loginName);
		map1.put("M_FROM_NAME",trueName);
		List<String> list=new ArrayList<String>();
		list.add(loginName);
		map1.put("M_TO",list);
		List<String> list1=new ArrayList<String>();
		list1.add(trueName);
		map1.put("M_TO_NAME",list1);
		map1.put("A_SHOW_ID","yKPjKzWZPPFGyzK9");
		map1.put("M_TITLE",title);
		map1.put("M_CONTENT",record.toString());
		map1.put("RM_SHOW_ID",showid);
		map1.put("RM_DATA","");
		map1.put("M_READ_STATUS", 0);
		map1.put("M_HANDLE_STATUS", 0);
		map1.put("M_TYPE","0");
		map1.put("M_APP_TYPE","SEND");
		map1.put("M_TRANS_TYPE","");
		map1.put("M_REMARK","");
		map1.put("M_GET_DETAIL_STATUS",0);
		map1.put("IS_NOT_PUSH",0);
		LinkedHashMap<String, Object> map2 = new LinkedHashMap<String, Object>();  
		map2.put("param",map1);
		JSONObject map3 = new JSONObject();  
		map3.put("header",map);
		map3.put("body",map2);
		
		return map3.toString();
		
	}
	public static void main(String[] args) {
		//System.out.println(getParameter());
		String data=login("18868724827");
		String str=getLogin(data,"http://xcgov.hzxc.gov.cn/Base-Module/CompanyUserLogin/Login");
		System.out.println(str);
		//JSONObject json=getToken(str);
		//System.out.println(json.toString());
		//String str=doPost("http://m.hzxc.gov.cn/Base-Module/CompanyUser/GetList/GetUser",data);

	}
	public static String doPost(String url,JSONObject json) throws UnsupportedEncodingException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost post=new HttpPost(url);
		//post.addHeader("Cookie", "LoginToken="+LoginToken+";UserName="+UserName);
		StringEntity entity = new StringEntity(json.toJSONString(),"utf-8");
		post.setEntity(entity);
		CloseableHttpResponse response = httpclient.execute(post);
		if(response.getStatusLine().getStatusCode()==200) {
			HttpEntity result = response.getEntity();
			return EntityUtils.toString(result, "utf-8");
		}
		return ""+response.getStatusLine().getStatusCode();
	}
	public static  String doPost1(String url,String outStr){
	      HttpClient httpClient = HttpClients.custom().build();
	        HttpPost httpPost=new HttpPost(url);
	        String result="";
	        try {
	        	StringEntity entity=new StringEntity(outStr,"utf8");
	    		entity.setContentType("application/json");
	    		httpPost.setEntity(entity);
	    		
	            HttpResponse response=httpClient.execute(httpPost);
	            if(response.getStatusLine().getStatusCode()==200){
	            	//HttpResponseProxy{HTTP/1.1 200 OK [Content-Length: 172, Content-Type: application/json; charset=utf-8, Server: Microsoft-IIS/8.0, X-Powered-By: ASP.NET, Date: Fri, 19 Jan 2018 03:41:44 GMT] ResponseEntityProxy{[Content-Type: application/json; charset=utf-8,Content-Length: 172,Chunked: false]}}
	            result=EntityUtils.toString(response.getEntity(),"UTF-8");
	               
	            }        
	        }catch (Exception e){
	            e.printStackTrace();
	        	//System.out.println(url+"--interface Exception");
	        }finally{
	        	
	        }
	       return   result;                                              
	      
	    }
	public static  JSONObject doPost2(String url,String outStr){
		HttpClient httpClient = HttpClients.custom().build();
		HttpPost httpPost=new HttpPost(url);
		String result="";
		try {
			StringEntity entity=new StringEntity(outStr,"utf8");
			entity.setContentType("application/json");
			httpPost.setEntity(entity);
			
			HttpResponse response=httpClient.execute(httpPost);
			if(response.getStatusLine().getStatusCode()==200){
				//HttpResponseProxy{HTTP/1.1 200 OK [Content-Length: 172, Content-Type: application/json; charset=utf-8, Server: Microsoft-IIS/8.0, X-Powered-By: ASP.NET, Date: Fri, 19 Jan 2018 03:41:44 GMT] ResponseEntityProxy{[Content-Type: application/json; charset=utf-8,Content-Length: 172,Chunked: false]}}
				result=EntityUtils.toString(response.getEntity(),"UTF-8");
				
			}        
		}catch (Exception e){
			e.printStackTrace();
			//System.out.println(url+"--interface Exception");
		}finally{
			
		}
		JSONObject json33 = JSON.parseObject(result);
		String Data1=json33.getString("Body");
		JSONObject json22 = JSON.parseObject(Data1);
		String Data2=json22.getString("response");
		JSONObject json11 = JSON.parseObject(Data2);
		String Data=json11.getString("Data");
		JSONObject json2 = JSON.parseObject(Data);
		return   json2;                                              
		
	}
	
	//登录获取token和username
	public static JSONObject loginIm(String imName) {
		String data = login(imName);
		//String str = getLogin(data, "http://xcgov.hzxc.gov.cn/Base-Module/CompanyUserLogin/Login");
		String str = getLogin(data, "http://xcgov.hzxc.gov.cn/Base-Module/CompanyUserLogin/UserValidate");
		System.out.println(str);
		JSONObject json = getToken(str);
		return json;
	}
	public static JSONObject loginIm1(String imName) {
		String data = login(imName);
		//String str = getLogin(data, "http://xcgov.hzxc.gov.cn/Base-Module/CompanyUserLogin/Login");
		//String str = getLogin(data, "http://xcgov.hzxc.gov.cn/Base-Module/CompanyUserLogin/UserValidate");
		String str = getLogin(data, "http://1call.avatar.com/Base-Module/CompanyUserLogin/Login");
		JSONObject json = getToken1(str);
		return json;
	}
	
	//获得登入接口参数
			public static String login(String name){
				//int i=(100000+(int)(Math.random()*900000));
				String loginName=name;
				long timestamp =new Date().getTime();
				int nonce=100000+(int)(Math.random()*900000);
				//String appId="3OjQ5nZLGmId1AGV"  ;
				String appSecret="1Call";
				List<String> list = new ArrayList<String>();
				list.add(loginName);
				list.add(timestamp+"");
				list.add(nonce+"");
				Collections.sort(list);  
				StringBuilder content = new StringBuilder();
				for(String s:list){
					content.append(s);
				}
			    String signature=getSha1(content.toString()+appSecret);
			    String echostr="abcdef";
			    JSONObject map=new JSONObject();
				map.put("appId", "3OjQ5nZLGmId1AGV");	
				//map.put("appId", "ZmOaK1y1Zehx2qX1");	
				map.put("loginName", loginName);
				map.put("nonce", nonce);
				map.put("echostr", echostr);
				map.put("timestamp", timestamp);
				map.put("signature",signature);
				return map.toString();
			}
    public static String getSha1(String str){
        if(str==null||str.length()==0){
            return null;
        }
        char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9',
                'a','b','c','d','e','f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j*2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];      
            }
            return new String(buf);
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }		
	//调用登入接口
   /* public static String getLogin(String data,String url){
		String str=HttpUtil.doPost(data,url);
		return str;	
	}	*/	
	      
	
	public static  String getLogin(String outStr,String url){
	      HttpClient httpClient = HttpClients.custom().build();
	        HttpPost httpPost=new HttpPost(url);
	        String result="";
	        try {
	        	StringEntity entity=new StringEntity(outStr,"utf8");
	    		entity.setContentType("application/json");
	    		httpPost.setEntity(entity);
	    		
	            HttpResponse response=httpClient.execute(httpPost);
	            if(response.getStatusLine().getStatusCode()==200){
	            	//HttpResponseProxy{HTTP/1.1 200 OK [Content-Length: 172, Content-Type: application/json; charset=utf-8, Server: Microsoft-IIS/8.0, X-Powered-By: ASP.NET, Date: Fri, 19 Jan 2018 03:41:44 GMT] ResponseEntityProxy{[Content-Type: application/json; charset=utf-8,Content-Length: 172,Chunked: false]}}
	            result=EntityUtils.toString(response.getEntity(),"UTF-8");
	               
	            }        
	        }catch (Exception e){
	            e.printStackTrace();
	        	//System.out.println(url+"--interface Exception");
	        }finally{
	        	
	        }
	       return   result;                                              
	      
	    }
	public static String bytesToHex(byte[] bytes) {  
	    StringBuffer md5str = new StringBuffer();  
	    // 把数组每一字节换成16进制连成md5字符串  
	    int digital;  
	    for (int i = 0; i < bytes.length; i++) {  
	        digital = bytes[i];  
	  
	        if (digital < 0) {  
	        digital += 256;  
	        }  
	        if (digital < 16) {  
	        md5str.append("0");  
	        }  
	        md5str.append(Integer.toHexString(digital));  
	    }  
	    return md5str.toString().toUpperCase();  
	    }  
	public static String getMD5(String message) {  
	    String md5str = "";  
	    try {  
	        // 1 创建一个提供信息摘要算法的对象，初始化为md5算法对象  
	        MessageDigest md = MessageDigest.getInstance("MD5");  
	  
	        // 2 将消息变成byte数组  
	        byte[] input = message.getBytes();  
	  
	        // 3 计算后获得字节数组,这就是那128位了  
	        byte[] buff = md.digest(input);  
	  
	        // 4 把数组每一字节（一个字节占八位）换成16进制连成md5字符串  
	        md5str = bytesToHex(buff);  
	  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }  
	    return md5str;  
	    }  
	
	
	/*public String getToken(String result){
		
		JSONObject json = JSON.parseObject(result);
        String Data=json.getString("Data");
        JSONObject json1 = JSON.parseObject(Data);
        String token=json1.getString("token");
        return token;
	}*/
	public static JSONObject getJSONObject(String result){
		
		
		//{"Header":{"ResCode":8200,"IsSuccess":true,"Reason":""},"Body":{"response":{"IsSuccess":true,"TotalCount":1,"Data":{"trueName":"谢洁","code":"xcgov","cShowId":"b5WJZ1bRLDCb7x2B","userName":"xiejie","userId":"XzaqamVZPDtAR3WP","echostr":"abcdef","token":"xdliiJTHZjSVN+nEZdIa51+t9g8="},"Code":8200,"Reason":""}}}
		JSONObject json33 = JSON.parseObject(result);
		String Data1=json33.getString("Body");
		JSONObject json22 = JSON.parseObject(Data1);
		String Data2=json22.getString("response");
		JSONObject json11 = JSON.parseObject(Data2);
		String Data=json11.getString("Data");
		JSONObject json1 = JSON.parseObject(Data);
		return json1;
	}
public static JSONObject getToken(String result){
	

		//{"Header":{"ResCode":8200,"IsSuccess":true,"Reason":""},"Body":{"response":{"IsSuccess":true,"TotalCount":1,"Data":{"trueName":"谢洁","code":"xcgov","cShowId":"b5WJZ1bRLDCb7x2B","userName":"xiejie","userId":"XzaqamVZPDtAR3WP","echostr":"abcdef","token":"xdliiJTHZjSVN+nEZdIa51+t9g8="},"Code":8200,"Reason":""}}}
	JSONObject json33 = JSON.parseObject(result);
        String Data1=json33.getString("Body");
        JSONObject json22 = JSON.parseObject(Data1);
        String Data2=json22.getString("response");
        JSONObject json11 = JSON.parseObject(Data2);
        String Data=json11.getString("Data");
        JSONObject json1 = JSON.parseObject(Data);
        String token=json1.getString("token");
       // String userId=json1.getString("userId");
        String userId=json1.getString("userId");
        String trueName=json1.getString("trueName");
        String userName=json1.getString("userName");
        String cShowId=json1.getString("cShowId");
        JSONObject json = new JSONObject();
       /* json.put("LoginToken", token);
        json.put("UserName", userId);*/
        json.put("cShowId", cShowId);
        json.put("LoginToken", token);
    	json.put("loginName", userId);
    	json.put("username", trueName);
    	json.put("useraccount", userName);
         //json.put("trueName", trueName);
        // json.put("UserName", userName);
        // json.put("userId", userId);
        return json;
        //String[] str = {token,userName};
      //  return str;
	}
public static JSONObject getToken1(String result){
	
	
	//{"Header":{"ResCode":8200,"IsSuccess":true,"Reason":""},"Body":{"response":{"IsSuccess":true,"TotalCount":1,"Data":{"trueName":"谢洁","code":"xcgov","cShowId":"b5WJZ1bRLDCb7x2B","userName":"xiejie","userId":"XzaqamVZPDtAR3WP","echostr":"abcdef","token":"xdliiJTHZjSVN+nEZdIa51+t9g8="},"Code":8200,"Reason":""}}}
	JSONObject json33 = JSON.parseObject(result);
	String Data1=json33.getString("Body");
	JSONObject json22 = JSON.parseObject(Data1);
	String Data2=json22.getString("response");
	JSONObject json11 = JSON.parseObject(Data2);
	String Data=json11.getString("Data");
	JSONObject json1 = JSON.parseObject(Data);
	String token=json1.getString("token");
	// String userId=json1.getString("userId");
	String userId=json1.getString("userId");
	// String trueName=json1.getString("trueName");
	String userName=json1.getString("trueName");
	//String userName=json1.getString("userName");
	String cShowId=json1.getString("cShowId");
	JSONObject json = new JSONObject();
	json.put("LoginToken", token);
	json.put("loginName", userId);
	json.put("username", userName);
	//json.put("trueName", trueName);
    json.put("cShowId", cShowId);
	//json.put("trueName", trueName);
	// json.put("UserName", userName);
	// json.put("userId", userId);
	return json;
	//String[] str = {token,userName};
	//  return str;
}
}
