package com.demo.controller;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.fileupload.util.Streams;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.common.model.T1doAttr;
import com.demo.common.model.T1doBase;
import com.demo.common.model.T1doFeedback;
import com.demo.common.model.T1doFw;
import com.demo.common.model.T1doLog;
import com.demo.common.model.T1doOrder;
import com.demo.common.model.T1doPstatus;
import com.demo.common.model.T1doSet;
import com.demo.common.model.T1doStatus;
import com.demo.common.model.T1doTemp;
import com.demo.common.model.T1doType;
import com.demo.common.model.T1doUser;
import com.demo.util.FileUtil;
import com.demo.util.HttpUtil;
import com.demo.util.IDUtil;
import com.demo.util.JsonUtil;
import com.demo.util.StrUtil;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.HttpKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.upload.UploadFile;
import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.MultipartParser;
import com.oreilly.servlet.multipart.ParamPart;
import com.oreilly.servlet.multipart.Part;


public class TempController extends Controller {
	
	    /*
		 2018年7月16日下午4:58:52 方升群  //整理层审核
		*/
		public void audit() {
			JSONObject json=JsonUtil.getJSONObject(getRequest());
			//JSONObject json=getSessionAttr("1doUser");
			T1doBase t1doBase =T1doBase.dao.findFirst("select * from t_1do_base where SHOW_ID=?", json.getString("SHOW_ID"));
			sendIdo(t1doBase);
			t1doBase.setISAUDIT(2).update();
			renderJson(JsonUtil.getMap(200, "审核成功"));
		}
	    /*
		 2018年7月5日上午9:56:46 方升群   //看板搜索
		*/

		public void search() {
			JSONObject json1=JsonUtil.getJSONObject(getRequest());
			JSONObject json=getSessionAttr("1doUser");
			String temp= "%"+json1.getString("base")+"%";
			String sql="and (b.O_TITLE like ? or b.O_DESCRIBE like ? )" ;
				if(json1.getString("method").equals("medo")){	
				List<T1doBase> t3=T1doBase.medo1(json.getString("loginName"), 3,sql,temp);//待处理
				for(T1doBase t:t3){
					//t.set("LIGHTNING", t.getIdoFeedbacks44().size());
					t.set1doIsLook(json.getString("loginName"));
					t.setLIGHTNING(t.getIdoFeedbacks44().size());
				}
				List<T1doBase> t4=T1doBase.medo1(json.getString("loginName"), 4,sql,temp);//处理中
				for(T1doBase t:t4){
					t.setLIGHTNING(t.getIdoFeedbacks44().size());
					t.set1doIsLook(json.getString("loginName"));
					//t.set("LIGHTNING", t.getIdoFeedbacks4().size());
				}
				List<T1doBase> t5=T1doBase.medo1(json.getString("loginName"), 5,json1.getIntValue("num"),sql,temp);//已完成
				for(T1doBase t:t5){
					t.setLIGHTNING(t.getIdoFeedbacks44().size());
					t.set1doIsLook(json.getString("loginName"));
					//t.set("LIGHTNING", t.getIdoFeedbacks4().size());
				}
				LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();  
				 map.put("1", t3);
				 map.put("2", t4);
				 map.put("3", t5);
				 renderJson(map);
			}else if(json1.getString("method").equals("hedo")){
				List<T1doBase> t3=T1doBase.hedo1(json.getString("loginName"), 3,sql,temp);//待处理
				for(T1doBase t:t3){
					//t.setLIGHTNING(t.getIdoFeedbacks44().size());
					t.set1doIsLook(json.getString("loginName"));
					
					t.set("LIGHTNING", t.getIdoFeedbacks44().size());
				}
				List<T1doBase> t4=T1doBase.hedo1(json.getString("loginName"), 4,sql,temp);//处理中
				for(T1doBase t:t4){
					t.setLIGHTNING(t.getIdoFeedbacks44().size());
					t.set1doIsLook(json.getString("loginName"));
					//t.set("LIGHTNING", t.getIdoFeedbacks4().size());
				}
				//List<T1doBase> t5=T1doBase.hedo1(json.getString("loginName"), 5);//已完成
				List<T1doBase> t5=T1doBase.hedo1(json.getString("loginName"), 5,json1.getIntValue("num"),sql,temp);//已完成
				for(T1doBase t:t5){
					t.setLIGHTNING(t.getIdoFeedbacks44().size());
					t.set1doIsLook(json.getString("loginName"));
					//t.set("LIGHTNING", t.getIdoFeedbacks4().size());
				}
				LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();  
				 map.put("1", t3);
				 map.put("2", t4);
				 map.put("3", t5);
				 renderJson(map);
			}else{
				List<T1doBase> t3=T1doBase.fwdo1(3,json.getString("loginName"),sql,temp);//待处理
				for(T1doBase t:t3){
					t.setLIGHTNING(t.getIdoFeedbacks44().size());
					t.set1doIsLook(json.getString("loginName"));
					
					//t.set("LIGHTNING", t.getIdoFeedbacks4().size());
				}
				List<T1doBase> t4=T1doBase.fwdo1(4,json.getString("loginName"),sql,temp);//处理中
				for(T1doBase t:t4){
					t.setLIGHTNING(t.getIdoFeedbacks44().size());
					t.set1doIsLook(json.getString("loginName"));
					
					//t.set("LIGHTNING", t.getIdoFeedbacks4().size());
				}
				List<T1doBase> t5=T1doBase.fwdo1(5,json.getString("loginName"),json1.getIntValue("num"),sql,temp);//已完成
				for(T1doBase t:t5){
					t.setLIGHTNING(t.getIdoFeedbacks44().size());
					t.set1doIsLook(json.getString("loginName"));
					
					//t.set("LIGHTNING", t.getIdoFeedbacks4().size());
				}
				List<T1doBase> t6=T1doBase.fwdo2(5,json.getString("loginName"),json1.getIntValue("num"),sql,temp);//删除
				for(T1doBase t:t6){
					t.setLIGHTNING(t.getIdoFeedbacks44().size());
					t.set1doIsLook(json.getString("loginName"));
					
					//t.set("LIGHTNING", t.getIdoFeedbacks4().size());
				}
				LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();  
				map.put("1", t3);
				map.put("2", t4);
				map.put("3", t5);
				map.put("4", t6);
				renderJson(map);
			}
		}
	    /*
		 2018年7月3日下午4:29:42 方升群   //通知设置
		*/
		 @Before(Tx.class)
		public void notice() {
			JSONObject json=JsonUtil.getJSONObject(getRequest());
			 JSONObject json1=getSessionAttr("1doUser");
			 T1doPstatus t1=T1doPstatus.dao.findFirst("select * from t_1do_pstatus where SHOW_ID=? and O_USER=? and USER_TYPE !=2",json.getString("SHOW_ID"),json1.getString("loginName"));
			if(json1.getBoolean("isfw")||StrUtil.getflag(t1.getUserType(), 1)){
				T1doSet t=T1doSet.dao.findFirst("select * from t_1do_set where SHOW_ID=?",json.getString("SHOW_ID"));
				HashSet<String> hs=new HashSet<String>();
				if(StrUtil.isNotEmpty(t.getEventType())){
					String[] temp = t.getEventType().split(";");
				   for(String tem:temp){
					hs.add(tem);
				   }
				}
				
				if(json.getString("method").equals("add")){
					hs.add(json.getString("EVENT_TYPE"));
				}else{
					hs.remove(json.getString("EVENT_TYPE"));
				}
				String result="";
				for(String str:hs){
					result+=str+";";
				}
				result=result.substring(0, result.length()-1);
				t.setEventType(result).update();
				renderJson(JsonUtil.getMap(200, "修改成功"));
			}else{
				renderJson(JsonUtil.getMap(202, "权限不足"));
			}
			
			
		
			
		}
		
	    /*
		 2018年6月28日上午11:18:08 方升群  //1call转1do
		*/
		@Before(Tx.class)
		public void IcallToIdo() {
			String meg=HttpKit.readData(getRequest());
			//JSONObject json=JsonUtil.getJSONObject(getRequest());
			JSONObject json=JSONObject.parseObject(meg);
			long id=IDUtil.getUid1();
			new T1doTemp().setID(id).setBASE(meg).save();
			
			LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();  	
			map.put("ID", id+"");
			String str1=json.getString("BASE");
			JSONObject json1=JSONObject.parseObject(str1);
			String[] str=json1.getString("MESSAGE_ID").split(";");
			map.put("msg", str);	
			System.out.println(map.toString());
			renderJson(map);
		
			
				
			
		}
		public void getIcallToIdo() {
			JSONObject json=JsonUtil.getJSONObject(getRequest());
			long i=json.getLongValue("ID");
			T1doTemp t=T1doTemp.dao.findById(i);
			if(t==null){
				renderJson(JsonUtil.getMap(200, "数据不存在"));
				return;
			}
			//T1doTemp t=T1doTemp.dao.findByIdLoadColumns(json.getLongValue("ID"), "ID");
			JSONObject json1=JSONObject.parseObject(t.getBASE());
			renderJson(json1);
		}
		/*
		 2018年6月28日上午9:42:54 方升群   //要我做
		*/
		public void medo() {
			JSONObject json=getSessionAttr("1doUser");
			JSONObject json1=JsonUtil.getJSONObject(getRequest());
			List<T1doBase> t3=T1doBase.medo1(json.getString("loginName"), 3);//待处理
			for(T1doBase t:t3){
				//t.set("LIGHTNING", t.getIdoFeedbacks44().size());
				t.set1doIsLook(json.getString("loginName"));
				t.setLIGHTNING(t.getIdoFeedbacks44().size());
			}
			List<T1doBase> t4=T1doBase.medo1(json.getString("loginName"), 4);//处理中
			for(T1doBase t:t4){
				t.setLIGHTNING(t.getIdoFeedbacks44().size());
				t.set1doIsLook(json.getString("loginName"));
				//t.set("LIGHTNING", t.getIdoFeedbacks4().size());
			}
			List<T1doBase> t5=T1doBase.medo1(json.getString("loginName"), 5,json1.getIntValue("num"));//已完成
			for(T1doBase t:t5){
				t.setLIGHTNING(t.getIdoFeedbacks44().size());
				t.set1doIsLook(json.getString("loginName"));
				//t.set("LIGHTNING", t.getIdoFeedbacks4().size());
			}
			LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();  
			 map.put("1", t3);
			 map.put("2", t4);
			 map.put("3", t5);
			 renderJson(map);
		}
		/*
		 2018年6月28日上午9:42:54 方升群   /要他做
		 */
		public void hedo() {
			JSONObject json=getSessionAttr("1doUser");
			JSONObject json1=JsonUtil.getJSONObject(getRequest());
			List<T1doBase> t3=T1doBase.hedo1(json.getString("loginName"), 3);//待处理
			for(T1doBase t:t3){
				t.setLIGHTNING(t.getIdoFeedbacks44().size());
				t.set1doIsLook(json.getString("loginName"));
				
				//t.set("LIGHTNING", t.getIdoFeedbacks4().size());
			}
			List<T1doBase> t4=T1doBase.hedo1(json.getString("loginName"), 4);//处理中
			for(T1doBase t:t4){
				t.setLIGHTNING(t.getIdoFeedbacks44().size());
				t.set1doIsLook(json.getString("loginName"));
				//t.set("LIGHTNING", t.getIdoFeedbacks4().size());
			}
			//List<T1doBase> t5=T1doBase.hedo1(json.getString("loginName"), 5);//已完成
			List<T1doBase> t5=T1doBase.hedo1(json.getString("loginName"), 5,json1.getIntValue("num"));//已完成
			for(T1doBase t:t5){
				t.setLIGHTNING(t.getIdoFeedbacks44().size());
				t.set1doIsLook(json.getString("loginName"));
				//t.set("LIGHTNING", t.getIdoFeedbacks4().size());
			}
			LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();  
			 map.put("1", t3);
			 map.put("2", t4);
			 map.put("3", t5);
			 renderJson(map);
		}
		/*
		 2018年6月28日上午9:42:54 方升群   /整理层看板
		 */
		public void fwdo() {
			JSONObject json=getSessionAttr("1doUser");
			JSONObject json1=JsonUtil.getJSONObject(getRequest());
			List<T1doBase> t3=T1doBase.fwdo1(3,json.getString("loginName"));//待处理
			for(T1doBase t:t3){
				t.setLIGHTNING(t.getIdoFeedbacks44().size());
				t.set1doIsLook(json.getString("loginName"));
				
				//t.set("LIGHTNING", t.getIdoFeedbacks4().size());
			}
			List<T1doBase> t4=T1doBase.fwdo1(4,json.getString("loginName"));//处理中
			for(T1doBase t:t4){
				t.setLIGHTNING(t.getIdoFeedbacks44().size());
				t.set1doIsLook(json.getString("loginName"));
				
				//t.set("LIGHTNING", t.getIdoFeedbacks4().size());
			}
			List<T1doBase> t5=T1doBase.fwdo1(5,json.getString("loginName"),json1.getIntValue("num"));//已完成
			for(T1doBase t:t5){
				t.setLIGHTNING(t.getIdoFeedbacks44().size());
				t.set1doIsLook(json.getString("loginName"));
				
				//t.set("LIGHTNING", t.getIdoFeedbacks4().size());
			}
			List<T1doBase> t6=T1doBase.fwdo2(5,json.getString("loginName"),json1.getIntValue("num"));//已删除
			for(T1doBase t:t6){
				t.setLIGHTNING(t.getIdoFeedbacks44().size());
				t.set1doIsLook(json.getString("loginName"));
				
				//t.set("LIGHTNING", t.getIdoFeedbacks4().size());
			}
			
			LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();  
			map.put("1", t3);
			map.put("2", t4);
			map.put("3", t5);
			map.put("4", t6);
			renderJson(map);
		}
	    
