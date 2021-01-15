 package com.luqi.controller;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.fileupload.util.Streams;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.luqi.common.model.Msg;
import com.luqi.common.model.T1doAttr;
import com.luqi.common.model.T1doBase;
import com.luqi.common.model.T1doBoard;
import com.luqi.common.model.T1doCall;
import com.luqi.common.model.T1doFeedback;
import com.luqi.common.model.T1doFw;
import com.luqi.common.model.T1doLabel;
import com.luqi.common.model.T1doLabelFeedback;
import com.luqi.common.model.T1doLog;
import com.luqi.common.model.T1doOrder;
import com.luqi.common.model.T1doProject1do;
import com.luqi.common.model.T1doProjectStakeholder;
import com.luqi.common.model.T1doPstatus;
import com.luqi.common.model.T1doRelation;
import com.luqi.common.model.T1doRelationFeedback;
import com.luqi.common.model.T1doRelationRecord;
import com.luqi.common.model.T1doType;
import com.luqi.common.model.T1doWechat;
import com.luqi.common.model.TRegUser;
import com.luqi.common.model.Temp;
import com.luqi.interceptor.DoLoginInterceptor;
import com.luqi.interceptor.LoginInterceptor;
import com.luqi.interfaces.UpdateBase;
import com.luqi.service.DoService;
import com.luqi.service.FourService;
import com.luqi.service.WeChatService;
import com.luqi.timer.AddLabel;
import com.luqi.timer.BxblTask;
import com.luqi.timer.CommonTask;
import com.luqi.timer.SendIdo;
import com.luqi.timer.SupervisionAndEvaluation;
import com.luqi.timer.YscgTask;
import com.luqi.util.DbUtil;
import com.luqi.util.ExcelExportUtil;
import com.luqi.util.HttpUtil;
import com.luqi.util.IDUtil;
import com.luqi.util.JsonUtil;
import com.luqi.util.MsgUtil;
import com.luqi.util.ShortMessageUtil;
import com.luqi.util.StrUtil;
import com.luqi.util.TimeUtil;
import com.luqi.util.UrlUtil;
import com.luqi.util.node.InfiniteLevelTreeUtil;
import com.luqi.util.node.Node;
import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.MultipartParser;
import com.oreilly.servlet.multipart.ParamPart;
import com.oreilly.servlet.multipart.Part;

@Before(DoLoginInterceptor.class)
public class DoController extends Controller {
	private Logger log=Logger.getLogger(DoController.class);
	
	/**
     * @Author coco
     * @Description 点击附件留痕
     * @Date 2019年6月10日
    */
	public void clickOnTheAttachment() {

		JSONObject json=getAttr("request");
		JSONObject user=getAttr("user");
	   	T1doLog.saveLog(json.getString("SHOW_ID"), user.getString("loginName"), user.getString("username"),user.getString("username")+"下载一个附件", 17,json.getString("ID"));
		renderJson(MsgUtil.successMsg("成功"));
	}

	/**
     * @Author coco
     * @Description 显示掩藏相关日志
     * @Date 2019年5月6日
    */
	public void updateRelationRecord(){

		JSONObject json=getAttr("request");
		JSONObject user=getAttr("user");
	   	if(user.getBooleanValue("isfw")){
	   		T1doRelationRecord.updateType(json.getLongValue("ID"));
	   		renderJson(MsgUtil.successMsg("成功"));
	   	}else{
	   		renderJson(MsgUtil.successMsg("权限不足"));
	   	}
	}

	/**
     * @Author coco
     * @Description 相关日志
     * @Date 2019年5月6日
    */
	public void getRelationRecord(){
		JSONObject json=getAttr("request");
		JSONObject user=getAttr("user");
		String sql=user.getBooleanValue("isfw")?"":" and b.type!=0";
		renderJson(MsgUtil.successMsg(T1doRelationRecord.selectRelation(json.getString("SHOW_ID"),sql)));
	}

	/**
     * @Author coco
     * @Description 显示掩藏相关反馈
     * @Date 2019年5月6日
    */
	public void updateRelationFeedback(){
		JSONObject json=getAttr("request");
		JSONObject user=getAttr("user");
    	if(user.getBooleanValue("isfw")){
    		T1doRelationFeedback.updateType(json.getLongValue("RFID"));
    		renderJson(MsgUtil.successMsg("成功"));
    	}else{
    		renderJson(MsgUtil.successMsg("权限不足"));
    	}
	}

	/**
     * @Author coco
     * @Description 相关反馈
     * @Date  2019年5月6日
    */
	public void getRelationFeedback(){
		JSONObject json=getAttr("request");
		JSONObject user=getAttr("user");
		String sql=user.getBooleanValue("isfw")?"":" and (c.O_CUSTOMER like CONCAT('%','"+user.getString("loginName")+"','%') or c.O_EXECUTOR like CONCAT('%','"+user.getString("loginName")+"','%')) and c.O_STATUS!=6  and b.type!=0";			
		renderJson(MsgUtil.successMsg(T1doRelationFeedback.selectRelation(json.getString("SHOW_ID"),sql)));
	}

	/**
     * @Author coco
     * @Description 删除关联
     * @Date 2019年3月6日
    */
	public void deleteRelation() {
		JSONObject json=getAttr("request");
		JSONObject user=getAttr("user");
		if(user.getBooleanValue("isfw"))
		 renderJson(MsgUtil.successMsg(DbUtil.update(json.getString("SHOW_ID"),json.getString("RELATION_SHOW_ID"),1,0,"","")));
		else
		 renderJson(MsgUtil.errorMsg("权限不足"));
	}
	
	/**
     * @Author coco
     * @Description 批量添加关联
     * @Date 2019年2月13日
    */
	
	public void batchAddRelation() {
    	JSONObject json=JsonUtil.getJSONObject(getRequest());
		JSONArray array=json.getJSONArray("list");
		for (int i = 0; i < array.size(); i++) {			
			DbUtil.update(json.getString("SHOW_ID"),array.getString(i),2,0,"","");
		}
		renderJson(MsgUtil.successMsg("添加成功"));

	}

	/**
     * @Author coco
     * @Description 传送门关键字查询1do
     * @Date  2019年2月13日
    */
	
	public void selectBybase() {		
    	renderJson(MsgUtil.successMsg(T1doBase.selectBybase(JsonUtil.getJSONObject(getRequest()))));
	}

	/**
     * @Author coco
     * @Description 传送门获取相关联1do
     * @Date 2019年2月13日
    */

	public void getRelation() {
		JSONObject json=getAttr("request");
		JSONObject user=getAttr("user");
    	String sql=user.getBooleanValue("isfw")?"":" and (O_CUSTOMER like CONCAT('%','"+user.getString("loginName")+"','%') or O_EXECUTOR like CONCAT('%','"+user.getString("loginName")+"','%')) ";			
		renderJson(MsgUtil.successMsg(T1doRelation.selectRelation(json.getString("SHOW_ID"),sql)));
	}

	/**
     * @Author coco
     * @Description 关联排序
     * @Date 2019年2月13日
    */
	public void relationSort() {
		JSONObject user=getAttr("user");
		if(user.getBooleanValue("isfw")){
			JSONObject json=getAttr("request");
			JSONArray array=json.getJSONArray("list");
			List<Record> list=new ArrayList<Record>();
			for (int i = 0; i < array.size(); i++) {			
				Record r=new Record();
				r.set("ID", array.getInteger(i));
				r.set("SORT",i+1);
				list.add(r);	
				//Db.update("update t_1do_relation set sort=? where id=?",i+1,array.getInteger(i));
			}
			Db.batchUpdate(json.getString("target"),"ID", list, 1000);
			renderJson(MsgUtil.successMsg("排序完成"));
		}else{
			renderJson(MsgUtil.errorMsg("无操作权限"));
		}
	}

