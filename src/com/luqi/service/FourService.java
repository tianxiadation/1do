package com.luqi.service;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.luqi.common.model.T1doBase;
import com.luqi.common.model.T1doFeedback;
import com.luqi.common.model.TRegUser;
import com.luqi.interceptor.MainInterceptor;
import com.luqi.util.HttpUtil;
import com.luqi.util.StrUtil;
import com.luqi.util.TimeUtil;
import com.luqi.util.UrlUtil;
import com.luqi.webservice.EventInWs;
import com.luqi.webservice.EventInWsService;
import com.luqi.webservice.EventInWsServiceTest;

/**
* @author coco
* @date 2019年9月2日 下午3:05:15
* 四个平台推送及反馈接口
*/
public class FourService {
	private static Logger log=Logger.getLogger(FourService.class);
	/**   
	　* 描述：   根据服务器获得不同的EventInWs
	　* 创建人：coco   
	　* 创建时间：2019年10月30日 下午6:59:42         
	*/
	public static EventInWs getEventInWs() {
		//测试环境调用测试
		if(UrlUtil.isHostAddress()) {
			EventInWsServiceTest factoryTest=new EventInWsServiceTest();
			//System.out.println(factoryTest.getWSDLDocumentLocation());
			EventInWs service = factoryTest.getEventInWsPort();
			return service;
		}else {
			//正式环境调用
			EventInWsService factory=new EventInWsService();
			//System.out.println(factory.getWSDLDocumentLocation());
			EventInWs service = factory.getEventInWsPort();
			
			return service;
		}
	}