		/*
		 2018年6月28日上午3:50:16 方升群  //模拟1do登入
		 */
		public void fwdoshowid(){
			List<T1doFw> t=T1doFw.dao.find("select * from t_1do_fw where icallshowid is null");
			for (T1doFw t1doFw : t) {
				t1doFw.setIcallshowid(HttpUtil.loginIm(t1doFw.getOUser()).getString("loginName")).update();
			}
			renderJson(JsonUtil.getMap(200, "id成功生成"));
		}
		public void login1do() {
			JSONObject json=JsonUtil.getJSONObject(getRequest());
			//T1doUser user=T1doUser.dao.findFirst("SELECT * from t_1do_user WHERE loginName=? or U_MAIL=? or U_MOBILE=? ",json.getString("useraccount"),json.getString("useraccount"),json.getString("useraccount"));
			//setSessionAttr("1doUser", json);
			
			T1doFw t1doFw =T1doFw.getIdoFw(json.getString("useraccount"));
			boolean isfw=t1doFw==null?false:true;
			if(StrUtil.isEmpty(json.getString("useraccount"))){
				renderJson(JsonUtil.getMap(202, "账号错误"));
				return;
			}
			
			//if(user==null){
				JSONObject json1=HttpUtil.loginIm(json.getString("useraccount"));
			    //JSONObject json2=HttpUtil.doGet1("http://xcgov.hzxc.gov.cn/Base-Module/CompanyUser?showId="+json1.getString("loginName"),json1.getString("LoginToken"),json1.getString("loginName"));
				T1doUser user=T1doUser.dao.findFirst("SELECT * from t_1do_user WHERE SHOW_ID=? ",json1.getString("loginName"));
				
				if(user==null){
					
				//JSONObject json2=HttpUtil.doGet1("http://xcgov.hzxc.gov.cn/Base-Module/CompanyUser?showId="+json1.getString("loginName"),json1.getString("LoginToken"),json1.getString("loginName"));
				String str=HttpUtil.getParameter1(json1, "/Base-Module/CompanyUser",json1.getString("loginName"));
				
				JSONObject json2   =   HttpUtil.doPost2("http://xcgovapi.hzxc.gov.cn/Base-Module/CompanyUser", str);
				
				
				T1doUser user1=json2.toJavaObject(T1doUser.class);
			    @SuppressWarnings("unchecked")
				List<String> arr=(List<String>) json2.get("D_NAME");
			    user1.setDName(arr.get(0));
			    @SuppressWarnings("unchecked")
				List<String> arr1=(List<String>) json2.get("U_DEPT_ID");
			    user1.setUDeptId(arr1.get(0));
			    user1.save();
			    json1.put("D_NAME", json2.get("D_NAME"));
				json1.put("U_DEPT_ID", json2.get("U_DEPT_ID"));
				}else{
					List<String> list=new ArrayList<String>();
					list.add(user.getDName());
					json1.put("D_NAME", list);
					List<String> list1=new ArrayList<String>();
					list1.add(user.getUDeptId());
					json1.put("U_DEPT_ID", list1);
				}
				json1.put("isfw", isfw);
				json1.put("useraccount", json.getString("useraccount"));
				
				//setSessionAttr("coco", json1);
				//renderJson(JsonUtil.getMap(200, "登入成功"));
				getSession().setMaxInactiveInterval(28800);//单位秒
				setSessionAttr("1doUser", json1);
				renderJson(json1);
				
			
		}
		
		//通讯录获取最近联系人
		public void GetContact() {
			//JSONObject json=getSessionAttr("coco");
			JSONObject json=getSessionAttr("1doUser");
			//JSONObject json=HttpUtil.loginIm(json1.getString("useraccount"));
			String str=HttpUtil.getParameter(json, "/Base-Module/CompanyUser/GetContact");
			System.out.println(str);
			String result   =   HttpUtil.doPost1("http://xcgovapi.hzxc.gov.cn/Base-Module/CompanyUser/GetContact", str);
			//String str=HttpUtil.doGet("http://xcgov.hzxc.gov.cn/Base-Module/CompanyUser/GetContact?size=100",json.getString("LoginToken"),json.getString("loginName"));
			renderJson(result);
		}
		//通讯录获取部门和部门人员列表
		public void GetList() {
			JSONObject json=getSessionAttr("1doUser");
			//JSONObject json=HttpUtil.loginIm(json1.getString("useraccount"));
			JSONObject json2=JsonUtil.getJSONObject(getRequest());
			String str=HttpUtil.getParameter(json, "/Base-Module/CompanyDept/GetList",json2.getIntValue("isContainChildDeptMember"),json2.getString("parentId"));

			String result   =   HttpUtil.doPost1("http://xcgovapi.hzxc.gov.cn/Base-Module/CompanyDept/GetList", str);

			//String str=HttpUtil.doGet("http://xcgov.hzxc.gov.cn/Base-Module/CompanyDept/GetList?isContainChildDeptMember="+json2.getIntValue("isContainChildDeptMember")+"&parentId="+json2.getString("parentId"),json.getString("LoginToken"),json.getString("loginName"));
			
			renderJson(result);
		}
		public void GetListUser() {
			JSONObject json2=JsonUtil.getJSONObject(getRequest());
			JSONObject json=getSessionAttr("1doUser");
			//JSONObject json=HttpUtil.loginIm(json1.getString("useraccount"));
			//String str=HttpUtil.doGet("http://xcgov.hzxc.gov.cn/Base-Module/CompanyUser/GetList?isContainChildDeptMember="+json2.getIntValue("isContainChildDeptMember")+"&sortColumn=U_DEPT_SORT&sortAscending=true&deptId="+json2.getString("deptId"),json.getString("LoginToken"),json.getString("loginName"));
			String str=HttpUtil.getParameter1(json, "/Base-Module/CompanyUser/GetList",json2.getIntValue("isContainChildDeptMember"),json2.getString("deptId"));
			//System.out.println(str);
			String result   =   HttpUtil.doPost1("http://xcgovapi.hzxc.gov.cn/Base-Module/CompanyUser/GetList", str);
			renderJson(result);
		}
		public void searchUser() {
			JSONObject json2=JsonUtil.getJSONObject(getRequest());
			JSONObject json=getSessionAttr("1doUser");
			//JSONObject json=HttpUtil.loginIm(json1.getString("useraccount"));
	
			//String str=HttpUtil.doGet("http://xcgov.hzxc.gov.cn/Base-Module/CompanyUser/GetList?isContainChildDeptMember="+json2.getIntValue("isContainChildDeptMember")+"&sortColumn=U_DEPT_SORT"
			//		+ "&sortAscending=true&createPage="+json2.getIntValue("createPage")+"&pageSize="+json2.getIntValue("pageSize")+"&searchKey="+json2.getString("searchKey"),json.getString("LoginToken"),json.getString("loginName"));
			String str=HttpUtil.getParameter1(json, "/Base-Module/CompanyUser/GetList",json2.getIntValue("isContainChildDeptMember"),json2.getIntValue("createPage"),json2.getIntValue("pageSize"),json2.getString("searchKey"));
			//System.out.println(str);
			String result   =   HttpUtil.doPost1("http://xcgovapi.hzxc.gov.cn/Base-Module/CompanyUser/GetList", str);
			renderJson(result);
		}
		
		//通讯录获取用户信息
		public void CompanyUser() {
			JSONObject json2=JsonUtil.getJSONObject(getRequest());
			//JSONObject json=getSessionAttr("coco");
			JSONObject json=getSessionAttr("1doUser");
			//JSONObject json=HttpUtil.loginIm(json1.getString("useraccount"));
			//String str=HttpUtil.doGet("http://xcgov.hzxc.gov.cn/Base-Module/CompanyUser?showId="+json2.getString("SHOW_ID"),json.getString("LoginToken"),json.getString("loginName"));
			String str=HttpUtil.getParameter1(json, "/Base-Module/CompanyUser",json2.getString("SHOW_ID"));

			String result   =   HttpUtil.doPost1("http://xcgovapi.hzxc.gov.cn/Base-Module/CompanyUser", str);
			renderJson(result);
		}
		/*
		 2018年6月28日上午3:50:16 方升群  //模拟1do登出
		 */
		public void exit1do() {
			removeSessionAttr("1doUser");
			renderJson(JsonUtil.getMap(200, "退出成功"));
		}
	    /*
		 2018年6月27日下午4:53:08 方升群 //查看
		*/
		/*public void action() {
			renderJson();
		}*/
		/*
		 2018年6月27日下午3:07:59 方升群  //1do详情修改发起时间/完成时间
		*/
		 @Before(Tx.class)
		public void changeTime() {
			JSONObject json=JsonUtil.getJSONObject(getRequest());
			JSONObject json1=getSessionAttr("1doUser");
			 T1doPstatus t1=T1doPstatus.dao.findFirst("select * from t_1do_pstatus where SHOW_ID=? and O_USER=? and USER_TYPE !=2",json.getString("SHOW_ID"),json1.getString("loginName"));
			   if(json1.getBoolean("isfw")||StrUtil.getflag(t1.getUserType(), 3)){	 
				   T1doBase t=json.toJavaObject(T1doBase.class);
					t.updateTime(json);
					renderJson(JsonUtil.getMap(200, "修改成功"));
				}else{
					renderJson(JsonUtil.getMap(202, "权限不足"));
				}
			
		}
		  //1do详情修改标题或者内容