	/**
     * @Author coco
     * @Description 添加或删除标签
     * @Date 2018年12月4日
    */
	public void addOrDeleteLabel() {
		JSONObject json=getAttr("request");
		JSONObject user=getAttr("user");
    	T1doLabel l=null;
		if(user.getBooleanValue("isfw")){
	    	if(json.getString("method").equals("add")){	    		
	    		if(T1doLabel.getT1doLabel(json)==null){
	    		    l =json.toJavaObject(T1doLabel.class);
	    			l.setTYPE(2).save();
	    			
	    			new Thread(()->T1doRelation.updateSimilarity(json.getString("SHOW_ID"),"",1)).start();
	    			
	    		}else{
	    			renderJson(MsgUtil.errorMsg("标签已存在"));
	    			return;
	    		}
	    	}else if(json.getString("method").equals("delete")){			
	    		DbUtil.deleteLable(json.getLongValue("id"));//加了权重所以可能删除多个相同的标签。
	    		
	    		new Thread(()->T1doRelation.updateSimilarity(json.getString("SHOW_ID"),"and SIMILARITY>0",1)).start();
	    	}else if(json.getString("method").equals("deleteAll")){			
	    		DbUtil.deleteALLLable(json.getString("SHOW_ID"));//加了权重所以可能删除多个相同的标签。
	    		new Thread(()->T1doRelation.updateSimilarity(json.getString("SHOW_ID"),"and SIMILARITY>0",1)).start();
	    	}
			renderJson(MsgUtil.successMsg(l));
		}else{
			renderJson(MsgUtil.errorMsg("无操作权限"));
		}
		
	}

	/**
     * @Author coco
     * @Description 获得附件
     * @Date  2018年10月31日
    */
	
	public void getAttr() {
    	JSONObject json=JsonUtil.getJSONObject(getRequest()); 	
		renderJson(MsgUtil.successMsg(T1doAttr.getAttr(json.getString("SHOW_ID"))));
	}

	/**
     * @Author coco
     * @Description 导出Excel
     * @Date 2018年10月31日
    */
	
    public void exportExcel() {    
    	//http://localhost:8080/1do/do/exportExcel?type=3
         int type = getParaToInt("type");
         
         List<Record> data = DoService.exportExcel(type);
 		 String fileName="1do"+TimeUtil.getCurrentDateTime("yyyyMMddhhmmss")+".xls";
         File file = ExcelExportUtil.createExcelFile(fileName, data);
 		 renderFile(file);
    }

    /**
     * @Author coco
     * @Description 修改参与人的身份（抄送人或受理人）
     * @Date 2018年8月14日下午5:32:24
    */
	public void chuangUserId() {
		JSONObject json=getAttr("request");
		JSONObject user=getAttr("user");
		if(user.getBooleanValue("isfw")){
			String[] users=json.getString("user").split(";");
		    DbUtil.update(json.getString("SHOW_ID"),"",3,json.getIntValue("otherid"),"","");		
			for(String user1:users){
			DbUtil.update(json.getString("SHOW_ID"),user1,4,json.getIntValue("otherid"),"","");
		    }
			renderJson(MsgUtil.successMsg("修改成功"));   
			
		}else {
			
			 renderJson(MsgUtil.errorMsg("权限不足"));
		}
		
	}

	/**
     * @Author coco
     * @Description 看板搜索
     * @Date 2018年7月5日上午9:56:46
    */
	public void searchNum() {
		JSONObject json=getAttr("request");
		JSONObject user=getAttr("user");
		JSONObject json2=DoService.searchNum(json,user.getString("loginName"));
		renderJson(json2);
		
	}
	    

		/**
	     * @Author coco
	     * @Description APP看板搜索
	     * @Date  2018年7月5日上午9:56:46
	    */
		public void appSearch() {
			JSONObject json=getAttr("request");
			JSONObject user=getAttr("user");
			renderJson(MsgUtil.successMsg(DoService.getAppSearchResult(json,user.getString("loginName"))));
			
		}
		
	
		/**
	     * @Author coco
	     * @Description 看板搜索 
	     * @Date 2018年7月5日上午9:56:46
	    */
		public void search() {

			JSONObject json=getAttr("request");
			JSONObject user=getAttr("user");
		    JSONObject json2=  DoService.getBase(user,json);
			renderJson(json2);
		}
		/**
	     * @Author coco
	     * @Description 看板搜索 
	     * @Date 2018年7月5日上午9:56:46
	    */
		public void doSearch() {

			JSONObject json=getAttr("request");
			JSONObject user=getAttr("user");
			renderJson(MsgUtil.successMsg(DoService.getDoBase(user,json)));
		}
		
		
		
		/**
	     * @Author coco
	     * @Description 登入
	     * @Date 
	    */
		
		public void login1do() {
			
			JSONObject json=JsonUtil.getJSONObject(getRequest());	
			JSONObject result=DoService.login1do(json);
			if(result.getIntValue("code")==200) {
				setSessionAttr("user", result);
			}
			renderJson(result);
		}
		/**
	     * @Author coco
	     * @Description 登入
	     * @Date 
	    */
		public void login() {

			JSONObject json=JsonUtil.getJSONObject(getRequest());	
			Msg msg=DoService.login(json);
			setSessionAttr("user", msg.getData());
			renderJson(msg);
		}
		
		/**
	     * @Author coco
	     * @Description 设置session		
	     * @Date 
	    */
		public  JSONObject setSession(String loginName){
			JSONObject json1=TRegUser.getUserForShowId(loginName);
			return json1;
		}
		
		/**
	     * @Author coco
	     * @Description 通讯录获取最近联系人
	     * @Date 
	    */
		public void GetContact() {
			JSONObject user=getAttr("user");
			String str=HttpUtil.getParameter(user, "/Base-Module/CompanyUser/GetContact");
			System.out.println(str);
			String result   =   HttpUtil.doPost1("http://xcgovapi.hzxc.gov.cn/Base-Module/CompanyUser/GetContact", str);
			renderJson(result);
		}
		/**
	     * @Author coco
	     * @Description 通讯录获取群组
	     * @Date 
	    */
		public void grouplist() {
			JSONObject user=getAttr("user");
			 JSONObject map=new JSONObject();
			 map.put("appName", "launchr");
			 map.put("userName", user.getString("loginName"));
			 
			String result   =   HttpUtil.doPost1("http://172.16.9.109:20001/launchr/chat/grouplist", map.toString());
			renderJson(result);
		}
		
		/**
	     * @Author coco
	     * @Description 通讯录获取部门和部门人员列表
	     * @Date 
	    */
		public void GetList() {
			JSONObject json=getAttr("request");
			JSONObject user=getAttr("user");
			String str=HttpUtil.getParameter(user, "/Base-Module/CompanyDept/GetList",json.getIntValue("isContainChildDeptMember"),json.getString("parentId"));
			String result   =   HttpUtil.doPost1("http://xcgovapi.hzxc.gov.cn/Base-Module/CompanyDept/GetList", str);		
			renderJson(result);
		}
		/**
	     * @Author coco
	     * @Description 获取部门人员列表
	     * @Date 
	    */
		public void GetListUser() {
			JSONObject json=getAttr("request");
			JSONObject user=getAttr("user");
			String str=HttpUtil.getParameter1(user, "/Base-Module/CompanyUser/GetList",json.getIntValue("isContainChildDeptMember"),json.getString("deptId"));
			String result   =   HttpUtil.doPost1("http://xcgovapi.hzxc.gov.cn/Base-Module/CompanyUser/GetList", str);
			renderJson(result);
		}
		/**
	     * @Author coco
	     * @Description 搜索人员
	     * @Date 
	    */
		public void searchUser() {
			JSONObject json=getAttr("request");
			JSONObject user=getAttr("user");
			String str=HttpUtil.getParameter1(user, "/Base-Module/CompanyUser/GetList",json.getIntValue("isContainChildDeptMember"),json.getIntValue("createPage"),json.getIntValue("pageSize"),json.getString("searchKey"));
			String result   =   HttpUtil.doPost1("http://xcgovapi.hzxc.gov.cn/Base-Module/CompanyUser/GetList", str);
			renderJson(result);
		}
		