	/**   
	　* 描述 ：事件上报
	　* 创建人：coco   
	　* 创建时间：2019年9月2日 下午3:07:01         
	*/
	public static String doCloseEvent(T1doBase t,String url) {
		JSONObject json1=new JSONObject();
		JSONObject json=new JSONObject();
		json.put("sysSource", "3D540000");
		String loginName=getString(t.getOCustomer());
		
		if(StrUtil.isNotEmpty(t.getSTREET())) {
			json.put("loginName",t.getSTREET());
		}else if(StrUtil.isEmpty(loginName)) {
			json.put("loginName","qzx01");
		}else {
			json.put("loginName",loginName);
		}
		
		/*if(StrUtil.isEmpty(loginName)) {
			if(StrUtil.isEmpty(t.getSTREET())) {
				json.put("loginName","qzx01");
			}else {
				json.put("loginName",t.getSTREET());
			}
		}else {	
				json.put("loginName",loginName);
			
		}*/

		String joinNames=getString(t.getOExecutor());
		json.put("joinNames", StrUtil.isEmpty(joinNames)?json.getString("loginName"):joinNames);
		json.put("loginNameStr", t.getOCustomerName());
		json.put("joinNamesStr", StrUtil.isEmpty(t.getOExecutorName())?json.getString("loginNameStr"):t.getOExecutorName());
		json.put("type", "3D020000");
		json.put("content", t.getODescribe());
		json.put("address", "下城区");
		json.put("eventFoundTime", TimeUtil.getyMdhmsSDF());
		json.put("urls", url);
		json.put("sysNumber", t.getShowId());
		json.put("coordinatex", "");
		json.put("coordinatey", "");
		json1.put("params", json);
		/*
		 * EventInWsService factory=new EventInWsService(); EventInWs service =
		 * factory.getEventInWsPort();
		 */
		EventInWs service =getEventInWs();
		System.out.println(json1.toJSONString());
		log.error(json1.toJSONString());
		String result=service.doCloseEvent(json1.toString());
		System.out.println(result);
		log.error(result);
		JSONObject json2=JSON.parseObject(result);		
		return json2.getString("eventId");
	}
	/**   
	 * 描述：事件办结反馈   
	 * 创建人：coco   
	 * 创建时间：2019年9月2日 下午3:07:01         
	 */
	public static String doEventDispose(String type,T1doBase tb,T1doFeedback t,String url) {
		JSONObject json1=new JSONObject();
		JSONObject json=new JSONObject();
		json.put("keyType", type);
		json.put("eventId", tb.getEventId());
		String loginName=getString(t.getOUser());
		if(StrUtil.isNotEmpty(tb.getSTREET())) {
			json.put("loginName",tb.getSTREET());
		}else if(StrUtil.isEmpty(loginName)) {
			json.put("loginName","qzx01");
		}else {
			json.put("loginName",loginName);
		}
		/*if(StrUtil.isEmpty(loginName)) {
			if(StrUtil.isEmpty(tb.getSTREET())) {
				json.put("loginName","qzx01");
			}else {
				json.put("loginName",tb.getSTREET());
			}
		}else {	
			json.put("loginName",loginName);
		}*/
		json.put("loginNameStr", t.getOUserName());
		json.put("content", t.getFBCONTENT());
		json.put("acceptTime",TimeUtil.getyMdhmsSDF());
		json.put("urls", url);
		json1.put("params", json);
		/*
		 * EventInWsService factory=new EventInWsService(); EventInWs service =
		 * factory.getEventInWsPort();
		 */
		EventInWs service =getEventInWs();
		String result=service.doEventDispose(json1.toString());
		return result;
	}
	/**   
	　* 描述：   
	　* 创建人：coco   
	　* 创建时间：2019年9月2日 下午3:34:40         
	*/
	public static String getString(String str) {
		if(StrUtil.isEmpty(str))
			return "";
		String[] str1=str.split(";");
		String result="";
		for (String str2 : str1) {
			TRegUser t=TRegUser.dao.findFirst("select * from t_reg_user where SHOW_ID=?",str2);
			Record r=Db.use("zh").findFirst("SELECT\r\n" + 
					"	a.ACC_USERNAME,\r\n" + 
					"	a.ACC_FULLNAME,\r\n" + 
					"	b.SUSR_ID,\r\n" + 
					"	b.SUSR_USERNAME\r\n" + 
					"FROM\r\n" + 
					"	TB_ACC a\r\n" + 
					"LEFT JOIN TB_SUSR b ON a.TSYS_ID = \"20181114094535334-4079-29CAEBAC6\"\r\n" + 
					"WHERE\r\n" + 
					"	a.SUSR_ID = b.SUSR_ID and b.SUSR_USERNAME=?",t.getULoginName());
			if(r!=null)
				result+=r.getStr("ACC_USERNAME")+";";
		}
		if(StrUtil.isNotEmpty(result)){
			return result.substring(0, result.length()-1);
		}
		return "";
	}
	/**   
	　* 描述：   获得4个平台账号
	　* 创建人：coco   
	　* 创建时间：2019年9月2日 下午3:34:40         
	*/
	public static String getUserAccount(String str) {
			Record r=Db.use("zh").findFirst("SELECT\r\n" + 
					"	a.ACC_USERNAME,\r\n" + 
					"	a.ACC_FULLNAME,\r\n" + 
					"	b.SUSR_ID,\r\n" + 
					"	b.SUSR_USERNAME\r\n" + 
					"FROM\r\n" + 
					"	TB_ACC a\r\n" + 
					"LEFT JOIN TB_SUSR b ON a.TSYS_ID = \"20181114094535334-4079-29CAEBAC6\"\r\n" + 
					"WHERE\r\n" + 
					"	a.SUSR_ID = b.SUSR_ID and a.ACC_USERNAME=?",str);
			
		return r!=null?r.getStr("SUSR_USERNAME"):"";
	}
	/**   
	　* 描述： 1do状态改变调用综合指挥平台接口
	　* 创建人：coco   
	　* 创建时间：2019年9月6日 上午11:09:46         
	*/
	public static String StatusCallback(String doId,int status,String module) {
		JSONObject json=new JSONObject();
		json.put("status",status);
		json.put("doId",doId);
		json.put("module",module);
		return HttpUtil.doPost1(UrlUtil.commandPlatform+"/CityBrain-Module/Center/StatusCallback", json.toString());
	}
	/**   
	 * 描述： 指挥平台新建1do调用综合指挥平台推送接口
	 * 创建人：coco   
	 * 创建时间：2019年9月6日 上午11:09:46         
	 */
	public static boolean NewOneDoCallback(String doId,String content,String showid,String module ,String street) {
		JSONObject json=new JSONObject();
		json.put("content",content);
		json.put("doId",doId);
		json.put("module",module);
		json.put("showId",showid);
		json.put("street",street);
		String result=HttpUtil.doPost1(UrlUtil.commandPlatform+"/CityBrain-Module/Center/NewOneDoCallback", json.toString());
		JSONObject json2=JSON.parseObject(result);
		return json2.getIntValue("Code")==2000;
	}
}