		 @Before(Tx.class)
		 public void changeText() {
			 JSONObject json=JsonUtil.getJSONObject(getRequest());
			 JSONObject json1=getSessionAttr("1doUser");
			 T1doPstatus t1=T1doPstatus.dao.findFirst("select * from t_1do_pstatus where SHOW_ID=? and O_USER=? and USER_TYPE !=2",json.getString("SHOW_ID"),json1.getString("loginName"));
			if(json1.getBoolean("isfw")||StrUtil.getflag(t1.getUserType(), 1)){
				int i= Db.update("UPDATE t_1do_base set "+json.getString("target")+"=? where SHOW_ID=?",json.getString("content"),json.getString("SHOW_ID"));
				 if(i==1){
					 renderJson(JsonUtil.getMap(200, "修改成功"));
				 }else{
					 renderJson(JsonUtil.getMap(201, "修改失败"));
				 }
			}else{
				renderJson(JsonUtil.getMap(202, "权限不足"));
			}
			
			
		 }
		/*
		 2018年6月27日下午3:07:59 方升群  //1do详情修改发起人/受理人/抄送人
		 */
		 @Before(Tx.class)
		public void changeUser() {
			JSONObject json=JsonUtil.getJSONObject(getRequest());
			//T1doBase t1doBase=T1doBase.getIdoBase(json.getString("SHOW_ID"));
			JSONObject json1=getSessionAttr("1doUser");
			 T1doPstatus t1=T1doPstatus.dao.findFirst("select * from t_1do_pstatus where SHOW_ID=? and O_USER=? and USER_TYPE !=2",json.getString("SHOW_ID"),json1.getString("loginName"));
			boolean flag=json.getString("object").equals("受理人")||json.getString("object").equals("抄送人")?true:json1.getBoolean("isfw")||StrUtil.getflag(t1.getUserType(), 1);
			 if(flag){
			
			T1doBase t1doBase =T1doBase.dao.findFirst("select * from t_1do_base where SHOW_ID=?", json.getString("SHOW_ID"));
			T1doStatus t1doStatus=t1doBase.getIdoStatus();
			T1doPstatus t= new T1doPstatus();
			int i1=0;
			if(json.getString("object").equals("发起人")){
				i1=1;
				t1doBase.setOCustomer(StrUtil.getUser1(t1doBase.getOCustomer(), json.getString("useraccount"), json.getString("method")))
				.setOCustomerName(StrUtil.getUser1(t1doBase.getOCustomerName(), json.getString("username"), json.getString("method"))).update();
			    t.setUserType(1);
			    if(t1doStatus.getOStatus()==3){
			    	t.setOStatus(1);
			    }else if(t1doStatus.getOStatus()==4){
			    	t.setOStatus(4);
			    }else{
			    	t.setOStatus(5);
			    }
			}else if(json.getString("object").equals("受理人")){
				i1=3;
				t1doBase.setOExecutor(StrUtil.getUser1(t1doBase.getOExecutor(), json.getString("useraccount"), json.getString("method")))
				.setOExecutorName(StrUtil.getUser1(t1doBase.getOExecutorName(), json.getString("username"), json.getString("method"))).update();
				t.setUserType(3);
			    t.setOStatus(t1doStatus.getOStatus());
			}else{
				i1=4;
				t1doBase.setCC(StrUtil.getUser1(t1doBase.getCC(), json.getString("useraccount"), json.getString("method")))
				.setCcName(StrUtil.getUser1(t1doBase.getCcName(), json.getString("username"),json.getString("method"))).update();
				t.setUserType(4);
				t.setOStatus(t1doStatus.getOStatus());
			}
			if(json.getString("method").equals("remove")){	
				Db.update("update t_1do_pstatus set isDelete=2 where SHOW_ID=? and O_USER=? and USER_TYPE=?",t1doBase.getShowId(),json.getString("useraccount"),i1);
			}else{
				String[] temp =json.getString("useraccount").split(";");
				String[] temp1 =json.getString("username").split(";");
				for (int j = 0; j < temp.length; j++) {
					int i=Db.update("update t_1do_pstatus set isDelete=1 where SHOW_ID=? and O_USER=? and USER_TYPE=?",t1doBase.getShowId(),temp[j],i1);
				 if(i==0){
					 
					t.setShowId(json.getString("SHOW_ID")).setOUser(temp[j]).setOUserName(temp1[j]).save();
						sendIdo2(t1doBase,t);
						
					t.remove("ID");
				 }
				}
				
				
			}
			renderJson(JsonUtil.getMap(200, "修改成功"));
			}else{
				renderJson(JsonUtil.getMap(202, "权限不足"));
			}
		}
		//置顶
		 @Before(Tx.class)
		public void top() {
			JSONObject json=JsonUtil.getJSONObject(getRequest());
			JSONObject json1=getSessionAttr("1doUser"); 	
			T1doOrder t=json.toJavaObject(T1doOrder.class);
			T1doOrder t1=T1doOrder.dao.findFirst("select * from t_1do_order where SHOW_ID=? and useraccount=? and type=?",json.getString("SHOW_ID"),json1.getString("loginName"),json.get("type"));
			if(t1==null){
				t.setUseraccount(json1.getString("loginName")).save();
				renderJson(JsonUtil.getMap(200, "置顶成功"));
			}else{
				t1.delete();
				//t1.setModifyTime(new Date()).update();
				renderJson(JsonUtil.getMap(200, "取消置顶成功"));

			}
		}
		
		
		