		/**
	     * @Author coco
	     * @Description 通讯录获取用户信息
	     * @Date 
	    */
		public void CompanyUser() {
			JSONObject json=getAttr("request");
			JSONObject user=getAttr("user");
			String str=HttpUtil.getParameter1(user, "/Base-Module/CompanyUser",json.getString("SHOW_ID"));
			String result   =   HttpUtil.doPost1("http://xcgovapi.hzxc.gov.cn/Base-Module/CompanyUser", str);
			renderJson(result);
		}
		
	
		
		/**
	     * @Author coco
	     * @Description 1do详情
	     * @Date 2018年6月25日下午3:37:00
	    */
		@Before(Tx.class)
		public void getIdoMessage() {
			JSONObject json=getAttr("request");
			JSONObject user=getAttr("user");
			final T1doBase t1doBase=T1doBase.getIdoBase2(json.getString("SHOW_ID"));
			if(t1doBase==null){
				renderJson(MsgUtil.errorMsg("工单不存在"));
				return;
			}
			final String loginName=user.getString("loginName");//user1==null?json.getString("loginName"):
			final String username=user.getString("username");
			/**
			 * 来源 0、全部  1、call 或者oa 2、主动办 3、三实库 4、其他 5、领导批示6.城市大脑,7综合信息系统，
			 * 	8、1call办9、城市大脑综合指挥平台 10、督查指挥，11云上城管，12百姓爆料，13红旗班
			 */
			//云上城管特殊定制用不到下面的字段这里直接返回
			if(t1doBase.getSOURCE()==11) {
				new Thread(new UpdateBase(t1doBase,loginName,username)).start();
				LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();  
				t1doBase.put("ITEM", new ArrayList<>());
				map.put("BASE", t1doBase);
				map.put("message", "Success");
				map.put("code", 200);		
				renderJson(map);
				return;
			}
           T1doProject1do t=T1doProject1do.getT1doProject1do(json.getString("SHOW_ID"));
           List<Node> list=new ArrayList<Node>();
           if(t!=null) {
	           List<T1doBoard> t1doBoards=T1doBoard.getItems(t.getItemId());
	           list=InfiniteLevelTreeUtil.getInfiniteLevelTree(t1doBoards);
           }
           new Thread(new UpdateBase(t1doBase,loginName,username)).start();
           t1doBase.put("ITEM", list);
            t1doBase.put("POWER", T1doProjectStakeholder.getFlag(loginName, json.getString("SHOW_ID"), user.getIntValue("POWER")));//1整理层2领导4项目干系人3普通用户
			t1doBase.put("ccp", t1doBase.getUser(2));
			t1doBase.put("executor", t1doBase.getUser(1));
			t1doBase.put("CUSTOMER_LIST", t1doBase.getUser(3));
			t1doBase.put("EXECUTOR_LIST", t1doBase.getUser(5));
			t1doBase.put("O_LABEL", t1doBase.getLabel());
			LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();  
			map.put("BASE", t1doBase);
			map.put("message", "Success");
			map.put("code", 200);		
			renderJson(map);
		}
		/**
	     * @Author coco
	     * @Description 1do详情
	     * @Date 2018年6月25日下午3:37:00
	    */
		@Before(Tx.class)
		public void getDoDetails() {
			JSONObject json=getAttr("request");
			JSONObject user=getAttr("user");
			final T1doBase t1doBase=T1doBase.getIdoBase2(json.getString("SHOW_ID"));
			if(t1doBase==null){
				renderJson(MsgUtil.errorMsg("工单不存在"));
				return;
			}
			final String loginName=user.getString("loginName");//user1==null?json.getString("loginName"):
			final String username=user.getString("username");
			/**
			 * 来源 0、全部  1、call 或者oa 2、主动办 3、三实库 4、其他 5、领导批示6.城市大脑,7综合信息系统，
			 * 	8、1call办9、城市大脑综合指挥平台 10、督查指挥，11云上城管，12百姓爆料，13红旗班
			 */
			//云上城管特殊定制用不到下面的字段这里直接返回
			if(t1doBase.getSOURCE()==11) {
				new Thread(new UpdateBase(t1doBase,loginName,username)).start();
				LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();  
				t1doBase.put("ITEM", new ArrayList<>());
				map.put("BASE", t1doBase);
				map.put("message", "Success");
				map.put("code", 200);		
				renderJson(map);
				return;
			}
           T1doProject1do t=T1doProject1do.getT1doProject1do(json.getString("SHOW_ID"));
           List<Node> list=new ArrayList<Node>();
           if(t!=null) {
	           List<T1doBoard> t1doBoards=T1doBoard.getItems(t.getItemId());
	           list=InfiniteLevelTreeUtil.getInfiniteLevelTree(t1doBoards);
           }
           new Thread(new UpdateBase(t1doBase,loginName,username)).start();
           t1doBase.put("ITEM", list);
            t1doBase.put("POWER", T1doProjectStakeholder.getFlag(loginName, json.getString("SHOW_ID"), user.getIntValue("POWER")));//1整理层2领导4项目干系人3普通用户
			t1doBase.put("ccp", t1doBase.getUser(2));
			t1doBase.put("executor", t1doBase.getUser(1));
			t1doBase.put("CUSTOMER_LIST", t1doBase.getUser(3));
			t1doBase.put("EXECUTOR_LIST", t1doBase.getUser(5));
			t1doBase.put("O_LABEL", t1doBase.getLabel());
			renderJson(MsgUtil.successMsg(t1doBase));
		}

		 
		/**
	     * @Author coco
	     * @Description 1do详情修改发起时间/完成时间
	     * @Date 2018年6月27日下午3:07:59
	    */
		 @Before(Tx.class)
		public void changeTime() {
		
			JSONObject json=getAttr("request");
			JSONObject user=getAttr("user");
	    	T1doPstatus t1=T1doPstatus.getUser(json.getString("SHOW_ID"),user.getString("loginName"));   
	    	int power=T1doProjectStakeholder.getFlag(user.getString("loginName"), json.getString("SHOW_ID"), user.getIntValue("POWER"));
			if(user.getBooleanValue("isfw")||StrUtil.getflag(t1.getUserType(), 3)||power!=3){
			   T1doBase t=json.toJavaObject(T1doBase.class);
				t.updateTime(json);
			
				renderJson(MsgUtil.successMsg("修改成功"));					
			}else{
				renderJson(MsgUtil.errorMsg("权限不足"));		
			}
			
		}
		 /**
		     * @Author coco
		     * @Description 1do详情修改计划时间
		     * @Date 2018年6月27日下午3:07:59
		    */
			 @Before(Tx.class)
			public void changePlannedTime() {
			
				JSONObject json=getAttr("request");
				JSONObject user=getAttr("user");
		    	T1doPstatus t1=T1doPstatus.getUser(json.getString("SHOW_ID"),user.getString("loginName"));   
		    	int power=T1doProjectStakeholder.getFlag(user.getString("loginName"), json.getString("SHOW_ID"), user.getIntValue("POWER"));
				if(user.getBooleanValue("isfw")||StrUtil.getflag(t1.getUserType(), 3)||power!=3){
				   T1doBase t=json.toJavaObject(T1doBase.class);
					t.changePlannedTime(json);
				
					renderJson(MsgUtil.successMsg("修改成功"));					
				}else{
					renderJson(MsgUtil.errorMsg("权限不足"));		
				}
				
			}
		/**
	     * @throws IOException 
		 * @throws UnsupportedEncodingException 
		 * @Author coco
	     * @Description 1do详情修改标题或者内容
	     * @Date 
	    */
		 @Before(Tx.class)
		 public void changeText() throws UnsupportedEncodingException, IOException {
		
			JSONObject json=getAttr("request");
			JSONObject user=getAttr("user");	
			 T1doPstatus t1=T1doPstatus.getUser(json.getString("SHOW_ID"),user.getString("loginName"));
			 int power=T1doProjectStakeholder.getFlag(user.getString("loginName"), json.getString("SHOW_ID"), user.getIntValue("POWER"));
			 T1doBase t=T1doBase.getIdoBase(json.getString("SHOW_ID"));
			 if(user.getBoolean("isfw")||(t1!=null&&StrUtil.getflag(t1.getUserType(), 1))||power!=3||t.getSOURCE()==11){
				int type=3;
				if(StrKit.isBlank(json.getString("content"))){
					 renderJson(MsgUtil.errorMsg("修改内容不能为空"));
					return;
				}else if(json.getString("target").equals("O_DESCRIBE")){
					if(StrKit.notBlank(t.getODescribe())){
						if(t.getODescribe().equals(json.getString("content"))){
							 renderJson(MsgUtil.errorMsg("修改内容不能与原内容相同"));
							return;
						}
					}
				}else if(json.getString("target").equals("O_TITLE")){
					if(StrKit.notBlank(t.getOTitle())){
						if(t.getOTitle().equals(json.getString("content"))){
							 renderJson(MsgUtil.errorMsg("修改标题不能与原标题相同"));
							return;
						}
					}
					type=4;
				}
				int i= DbUtil.update(json.getString("target"), json.getString("content"), 9, 0,json.getString("AT"),json.getString("SHOW_ID"));				
				if(i==1){
					 T1doLog.saveLog(json.getString("SHOW_ID"),user.getString("loginName"),user.getString("username"), user.getString("username")+"修改此1do", 14,new Temp(t.getODescribe(),json.getString("content")).toString());
					 if(json.getString("target").equals("O_DESCRIBE")&&t.getSOURCE()!=11){			 
						new Thread(new AddLabel(t.getODescribe(), json.getString("SHOW_ID"),1)).start();//批量添加标签
						StrUtil.getQTR(t);
						  
					 }
					//String describe= "1Do 内容更新："+json.getString("content")+"\n"
					//		       + "查看详情 -"+ UrlUtil.ip+"/1do/shortMessage/MyHtml.html?SHOW_ID="+t.getShowId();
					 if(StrKit.notBlank(t.getWechatGroupId()))
					T1doWechat.saveT1doWechat(t.getShowId(), WeChatService.CreateGroupNotice(t.getWechatGroupId(),WeChatService.getMsg("1do 内容更新：",t)), 3,"");
					if(t.getSOURCE()==11) {
						t.setODescribe(json.getString("content"));
						//T1doBase yscg=T1doBase.getIdoBase(json.getString("SHOW_ID"));
						new Thread(new YscgTask(t,3)).start();
					}else {
						//回调接口
						new Thread(new CommonTask(t,type)).start();
					}
					 renderJson(MsgUtil.successMsg("修改成功"));   
				 }else{
					 renderJson(MsgUtil.errorMsg("修改失败"));
				 }
			}else{
				 renderJson(MsgUtil.errorMsg("权限不足"));
			}
			
			
		 }
		 /**
		     * @throws IOException 
		 * @throws UnsupportedEncodingException 
		 * @Author coco
		     * @Description 1do详情修改发起人/参与人/抄送人
		     * @Date  2018年6月27日下午3:07:59 coco
		    */
			 @Before(Tx.class)
			public void changeUser() throws UnsupportedEncodingException, IOException {
		
					JSONObject json=getAttr("request");
					JSONObject user=getAttr("user");
				 T1doPstatus t1=T1doPstatus.getUser(json.getString("SHOW_ID"),user.getString("loginName"));
				 int power=T1doProjectStakeholder.getFlag(user.getString("loginName"), json.getString("SHOW_ID"), user.getIntValue("POWER"));
				boolean flag=json.getString("object").equals("参与人")?true:user.getBoolean("isfw")||StrUtil.getflag(t1==null?2:t1.getUserType(), 2)||power!=3;
				 if(flag){
				
						    int result =DoService.changeUser(json,user);
						    if(result==1)
						    	renderJson(MsgUtil.errorMsg("发起人不能修改"));
						    else
						    	renderJson(MsgUtil.successMsg("修改成功"));
				}else{
					renderJson(MsgUtil.errorMsg("权限不足"));
				}
			}
		
	
		/**
	     * @Author coco
	     * @Description 1do看板置顶
	     * @Date 
	    */
		@Before(Tx.class)
		public void top() {
			JSONObject json=getAttr("request");
			JSONObject user=getAttr("user");
			T1doOrder t=json.toJavaObject(T1doOrder.class);
			T1doOrder t1=T1doOrder.getT1doOrder(json.getString("SHOW_ID"),user.getString("loginName"),json.getIntValue("type"));
			if(t1==null){
				t.setUseraccount(json.getString("loginName")).save();
				renderJson(MsgUtil.successMsg("置顶成功"));
			}else{
				t1.delete();
				renderJson(MsgUtil.successMsg("取消置顶成功"));

			}
		}
		
		/**
	     * @Author coco
	     * @Description 获得操作日志
	     * @Date 2018年6月26日下午11:20:20
	    */
	
		public void getLog() {
			JSONObject json=JsonUtil.getJSONObject(getRequest()); 	
			T1doBase t1doBase =json.toJavaObject(T1doBase.class);
			List<T1doLog> t1doLogs=t1doBase.getIdoLogs1();
			renderJson(t1doLogs); 
		}
		/**
	     * @Author coco
	     * @Description 获得1do操作日志
	     * @Date 2018年6月26日下午11:20:20
	    */
		
		public void getDoLog() {
			JSONObject json=JsonUtil.getJSONObject(getRequest()); 	
			T1doBase t1doBase =json.toJavaObject(T1doBase.class);
			List<T1doLog> t1doLogs=t1doBase.getIdoLogs1();
			renderJson(MsgUtil.successMsg(t1doLogs)); 
		}
		/**
	     * @Author coco
	     * @Description 获得反馈消息。
	     * @Date 2018年6月26日下午10:50:37
	    */
		
		public void getFeedback() { 
			JSONObject json=JsonUtil.getJSONObject(getRequest()); 	
			T1doBase t1doBase =json.toJavaObject(T1doBase.class);
			String sql="";
			if(json.toString().contains("type")&&json.getInteger("type")==2){
				sql=" and Fb_TYPE in(1,2,5,6,10) ";
			}else if(json.toString().contains("type")&&json.getInteger("type")==3){
				sql=" and Fb_TYPE in(3,9) ";
			}
			List<T1doFeedback> t1doFeedbacks=t1doBase.getIdoFeedbacks1(json.getIntValue("id"),json.getIntValue("num"),sql);

		    setSessionAttr(json.getString("SHOW_ID"), T1doFeedback.getNum(json.getString("SHOW_ID")));
			renderJson(t1doFeedbacks);
		}
		/**
		 * @Author coco
	     * @Description 获得1do反馈消息。
	     * @Date 2018年6月26日下午10:50:37
		 */
		
		public void getDoFeedback() { 
			JSONObject json=JsonUtil.getJSONObject(getRequest()); 	
			T1doBase t1doBase =json.toJavaObject(T1doBase.class);
			String sql="";
			if(json.toString().contains("type")&&json.getInteger("type")==2){
				sql=" and Fb_TYPE in(1,2,5,6,10) ";
			}else if(json.toString().contains("type")&&json.getInteger("type")==3){
				sql=" and Fb_TYPE in(3,9) ";
			}
			List<T1doFeedback> t1doFeedbacks=t1doBase.getIdoFeedbacks1(json.getIntValue("id"),json.getIntValue("num"),sql);

		    setSessionAttr(json.getString("SHOW_ID"), T1doFeedback.getNum(json.getString("SHOW_ID")));
			renderJson(MsgUtil.successMsg(t1doFeedbacks));
		}

		/**
	     * @Author coco
	     * @Description 获得反馈消息。（城市大脑）
	     * @Date  2018年6月26日下午10:50:37
	    */
		