	    /*
		 2018年6月27日下午2:26:01 方升群   //判断是否是整理层
		*/
		public void isfw() {
			JSONObject json=getSessionAttr("1doUser"); 	
			T1doFw t1doFw =T1doFw.getIdoFw(json.getString("loginName"));
			if(t1doFw==null){
				renderJson(JsonUtil.getMap(201, false));
			}else{
				renderJson(JsonUtil.getMap(200, true));
			}
			
		}
	    /*
		 2018年6月26日下午11:20:20 方升群  //获得流程
		*/
		public void getProcess() {
			JSONObject json=JsonUtil.getJSONObject(getRequest()); 	
			//T1doBase t1doBase =T1doBase.getIdoBase(json.getString("SHOW_ID"));
			T1doBase t1doBase =T1doBase.dao.findFirst("select * from t_1do_base where SHOW_ID=?", json.getString("SHOW_ID"));
			List<T1doFeedback> t1doFeedbacks=t1doBase.getIdoFeedbacks2();//反馈
			String str=StrUtil.getStr(t1doFeedbacks);
			List<T1doFeedback> t1doFeedbacks4=t1doBase.getIdoFeedbacks4();//催办
			List<T1doFeedback> t1doFeedbacks44=t1doBase.getIdoFeedbacks44();//催办
			String str4=StrUtil.getStr(t1doFeedbacks4);
			T1doFeedback t1doFeedbacks5=t1doBase.getIdoFeedbacks5();//办结
			
			List<T1doFeedback> t1doFeedbacks6=t1doBase.getIdoFeedbacks6();//评价
			String str6=StrUtil.getStr(t1doFeedbacks6);
			List<T1doBase> sonT1doBase=t1doBase.getSonIdoBase1();
		//	LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>(); 
			 String[] oc=t1doBase.getOCustomerName().split(";");
			 List<Record> list = new ArrayList<Record>();
			 List<Record> list1 = new ArrayList<Record>();
			 for(String co1:oc){
				 Record r=new Record();
				 r.set("user", co1);
				 r.set("time", t1doBase.getOStartTime());
				 list.add(r);
			 }
			 List<LinkedHashMap<String, Object>> all = new ArrayList<LinkedHashMap<String, Object>>();
			 LinkedHashMap<String, Object> map1 = new LinkedHashMap<String, Object>();
			 map1.put("title", list);
			 map1.put("type", 1);
			 all.add(map1);
			 LinkedHashMap<String, Object> map2 = new LinkedHashMap<String, Object>();
			 Record r=new Record();
			 r.set("user", t1doBase.getCreateUserName());
			 r.set("time", t1doBase.getOCreateTime());
			 list1.add(r);
			 map2.put("title", list1);
			 map2.put("type", 2);
			 all.add(map2);
			 T1doFeedback t1=T1doFeedback.dao.findFirst("select * from t_1do_feedback where SHOW_ID=?",t1doBase.getShowId());
			 T1doFeedback t2=T1doFeedback.dao.findFirst("select * from t_1do_feedback where SHOW_ID=? and FB_TYPE !=4",t1doBase.getShowId());//反馈
			 T1doFeedback t3=T1doFeedback.dao.findFirst("select * from t_1do_feedback where SHOW_ID=? and FB_TYPE=4",t1doBase.getShowId());//催报
			// T1doBase sonT1doBase2=t1doBase.getSonIdoBase2();
			 T1doBase sonT1doBase3=t1doBase.getSonIdoBase3();//拆项
			    int[] i=new int[]{3,4,5};
				if(t2==null&&t3==null&&sonT1doBase3==null){
					i=new int[]{3,4,5};
				}else if(t2==null&&t3!=null&&sonT1doBase3==null){
					i=new int[]{4,3,5};
				}else if(t2==null&&t3==null&&sonT1doBase3!=null){
					i=new int[]{5,3,4};
				}else if(t2!=null&&t3==null&&sonT1doBase3==null){
					i=new int[]{3,4,5};
				}else if(t2!=null&&t3!=null&&sonT1doBase3==null){
					if(t1.getFbType()==4){
						i=new int[]{4,3,5};
					}else{
					    i=new int[]{3,4,5};
					}
				}else if(t2!=null&&t3==null&&sonT1doBase3!=null){
					if(t2.getFbTime().before(sonT1doBase3.getOCreateTime())){
						 i=new int[]{3,5,4};
					}else{
						i=new int[]{5,3,4};
					}
					
				} else if(t2==null&&t3!=null&&sonT1doBase3!=null){
					if(t3.getFbTime().before(sonT1doBase3.getOCreateTime())){
						 i=new int[]{4,5,3};
					}else{
						i=new int[]{5,4,3};
					}
					
				}else{
					if(t1.getFbType()==4){
						if(t2.getFbTime().before(sonT1doBase3.getOCreateTime())){
							i=new int[]{4,3,5};
						}else if(t3.getFbTime().before(sonT1doBase3.getOCreateTime())){
							i=new int[]{4,5,3};
						}else{
							i=new int[]{5,4,3};
						}
						
					}else{
						if(t3.getFbTime().before(sonT1doBase3.getOCreateTime())){
							i=new int[]{3,4,5};
						}else if(t2.getFbTime().before(sonT1doBase3.getOCreateTime())){
							i=new int[]{3,5,4};
						}else{
							i=new int[]{5,3,4};
						}
					}
				}
			for(int num:i){
				if(num==3){
					LinkedHashMap<String, Object> map3 = new LinkedHashMap<String, Object>();
					 map3.put("title", str);
					 map3.put("type", 3);
					 all.add(map3);
				}else if(num==4){
					LinkedHashMap<String, Object> map4 = new LinkedHashMap<String, Object>();
					 map4.put("title", str4);
					 map4.put("type", 4);
					 map4.put("num", t1doFeedbacks44.size());
					 all.add(map4);
				}else{
					LinkedHashMap<String, Object> map5 = new LinkedHashMap<String, Object>();
					 map5.put("son", sonT1doBase);
					 T1doBase sonT1doBase23=t1doBase.getSonIdoBase23();
					 List<Record> list11 = new ArrayList<Record>();
					 if(sonT1doBase23!=null){
						 Record r1=new Record();
						 r1.set("user", sonT1doBase23.getCreateUserName());
						 r1.set("time", sonT1doBase23.getOCreateTime());
						 list11.add(r1);
					 }
		
					 map5.put("title", list11);
					 
					 map5.put("type", 5);
					 all.add(map5);
				}
			}
			 //map.put("3", str);
			// map.put("4", str4);
			// map.put("5", array);
			LinkedHashMap<String, Object> map6 = new LinkedHashMap<String, Object>();
			
		     map6.put("title",t1doFeedbacks5==null?"":t1doFeedbacks5.getFBCONTENT());
			 
			 map6.put("type", 6);
			 all.add(map6);
			 LinkedHashMap<String, Object> map7 = new LinkedHashMap<String, Object>();
			 map7.put("title", str6);
			 map7.put("type", 7);
			 all.add(map7);
			
			renderJson(all); 
		}
		/*
		 2018年6月26日下午11:20:20 方升群  //获得操作日志
		 */
		public void getLog() {
			JSONObject json=JsonUtil.getJSONObject(getRequest()); 	
			T1doBase t1doBase =json.toJavaObject(T1doBase.class);
			List<T1doLog> t1doLogs=t1doBase.getIdoLogs1();
			renderJson(t1doLogs); 
		}
	    /*
		 2018年6月26日下午10:50:37 方升群  //获得反馈消息。
		*/
		public void getFeedback() { 
			JSONObject json=JsonUtil.getJSONObject(getRequest()); 	
			T1doBase t1doBase =json.toJavaObject(T1doBase.class);
			List<T1doFeedback> t1doFeedbacks=t1doBase.getIdoFeedbacks1(json.getIntValue("id"),json.getIntValue("num"));
		    List<T1doFeedback> getIdoFeedbacks11= T1doFeedback.dao.find("select  O_USER_NAME from t_1do_feedback where SHOW_ID=? and FB_TYPE!=4",json.getString("SHOW_ID"));
		    setSessionAttr(json.getString("SHOW_ID"), getIdoFeedbacks11.size());
			renderJson(t1doFeedbacks);
		}
		/*
		 2018年7月1日下午8:34:11 方升群  //轮询
		*/
		public void polling() throws InterruptedException {
	         JSONObject json=JsonUtil.getJSONObject(getRequest());
	         Long time=new Date().getTime();
	         String str=json.getString("SHOW_ID");
		     // 死循环 查询有无数据变化
		     while (true) {
		      List<T1doFeedback> getIdoFeedbacks11= T1doFeedback.dao.find("select O_USER_NAME from t_1do_feedback where SHOW_ID=? and FB_TYPE!=4",str);
				int i=(int)getSessionAttr(json.getString("SHOW_ID"));
		      int n=getIdoFeedbacks11.size()-i;
		      if(n!=0){
				     List<T1doFeedback> getIdoFeedbacks12=T1doFeedback.dao.find("select ID,O_USER_NAME,FB_TIME,O_USER,FBCONTENT,FB_TYPE,FB_USER_NAME,FB_USER,ATTR_PATH,star from t_1do_feedback where SHOW_ID=? and FB_TYPE!=4  LIMIT ?,?",str,i,n);;
				   
				     renderJson(getIdoFeedbacks12); 	 	
		             setSessionAttr(str, getIdoFeedbacks11.size());
		             break; // 跳出循环，返回数据
		         } // 模拟没有数据变化，将休眠 hold住连接
		             Thread.sleep(1000);
		             Long time1=new Date().getTime();
		             if(time1-time>40000){
		            	 List<T1doFeedback> getIdoFeedbacks12=new ArrayList<T1doFeedback>();
		            	 renderJson(getIdoFeedbacks12);
		            	 break;
		             }
		             
		         
		     }
		     
		     }
	    /*
		 2018年6月25日下午3:37:00 方升群  //1do详情
		*/
		public void getIdoMessage() {
	  
	    
	    	JSONObject json=JsonUtil.getJSONObject(getRequest()); 	
			//T1doBase t1doBase =T1doBase.getIdoBase1(json.getString("SHOW_ID"));
			T1doBase t1doBase=T1doBase.dao.findFirst("select SHOW_ID,O_TITLE,O_DESCRIBE,O_CUSTOMER,O_CUSTOMER_NAME,O_START_TIME,O_FINISH_TIME,O_EXECUTOR,O_EXECUTOR_NAME,CC,CC_NAME from t_1do_base where SHOW_ID=?", json.getString("SHOW_ID"));
			t1doBase.setLIGHTNING(t1doBase.getIdoFeedbacks44().size());
			JSONObject json1=getSessionAttr("1doUser");
			T1doBase t1doBase1 =json.toJavaObject(T1doBase.class).setORange(json1.getString("loginName")).setORangeName(json1.getString("username"));
			T1doLog t=T1doLog.dao.findFirst("select * from t_1do_log where SHOW_ID=? and O_USER =? and log_type=2",t1doBase1.getShowId(),json1.getString("loginName"));
			if(t==null){
				new T1doLog().setShowId(t1doBase1.getShowId()).setOUser(t1doBase1.getORange()).setOUserName(t1doBase1.getORangeName())
				.setOpTime(new Date()).setLog(t1doBase1.getORangeName()+"查看此1do").setLogType(2).save();
			}
			
			t1doBase.put("EVENT_TYPE", t1doBase.getIdoSet().getEventType());
			t1doBase.put("O_STATUS", t1doBase.getIdoStatus().getOStatus());
			List<T1doAttr> t1doAttrs=t1doBase.getIdoAttr1();//附件
			List<T1doBase> t1doBases=t1doBase.getSonIdoBase1();//子项
			
			LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();  
			map.put("BASE", t1doBase);
			 map.put("SONBASE", t1doBases);
			 map.put("ATTR", t1doAttrs);
			// map.put("EVENT_TYPE", t1doBase.getIdoSet().getEventType());
			 renderJson(map);
		}
		/*
		 2018年6月26日上午10:17:23 方升群  附件反馈
		*/
		@Before(Tx.class)
		public void feedbackUpload1() throws IOException {
			//MultipartParser mp = new MultipartParser(this.getRequest(), 52428800, false, false,"UTF-8");//52428800=50*1024*1024
			List<UploadFile> uploadFiles=this.getFiles("FILE");//获取前台上传文件对象
			String base=getPara("BASE");
			JSONObject json=JSONObject.parseObject(base);
			JSONObject json1=getSessionAttr("1doUser");
			//JSONObject json1=JSON.parseObject("{\"useraccount\": \"fangshengqun\",\"username\": \"方升群\"}");
			T1doFeedback t1doFeedback =json.toJavaObject(T1doFeedback.class);
			t1doFeedback.setFbTime(new Date()).setOUser(json1.getString("loginName")).setOUserName(json1.getString("username"));
		
			T1doAttr t1doAttr=t1doFeedback.getIdoAttr();
			String attrId="";
			String fn="";
			for(UploadFile uploadFile:uploadFiles){
				File file = uploadFile.getFile();//获取文件对象
	            FileUtil fs = new FileUtil();
	            String fileName=IDUtil.getUid()//UUID.randomUUID().toString()
		                + file.getName().substring(file.getName().lastIndexOf("."));
	            File t = new File("D:\\1do\\upload\\" + fileName);//设置本地上传文件对象（并重命名）
	            t1doAttr.setAttrPath("http://172.16.9.195:8080/1do/upload/"+fileName);
	            t1doAttr.setAttrName(file.getName());
	            fn+=file.getName()+" ";
	            try {
	                t.createNewFile();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	                fs.fileChannelCopy(file, t);//将上传的文件的数据拷贝到本地新建的文件
	                file.delete();
	                t1doAttr.save();
	                t1doAttr.setAttrOrder(t1doAttr.getID()).update();
	                attrId+=t1doAttr.getID()+";";
	                t1doAttr.remove("ID");
			}
	        t1doFeedback.setATTRID(attrId).setFBCONTENT("上传 "+fn).save();
	        
	        T1doLog t1doLog=t1doFeedback.getIdoLog();
	        if(t1doFeedback.getFbType()==2){
				t1doFeedback.setFBCONTENT("回复"+t1doFeedback.getFbUserName()+" "+fn).update();
			}
	        t1doLog.save();
	        
			renderJson(JsonUtil.getMap(200, "反馈成功"));
		}
		
		//附件

		@Before(Tx.class)
		public void feedbackUpload() throws IOException {
			MultipartParser mp = new MultipartParser(this.getRequest(), 52428800, false, false,"UTF-8");//52428800=50*1024*1024
		//	List<UploadFile> uploadFiles=this.getFiles("FILE");//获取前台上传文件对象
			List<String> uploadFiles=new ArrayList<String>();
			List<String> uploadFiles1=new ArrayList<String>();
			
			//String base = null;
			Part part;
			// Map<String, String > map =  new HashMap<String, String>();
			 JSONObject json=new JSONObject();
			while((part=mp.readNextPart())!=null){
				String name = part.getName();
				if(part.isParam()){
					ParamPart param=(ParamPart) part;
					// base=param.getStringValue();
					 String value=param.getStringValue();
					 json.put(name, value);
				}else if(part.isFile()){
					FilePart filePart = (FilePart) part;
				//	uploadFiles.add(filePart);
					String fileName=IDUtil.getUid()//UUID.randomUUID().toString()
							+ filePart.getFileName().substring(filePart.getFileName().lastIndexOf("."));
					File t1 = new File("D:\\1do\\upload\\");//设置本地上传文件对象（并重命名）
					File t = new File(t1,fileName);//设置本地上传文件对象（并重命名）
					FileOutputStream out = new FileOutputStream(t);
	        		Streams.copy(filePart.getInputStream(), out, true);
	        		uploadFiles.add(fileName);
	        		uploadFiles1.add(filePart.getFileName());
				}
			}
		
			JSONObject json1=getSessionAttr("1doUser");
			//JSONObject json1=JSON.parseObject("{\"useraccount\": \"fangshengqun\",\"username\": \"方升群\"}");
			T1doFeedback t1doFeedback =json.toJavaObject(T1doFeedback.class);
			t1doFeedback.setFbTime(new Date()).setOUser(json1.getString("loginName")).setOUserName(json1.getString("username"));
			T1doAttr t1doAttr=t1doFeedback.getIdoAttr();
			String attrId="";
			String fn="";
			String ATTR_PATH="";
			//for(HashMap<String,String> uploadFile:uploadFiles){
		    for (int i = 0; i < uploadFiles.size(); i++) {
		
	
				
				t1doAttr.setAttrPath("http://172.16.9.195:8080/1do/upload/"+uploadFiles.get(i));
				t1doAttr.setAttrName(uploadFiles1.get(i));
				fn+=uploadFiles1.get(i)+" ";
				ATTR_PATH+="http://172.16.9.195:8080/1do/upload/"+uploadFiles.get(i)+" ";
				t1doAttr.save();
				t1doAttr.setAttrOrder(t1doAttr.getID()).update();
				attrId+=t1doAttr.getID()+" ";
				t1doAttr.remove("ID");
			}
			t1doFeedback.setATTRID(attrId).setAttrPath(ATTR_PATH).setFBCONTENT("上传 "+fn).save();
			T1doLog t1doLog=t1doFeedback.getIdoLog();
			if(t1doFeedback.getFbType()==2){
				t1doFeedback.setFBCONTENT("回复"+t1doFeedback.getFbUserName()+" "+fn).update();
			}
			t1doLog.save();
			
			renderJson(JsonUtil.getMap(200, "反馈成功"));
		}
		//附件单独删除
		@Before(Tx.class)
		public void deleteAttr(){
	    	JSONObject json=JsonUtil.getJSONObject(getRequest()); 	
	    	int i=Db.delete("DELETE from t_1do_attr where attr_path=?",json.getString("ATTR_PATH"));
	    	if(i==1){
				renderJson(JsonUtil.getMap(200, "删除成功"));
	    	}else{
				renderJson(JsonUtil.getMap(201, "删除失败"));
	    	}
			
		}
		//删除或恢复id//重做
		@Before(Tx.class)
		public void deleteIdoOrRestoreIdoOrRedo(){
			JSONObject json=JsonUtil.getJSONObject(getRequest()); 	
			 JSONObject json1=getSessionAttr("1doUser");
			 T1doPstatus t1=T1doPstatus.dao.findFirst("select * from t_1do_pstatus where SHOW_ID=? and O_USER=? and USER_TYPE=1",json.getString("SHOW_ID"),json1.getString("loginName"));
			if(json.getString("result").equals("delete")){//删除
			 if(json1.getBoolean("isfw")||t1!=null){
				T1doStatus t=T1doStatus.dao.findFirst("select * from t_1do_status where SHOW_ID=?",json.getString("SHOW_ID"));
				if(t.getOStatus()==5){
					//Db.delete("DELETE from t_1do_base where SHOW_ID=?",json.getString("SHOW_ID"));
					int i=Db.update("update t_1do_base set O_IS_DELETED=2 where SHOW_ID=?",json.getString("SHOW_ID"));
					  if(i==1){
						renderJson(JsonUtil.getMap(200, "删除成功"));
					  }else{
						renderJson(JsonUtil.getMap(201, "删除失败"));
					  }
				}else{
					renderJson(JsonUtil.getMap(203, "任务进行中不能删除"));
				}
			  
			}else{
				renderJson(JsonUtil.getMap(202, "权限不足"));
			}
		}else if(json.getString("result").equals("Restore")) {//恢复
			int i=Db.update("update t_1do_base set O_IS_DELETED=1 where SHOW_ID=?",json.getString("SHOW_ID"));
			if(i==1){
				renderJson(JsonUtil.getMap(200, "恢复成功"));
			  }else{
				renderJson(JsonUtil.getMap(201, "恢复失败"));
			  }
		}else{//重做
			
			
		}
			
		}
		//附件单独上传
		
		@Before(Tx.class)
		public void upload() throws IOException {
			MultipartParser mp = new MultipartParser(this.getRequest(), 52428800, false, false,"UTF-8");//52428800=50*1024*1024
			//	List<UploadFile> uploadFiles=this.getFiles("FILE");//获取前台上传文件对象
			List<String> uploadFiles=new ArrayList<String>();
			List<String> uploadFiles1=new ArrayList<String>();
			
			//String base = null;
			Part part;
			// Map<String, String > map =  new HashMap<String, String>();
			JSONObject json=new JSONObject();
			while((part=mp.readNextPart())!=null){
				String name = part.getName();
				if(part.isParam()){
					ParamPart param=(ParamPart) part;
					// base=param.getStringValue();
					String value=param.getStringValue();
					json.put(name, value);
				}else if(part.isFile()){
					FilePart filePart = (FilePart) part;
					//	uploadFiles.add(filePart);
					String fileName=IDUtil.getUid()//UUID.randomUUID().toString()
							+ filePart.getFileName().substring(filePart.getFileName().lastIndexOf("."));
					File t1 = new File("D:\\1do\\upload\\");//设置本地上传文件对象（并重命名）
					File t = new File(t1,fileName);//设置本地上传文件对象（并重命名）
					FileOutputStream out = new FileOutputStream(t);
					Streams.copy(filePart.getInputStream(), out, true);
					uploadFiles.add(fileName);
					uploadFiles1.add(filePart.getFileName());
				}
			}
			JSONObject json1=getSessionAttr("1doUser");
			T1doAttr t1doAttr=new T1doAttr().setShowId(json.getString("SHOW_ID")).setUploadUser(json1.getString("loginName")).setUploadTime(new Date())
					.setIsFb(1);
			//String attrId="";
			String fn="";
			List<T1doAttr> list = new ArrayList<T1doAttr>();
			for (int i = 0; i < uploadFiles.size(); i++) {
			
				t1doAttr.setAttrPath("http://172.16.9.195:8080/1do/upload/"+uploadFiles.get(i));
				t1doAttr.setAttrName(uploadFiles1.get(i));
				fn+=uploadFiles1.get(i)+" ";
				t1doAttr.save();
				t1doAttr.setAttrOrder(t1doAttr.getID()).update();
				list.add(t1doAttr);
				//attrId+=t1doAttr.getID()+";";
				t1doAttr.remove("ID");
			}
		
			T1doLog t1doLog=new T1doLog().setShowId(json.getString("SHOW_ID")).setOUser(json1.getString("loginName")).setOUserName(json1.getString("username"))
					.setOpTime(new Date()).setLogType(3).setLog(json1.getString("username")+"上传 "+fn);
			t1doLog.save();
			
			renderJson(list);
		}
		
		/*
		 2018年6月26日上午10:17:23 方升群  新建1do保存
		 */
		@Before(Tx.class)
		public void saveIdo1() {
			List<UploadFile> uploadFiles=this.getFiles("FILE");//获取前台上传文件对象
			JSONObject json1=getSessionAttr("1doUser");
			String base=getPara("BASE");
			JSONObject json=JSONObject.parseObject(base);
			
			T1doBase t1doBase =json.toJavaObject(T1doBase.class);
			t1doBase.setOStartTime(new Date()).setOStartTime(new Date()).setOCreateTime(new Date()).setCreateUser(json1.getString("loginName")).setCreateUserName(json1.getString("username"));
			t1doBase.setShowId(IDUtil.getUid());
			t1doBase.setORange(StrUtil.getOnly(t1doBase.getOCustomer(),t1doBase.getCreateUser(),t1doBase.getOExecutor(),t1doBase.getCC()));
			t1doBase.setORangeName(StrUtil.getOnly(t1doBase.getOCustomerName(),t1doBase.getCreateUserName(),t1doBase.getOExecutorName(),t1doBase.getCcName()));
			t1doBase.save(); 
			new T1doStatus().setShowId(t1doBase.getShowId()).save();//1do状态表
			new T1doSet().setShowId(t1doBase.getShowId()).setEventType(json.getString("EVENT_TYPE")).save();
			//T1doPstatus.saveIdoPstatus(t1doBase.getShowId(), t1doBase.getOCustomer(),t1doBase.getCreateUser(),t1doBase.getOExecutor(),t1doBase.getCC());
			String[] users={t1doBase.getOCustomer(),t1doBase.getCreateUser(),t1doBase.getOExecutor(),t1doBase.getCC()};
			String[] usernames={t1doBase.getOCustomerName(),t1doBase.getCreateUserName(),t1doBase.getOExecutorName(),t1doBase.getCcName()};
			T1doPstatus.saveIdoPstatus1(t1doBase.getShowId(),users,usernames);
			//new T1doLog().setShowId(t1doBase.getShowId()).setOUser(t1doBase.getCreateUser()).setOUserName(t1doBase.getCreateUserName()).setOpTime(t1doBase.getOCreateTime()).setLog(t1doBase.getCreateUserName()+"帮助"+t1doBase.getOCustomerName().replace(";","、")+"创建了此1do").setLogType(1).save();                                                   
			new T1doLog().setShowId(t1doBase.getShowId()).setOUser(t1doBase.getCreateUser()).setOUserName(t1doBase.getCreateUserName()).setOpTime(t1doBase.getOCreateTime()).setLog(t1doBase.getCreateUserName()+"创建了此1do").setLogType(1).save();                                                   
		
			T1doAttr t1doAttr=t1doBase.newIdoAttr();
			//String attrId="";
			//String fn="";
			for(UploadFile uploadFile:uploadFiles){
				File file = uploadFile.getFile();//获取文件对象
				FileUtil fs = new FileUtil();
				String fileName=IDUtil.getUid()//UUID.randomUUID().toString()
						+ file.getName().substring(file.getName().lastIndexOf("."));
				File t = new File("D:\\1do\\upload\\" + fileName);//设置本地上传文件对象（并重命名）
				t1doAttr.setAttrPath("http://172.16.9.195:8080/1do/upload/"+fileName);
				t1doAttr.setAttrName(file.getName());
				//fn+=file.getName()+" ";
				try {
					t.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				fs.fileChannelCopy(file, t);//将上传的文件的数据拷贝到本地新建的文件
				file.delete();
				t1doAttr.save();
				t1doAttr.setAttrOrder(t1doAttr.getID()).update();
				//attrId+=t1doAttr.getID()+";";
				t1doAttr.remove("ID");
			}
		//	t1doFeedback.setATTRID(attrId).setFBCONTENT("上传 "+fn).save();
		//	T1doLog t1doLog=t1doFeedback.getIdoLog();
		//	t1doLog.save();
			
			renderJson(JsonUtil.getMap(200, "创建1do成功！"));
		}
		/*
		 2018年6月26日上午10:17:23 方升群  新建1do保存1
		 */
		@Before(Tx.class)
		public void saveIdo() throws IOException {
			MultipartParser mp = new MultipartParser(this.getRequest(), 52428800, false, false,"UTF-8");//52428800=50*1024*1024
				List<String> uploadFiles=new ArrayList<String>();
				List<String> uploadFiles1=new ArrayList<String>();
				
				//String base = null;
				Part part;
				JSONObject json=new JSONObject();
					while((part=mp.readNextPart())!=null){
						String name = part.getName();
						if(part.isParam()){
							ParamPart param=(ParamPart) part;
							// base=param.getStringValue();
							 String value=param.getStringValue();
							 json.put(name, value);
				    }else if(part.isFile()){
						FilePart filePart = (FilePart) part;
						String fileName=IDUtil.getUid()//UUID.randomUUID().toString()
								+ filePart.getFileName().substring(filePart.getFileName().lastIndexOf("."));
						File t1 = new File("D:\\1do\\upload\\");//设置本地上传文件对象（并重命名）
						File t = new File(t1,fileName);//设置本地上传文件对象（并重命名）
						FileOutputStream out = new FileOutputStream(t);
		        		Streams.copy(filePart.getInputStream(), out, true);
		        		uploadFiles.add(fileName);
		        		uploadFiles1.add(filePart.getFileName());
					}
				}
			JSONObject json1=getSessionAttr("1doUser");
            if(json1==null){
            	renderJson(JsonUtil.getMap(400, "用户未登入"));
            	return;
            }
			//JSONObject json=JSONObject.parseObject(base);
			T1doBase t1doBase =json.toJavaObject(T1doBase.class);
			t1doBase.setOStartTime(new Date()).setOCreateTime(new Date()).setCreateUser(json1.getString("loginName")).setCreateUserName(json1.getString("username"));
			t1doBase.setShowId(IDUtil.getUid());
			t1doBase.setORange(StrUtil.getOnly(t1doBase.getOCustomer(),t1doBase.getCreateUser(),t1doBase.getOExecutor()));
			t1doBase.setORangeName(StrUtil.getOnly(t1doBase.getOCustomerName(),t1doBase.getCreateUserName(),t1doBase.getOExecutorName()));
			t1doBase.save(); 
			new T1doStatus().setShowId(t1doBase.getShowId()).save();//1do状态表
			new T1doSet().setShowId(t1doBase.getShowId()).setEventType(json.getString("EVENT_TYPE")).save();
			//T1doPstatus.saveIdoPstatus(t1doBase.getShowId(), t1doBase.getOCustomer(),t1doBase.getCreateUser(),t1doBase.getOExecutor(),t1doBase.getCC());
			String[] users={t1doBase.getOCustomer(),t1doBase.getCreateUser(),t1doBase.getOExecutor()};
			String[] usernames={t1doBase.getOCustomerName(),t1doBase.getCreateUserName(),t1doBase.getOExecutorName()};
			T1doPstatus.saveIdoPstatus1(t1doBase.getShowId(),users,usernames);
			T1doAttr t1doAttr=t1doBase.newIdoAttr();	
			//new T1doLog().setShowId(t1doBase.getShowId()).setOUser(t1doBase.getCreateUser()).setOUserName(t1doBase.getCreateUserName()).setOpTime(t1doBase.getOCreateTime()).setLog(t1doBase.getCreateUserName()+"帮助"+t1doBase.getOCustomerName().replace(";","、")+"创建了此1do").setLogType(1).save();                                              			
			new T1doLog().setShowId(t1doBase.getShowId()).setOUser(t1doBase.getCreateUser()).setOUserName(t1doBase.getCreateUserName()).setOpTime(t1doBase.getOCreateTime()).setLog(t1doBase.getCreateUserName()+"创建了此1do").setLogType(1).save();                                              			
			 for (int i = 0; i < uploadFiles.size(); i++) {		
			    t1doAttr.setAttrPath("http://172.16.9.195:8080/1do/upload/"+uploadFiles.get(i));
				t1doAttr.setAttrName(uploadFiles1.get(i));
				t1doAttr.save();
				t1doAttr.setAttrOrder(t1doAttr.getID()).update();			
				t1doAttr.remove("ID");
			}
			
			 sendIdo(t1doBase);
			
			renderJson(JsonUtil.getMap(200, "创建1do成功！"));
		}
		/*
		 2018年6月25日下午3:59:19 方升群  //普通反馈
		*/
		@Before(Tx.class)
		public void feedback() {
			JSONObject json=JsonUtil.getJSONObject(getRequest());
			JSONObject json1=getSessionAttr("1doUser");
			T1doFeedback t1doFeedback =json.toJavaObject(T1doFeedback.class);
			T1doPstatus t1doPstatus=T1doPstatus.dao.findFirst("select * from t_1do_pstatus where SHOW_ID=? and O_USER=? and USER_TYPE=3",t1doFeedback.getShowId(),json1.getString("loginName"));
			T1doStatus t1doStatus=t1doFeedback.getIdoStatus();
			T1doBase t1doBase=t1doFeedback.getT1doBase();
			t1doFeedback.setFbTime(new Date()).setOUser(json1.getString("loginName")).setOUserName(json1.getString("username"));
			T1doLog t1doLog=json.toJavaObject(T1doLog.class);
			t1doLog.setOpTime(t1doFeedback.getFbTime()).setOUser(json1.getString("loginName")).setOUserName(json1.getString("username"));	
			if(t1doFeedback.getFbType()==1||t1doFeedback.getFbType()==2){
				t1doFeedback.save();
				if(t1doPstatus!=null&&t1doStatus.getOStatus()==3){
					t1doStatus.setOStatus(4).update();
					t1doPstatus.setOStatus(4).update();
					Db.update("update t_1do_pstatus set O_STATUS=4 where SHOW_ID=? and USER_TYPE!=3",t1doFeedback.getShowId());
					sendIdo(t1doBase,4);
				}
				String str=json1.getString("username")+"反馈:"+t1doFeedback.getFBCONTENT();
				t1doLog.setLogType(10).setLog(str).save();
				renderJson(JsonUtil.getMap(200, "反馈成功"));
				return;
			}else{
				t1doLog.setLogType(t1doFeedback.getFbType());
				String str=t1doLog.getOUserName();
				if(t1doLog.getLogType()==4){
					if(t1doBase.getIdoFeedbacks44().size()==5){
						renderJson(JsonUtil.getMap(201, "催办已达五次"));
						return;
					}
				 T1doPstatus t1=T1doPstatus.dao.findFirst("select * from t_1do_pstatus where SHOW_ID=? and O_USER=? and USER_TYPE !=2",json.getString("SHOW_ID"),json1.getString("loginName"));
				 if(t1.getUserType()==3){
					 renderJson(JsonUtil.getMap(202, "权限不足"));
						return;
				 }
				 if(t1doStatus.getOStatus()==3){
						t1doStatus.setOStatus(4).update();
						
					}
					str+="催办"+t1doFeedback.getFbUserName();
					t1doFeedback.setFBCONTENT(json1.getString("username")+"催办"+t1doBase.getOExecutorName().replace(";", "、"));
					t1doFeedback.save();

					//t1doFeedback.update();
					sendIdo(t1doBase,3);
				}else if(t1doLog.getLogType()==5){
					T1doPstatus t1=T1doPstatus.dao.findFirst("select * from t_1do_pstatus where SHOW_ID=? and O_USER=? and USER_TYPE !=2",json.getString("SHOW_ID"),json1.getString("loginName"));
					if(json1.getBoolean("isfw")||StrUtil.getflag(t1.getUserType(), 1)){
						T1doFeedback t1doFeedback1=T1doFeedback.dao.findFirst("select * from t_1do_feedback where SHOW_ID=? and FB_TYPE=5",t1doFeedback.getShowId());						
						if(t1doFeedback1!=null){
							renderJson(JsonUtil.getMap(200, "该1do已经办结"));
							return;
						}
						str+="确认办结";
						t1doFeedback.setFBCONTENT(json1.getString("username")+"确认办结");
						Db.update("update t_1do_status set O_STATUS=5 where SHOW_ID=?",t1doFeedback.getShowId());
						Db.update("update t_1do_pstatus set O_STATUS=5 where SHOW_ID=?",t1doFeedback.getShowId());
						t1doFeedback.save();
						sendIdo(t1doBase,5);
					}else{
						renderJson(JsonUtil.getMap(202, "权限不足"));
						return;
					}
					
				}else if(t1doLog.getLogType()==6){
					t1doFeedback.setFBCONTENT(json1.getString("username")+"评价："+t1doFeedback.getFBCONTENT()).save();
					str+="评价";
					t1doBase.setStar(t1doFeedback.getStar()).setEvaluation(t1doFeedback.getFBCONTENT()).update();
				}		
				t1doLog.setLog(str);
				t1doLog.save();

				
				renderJson(JsonUtil.getMap(200, "反馈成功"));
				return;
			}
			
		}
	    /*
		 2018年6月24日下午4:53:49 方升群   //获得发起人/受理人1do状态为1已送达2/3.待处理/4.处理中并且   1do和人员订阅事件都包含1.送达  //通知
		*/
		@Before(Tx.class)
		public void getIdo() {
			JSONObject json=getSessionAttr("1doUser");//{"O_USER":"fangshengqun"}
	    	T1doPstatus t1doPstatus=new T1doPstatus().setOUser(json.getString("loginName"));
	        List<Record> records=t1doPstatus.getRecords();
	        for(Record record:records){
	        	record.set("O_STATUS", StrUtil.getStatus(record.getStr("O_STATUS")));
	        	record.set("USER_TYPE", StrUtil.getUserType(record.getStr("USER_TYPE")));
	        	List<T1doFeedback> l=T1doFeedback.dao.find("select O_USER_NAME from t_1do_feedback where SHOW_ID=? and FB_TYPE=4",record.getStr("SHOW_ID"));
	        	record.set("LIGHTNING", l.size());
	        }
			renderJson(records);
		}
		public void get1do() {
			JSONObject json=JsonUtil.getJSONObject(getRequest());
			//JSONObject json=getSessionAttr("1doUser");//{"O_USER":"fangshengqun"}
			//T1doPstatus t1doPstatus=new T1doPstatus().setOUser(json.getString("useraccount"));
			T1doPstatus t1doPstatus=json.toJavaObject(T1doPstatus.class);
			List<Record> records=t1doPstatus.getRecords();
			for(Record record:records){
				record.set("O_STATUS", StrUtil.getStatus(record.getStr("O_STATUS")));
				record.set("USER_TYPE", StrUtil.getUserType(record.getStr("USER_TYPE")));
				List<T1doFeedback> l=T1doFeedback.dao.find("select O_USER_NAME from t_1do_feedback where SHOW_ID=? and FB_TYPE=4",record.getStr("SHOW_ID"));
				record.set("LIGHTNING", l.size());
			}
			renderJson(records);
		}
		
		/*
		 2018年7月9日上午10:52:04 方升群
		*/
		public static String sendIdo(T1doBase t1doBase){
			//JSONObject json2=HttpUtil.loginIm(json.getString("useraccount")); //可以
			T1doType t1doType=t1doBase.getT1doType();
			List<T1doPstatus> t1=T1doPstatus.dao.find("select * from t_1do_pstatus where SHOW_ID=? and USER_TYPE in(1,3,4)",t1doBase.getShowId());
			String result = null;
			for(T1doPstatus tt:t1){
				//T1doUser user=T1doUser.dao.findFirst("SELECT * from t_1do_user WHERE loginName=? or U_MAIL=? or U_MOBILE=? ",tt.getOUser(),tt.getOUser(),tt.getOUser());
				String loginName=tt.getOUser();
				String trueName=tt.getOUserName();
				
				JSONObject object = new JSONObject();
				object.put("O_TITLE", t1doBase.getOTitle());
				object.put("SHOW_ID", t1doBase.getShowId());
				object.put("O_DESCRIBE", t1doBase.getODescribe());
				if(tt.getUserType()==3||tt.getUserType()==4){					
					object.put("O_STATUS", "待处理");
				}else{
					object.put("O_STATUS", "已送达");
				}
				object.put("O_CUSTOMER_NAME", t1doBase.getOCustomerName());
				object.put("O_EXECUTOR_NAME", t1doBase.getOExecutorName());
				
				object.put("LIGHTNING", 0);
				object.put("ISLOOK", 2);
				object.put("O_TYPE_NAME", t1doType.getOTypeName());
				object.put("USER_TYPE", tt.getUserType());
				
				//String str=HttpUtil.getParameter1(t1doBase.getShowId(),t1doBase.getOTitle(), json2, loginName, trueName, object);
				String str=HttpUtil.getParameter2(t1doBase.getShowId(), loginName, trueName, object,t1doBase.getODescribe());
				System.out.println(str);
			    result   =   HttpUtil.doPost1("http://xcgovapi.hzxc.gov.cn/Base-Module/Message", str);
				//new T1doTemp().setBASE(result).setID((long)tt.getID()).save();
				new T1doTemp().setBASE(result).setID(IDUtil.getUid1()).setPid(tt.getID()).save();

			   
			}
			return result;
			

		}
		//单独发通知
		public static String sendOneIdo(T1doBase t1doBase,T1doPstatus tt){
			T1doType t1doType=t1doBase.getT1doType();
			
				String loginName=tt.getOUser();
				String trueName=tt.getOUserName();
				
				JSONObject object = new JSONObject();
				object.put("O_TITLE", t1doBase.getOTitle());
				object.put("SHOW_ID", t1doBase.getShowId());
				object.put("O_DESCRIBE", t1doBase.getODescribe());
				/*if(tt.getUserType()==3||tt.getUserType()==4){					
					object.put("O_STATUS", "待处理");
				}else{
					object.put("O_STATUS", "已送达");
				}*/
				object.put("O_CUSTOMER_NAME", t1doBase.getOCustomerName());
				object.put("O_EXECUTOR_NAME", t1doBase.getOExecutorName());
				
				object.put("LIGHTNING", t1doBase.getIdoFeedbacks44().size());
				object.put("ISLOOK", 1);
				object.put("O_TYPE_NAME", t1doType.getOTypeName());
				object.put("USER_TYPE", tt.getUserType());
				
				String str=HttpUtil.getParameter2(t1doBase.getShowId(), loginName, trueName, object,t1doBase.getODescribe());
				System.out.println(str);
				String result   =   HttpUtil.doPost1("http://xcgovapi.hzxc.gov.cn/Base-Module/Message", str);
				new T1doTemp().setBASE(result).setID(IDUtil.getUid1()).setPid(tt.getID()).save();
				
			return result;
			
			
		}
		public static String sendIdo(T1doBase t1doBase,int i){
			//JSONObject json2=HttpUtil.loginIm(json.getString("useraccount")); 
			T1doType t1doType=t1doBase.getT1doType();
			List<T1doPstatus> t1 = null;
			if(i==4){
				 t1=T1doPstatus.dao.find("select * from t_1do_pstatus where SHOW_ID=? and O_STATUS=? and USER_TYPE !=2",t1doBase.getShowId(),4);
			}else if(i==5){
				 t1=T1doPstatus.dao.find("select * from t_1do_pstatus where SHOW_ID=? and USER_TYPE !=2",t1doBase.getShowId());
			}else{
				 t1=T1doPstatus.dao.find("select * from t_1do_pstatus where SHOW_ID=? and USER_TYPE !=2 ",t1doBase.getShowId());
			}
			String result = null;
			for(T1doPstatus tt:t1){
				//T1doUser user=T1doUser.dao.findFirst("SELECT * from t_1do_user WHERE loginName=? or U_MAIL=? or U_MOBILE=? ",tt.getOUser(),tt.getOUser(),tt.getOUser());
				String loginName=tt.getOUser();
				String trueName=tt.getOUserName();
				
				JSONObject object = new JSONObject();
				object.put("O_TITLE", t1doBase.getOTitle());
				object.put("SHOW_ID", t1doBase.getShowId());
				object.put("O_DESCRIBE", t1doBase.getODescribe());
				if(i==4){
					object.put("O_STATUS", "处理中");
				}else if(i==5){
					object.put("O_STATUS", "已完成");
				}else{
					object.put("O_STATUS", StrUtil.getStatus(tt.getOStatus()));
				}
				object.put("O_CUSTOMER_NAME", t1doBase.getOCustomerName());
				object.put("O_EXECUTOR_NAME", t1doBase.getOExecutorName());
				//object.put("O_EXECUTOR_NAME", t1doBase.getOExecutorName());
				object.put("LIGHTNING", t1doBase.getIdoFeedbacks44().size());
				object.put("O_TYPE_NAME", t1doType.getOTypeName());
				object.put("USER_TYPE", tt.getUserType());
				
				//String str=HttpUtil.getParameter1(t1doBase.getShowId(),t1doBase.getOTitle(), json2, loginName, trueName, object);
				String str=HttpUtil.getParameter2(t1doBase.getShowId(), loginName, trueName, object,t1doBase.getODescribe());
				System.out.println(str);
				result   =   HttpUtil.doPost1("http://xcgovapi.hzxc.gov.cn/Base-Module/Message", str);
				new T1doTemp().setBASE(result).setID(IDUtil.getUid1()).setPid(tt.getID()).save();
				
				
			}
			return result;
			
			
		}
		public static String sendIdo2(T1doBase t1doBase,T1doPstatus t1doPstatus){
			T1doType t1doType=t1doBase.getT1doType();

			String result = null;
			
				JSONObject object = new JSONObject();
				object.put("O_TITLE", t1doBase.getOTitle());
				object.put("SHOW_ID", t1doBase.getShowId());
				object.put("O_DESCRIBE", t1doBase.getODescribe());
			
			    object.put("O_STATUS", StrUtil.getStatus(t1doPstatus.getOStatus()));
				
				object.put("O_CUSTOMER_NAME", t1doBase.getOCustomerName());
				object.put("O_EXECUTOR_NAME", t1doBase.getOExecutorName());
				//object.put("O_EXECUTOR_NAME", t1doBase.getOExecutorName());
				object.put("LIGHTNING", t1doBase.getIdoFeedbacks44().size());
				object.put("O_TYPE_NAME", t1doType.getOTypeName());
				object.put("USER_TYPE", t1doPstatus.getUserType());
				
				//String str=HttpUtil.getParameter1(t1doBase.getShowId(),t1doBase.getOTitle(), json2, loginName, trueName, object);
				String str=HttpUtil.getParameter2(t1doBase.getShowId(), t1doPstatus.getOUser(), t1doPstatus.getOUserName(), object,t1doBase.getODescribe());
				System.out.println(str);
				result   =   HttpUtil.doPost1("http://xcgovapi.hzxc.gov.cn/Base-Module/Message", str);
				new T1doTemp().setBASE(result).setID(IDUtil.getUid1()).setPid(t1doPstatus.getID()).save();
				
				
			
			return result;
			
			
		}
		
	    /*
		 2018年6月23日下午6:12:15 方升群   //拆项
		*/
        @Before(Tx.class)
		public void splitItem() {
        	JSONObject json=JsonUtil.getJSONObject(getRequest());
			T1doBase t1doBase =json.getObject("BASE", T1doBase.class);
			JSONObject json1=getSessionAttr("1doUser");
			t1doBase.setOStartTime(new Date()).setOCreateTime(new Date()).setCreateUser(json1.getString("loginName")).setCreateUserName(json1.getString("username"));
			t1doBase.setShowId(IDUtil.getUid());
			t1doBase.setORange(StrUtil.getOnly(t1doBase.getOCustomer(),t1doBase.getCreateUser(),t1doBase.getOExecutor()));
			t1doBase.setORangeName(StrUtil.getOnly(t1doBase.getOCustomerName(),t1doBase.getCreateUserName(),t1doBase.getOExecutorName()));	
			T1doBase pt1doBase=t1doBase.getParentIdoBase();//得到父1do；
			if(pt1doBase.getIdoStatus().getOStatus()==5){
				renderJson(JsonUtil.getMap(200, "父项已完成，不可拆项"));
				return;
			}
			t1doBase.setOTypeId(pt1doBase.getOTypeId());
			t1doBase.save();
			List<T1doAttr> t1doAttrs=pt1doBase.getIdoAttr();
			for(T1doAttr attr:t1doAttrs){
				attr.remove("ID");
				attr.remove("ATTR_ORDER");	
				attr.setShowId(t1doBase.getShowId());
				attr.save();
				attr.setAttrOrder(attr.getID());
				attr.update();
			}
			new T1doStatus().setShowId(t1doBase.getShowId()).save();//1do状态表

			new T1doSet().setShowId(t1doBase.getShowId()).setEventType(json.getString("EVENT_TYPE")).save();
			String[] users={t1doBase.getOCustomer(),t1doBase.getCreateUser(),t1doBase.getOExecutor(),t1doBase.getCC()};
			String[] usernames={t1doBase.getOCustomerName(),t1doBase.getCreateUserName(),t1doBase.getOExecutorName(),t1doBase.getCcName()};
			T1doPstatus.saveIdoPstatus1(t1doBase.getShowId(),users,usernames);
			//T1doPstatus.saveIdoPstatus(t1doBase.getShowId(), t1doBase.getOCustomer(),t1doBase.getCreateUser(),t1doBase.getOExecutor());
			//new T1doLog().setShowId(t1doBase.getShowId()).setOUser(t1doBase.getCreateUser()).setOUserName(t1doBase.getCreateUserName()).setOpTime(t1doBase.getOCreateTime()).setLog(t1doBase.getCreateUserName()+"帮助"+t1doBase.getOCustomerName().replace(";","、")+"新建子项").setLogType(1).save();                                                   
			new T1doLog().setShowId(t1doBase.getShowId()).setOUser(t1doBase.getCreateUser()).setOUserName(t1doBase.getCreateUserName()).setOpTime(t1doBase.getOCreateTime()).setLog(t1doBase.getCreateUserName()+"新建子项").setLogType(1).save();                                                   
			new T1doLog().setShowId(pt1doBase.getShowId()).setOUser(t1doBase.getCreateUser()).setOUserName(t1doBase.getCreateUserName()).setOpTime(t1doBase.getOCreateTime()).setLog(t1doBase.getCreateUserName()+"进行拆项").setLogType(7).save();                                                   
			//new T1doLog().setShowId(pt1doBase.getShowId()).SET
			sendIdo(t1doBase);
			renderJson(JsonUtil.getMap(200, "新建子项成功！"));
		}
        /*
		 2018年6月21日 方升群 //创建保存1do（1call转1do时）
		*/
	    @Before(Tx.class)
		public void createIdo() {
			JSONObject json=JsonUtil.getJSONObject(getRequest());
			System.out.println(json.toString());
			JSONObject json1=getSessionAttr("1doUser");
			T1doBase t1doBase =json.getObject("BASE", T1doBase.class);
			t1doBase.setOStartTime(new Date()).setOCreateTime(new Date()).setCreateUser(json1.getString("loginName")).setCreateUserName(json1.getString("username"));
			t1doBase.setShowId(IDUtil.getUid());
			t1doBase.setORange(StrUtil.getOnly(t1doBase.getOCustomer(),t1doBase.getCreateUser(),t1doBase.getOExecutor()));
			t1doBase.setORangeName(StrUtil.getOnly(t1doBase.getOCustomerName(),t1doBase.getCreateUserName(),t1doBase.getOExecutorName()));
			t1doBase.save(); 
			//JSONObject json2=HttpUtil.loginIm(json1.getString("useraccount")); 
			JSONArray arr=json.getJSONArray("ATTR");
			if(arr!=null){
			for (int i = 0; i < arr.size(); i++) {
				T1doAttr t1doAttr=arr.getObject(i, T1doAttr.class);
				t1doAttr.setShowId(t1doBase.getShowId());
				t1doAttr.save();
				t1doAttr.setAttrOrder(t1doAttr.getID());
				t1doAttr.update();
				
			}}
			new T1doStatus().setShowId(t1doBase.getShowId()).save();//1do状态表

			new T1doSet().setShowId(t1doBase.getShowId()).setEventType(json.getString("EVENT_TYPE")).save();
			//T1doPstatus.saveIdoPstatus(t1doBase.getShowId(), t1doBase.getOCustomer(),t1doBase.getCreateUser(),t1doBase.getOExecutor(),t1doBase.getCC());
			String[] users={t1doBase.getOCustomer(),t1doBase.getCreateUser(),t1doBase.getOExecutor(),t1doBase.getCC()};
			String[] usernames={t1doBase.getOCustomerName(),t1doBase.getCreateUserName(),t1doBase.getOExecutorName(),t1doBase.getCcName()};
			T1doPstatus.saveIdoPstatus1(t1doBase.getShowId(),users,usernames);
			//new T1doLog().setShowId(t1doBase.getShowId()).setOUser(t1doBase.getCreateUser()).setOUserName(t1doBase.getCreateUserName()).setOpTime(t1doBase.getOCreateTime()).setLog(t1doBase.getCreateUserName()+"帮助"+t1doBase.getOCustomerName().replace(";","、")+"创建了此1do").setLogType(1).save();                                                   
			new T1doLog().setShowId(t1doBase.getShowId()).setOUser(t1doBase.getCreateUser()).setOUserName(t1doBase.getCreateUserName()).setOpTime(t1doBase.getOCreateTime()).setLog(t1doBase.getCreateUserName()+"创建了此1do").setLogType(1).save();                                                   
			sendIdo(t1doBase);
			
			renderJson(JsonUtil.getMap(200, "创建1do成功！"));
		}
	   
	   
	    @Before(Tx.class)
	    public void createIdo2() {
	    	List<UploadFile> uploadFiles=this.getFiles("FILE");//获取前台上传文件对象
	    	String base=getPara("BASE");
	    	JSONObject json=JSONObject.parseObject(base);	
	    	JSONObject json1=getSessionAttr("1doUser");
	    	T1doBase t1doBase =json.toJavaObject(T1doBase.class);
	    	t1doBase.setOStartTime(new Date()).setOCreateTime(new Date()).setCreateUser(json1.getString("loginName")).setCreateUserName(json1.getString("username"));
	    	t1doBase.setShowId(IDUtil.getUid());
	    	
	    	t1doBase.setORange(StrUtil.getOnly(t1doBase.getOCustomer(),t1doBase.getCreateUser(),t1doBase.getOExecutor()));
	    	t1doBase.setORangeName(StrUtil.getOnly(t1doBase.getOCustomerName(),t1doBase.getCreateUserName(),t1doBase.getOExecutorName()));
	    	t1doBase.save(); 
	    	String ATTR=getPara("ATTR");
	    	JSONArray arr=JSONArray.parseArray(ATTR);
	    	if(arr!=null){
	    		for (int i = 0; i < arr.size(); i++) {
	    			T1doAttr t1doAttr=arr.getObject(i, T1doAttr.class);
	    			t1doAttr.setShowId(t1doBase.getShowId());
	    			t1doAttr.save();
	    			t1doAttr.setAttrOrder(t1doAttr.getID());
	    			t1doAttr.update();
	    			new T1doFeedback().setShowId(t1doBase.getShowId()).setOUser(t1doAttr.getUploadUser())
					.setOUserName(t1doAttr.getUploadUserName()).setFbTime(t1doAttr.getUploadTime())
					.setFbType(3).setATTRID(t1doAttr.getID()+"").setAttrPath(t1doAttr.getAttrPath()).setFBCONTENT("上传"+t1doAttr.getAttrName()).save();
	    		}}
	    	new T1doStatus().setShowId(t1doBase.getShowId()).save();//1do状态表
	    	
	    	new T1doSet().setShowId(t1doBase.getShowId()).setEventType(getPara("EVENT_TYPE")).save();
	    	String[] users={t1doBase.getOCustomer(),t1doBase.getCreateUser(),t1doBase.getOExecutor()};
	    	String[] usernames={t1doBase.getOCustomerName(),t1doBase.getCreateUserName(),t1doBase.getOExecutorName()};
	    	T1doPstatus.saveIdoPstatus1(t1doBase.getShowId(),users,usernames);
	    	new T1doLog().setShowId(t1doBase.getShowId()).setOUser(t1doBase.getCreateUser()).setOUserName(t1doBase.getCreateUserName()).setOpTime(t1doBase.getOCreateTime()).setLog(t1doBase.getCreateUserName()+"创建了此1do").setLogType(1).save();                                                   
	    	T1doAttr t1doAttr=t1doBase.newIdoAttr();
	    	for(UploadFile uploadFile:uploadFiles){
	    		File file = uploadFile.getFile();//获取文件对象
	    		FileUtil fs = new FileUtil();
	    		String fileName=IDUtil.getUid()//UUID.randomUUID().toString()
	    				+ file.getName().substring(file.getName().lastIndexOf("."));
	    		File t = new File("D:\\1do\\upload\\" + fileName);//设置本地上传文件对象（并重命名）
	    		t1doAttr.setAttrPath("http://172.16.9.195:8080/1do/upload/"+fileName);
	    		t1doAttr.setAttrName(file.getName());
	    		try {
	    			t.createNewFile();
	    		} catch (IOException e) {
	    			e.printStackTrace();
	    		}
	    		fs.fileChannelCopy(file, t);//将上传的文件的数据拷贝到本地新建的文件
	    		file.delete();
	    		t1doAttr.save();
	    		t1doAttr.setAttrOrder(t1doAttr.getID()).update();
	    		new T1doFeedback().setShowId(t1doBase.getShowId()).setOUser(json1.getString("loginName"))
				.setOUserName(json1.getString("username")).setFbTime(t1doAttr.getUploadTime())
				.setFbType(3).setATTRID(t1doAttr.getID()+"").setAttrPath(t1doAttr.getAttrPath()).setFBCONTENT("上传 "+t1doAttr.getAttrName()).save();
	    		t1doAttr.remove("ID");
	    		
	    	}
	    	
	    	sendIdo(t1doBase);
	    	
	    	renderJson(JsonUtil.getMap(200, "创建1do成功！"));
	    }
	    @Before(Tx.class)
	    public void createIdo1() {
	    	List<UploadFile> uploadFiles=this.getFiles("FILE");//获取前台上传文件对象
			String base=getPara("BASE");
			JSONObject json=JSONObject.parseObject(base);	
	    	JSONObject json1=getSessionAttr("1doUser");
	    	T1doBase t1doBase =json.getObject("BASE", T1doBase.class);
	    	t1doBase.setOStartTime(new Date()).setOCreateTime(new Date()).setCreateUser(json1.getString("loginName")).setCreateUserName(json1.getString("username"));
	    	t1doBase.setShowId(IDUtil.getUid());
	    	
	    	t1doBase.setORange(StrUtil.getOnly(t1doBase.getOCustomer(),t1doBase.getCreateUser(),t1doBase.getOExecutor()));
	    	t1doBase.setORangeName(StrUtil.getOnly(t1doBase.getOCustomerName(),t1doBase.getCreateUserName(),t1doBase.getOExecutorName()));
	    	t1doBase.save(); 
	    	JSONArray arr=json.getJSONArray("ATTR");
	    	if(arr!=null){
	    		for (int i = 0; i < arr.size(); i++) {
	    			T1doAttr t1doAttr=arr.getObject(i, T1doAttr.class);
	    			t1doAttr.setShowId(t1doBase.getShowId());
	    			t1doAttr.save();
	    			t1doAttr.setAttrOrder(t1doAttr.getID());
	    			t1doAttr.update();
	    		}}
	    	new T1doStatus().setShowId(t1doBase.getShowId()).save();//1do状态表
	    	
	    	new T1doSet().setShowId(t1doBase.getShowId()).setEventType(json.getString("EVENT_TYPE")).save();
	    	String[] users={t1doBase.getOCustomer(),t1doBase.getCreateUser(),t1doBase.getOExecutor()};
	    	String[] usernames={t1doBase.getOCustomerName(),t1doBase.getCreateUserName(),t1doBase.getOExecutorName()};
	    	T1doPstatus.saveIdoPstatus1(t1doBase.getShowId(),users,usernames);
	    	new T1doLog().setShowId(t1doBase.getShowId()).setOUser(t1doBase.getCreateUser()).setOUserName(t1doBase.getCreateUserName()).setOpTime(t1doBase.getOCreateTime()).setLog(t1doBase.getCreateUserName()+"创建了此1do").setLogType(1).save();                                                   
	    	T1doAttr t1doAttr=t1doBase.newIdoAttr();
			for(UploadFile uploadFile:uploadFiles){
				File file = uploadFile.getFile();//获取文件对象
				FileUtil fs = new FileUtil();
				String fileName=IDUtil.getUid()//UUID.randomUUID().toString()
						+ file.getName().substring(file.getName().lastIndexOf("."));
				File t = new File("D:\\1do\\upload\\" + fileName);//设置本地上传文件对象（并重命名）
				t1doAttr.setAttrPath("http://172.16.9.195:8080/1do/upload/"+fileName);
				t1doAttr.setAttrName(file.getName());
				try {
					t.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				fs.fileChannelCopy(file, t);//将上传的文件的数据拷贝到本地新建的文件
				file.delete();
				t1doAttr.save();
				t1doAttr.setAttrOrder(t1doAttr.getID()).update();
				t1doAttr.remove("ID");
			}
		
	    	//sendIdo(t1doBase);
	        
	    	renderJson(JsonUtil.getMap(200, "创建1do成功！"));
	    }
		/*
		 2018年6月21日下午2:36:27 方升群  //新增1do分类（T1doType）
		*/
		public void test() {
			renderJson(getSessionAttr("1doUser"));
		}
		public void saveIdoType() {
			JSONObject json=JsonUtil.getJSONObject(getRequest());
			T1doType t1doType=json.toJavaObject(T1doType.class);
			
			t1doType.setOTypeId(IDUtil.getUid());
			t1doType.save();
			renderJson(JsonUtil.getMap(200, "添加成功！"));
		}

	      public void msginfo() {
	    	JSONObject json=JsonUtil.getJSONObject(getRequest());	      
	    	JSONArray jsonArray = json.getJSONArray("msgId");
			@SuppressWarnings("rawtypes")
			List<LinkedHashMap> list=new ArrayList<LinkedHashMap>();
			for(int i=0;i<jsonArray.size();i++) {
				//T1doBase msg =T1doBase.dao.findFirst("select * from t_1do_base where MESSAGE_ID like ?", "%"+jsonArray.get(i)+"%");
				T1doBase msg =T1doBase.dao.findFirst("select * from t_1do_temp where BASE like ?", "%"+jsonArray.get(i)+"%");
				//Msginfo msg = Msginfo.dao.findById(jsonArray.get(i));
				LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>(); 
				if(msg!=null){
					map.put("msgId", jsonArray.get(i));
					map.put("exist", true);
					list.add(map);
				}
				
			}
			LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();  
			 map.put("code", 200);
			 map.put("message", list);
	         renderJson(map);
	    	  
	}
	      /*
		 2018年8月6日下午3:38:11 方升群
		*/
		public void getsessionName() {
			JSONObject json1=JsonUtil.getJSONObject(getRequest());
			JSONObject json= new JSONObject();
			JSONObject json2= new JSONObject();
			JSONObject json3= new JSONObject();
			JSONObject json4= new JSONObject();
			json.put("appName", "launchr");
			json.put("appToken", "verify-code");
			json.put("userName", "NO6lZyJjYRCAKd9R");
			/*json.put("userToken", json1.getString("LoginToken"));
			json.put("userName", json1.getString("loginName"));*/
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
		}
	      public static void main(String[] args) {
	  		HashSet<String> hs=new HashSet<String>();
	  		hs.add("1");
	  		hs.add("3");
	  		hs.add("5");
	  		hs.add("4");
	  		hs.add("6");
	  		hs.remove("5");
	  		System.out.println(hs);
	  		
	  		/*HashMap<String,String> uploadFiles=new HashMap<String,String>();
	  		uploadFiles.put("1", "sssss");
	  		System.out.println(uploadFiles);

	  		System.out.println("1234.jsp".substring("1234.jsp".lastIndexOf(".")));*/
	  	}	
		

}