		public void getFeedbacks() { 
			JSONObject json=JsonUtil.getJSONObject(getRequest()); 	
			T1doBase t1doBase =json.toJavaObject(T1doBase.class);
			List<T1doFeedback> t1doFeedbacks=t1doBase.getIdoFeedbacks1(json.getIntValue("id"),json.getIntValue("num"));
			renderJson(t1doFeedbacks);
		}
		/**
	     * @Author coco
	     * @Description 轮询
	     * @Date  2018年7月1日下午8:34:11
	    */
		
		public void polling() throws InterruptedException {
	         JSONObject json=JsonUtil.getJSONObject(getRequest());
	         Long time=new Date().getTime();
	         String str=json.getString("SHOW_ID");
	         //if(json.getBooleanValue(key))
	         setSessionAttr(str+"A", json.getBooleanValue("flag"));
		     // 死循环 查询有无数据变化
        	 List<T1doFeedback> getIdoFeedbacks12=new ArrayList<T1doFeedback>();
		     while ((boolean) getSessionAttr(str+"A")) {
		      List<T1doFeedback> getIdoFeedbacks11= T1doFeedback.dao.find("select O_USER_NAME from t_1do_feedback where SHOW_ID=? and FB_TYPE!=4 and isoverdue=1",str);
				int i=(int)getSessionAttr(str);
		      int n=getIdoFeedbacks11.size()-i;
		      if(n!=0){
				     getIdoFeedbacks12=T1doFeedback.dao.find(T1doBase.selectField+" from t_1do_feedback where SHOW_ID=? and FB_TYPE!=4  and isoverdue=1 LIMIT ?,?",str,i,n);;
				   
				     renderJson(getIdoFeedbacks12); 	 	
		             setSessionAttr(str, getIdoFeedbacks11.size());
		             return; // 跳出循环，返回数据
		         } // 模拟没有数据变化，将休眠 hold住连接
		             Thread.sleep(1000);
		             Long time1=new Date().getTime();
		             if(time1-time>40000){
		            	 renderJson(getIdoFeedbacks12);
		            	 return;
		             }
		             
		         
		     }
		     renderJson(getIdoFeedbacks12);
		}
		/**
	     * @Author coco
	     * @Description 详情关闭回调接口
	     * @Date 
	    */
		
		public void closeIdo() {
			JSONObject json=getAttr("request");
			JSONObject user=getAttr("user");
			DbUtil.update(json.getString("SHOW_ID"),user.getString("loginName"), 14, 0, "", "");
			renderJson(MsgUtil.successMsg("下线成功"));
	
		}
	    
		
		/**
	     * @Author coco
	     * @Description 附件反馈
	     * @Date 
	    */
		
		@Before(Tx.class)
		public void feedbackUpload() throws IOException {
			MultipartParser mp = new MultipartParser(this.getRequest(),2147483647, false, false,"UTF-8");//52428800=50*1024*1024    最大2147483647
			List<String> uploadFiles=new ArrayList<String>();
			List<String> uploadFiles1=new ArrayList<String>();
			Part part;
			 JSONObject json=new JSONObject();
			while((part=mp.readNextPart())!=null){
				String name = part.getName();
				if(part.isParam()){
					ParamPart param=(ParamPart) part;
					 String value=param.getStringValue();
					 json.put(name, value);
				}else if(part.isFile()){
					FilePart filePart = (FilePart) part;
					String fileName=IDUtil.getUid()//UUID.randomUUID().toString()
							+ filePart.getFileName().substring(filePart.getFileName().lastIndexOf("."));
					File t1 = new File(UrlUtil.jdlj);//设置本地上传文件对象（并重命名）
					File t = new File(t1,fileName);//设置本地上传文件对象（并重命名）
					FileOutputStream out = new FileOutputStream(t);
	        		Streams.copy(filePart.getInputStream(), out, true);
	        		uploadFiles.add(fileName);
	        		uploadFiles1.add(filePart.getFileName());
				}
			}
			int j=0;
			 JSONObject user1=getSessionAttr("user");
				if(json.getString("loginName")==null&&user1==null&&json.getString("FourPlatformsUser")==null){
					renderJson(MsgUtil.errorMsg("用户未登入"));
					return;
				}else if(json.getString("loginName")!=null&&user1==null){
					user1=setSession(json.getString("loginName"));				
				}else if(json.getString("FourPlatformsUser")!=null&&user1==null){
				    user1=setSession(FourService.getUserAccount(json.getString("FourPlatformsUser")));				
			   }
			JSONObject user=user1;
			System.out.println(json);
			System.out.println("------------------------------");
			if(!json.containsKey("SOURCE")) 
				json.put("source", 0);				
			T1doFeedback t1doFeedback =json.toJavaObject(T1doFeedback.class);
			t1doFeedback.setFbTime(new Date()).setTimeStamp(new Date().getTime()).setOUser(user.getString("loginName")).setOUserName(user.getString("username"));
			T1doAttr t1doAttr=t1doFeedback.getIdoAttr();
			String fn="";
			String urls="";
			T1doBase t1doBase=t1doFeedback.getT1doBase();
		    for (int i = 0; i < uploadFiles.size(); i++) {
				t1doAttr.setAttrPath(UrlUtil.attrUrl+uploadFiles.get(i));
				urls+=UrlUtil.attrUrl+uploadFiles.get(i)+";";
				t1doAttr.setAttrName(uploadFiles1.get(i));
				fn+=uploadFiles1.get(i);
				t1doAttr.save();

				t1doFeedback.setATTRID(t1doAttr.getID()+"").setAttrPath(t1doAttr.getAttrPath()).setFBCONTENT(t1doAttr.getAttrName()).save();
				//如果绑定微信群则同步到微信群
				if(t1doBase.getIsBindingWechatGroup()&&t1doFeedback.getSource()!=1)
					WeChatService.SendFile(t1doBase.getWechatGroupId(),t1doFeedback);

				t1doAttr.remove("ID");
				new T1doLog().setShowId(t1doFeedback.getShowId()).setOpTime(new Date())
				.setOUser(user.getString("loginName")).setOUserName(user.getString("username"))
				.setLog(user.getString("username")+"上传"+uploadFiles1.get(i)).setContent(t1doFeedback.getID()+"").setLogType(3).save();
				if(i!=uploadFiles.size()-1)
				t1doFeedback.remove("ID");
		    }
			if(t1doFeedback.getFbType()==2){
				t1doFeedback.setFBCONTENT("回复"+t1doFeedback.getFbUserName()+" "+fn).update();
			}
			DoService.feedback1(t1doFeedback,user);
			//4个平台反馈
			if(StrUtil.isNotEmpty(urls))
				urls=urls.substring(0, urls.length()-1);
			final String url=urls;
			//城市大脑综合指挥平台
			if(j==0&&t1doBase.getSOURCE()==9) {
				new Thread(()->t1doFeedback.setResult(FourService.doEventDispose("02", t1doBase, t1doFeedback,url)).update()).start();
			}
			
			new Thread(()->new WebSocketController().sendMessage(t1doFeedback.getShowId())).start();
			
			renderJson(MsgUtil.successMsg(t1doFeedback));

		}
		

		/**
	     * @Author coco
	     * @Description 附件单独删除
	     * @Date 
	    */
		
		@Before(Tx.class)
		public void deleteAttr(){
	    	JSONObject json=JsonUtil.getJSONObject(getRequest()); 	
	    	int i=Db.delete("DELETE from t_1do_attr where attr_path=?",json.getString("ATTR_PATH"));
	    	if(i==1){
				
				renderJson(MsgUtil.successMsg("删除成功"));
	    	}else{
	    		renderJson(MsgUtil.errorMsg("删除失败"));
			
	    	}
			
		}
		/**
	     * @Author coco
	     * @Description 批量删除1do
	     * @Date 
	    */
		@Before(Tx.class)
		public void deleteAlldo(){
			JSONObject json=getAttr("request");
			JSONObject user=getAttr("user");
			JSONArray doList=json.getJSONArray("list");
			String loginName=user.getString("loginName");
			String username=user.getString("username");
		    for (int j = 0; j < doList.size(); j++) {
				String showID=doList.getString(j);
				final T1doBase t1doBase=T1doBase.getIdoBase(showID);
				if(t1doBase.getSOURCE()==11) {
					if(user.getIntValue("YSCG_POWER")>2) {
						int i=Db.update("update t_1do_base set O_IS_DELETED=2 ,DELETE_TIME=now(),O_STATUS=6 where SHOW_ID=?",showID);	  
						if(i==1){
							//保存日志
							T1doLog.saveLog(showID, loginName, username, username+"删除此1do", 12,"");	
							//修改工单时间
				        	   t1doBase.setSendTime(new Date().getTime()).update();
				        	   //修改1do传送门关联表状态	
				        	   Db.update("update t_1do_relation set TYPE=6 where  RELATION_SHOW_ID=?",showID);
				        	   //修改工单关联用户状态
				        	   Db.update("update t_1do_pstatus set O_STATUS=6,STATUS='已删除' where SHOW_ID=?",showID);
				        	//发送通知
				           	new Thread(new SendIdo(t1doBase,77,loginName,"",1,username+"删除1do:"+t1doBase.getODescribe(),T1doPstatus.getUser(showID))).start();
				           	//云上城管
				           	new Thread(new YscgTask(t1doBase,4)).start();
				           
							
							//System.out.println("删除成功");
						}
					}else {
						renderJson(MsgUtil.errorMsg("权限不足"));
					}
					
				}else {
					T1doPstatus t1=T1doPstatus.getUser(showID,loginName);

					if(user.getBoolean("isfw")||(t1!=null&&StrUtil.getflag(t1.getUserType(), 9))){
						if(t1doBase.getOStatus()==5){
							int i=Db.update("update t_1do_base set O_IS_DELETED=2 ,DELETE_TIME=now(),O_STATUS=6 where SHOW_ID=?",showID);	  
							if(i==1){
								T1doLog.saveLog(showID, loginName, username, username+"删除此1do", 12,"");	
					        	   t1doBase.setSendTime(new Date().getTime()).update();
								
					        	   Db.update("update t_1do_relation set TYPE=6 where  RELATION_SHOW_ID=?",showID);
					        	   Db.update("update t_1do_pstatus set O_STATUS=6,STATUS='已删除' where SHOW_ID=?",showID);
					        	
					           	new Thread(new SendIdo(t1doBase,77,loginName,"",1,username+"删除1do:"+t1doBase.getODescribe(),T1doPstatus.getUser(showID))).start();
					          //百姓爆料的话回调接口
					           	new Thread(new BxblTask(showID, 6,t1doBase.getSOURCE())).start();
					          //公共配置回调接口
								new Thread(new CommonTask(t1doBase.setOStatus(6),2)).start();
								System.out.println("删除成功");
							
							}else{
								System.out.println("删除失败");
							}
						}else{
							renderJson(MsgUtil.errorMsg("任务进行中不能删除"));
						
							return;
						}
						
					}else{
						System.out.println("权限不足");
						renderJson(MsgUtil.errorMsg("权限不足"));
						return;
					}
				}
		     }
		     renderJson(MsgUtil.successMsg("删除成功"));
	  }
		
		/**
	     * @Author coco
	     * @Description 删除或恢复或者重做
	     * @Date 
	    */
		@Before(Tx.class)
		public void deleteIdoOrRestoreIdoOrRedo(){
			JSONObject json=getAttr("request");
			JSONObject user=getAttr("user");
			T1doPstatus t1=T1doPstatus.getUser(json.getString("SHOW_ID"),user.getString("loginName"));
			final T1doBase t1doBase=T1doBase.getIdoBase(json.getString("SHOW_ID"));
			if(json.getString("result").equals("delete")){//删除
				if(t1doBase.getSOURCE()==11) {
					if(user.getIntValue("YSCG_POWER")>2) {
						int i= T1doBase.updateStatus(6,json.getString("SHOW_ID"));
						if(i==1){
							T1doLog.saveLog(json.getString("SHOW_ID"), user.getString("loginName"), user.getString("username"), user.getString("username")+"删除此1do", 12,"");
				        	   t1doBase.setSendTime(new Date().getTime()).update();
				        	 //修改人员工单状态
								T1doPstatus.setStatus(6, "已删除", t1doBase.getShowId());
								//修改1do传送门关联表状态
								T1doRelation.updateType(6, json.getString("SHOW_ID"));
								//发送通知
				           	new Thread(new SendIdo(t1doBase,77,user.getString("loginName"),"",1,user.getString("username")+"删除1do:"+t1doBase.getODescribe(),T1doPstatus.getUser(t1doBase.getShowId()))).start();
				           	new Thread(new YscgTask(t1doBase,4)).start();
				          
				           	renderJson(MsgUtil.successMsg("删除成功"));
							//System.out.println("删除成功");
						}
					}else{
						renderJson(MsgUtil.errorMsg("权限不足"));
					 }
				} 
				else if(user.getBoolean("isfw")||(t1!=null&&StrUtil.getflag(t1.getUserType(), 9))){
				if(t1doBase.getOStatus()==5){
					
					//修改工单状态
					int i= T1doBase.updateStatus(6,json.getString("SHOW_ID"));
					if(i==1){
						T1doLog.saveLog(json.getString("SHOW_ID"), user.getString("loginName"), user.getString("username"), user.getString("username")+"删除此1do", 12,"");
			        	   t1doBase.setSendTime(new Date().getTime()).update();
			        	 //修改人员工单状态
							T1doPstatus.setStatus(6, "已删除", t1doBase.getShowId());
							//修改1do传送门关联表状态
							T1doRelation.updateType(6, json.getString("SHOW_ID"));
							//发送通知
			           	new Thread(new SendIdo(t1doBase,77,user.getString("loginName"),"",1,user.getString("username")+"删除1do:"+t1doBase.getODescribe(),T1doPstatus.getUser(t1doBase.getShowId()))).start();
			            //百姓爆料的话回调接口
			           	new Thread(new BxblTask(json.getString("SHOW_ID"), 6,t1doBase.getSOURCE())).start();
			          //公共配置回调接口
						new Thread(new CommonTask(t1doBase.setOStatus(6),2)).start();
			    		renderJson(MsgUtil.successMsg("删除成功"));
					  }else{
					
						renderJson(MsgUtil.errorMsg("删除失败"));
					  }
				}else{
				
					renderJson(MsgUtil.errorMsg("任务进行中不能删除"));

				}
			  
			}else{
				
				renderJson(MsgUtil.errorMsg("权限不足"));
			}
		}else if(json.getString("result").equals("Restore")) {
			//恢复
			if(user.getBoolean("isfw")){
			     String STATUS="已完成";
			     int O_STATUS=5;
			     //如果已完成恢复成未完成
			     if(t1doBase.getOStatus()==5) {
			    	 //如果参与人有反馈
			    	 if(T1doFeedback.getExecutorFeedback(json.getString("SHOW_ID"))){
			    		 STATUS="已接单";
			    		 O_STATUS=4;
			    		 
			          //参与人没有反馈	 
			    	 }else {
			    		 STATUS="待接单";
			    		 O_STATUS=3;
			    	 }
			     }
			     //修改工单状态
			     int i= T1doBase.updateStatus(O_STATUS,json.getString("SHOW_ID"));
			     if(i==1){
			    	 //保存日志
					T1doLog.saveLog(json.getString("SHOW_ID"), user.getString("loginName"), user.getString("username"), user.getString("username")+"恢复此1do", 13,"");
					//修改工单发送通知时间
					t1doBase.setSendTime(new Date().getTime()).update();
					//修改人员工单状态
		            T1doPstatus.setStatus(O_STATUS,STATUS,json.getString("SHOW_ID"));
		          //修改1do传送门关联表状态
		            T1doRelation.updateType(O_STATUS,json.getString("SHOW_ID"));
		            //发送通知
		           	new Thread(new SendIdo(t1doBase,88,user.getString("loginName"),"",1,user.getString("username")+"恢复1do:"+t1doBase.getODescribe(),T1doPstatus.getUser(t1doBase.getShowId()))).start();
					if(O_STATUS<5)
						//设置办结的反馈过期
						T1doFeedback.updateIsoverdueByTypeIsFive(t1doBase.getShowId());
						
					 //百姓爆料的话回调接口
		           	new Thread(new BxblTask(json.getString("SHOW_ID"),O_STATUS,t1doBase.getSOURCE())).start();	
		            //公共配置回调接口
					new Thread(new CommonTask(t1doBase.setOStatus(O_STATUS),2)).start();
					renderJson(MsgUtil.successMsg("恢复成功"));

			  }else{
				renderJson(MsgUtil.errorMsg("恢复失败"));
				
			  }
			}else{
				renderJson(MsgUtil.errorMsg("权限不足"));
			}
		}else{//重做
			if(user.getBoolean("isfw")){
				 //保存日志
				T1doLog.saveLog(json.getString("SHOW_ID"), user.getString("loginName"), user.getString("username"), user.getString("username")+"设置重做此1do", 15,"");
				//修改人员工单状态
				T1doPstatus.setStatus(3, "待接单", json.getString("SHOW_ID"));
				//初始化工单
				T1doBase.initialization(json.getString("SHOW_ID"));
				//设置所以反馈数据过期
				T1doFeedback.updateIsoverdue(json.getString("SHOW_ID"));
				//设置日志数据过期
				T1doLog.updateIsoverdue(json.getString("SHOW_ID"));
				//修改1do传送门关联表状态
				T1doRelation.updateType(3, json.getString("SHOW_ID"));
				//发送通知
				new Thread(new SendIdo(t1doBase,11,user.getString("loginName"),"",1,t1doBase.getODescribe()+"重做",T1doPstatus.getUser(t1doBase.getShowId()))).start();
				 //百姓爆料的话回调接口
	           	new Thread(new BxblTask(json.getString("SHOW_ID"),3,t1doBase.getSOURCE())).start();
	            //公共配置回调接口
				new Thread(new CommonTask(t1doBase,2)).start();
			   renderJson(MsgUtil.successMsg("重做成功"));
			}else{
				
				renderJson(MsgUtil.errorMsg("权限不足"));
			}
		}
			
		}
		 
		/**
	     * @Author coco
	     * @Description 新建1do
	     * @Date 2018年6月26日上午10:17:2
	    */
		
		@Before(Tx.class)
		public void saveIdo() throws IOException {
			MultipartParser mp = new MultipartParser(this.getRequest(), 2147483647, false, false,"UTF-8");//52428800=50*1024*1024
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
						File t1 = new File(UrlUtil.jdlj);//设置本地上传文件对象（并重命名）
						File t = new File(t1,fileName);//设置本地上传文件对象（并重命名）
						FileOutputStream out = new FileOutputStream(t);
		        		Streams.copy(filePart.getInputStream(), out, true);
		        		uploadFiles.add(fileName);
		        		uploadFiles1.add(filePart.getFileName());
					}
				}
			JSONObject user=getSessionAttr("user");
			if(user==null){
				if(json.toString().contains("loginName")&&json.getString("loginName")!=null){					
					user=setSession(json.getString("loginName"));
				}else{
					renderJson(MsgUtil.errorMsg("用户未登入"));
					return;
				}
			}
			final T1doBase t1doBase =json.toJavaObject(T1doBase.class);
			log.error("*****************************新建参数");
			log.error(json);	
			if(StrKit.isBlank(t1doBase.getODescribe())||StrUtil.isEmpty(t1doBase.getOCustomer())){				
				renderJson(MsgUtil.errorMsg("内容不能为空或者发起人不能为空"));
				return;
			}else {
				//发起人有且只有一位
				//t1doBase.setOCustomer(t1doBase.getOCustomer().split(";")[0]).setOCustomerName(t1doBase.getOCustomerName().split(";")[0]);					
				//1Do 创建时必须有参与人，默认参与人是创建人
				if(StrUtil.isEmpty(t1doBase.getOExecutor()))
					t1doBase.setOExecutor(user.getString("loginName")).setOExecutorName(user.getString("username"));
				else if(!t1doBase.getOExecutor().contains(user.getString("loginName"))&&!t1doBase.getOCustomer().contains(user.getString("loginName"))) 
					t1doBase.setOExecutor(t1doBase.getOExecutor()+";"+user.getString("loginName")).setOExecutorName(t1doBase.getOExecutorName()+";"+user.getString("username"));
				
			}
			String showId=IDUtil.getUid();
			if(json.containsKey("ITEM_ID")&&StrUtil.isNotEmpty(json.getString("ITEM_ID"))) {
				new T1doProject1do().setItemId(json.getLongValue("ITEM_ID")).setShowId(showId).save();
			}
			if(json.getBooleanValue("IS_YSCG")) {
				t1doBase.setSOURCE(11);//云上城管设置为11
				 new Thread(new YscgTask(t1doBase, 1)).start();//是云上城管数据就推送
			}
			t1doBase.saveBase(user, showId, uploadFiles.size());
			
			t1doBase.savefw();//保存整理层为查看通知做准备。
			T1doPstatus.saveIdoPstatus2(t1doBase,1);
			StrUtil.getQTR(t1doBase);//设置处理人和抄送人
			T1doAttr t1doAttr=t1doBase.newIdoAttr();	
			T1doLog.saveLog(t1doBase.getShowId(), t1doBase.getCreateUser(), t1doBase.getCreateUserName(), t1doBase.getCreateUserName()+"创建了此1do", 1, "");
			//新建子1do时父工单保留痕迹
			if(json.toString().contains("PARENT_ID")&&StrUtil.isNotEmpty(t1doBase.getParentId())&&!t1doBase.getParentId().equals("0")&&!t1doBase.getParentId().equals("undefined")){
				T1doLog.saveLog(t1doBase.getParentId(), t1doBase.getCreateUser(), t1doBase.getCreateUserName(), t1doBase.getCreateUserName()+"新建下级1do", 16, "");
			}
			Long time=new Date().getTime();
			String urls="";
			for (int i = 0; i < uploadFiles.size(); i++) {		
			    t1doAttr.setAttrPath(UrlUtil.attrUrl+uploadFiles.get(i)).setAttrName(uploadFiles1.get(i)).save();
				new T1doFeedback().setShowId(t1doBase.getShowId()).setOUser(t1doAttr.getUploadUser())
				.setOUserName(t1doAttr.getUploadUserName()).setFbTime(t1doAttr.getUploadTime())
				.setFbType(3).setATTRID(t1doAttr.getID()+"").setAttrPath(t1doAttr.getAttrPath()).setTimeStamp(time+i).setFBCONTENT(t1doAttr.getAttrName()).save();
				urls+=t1doAttr.getAttrPath()+";";
				t1doAttr.remove("ID");
				T1doLog.saveLog(t1doBase.getShowId(),t1doAttr.getUploadUser(), t1doAttr.getUploadUserName(), t1doAttr.getUploadUserName()+"上传"+t1doAttr.getAttrName(), 3, t1doAttr.getAttrName()); 	
			}			
			//4个平台反馈
			if(StrUtil.isNotEmpty(urls))
				urls=urls.substring(0, urls.length()-1);
			final String url=urls;
			if(t1doBase.getSOURCE()!=null) {
				if(t1doBase.getSOURCE()==9) {
					new Thread(()->{	
						//推送事件
						String result=FourService.doCloseEvent(t1doBase,url);
						//成功获得推送事件id保存下来反馈用到
						t1doBase.setEventId(result);
						//推送到综合指挥中心
						boolean flag=FourService.NewOneDoCallback(t1doBase.getShowId(), t1doBase.getODescribe(),t1doBase.getCommandPlatformId(),t1doBase.getModule(),t1doBase.getSTREET() );
						t1doBase.setIsSuccess(flag).update();			
				   }).start();
				}else {
					//回调接口
					new Thread(new CommonTask(t1doBase,7)).start();
				}
				
				
			}
	        new Thread(new SendIdo(t1doBase,11,user.getString("loginName"),"",1,t1doBase.getODescribe(),T1doPstatus.getUser(t1doBase.getShowId()))).start();//发送1do通知给参与人
	     
	        new Thread(new AddLabel(t1doBase.getODescribe(), showId,2)).start();//批量添加标签
		    renderJson(MsgUtil.successMsg(showId));
		}

		/**
	     * @throws IOException 
		 * @throws UnsupportedEncodingException 
		 * @Author coco
	     * @Description 普通反馈
	     * @Date  2018年6月25日下午3:59:19
	    */
		@Before(Tx.class)
		public void feedback() throws UnsupportedEncodingException, IOException {
			
			JSONObject json=getAttr("request");
			JSONObject user=getAttr("user");
			renderJson(DoService.feedback(user,json,this));
		
		}

		/**
	     * @Author coco
	     * @Description 删除反馈
	     * @Date  2018年6月25日下午3:59:19
	    */
		@Before(Tx.class)
		public void deleteFeedback() {
		
			JSONObject json=getAttr("request");
			JSONObject user=getAttr("user");
			T1doFeedback t=T1doFeedback.dao.findById(json.getIntValue("ID"));
			//int i=Db.update("update t_1do_feedback set FB_TYPE=7 where ID=? and O_USER=?",json.getIntValue("ID"),json1.getString("loginName"));
		    if(t.getOUser().equals(user.getString("loginName"))){
		    	T1doLog.saveLog(t.getShowId(), user.getString("loginName"), user.getString("username"),user.getString("username")+"删除一条信息", 11,"");
			    t.setFbType(7).update();
			    t.setFbType(8).remove("ID").save();
		    	renderJson(MsgUtil.successMsg("删除成功"));
			}else{
				renderJson(MsgUtil.errorMsg("删除失败"));

			}
			
		}
		
		/**
	     * @Author coco
	     * @Description 1call转1do
	     * @Date 2018年6月21日
	    */
		
	    @Before(Tx.class)
		public void createIdo() {
			JSONObject json=JsonUtil.getJSONObject(getRequest());
			log.error(json.toString());
			final T1doBase t1doBase =json.getObject("BASE", T1doBase.class);
			
			JSONArray arr=json.getJSONArray("ATTR");
			String showId=IDUtil.getUid();
			if(StrKit.isBlank(t1doBase.getODescribe())||StrUtil.isEmpty(t1doBase.getOCustomer())||StrUtil.isEmpty(t1doBase.getCreateUser())){
				renderJson(MsgUtil.errorMsg("内容不能为空或者发起人不能为空或者创建人不能为空"));
				return;
			}else {
				//发起人有且只有一位
				//t1doBase.setOCustomer(t1doBase.getOCustomer().split(";")[0]).setOCustomerName(t1doBase.getOCustomerName().split(";")[0]);	
				
				//1Do 创建时必须有参与人，默认参与人是创建人
				if(StrUtil.isEmpty(t1doBase.getOExecutor()))
					t1doBase.setOExecutor(t1doBase.getCreateUser()).setOExecutorName(t1doBase.getCreateUserName());
				else if(!t1doBase.getOExecutor().contains(t1doBase.getCreateUser())&&!t1doBase.getOCustomer().contains(t1doBase.getCreateUser())) 
					t1doBase.setOExecutor(t1doBase.getOExecutor()+";"+t1doBase.getCreateUser()).setOExecutorName(t1doBase.getOExecutorName()+";"+t1doBase.getCreateUserName());
					
				
			}
			
			t1doBase.saveBase(showId, arr==null?0:arr.size());
			//t1doBase.savefw();//保存整理层为查看通知做准备。
			log.error(t1doBase.getShowId());
			String urls="";
			if(arr!=null){
				Long time=new Date().getTime();
			for (int i = 0; i < arr.size(); i++) {
				T1doAttr t1doAttr=arr.getObject(i, T1doAttr.class);
				t1doAttr.setShowId(t1doBase.getShowId());
				t1doAttr.save();
				new T1doFeedback().setShowId(t1doBase.getShowId()).setOUser(t1doAttr.getUploadUser())
				.setOUserName(t1doAttr.getUploadUserName()).setFbTime(t1doAttr.getUploadTime()).setTimeStamp(time+i)
				.setFbType(t1doBase.getSOURCE()==2?10:3).setATTRID(t1doAttr.getID()+"").setAttrPath(t1doAttr.getAttrPath()).setFBCONTENT(t1doAttr.getAttrName()==null?"":t1doAttr.getAttrName()).save();
				urls+=t1doAttr.getAttrPath()+";";
				T1doLog.saveLog(t1doBase.getShowId(),t1doAttr.getUploadUser(), t1doAttr.getUploadUserName(), t1doAttr.getUploadUserName()+"上传"+t1doAttr.getAttrName(), 3, t1doAttr.getAttrName()); 	
			}}			
			//4个平台反馈
			if(StrUtil.isNotEmpty(urls))
				urls=urls.substring(0, urls.length()-1);
			final String url=urls;
			if(t1doBase.getSOURCE()==9) {
				new Thread(()-> {
					boolean flag=FourService.NewOneDoCallback(t1doBase.getShowId(), t1doBase.getODescribe(),t1doBase.getCommandPlatformId(),t1doBase.getModule(),t1doBase.getSTREET()  );
						t1doBase.setIsSuccess(flag);
						//推送事件
						String result=FourService.doCloseEvent(t1doBase,url);
						//成功获得推送事件id保存下来反馈用到
						t1doBase.setEventId(result).update();
				}).start();
					//来源是
			}else if(t1doBase.getSOURCE()==11&&t1doBase.getAPARAMETER()==null) {
				new Thread(new YscgTask(t1doBase,3)).start();
			}else {
				//推送数据
				new Thread(new CommonTask(t1doBase,7)).start();
			}
				T1doPstatus.saveIdoPstatus2(t1doBase,1);
				if(t1doBase.getSOURCE()!=11) {
					StrUtil.getQTR(t1doBase);//设置处理人和抄送人
					new Thread(new AddLabel(t1doBase.getODescribe(), showId,2)).start();//批量添加标签
		           	new Thread(new SendIdo(t1doBase,11,t1doBase.getCreateUser(),"",1,t1doBase.getODescribe(),T1doPstatus.getUser(t1doBase.getShowId()))).start();
				}
				T1doLog.saveLog(t1doBase.getShowId(), t1doBase.getCreateUser(), t1doBase.getCreateUserName(), t1doBase.getCreateUserName()+"创建了此1do", 1, "");
			renderJson(MsgUtil.successMsg( t1doBase.getShowId(), t1doBase.getShowId()));
		
	   
	   
	    }
		/**
	     * @Author coco
	     * @Description 转1do的消息
	     * @Date 
	    */
	
	      public void msginfo() {
	    	JSONObject json=JsonUtil.getJSONObject(getRequest());	      
	    	JSONArray jsonArray = json.getJSONArray("msgId");
			@SuppressWarnings("rawtypes")
			List<LinkedHashMap> list=new ArrayList<LinkedHashMap>();
			for(int i=0;i<jsonArray.size();i++) {
				//T1doBase msg =T1doBase.dao.findFirst("select * from t_1do_base where MESSAGE_ID like ?", "%"+jsonArray.get(i)+"%");
				T1doBase msg =T1doBase.dao.findFirst("select * from t_1do_base where MESSAGE_ID like ?", "%"+jsonArray.get(i)+"%");
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
       /**
	     * @Author coco
	     * @Description 获得1do看板理来源数据用于前端搜索
	     * @Date 
	   */ 
		
		public void getSourceData() {
			
			renderJson(MsgUtil.successMsg(T1doType.getSourceData()));
			
		}
		
		/**
	     * @Author coco
	     * @Description 国际语音状态报告推送
	     * @Date 
	    */
		
		@Before(Tx.class)
		public void setCallStatus() {
			T1doCall tc=new T1doCall();
			tc.setCallStatusMessage(getPara("message")).save();
			renderJson(MsgUtil.successMsg("国际语音状态报告推送成功"));
			
		}
		
}