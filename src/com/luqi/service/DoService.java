package com.luqi.service;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;
import com.luqi.common.model.Approval;
import com.luqi.common.model.Msg;
import com.luqi.common.model.Notice;
import com.luqi.common.model.T1doBase;
import com.luqi.common.model.T1doFeedback;
import com.luqi.common.model.T1doFw;
import com.luqi.common.model.T1doLabelFeedback;
import com.luqi.common.model.T1doLog;
import com.luqi.common.model.T1doPstatus;
import com.luqi.common.model.T1doRelation;
import com.luqi.common.model.T1doType;
import com.luqi.common.model.T1doWechat;
import com.luqi.common.model.TRegUser;
import com.luqi.controller.DoController;
import com.luqi.controller.UrgeController;
import com.luqi.controller.WebSocketController;
import com.luqi.timer.BxblTask;
import com.luqi.timer.CommonTask;
import com.luqi.timer.SendIdo;
import com.luqi.timer.SupervisionAndEvaluation;
import com.luqi.timer.YscgTask;
import com.luqi.util.DbUtil;
import com.luqi.util.HttpUtil;
import com.luqi.util.JsonUtil;
import com.luqi.util.MsgUtil;
import com.luqi.util.ShortMessageUtil;
import com.luqi.util.StrUtil;
import com.luqi.util.TimeUtil;
import com.luqi.util.UrlUtil;


public class DoService {
	private static Logger log=Logger.getLogger(DoService.class);
	
	/*
	 2019年3月5日 coco 注解：1do代办推送接口
	*/
	public static String dbts(String showid) {
		List<TRegUser> tp=TRegUser.dao.find("select b.U_LOGIN_NAME,b.U_MAIL from t_1do_pstatus a,t_reg_user b where "
				+ " a.o_user=b.show_id and a.show_id=? and a.user_type!=2 and isdelete=1 and isRead=2 ",showid);
		tp.forEach(t->{
			List<Record> list=Db.find("select * from (SELECT b.SHOW_ID showId,a.otherid isMain,b.O_DESCRIBE title,cast(b.SEND_TIME as char) updateTime FROM t_1do_pstatus a,t_1do_base b,t_reg_user c"
					+" where a.O_USER=c.SHOW_ID and a.SHOW_ID=b.SHOW_ID and a.isDelete=1 and (a.isRead=2 or a.otherid=1) and a.USER_TYPE!=2 and b.O_STATUS<5"
					+" and  (c.U_MAIL=? or U_LOGIN_NAME=?) ORDER BY otherid desc)d  GROUP BY showId",t.getUMail(),t.getULoginName());							
			
				if(list.size()>0){
				String str1=HttpUtil.doPost1(UrlUtil.zhxxpt+"3th/1do/dolist?appName=1do&appKey=1do", getJson(t.getULoginName()==null?t.getUMail():t.getULoginName(), list));
				log.error(str1);
				}
		});
		return "";
	}
	
	/*
	 2019年3月5日 coco 注解：1do催办推送接口
	 */
	public static String cbts(String showid) {
		
		List<TRegUser> tp=TRegUser.dao.find("select b.U_LOGIN_NAME,b.U_MAIL from t_1do_pstatus a,t_reg_user b where "
				+ " a.o_user=b.show_id and a.show_id=? and a.user_type=3 and isdelete=1 and urge_isLook=0 and a.otherid=1 ",showid);
		tp.forEach(t->{
			List<Record> list=Db.find("select * from (SELECT b.SHOW_ID showId,b.O_DESCRIBE title,b.LIGHTNING lightning,b.O_CREATE_TIME createTime,b.O_FINISH_TIME finishTime,b.URGENAME ouserName FROM t_1do_pstatus a,t_1do_base b,t_reg_user c"
					+" where a.O_USER=c.SHOW_ID and a.SHOW_ID=b.SHOW_ID and a.isDelete=1 and a.urge_isLook=0  and a.USER_TYPE!=2 and b.O_STATUS<5 AND b.LIGHTNING >0"
					+" and  (c.U_MAIL=? or U_LOGIN_NAME=?) ORDER BY otherid desc)d  GROUP BY showId",t.getUMail(),t.getULoginName());							
			
				if(list.size()>0){
					String str1=HttpUtil.doPost1(UrlUtil.zhxxpt+"3th/1do/urgedlist?appName=1do&appKey=1do", getJson(t.getULoginName()==null?t.getUMail():t.getULoginName(), list));
				log.error(str1);
				}
		});
		return "";
	}
	public static String cbts1(String username) {
		
		
			List<Record> list=Db.find("select * from (SELECT b.SHOW_ID showId,b.O_DESCRIBE title,b.LIGHTNING lightning,b.O_CREATE_TIME createTime,b.O_FINISH_TIME finishTime,b.URGENAME ouserName FROM t_1do_pstatus a,t_1do_base b,t_reg_user c"
					+" where a.O_USER=c.SHOW_ID and a.SHOW_ID=b.SHOW_ID and a.isDelete=1 and a.urge_isLook=0  and a.USER_TYPE!=2 and b.O_STATUS<5 AND b.LIGHTNING >0"
					+" and  (c.U_MAIL=? or U_LOGIN_NAME=?) ORDER BY otherid desc)d  GROUP BY showId",username,username);							
			
			if(list.size()>0){
				String str1=HttpUtil.doPost1(UrlUtil.zhxxpt+"3th/1do/urgedlist?appName=1do&appKey=1do", getJson(username, list));
				log.error(str1);
			}
		
		return "";
	}
	/*
	 2019年3月5日 coco 注解：
	*/
	public static String getJson(String userName,List<Record> list) {	
		JSONObject json=new JSONObject();
		json.put("userName", userName);
		json.put("pageNo", 1);
		json.put("listCount", list.size());
		JSONArray array= JSONArray.parseArray(JsonKit.toJson(list));
		json.put("data",array);	
		return json.toString();
	}
	public static List<Record> exportExcel(int type){
		String shi="否";
		//String temp=type==6?" and a.O_IS_DELETED=2 ":"";
		if(type==5||type==6){
			shi="是";
			//type=5;
		}
		
		/*String sql = "SELECT a.O_DESCRIBE,a.O_CREATE_TIME,a.O_FINISH_TIME,"
				+ "a.Real_FINISH_TIME,a.O_CUSTOMER_NAME,a.O_EXECUTOR_NAME,'"+shi+"' FINISH FROM t_1do_base a,"
				+ "t_1do_status b where a.SHOW_ID=b.SHOW_ID and b.O_STATUS=?"+temp; */
		
		String sql = "SELECT O_DESCRIBE,O_CREATE_TIME,O_FINISH_TIME,"
				+ "Real_FINISH_TIME,O_CUSTOMER_NAME,O_EXECUTOR_NAME,'"+shi+"' FINISH FROM t_1do_base where O_STATUS=?"; 
		if(type==0) {
			sql="SELECT O_DESCRIBE,O_CREATE_TIME,O_FINISH_TIME," + 
					"Real_FINISH_TIME,O_CUSTOMER_NAME,O_EXECUTOR_NAME,'"+shi+"' FINISH FROM t_1do_base ";
			return Db.find(sql);
		}else if(type==7) {
			sql="SELECT O_DESCRIBE,O_CREATE_TIME,O_FINISH_TIME," + 
					"Real_FINISH_TIME,O_CUSTOMER_NAME,O_EXECUTOR_NAME,'"+shi+"' FINISH FROM t_1do_base where O_STATUS<6 and LIGHTNING>0";
			return Db.find(sql);
		}
		return Db.find(sql,type);
	}
	/*
	 2018年7月9日上午10:52:04 coco   群发送通知
	*/
	public static String sendIdo(T1doBase t1doBase,int i,String O_USER,String content, String at,List<T1doPstatus> t1){
		//通知原则：离线状态（普通反馈等发通知静音，催办、状态改变、@、新建不静音），在线状态（状态改变、@、普通反馈、新建等发通知禁音，催办不静音）
		
		//String[] str=StrUtil.getSql(i,O_USER,t1doBase.getOStatus());
		//String str="select * from t_1do_pstatus where SHOW_ID=? and USER_TYPE!=2 and isDelete=1 GROUP BY O_USER";
		//List<T1doPstatus> t1=T1doPstatus.dao.find(str,t1doBase.getShowId());
		String result = null;
		JSONObject object = new JSONObject();
		object.put("SHOW_ID", t1doBase.getShowId());
		object.put("O_DESCRIBE", t1doBase.getODescribe());
		object.put("SOURCE", t1doBase.getSOURCE());
		object.put("O_TYPE_ID", t1doBase.getOTypeId());
		object.put("O_CUSTOMER_NAME", t1doBase.getOCustomerName());
		object.put("O_CUSTOMER", t1doBase.getOCustomer());
		object.put("O_EXECUTOR", t1doBase.getOExecutor());
		object.put("O_EXECUTOR_NAME", t1doBase.getOExecutorName());
		object.put("AT", t1doBase.getAT());
		object.put("SEND_TIME", t1doBase.getSendTime());
		object.put("LOOKNUM", t1doBase.getLOOKNUM());//查看数量
		object.put("FBNUM", t1doBase.getFBNUM());//反馈数量	
		object.put("LIGHTNING", t1doBase.getLIGHTNING());
		object.put("O_CREATE_TIME",t1doBase.getOCreateTime());
		object.put("O_FINISH_TIME",t1doBase.getOFinishTime()==null?"":t1doBase.getOFinishTime());
		object.put("Real_FINISH_TIME",t1doBase.getRealFinishTime()==null?"":t1doBase.getRealFinishTime());
		object.put("DELETE_TIME",t1doBase.getDeleteTime()==null?"":t1doBase.getDeleteTime());
		object.put("content",content);
		
		for(T1doPstatus tt:t1){
			String loginName=tt.getOUser();
			String trueName=tt.getOUserName();
			Record r2=Db.findFirst("select count(DISTINCT SHOW_ID) num from t_1do_pstatus where USER_TYPE!=2 and isDelete=1 and isRead=2 and O_USER=?",loginName);
			object.put("UNREAD", r2.getInt("num"));//未读数
			object.put("ISLOOK", tt.getIsRead());//1是2否
			object.put("O_STATUS", tt.getSTATUS());
			object.put("USER_TYPE", tt.getUserType());
			//离线
			if(tt.getIsRead()==2) {
				if(i%11==0) {
					//i=11表示新建1do,重做 33第一个参与人反馈修改工单状态 44催办此1do 55确认办结 77删除1do 88恢复1do 
					object.put("NOTICE", true);
				}else {
					if(StrUtil.isNotEmpty(at)&&at.contains(tt.getOUser())) {
						object.put("NOTICE", true);
					}else {
						object.put("NOTICE", false);
					}
				}
			//在线
			}else {
				if(i==44) {
					//i=44催办全部都通知
					object.put("NOTICE", true);
				}else {
					object.put("NOTICE", false);
				}
			}
			
			String str1=HttpUtil.getParameter2(t1doBase.getShowId(), loginName, trueName, object,t1doBase.getSOURCE()==8?t1doBase.getOTitle():t1doBase.getODescribe());	
			//new Notice().setTest(str1).save();
		    result   =   HttpUtil.doPost1(UrlUtil.url, str1);
		    log.error(result);
		  
		}
		List<T1doFw> list=T1doFw.dao.find("SELECT a.*,b.isRead FROM t_1do_fw a LEFT JOIN t_1do_fwpstatus b on a.SHOW_ID=b.SHOW_ID and a.icallshowid=b.O_USER where a.type=1");
		for (T1doFw t1doFw : list) {
			String loginName=t1doFw.getIcallshowid();
			String trueName=t1doFw.getOUserName();
			object.put("USER_TYPE", 6);
			object.put("ISLOOK", t1doFw.getIsRead());//1是2否
			if(i==7){
				object.put("ISLOOK", 1);//1是2否
			}
			String att="select count(*)  from t_1do_fwpstatus where isRead=2 and O_USER='"+loginName+"' GROUP BY O_USER";
			Record r2=Db.findFirst(att);
			object.put("UNREAD", r2.getInt("num"));//未读数
			String str2=HttpUtil.getParameter2(t1doBase.getShowId(), loginName, trueName, object,t1doBase.getODescribe());				
		    result   =   HttpUtil.doPost1(UrlUtil.url, str2);
		    log.error(result);
		}
		return result;
		

	}
	/*
	 2019年7月15日上午10:52:04 coco   添加1do通知
	*/
	public static String sendIdoBatchPut(T1doBase t1doBase,JSONObject user,String content){
		List<T1doPstatus> t1=T1doPstatus.dao.find("select * from t_1do_pstatus where SHOW_ID=? and USER_TYPE!=2 and isDelete=1 ",t1doBase.getShowId());
		String result = null;
		JSONObject object = new JSONObject();
		object.put("SHOW_ID", t1doBase.getShowId());
		object.put("O_DESCRIBE", t1doBase.getODescribe());
		object.put("SOURCE", t1doBase.getSOURCE());
		object.put("O_TYPE_ID", t1doBase.getOTypeId());
		object.put("O_DESCRIBE", t1doBase.getODescribe());
		object.put("O_CUSTOMER_NAME", t1doBase.getOCustomerName());
		object.put("O_CUSTOMER", t1doBase.getOCustomer());
		object.put("O_EXECUTOR", t1doBase.getOExecutor());
		object.put("O_EXECUTOR_NAME", t1doBase.getOExecutorName());
		object.put("AT", t1doBase.getAT());
		object.put("SEND_TIME", t1doBase.getSendTime());
		object.put("LOOKNUM", t1doBase.getLOOKNUM());//查看数量
		object.put("FBNUM", t1doBase.getFBNUM());//反馈数量	
		object.put("LIGHTNING", t1doBase.getLIGHTNING());
		object.put("O_CREATE_TIME",t1doBase.getOCreateTime());
		object.put("O_FINISH_TIME",t1doBase.getOFinishTime()==null?"":t1doBase.getOFinishTime());
		object.put("Real_FINISH_TIME",t1doBase.getRealFinishTime()==null?"":t1doBase.getRealFinishTime());
		object.put("DELETE_TIME",t1doBase.getDeleteTime()==null?"":t1doBase.getDeleteTime());
		object.put("content",content);
		for(T1doPstatus tt:t1){
			String loginName=tt.getOUser();
			String trueName=tt.getOUserName();
		//	Record r2=Db.findFirst("select count(DISTINCT SHOW_ID) num from t_1do_pstatus where USER_TYPE!=2 and isDelete=1 and isRead=2 and O_USER=?",loginName);
			Integer ret = Db.queryInt("select count(DISTINCT SHOW_ID) num from t_1do_pstatus where USER_TYPE!=2 and isDelete=1 and isRead=2 and O_USER=?",loginName);
			object.put("UNREAD", ret);//未读数
			object.put("ISLOOK", tt.getIsRead());//1是2否
			object.put("O_STATUS", tt.getSTATUS());
			object.put("USER_TYPE", tt.getUserType());
			String str1=HttpUtil.getBatchPutParameter(t1doBase, loginName, trueName, object,user);	
		    result   =   HttpUtil.doPut(UrlUtil.batchPut, str1);
		    //System.out.println("添加1do通知:"+result);
		}
		return result;
		/*
		 * //批量添加1do通知数据不发通知 new Thread( new Runnable() {
		 * 
		 * @Override public void run() {
		 * DoService.sendIdoBatchPut(t1doBase,user,user.getString("username")+"恢复1do:"+
		 * t1doBase.getODescribe()); } }).start();
		 */
		 //批量添加1do通知数据不发通知
		/*
		 * new Thread( new Runnable() {
		 * 
		 * @Override public void run() {
		 * DoService.sendIdoBatchPut(t1doBase,user,t1doBase.getODescribe()+"重做"); }
		 * }).start();
		 */
		 //批量添加1do通知数据不发通知
		/*
		 * new Thread( new Runnable() {
		 * 
		 * @Override public void run() {
		 * DoService.sendIdoBatchPut(t1doBase,user,user.getString("username")+"删除1do:"+
		 * t1doBase.getODescribe()); } }).start();
		 */
		/*
		 * new Thread( new Runnable() {
		 * 
		 * @Override public void run() {
		 * DoService.sendIdoBatchPut(t1doBase,user,"评价："+t1doFeedback.getFBCONTENT()); }
		 * }).start();
		 */
		  //批量添加1do通知数据不发通知(PC,安卓，IOS的1do会话列表中的信息显示的最新1do)
				/*
				 * final JSONObject user=setSession(t1doBase.getCreateUser()); new Thread( new
				 * Runnable() {
				 * 
				 * @Override public void run() {
				 * DoService.sendIdoBatchPut(t1doBase,user,t1doBase.getODescribe()); }
				 * }).start();
				 */
		/*
		 * if(t2!=null&&r==null){ //修改该1do发送通知时间 t1doBase.setSendTime(new
		 * Date().getTime()).update(); //群发 new Thread(new
		 * SendIdo(t1doBase,2,loginName,"",1)).start(); //批量添加1do通知数据不发通知 new Thread(
		 * new Runnable() {
		 * 
		 * @Override public void run() { DoService.sendIdoBatchPut(t1doBase,user); }
		 * }).start(); }
		 */		
		 //批量添加1do通知数据不发通知
		/*
		 * new Thread( new Runnable() {
		 * 
		 * @Override public void run() {
		 * DoService.sendIdoBatchPut(t1doBase,user,username+"删除1do:"+t1doBase.
		 * getODescribe()); } }).start();
		 */
		   //批量添加1do通知数据不发通知
        /*    final JSONObject user1= user;
		
		 * new Thread( new Runnable() {
		 * 
		 * @Override public void run() {
		 * DoService.sendIdoBatchPut(t1doBase,user1,t1doBase.getODescribe()); }
		 * }).start();
		 */
		//批量添加1do通知数据不发通知
		/*
		 * new Thread( new Runnable() {
		 * 
		 * @Override public void run() {
		 * DoService.sendIdoBatchPut(t1doBase,user,t1doFeedback.getFBCONTENT()); }
		 * }).start();
		 */
	}
	//单独发送通知
	public static String sendOneIdo(T1doBase t1doBase,int i,String loginName,String trueName,String content){ //i 1加入2查看3反馈	
		T1doPstatus user=T1doPstatus.getUser(t1doBase.getShowId(),loginName);
		String result = null;	
			JSONObject object = new JSONObject();
			object.put("SHOW_ID", t1doBase.getShowId());
			object.put("O_DESCRIBE", t1doBase.getODescribe());
			object.put("SOURCE", t1doBase.getSOURCE());
			object.put("O_TYPE_ID", t1doBase.getOTypeId());
			object.put("O_STATUS", user.getSTATUS());
			object.put("LOOKNUM", t1doBase.getLOOKNUM());//查看数量
			object.put("FBNUM", t1doBase.getFBNUM());//反馈数量	
			object.put("LIGHTNING", t1doBase.getLIGHTNING());
			object.put("content",content);
			if(i==1){
				object.put("ISLOOK", 2);//1是2否
			}else{
				object.put("ISLOOK", 1);//1是2否

			}
			object.put("NOTICE", true);
			Record r2=Db.findFirst("select count(DISTINCT SHOW_ID) num from t_1do_pstatus where USER_TYPE!=2 and isDelete=1 and isRead=2 and O_USER=?",loginName);
			object.put("UNREAD", r2.getInt("num"));//未读数
			object.put("SEND_TIME", t1doBase.getSendTime());
			object.put("O_CUSTOMER_NAME", t1doBase.getOCustomerName());
			object.put("O_CUSTOMER", t1doBase.getOCustomer());
			object.put("O_EXECUTOR", t1doBase.getOExecutor());
			object.put("O_EXECUTOR_NAME", t1doBase.getOExecutorName());
			object.put("AT", t1doBase.getAT());
			object.put("USER_TYPE", user==null?6:user.getUserType());	
			object.put("O_CREATE_TIME",t1doBase.getOCreateTime());
			object.put("O_FINISH_TIME",t1doBase.getOFinishTime());
			object.put("Real_FINISH_TIME",t1doBase.getRealFinishTime()==null?"":t1doBase.getRealFinishTime());
			object.put("DELETE_TIME",t1doBase.getDeleteTime()==null?"":t1doBase.getDeleteTime());
			String str1=HttpUtil.getParameter2(t1doBase.getShowId(), loginName, trueName, object,t1doBase.getSOURCE()==8?t1doBase.getOTitle():t1doBase.getODescribe());	
			//new Notice().setTest(str1).save();
			result   =   HttpUtil.doPost1(UrlUtil.url, str1);
			 log.error(result);
		    return result; 
		 
		
	}
	//整理层单独发送通知
	public static String fwsendOneIdo(T1doBase t1doBase,int i,String loginName,String trueName,String content){ //i 1加入2查看3反馈	
		String result = null;
		JSONObject object = new JSONObject();	
		object.put("SHOW_ID", t1doBase.getShowId());
		object.put("O_DESCRIBE", t1doBase.getODescribe());	
		object.put("content",content);
		switch (t1doBase.getOStatus()) {
		case 3:
			object.put("O_STATUS", "待接单");
			break;
		case 4:
			object.put("O_STATUS", "已接单");
			break;
		case 5:
			object.put("O_STATUS", "已完成");
			break;
		case 6:
	    	object.put("O_STATUS", "已删除");
			break;
		default:
	    	object.put("O_STATUS", "已删除");
			break;
		}
		object.put("LOOKNUM", t1doBase.getLOOKNUM());//查看数量
		object.put("FBNUM", t1doBase.getFBNUM());//反馈数量	
		object.put("LIGHTNING", t1doBase.getLIGHTNING());
		if(i==1){
			object.put("ISLOOK", 2);//1是2否
		}else{
			object.put("ISLOOK", 1);//1是2否
			
		}
		object.put("NOTICE", false);
		String att="select count(*)  from t_1do_fwpstatus where isRead=2 and O_USER='"+loginName+"' GROUP BY O_USER";
		Record r2=Db.findFirst(att);
		object.put("UNREAD", r2.getInt("num"));//未读数
		object.put("SEND_TIME", t1doBase.getSendTime());
		object.put("O_CUSTOMER_NAME", t1doBase.getOCustomerName());
		object.put("O_CUSTOMER", t1doBase.getOCustomer());
		object.put("O_EXECUTOR", t1doBase.getOExecutor());
		object.put("O_EXECUTOR_NAME", t1doBase.getOExecutorName());
		object.put("AT", t1doBase.getAT());
		object.put("SOURCE", t1doBase.getSOURCE());
		object.put("O_TYPE_ID", t1doBase.getOTypeId());
		object.put("USER_TYPE", 6);		
		object.put("O_CREATE_TIME",t1doBase.getOCreateTime());
		object.put("O_FINISH_TIME",t1doBase.getOFinishTime());
		object.put("Real_FINISH_TIME",t1doBase.getRealFinishTime()==null?"":t1doBase.getRealFinishTime());
		object.put("DELETE_TIME",t1doBase.getDeleteTime()==null?"":t1doBase.getDeleteTime());
		String str1=HttpUtil.getParameter2(t1doBase.getShowId(), loginName, trueName, object,t1doBase.getSOURCE()==8?t1doBase.getOTitle():t1doBase.getODescribe());				
		result   =   HttpUtil.doPost1(UrlUtil.url, str1);
		 log.error(result);
		return result;
		
		
	}
	/*
	 三实库审批
	*/
	public static void ssk(T1doBase t1doBase,String content) {
		if(t1doBase.getSOURCE()==3&&t1doBase.getCPARAMETER()==1&&!t1doBase.getISAPPROVAL()){
			
			Approval arr=Approval.dao.findFirst("select * from approval where source=3 and name=?",content);
			
			if(arr!=null){
				JSONObject json=new JSONObject();
				json.put("id", t1doBase.getAPARAMETER());
				json.put("type", arr.getType());
				JSONObject result1 = HttpUtil.doPost3(UrlUtil.ssk,json.toString());
				 log.error(result1);
				if(result1.getInteger("code")==200){
					t1doBase.setCPARAMETER(arr.getType()).setISAPPROVAL(true).update();
				}
				
			}else{
				System.out.println("不审批");
			}
			
		}
	}
	
	//审批主动办
	public static void approval(T1doBase t1doBase,String content){
		if(t1doBase.getSOURCE()==2&&t1doBase.getDPARAMETER()==2&&t1doBase.getCPARAMETER()==2&&!t1doBase.getISAPPROVAL()){
			
			Approval arr=Approval.dao.findFirst("select * from approval where source=2 and name=?",content);
			if(arr!=null){
				if(arr.getType()==1){
					String result1 = null;
					
					try {
						//System.out.println("http://172.16.8.18:8080/1call/getSchemeStart?id="+t1doBase.getAPARAMETER()+"&schemeStart=3&examineTime="+TimeUtil.getDateTime1());
						result1 = HttpUtil.doPost11("http://172.16.8.18:8080/1call/getSchemeStart?id="+t1doBase.getAPARAMETER()+"&schemeStart=3&examineTime="+TimeUtil.getDateTime1());
						
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("------------"+result1);
					
					
					JSONObject json=JSON.parseObject(result1);
					System.out.println("json-----"+json.toString());
					if(json.getInteger("code")==200){
						 log.error(result1);
						t1doBase.setCPARAMETER(3).setISAPPROVAL(true).update();
						result1 = HttpUtil.doPost11("http://172.16.8.18:8080/1call/combination?id="+t1doBase.getAPARAMETER());
						 log.error(result1);
						System.out.println("------------"+result1);
					}
				}else{
					t1doBase.setISAPPROVAL(true).update();
				}
			}else{
				System.out.println("不审批");
			}
			
		}
	}
	
	/**
     * @Author coco
     * @Description 附件反馈通知
     * @Date 
    */
	public static void feedback1(final T1doFeedback t1doFeedback,final JSONObject user) {
		
		//查询是否是参与人
		final T1doBase t1doBase=t1doFeedback.getT1doBase();
		T1doPstatus t1doPstatus=T1doPstatus.getCustomerOrExecutor(t1doFeedback.getShowId(),user.getString("loginName"),3);
		
			if(t1doPstatus!=null&&t1doBase.getOStatus()==3){
				t1doBase.setOStatus(4).setSendTime(new Date().getTime()).setFBNUM(t1doBase.getFBNUM()+1).update();
				DbUtil.update(t1doFeedback.getShowId(),user.getString("loginName"), 15, 0, "", "");
				
	           	new Thread(new SendIdo(t1doBase,33,user.getString("loginName"),"",1,t1doFeedback.getFBCONTENT(),T1doPstatus.getUser(t1doFeedback.getShowId()))).start();
			}else{
				t1doBase.setSendTime(new Date().getTime()).setFBNUM(t1doBase.getFBNUM()+1).update();
				DbUtil.update(t1doFeedback.getShowId(),user.getString("loginName"), 16, 0, "", "");
				final int temp=t1doBase.getOStatus()==3?1:t1doBase.getOStatus();									
	           	new Thread(new SendIdo(t1doBase,temp,user.getString("loginName"),"",1,t1doFeedback.getFBCONTENT(),T1doPstatus.getUser(t1doFeedback.getShowId()))).start();

			}
			 //批量添加1do通知数据不发通知
	/*
	 * new Thread( new Runnable() {
	 * 
	 * @Override public void run() {
	 * DoService.sendIdoBatchPut(t1doBase,user,t1doFeedback.getFBCONTENT()); }
	 * }).start();
	 */	
		
	}

	public static void getBase() {
		// TODO Auto-generated method stub
		
	}
	//看板搜索数据
	public static JSONObject getBase(JSONObject user, JSONObject json1) {
		String loginName=user==null?json1.getString("loginName"):user.getString("loginName");
		
		int type=json1.getIntValue("type");
		String sql=" o_status!=6 ";			
		if(type==7){
			sql=" (o_status=3 or o_status=4) and LIGHTNING>0";
		}else if(type==8){
			sql=" o_status<5";
		}else if(type!=0){
			sql=" o_status="+type;
		}
		int orderType=1;
		if(json1.getString("method").equals("medo")){
			sql+=" and O_EXECUTOR like CONCAT('%','"+loginName+"','%')" ;				
		}else if(json1.getString("method").equals("hedo")){
			orderType=2;
			sql+=" and O_CUSTOMER like CONCAT('%','"+loginName+"','%')";				
		}else if(json1.getString("method").equals("all")){
			orderType=2;
		    sql+=" and (O_EXECUTOR like CONCAT('%','"+loginName+"','%') or O_CUSTOMER like CONCAT('%','"+loginName+"','%') )";				
	    }else{
	    	orderType=3;
	    }
		if(StrUtil.isNotEmpty(json1.getString("base"))){
			//来源是10督查指挥的话，base查询关键词和姓名
			if(StrUtil.isNotEmpty(json1.getString("SOURCE"))&&json1.getIntValue("SOURCE")==10) {
				sql+=" and ( O_DESCRIBE like CONCAT('%','"+json1.getString("base")+"','%')  "
						+ "or O_CUSTOMER_NAME like CONCAT('%','"+json1.getString("base")+"','%') or"
								+ " O_EXECUTOR_NAME like CONCAT('%','"+json1.getString("base")+"','%') )";
			}else {
				sql+=" and O_DESCRIBE like CONCAT('%','"+json1.getString("base")+"','%')";
			}
			
		}
	    if(StrUtil.isNotEmpty(json1.getString("O_CUSTOMER_NAME"))){
			sql+=" and O_CUSTOMER_NAME like CONCAT('%','"+json1.getString("O_CUSTOMER_NAME")+"','%')";
		}
		if(StrUtil.isNotEmpty(json1.getString("O_EXECUTOR_NAME"))){
			sql+=" and O_EXECUTOR_NAME like CONCAT('%','"+json1.getString("O_EXECUTOR_NAME")+"','%')";
		}
		if(StrUtil.isNotEmpty(json1.getString("O_CREATE_TIME_START"))&&StrUtil.isNotEmpty(json1.getString("O_CREATE_TIME_END"))){
			sql+=" and DATE_FORMAT(O_CREATE_TIME,'%Y-%m-%d')  BETWEEN '"+json1.getString("O_CREATE_TIME_START")+"' and '"+json1.getString("O_CREATE_TIME_END")+"'";
		}
		if(StrUtil.isNotEmpty(json1.getString("O_FINISH_TIME_START"))&&StrUtil.isNotEmpty(json1.getString("O_FINISH_TIME_END"))){
			sql+=" and DATE_FORMAT(O_FINISH_TIME,'%Y-%m-%d')  BETWEEN '"+json1.getString("O_FINISH_TIME_START")+"' and '"+json1.getString("O_FINISH_TIME_END")+"'  ";
		}
	
			if(StrUtil.isNotEmpty(json1.getString("SOURCE"))&&json1.getIntValue("SOURCE")!=0) {
				sql+=" and SOURCE="+json1.getIntValue("SOURCE");
					if(StrUtil.isNotEmpty(json1.getString("O_TYPE_ID"))&&!json1.getString("O_TYPE_ID").equals("0")) {
						sql+=" and O_TYPE_ID="+json1.getString("O_TYPE_ID");
					}
					
			/*if(json1.getIntValue("SOURCE")==11) {
				
			}*/
						
			}
		String from ="select a.*,b.type TYPE,(case a.LOOK_USER like ? when true  then 1 else 2 end )ISLOOK"
				+ " from (select SHOW_ID,O_TITLE, O_DESCRIBE,O_EXECUTOR_NAME,O_CUSTOMER_NAME,O_START_TIME,O_CREATE_TIME,O_FINISH_TIME,"
				+ " Real_FINISH_TIME,DELETE_TIME,LOOK_USER,SOURCE,O_TYPE_ID,LIGHTNING,O_STATUS,evaluation,star "
				+ " from t_1do_base where "+sql+" ORDER BY ID desc)a LEFT JOIN (select * from t_1do_order "
				+ " where useraccount=? and type=?)b on a.show_id=b.show_id ORDER BY modifyTime desc LIMIT ?,10";
		List<Record> t3=Db.find(from,"%"+loginName+"%",loginName,orderType,(json1.getIntValue("pageNumber")-1)*10);
		if(StrUtil.isNotEmpty(json1.getString("sorting"))){
		    from ="select SHOW_ID,O_TITLE,O_DESCRIBE,O_EXECUTOR_NAME,O_CUSTOMER_NAME,O_START_TIME,O_CREATE_TIME,O_FINISH_TIME,evaluation,star "
					+ "(case LOOK_USER like ? when true  then 1 else 2 end )ISLOOK,Real_FINISH_TIME,"
					+ "DELETE_TIME,SOURCE,O_TYPE_ID ,LIGHTNING,O_STATUS from t_1do_base  where "+sql+"  ORDER BY "+json1.getString("sorting")+" LIMIT ?,10";
			t3=Db.find(from,"%"+loginName+"%",(json1.getIntValue("pageNumber")-1)*10);

		}
		String from1="select count(*) num from t_1do_base  where "+sql;
		T1doType.setSOURCE(t3);
		int allPage=Db.queryInt(from1);
		JSONObject json2=new JSONObject();
		json2.put("base", t3);
		json2.put("allPage",allPage );
		//json2.put("data", json2.clone());
		json2.put("code", 200);		
		json2.put("message", "Success");
		return json2;
	}
	//看板搜索数据
		public static Page<Record> getDoBase(JSONObject user, JSONObject json1) {
			String loginName=user==null?json1.getString("loginName"):user.getString("loginName");
			
			int type=json1.getIntValue("type");
			String sql=" o_status!=6 ";			
			if(type==7){
				sql=" (o_status=3 or o_status=4) and LIGHTNING>0";
			}else if(type==8){
				sql=" o_status<5";
			}else if(type!=0){
				sql=" o_status="+type;
			}
			int orderType=1;
			if(json1.getString("method").equals("medo")){
				sql+=" and O_EXECUTOR like CONCAT('%','"+loginName+"','%')" ;				
			}else if(json1.getString("method").equals("hedo")){
				orderType=2;
				sql+=" and O_CUSTOMER like CONCAT('%','"+loginName+"','%')";				
			}else if(json1.getString("method").equals("all")){
				orderType=2;
			    sql+=" and (O_EXECUTOR like CONCAT('%','"+loginName+"','%') or O_CUSTOMER like CONCAT('%','"+loginName+"','%') )";				
		    }else{
		    	orderType=3;
		    }
			if(StrUtil.isNotEmpty(json1.getString("base"))){
				//来源是10督查指挥的话，base查询关键词和姓名
				if(StrUtil.isNotEmpty(json1.getString("SOURCE"))&&json1.getIntValue("SOURCE")==10) {
					sql+=" and ( O_DESCRIBE like CONCAT('%','"+json1.getString("base")+"','%')  "
							+ "or O_CUSTOMER_NAME like CONCAT('%','"+json1.getString("base")+"','%') or"
									+ " O_EXECUTOR_NAME like CONCAT('%','"+json1.getString("base")+"','%') )";
				}else {
					sql+=" and O_DESCRIBE like CONCAT('%','"+json1.getString("base")+"','%')";
				}
				
			}
		    if(StrUtil.isNotEmpty(json1.getString("O_CUSTOMER_NAME"))){
				sql+=" and O_CUSTOMER_NAME like CONCAT('%','"+json1.getString("O_CUSTOMER_NAME")+"','%')";
			}
			if(StrUtil.isNotEmpty(json1.getString("O_EXECUTOR_NAME"))){
				sql+=" and O_EXECUTOR_NAME like CONCAT('%','"+json1.getString("O_EXECUTOR_NAME")+"','%')";
			}
			if(StrUtil.isNotEmpty(json1.getString("O_CREATE_TIME_START"))&&StrUtil.isNotEmpty(json1.getString("O_CREATE_TIME_END"))){
				sql+=" and DATE_FORMAT(O_CREATE_TIME,'%Y-%m-%d')  BETWEEN '"+json1.getString("O_CREATE_TIME_START")+"' and '"+json1.getString("O_CREATE_TIME_END")+"'";
			}
			if(StrUtil.isNotEmpty(json1.getString("O_FINISH_TIME_START"))&&StrUtil.isNotEmpty(json1.getString("O_FINISH_TIME_END"))){
				sql+=" and DATE_FORMAT(O_FINISH_TIME,'%Y-%m-%d')  BETWEEN '"+json1.getString("O_FINISH_TIME_START")+"' and '"+json1.getString("O_FINISH_TIME_END")+"'  ";
			}
		
				if(StrUtil.isNotEmpty(json1.getString("SOURCE"))&&json1.getIntValue("SOURCE")!=0) {
					sql+=" and SOURCE="+json1.getIntValue("SOURCE");
						if(StrUtil.isNotEmpty(json1.getString("O_TYPE_ID"))&&!json1.getString("O_TYPE_ID").equals("0")) {
							sql+=" and O_TYPE_ID="+json1.getString("O_TYPE_ID");
						}
					
				}
			String from ="select SHOW_ID,O_TITLE,O_DESCRIBE,O_EXECUTOR_NAME,O_CUSTOMER_NAME,O_START_TIME,O_CREATE_TIME,O_FINISH_TIME,evaluation,star "
						+ "(case LOOK_USER like ? when true  then 1 else 2 end )ISLOOK,Real_FINISH_TIME,"
						+ "DELETE_TIME,SOURCE,O_TYPE_ID ,LIGHTNING,O_STATUS from t_1do_base  where "+sql+"  ORDER BY "+json1.getString("sorting");
			//List<Record> t3=Db.find(from,"%"+loginName+"%",loginName,orderType,(json1.getIntValue("pageNumber")-1)*10);
			SqlPara sqlPara = new SqlPara();
			sqlPara.addPara("%"+loginName+"%");
			//排序字段为空
			if(StrUtil.isEmpty(json1.getString("sorting"))){
				 from ="select a.*,b.type TYPE "
						+ " from (select SHOW_ID,O_TITLE, O_DESCRIBE,O_EXECUTOR_NAME,O_CUSTOMER_NAME,O_START_TIME,O_CREATE_TIME,O_FINISH_TIME,"
						+ " Real_FINISH_TIME,DELETE_TIME,(case LOOK_USER like ? when true  then 1 else 2 end )ISLOOK,SOURCE,O_TYPE_ID,LIGHTNING,O_STATUS,evaluation,star "
						+ " from t_1do_base where "+sql+" ORDER BY ID desc)a LEFT JOIN (select * from t_1do_order "
						+ " where useraccount=? and type=?)b on a.show_id=b.show_id ORDER BY modifyTime desc ";
				//t3=Db.find(from,"%"+loginName+"%",(json1.getIntValue("pageNumber")-1)*10);
					sqlPara.addPara(loginName);
					sqlPara.addPara(orderType);
			}
			sqlPara.setSql(from);
			Page<Record> t3=Db.paginate(json1.getIntValue("pageNumber"), json1.getIntValue("pageSize"), sqlPara);
			T1doType.setSOURCE(t3.getList());
			return t3;
		}
	//修改1do人员
	public static int changeUser(JSONObject json, JSONObject user) throws UnsupportedEncodingException, IOException {
		T1doBase t1doBase =T1doBase.getIdoBase(json.getString("SHOW_ID"));
		
		T1doPstatus t= new T1doPstatus();
		int i1=0;
		int type=5;
		if(json.getString("object").equals("发起人")){
			/*return 1;*/
			i1=1;
			t1doBase.setOCustomer(StrUtil.getUser1(t1doBase.getOCustomer(), json.getString("useraccount"), json.getString("method")))
			.setOCustomerName(StrUtil.getUser1(t1doBase.getOCustomerName(), json.getString("username"), json.getString("method"))).update();
			t.setUserType(1).setOStatus(t1doBase.getOStatus()).setSTATUS(StrUtil.getStatus2(t1doBase.getOStatus()));
		}else if(json.getString("object").equals("参与人")){
			i1=3;
			type=6;
			t1doBase.setOExecutor(StrUtil.getUser1(t1doBase.getOExecutor(), json.getString("useraccount"), json.getString("method")))
			.setOExecutorName(StrUtil.getUser1(t1doBase.getOExecutorName(), json.getString("username"), json.getString("method"))).update();
			t.setUserType(3).setOStatus(t1doBase.getOStatus()).setSTATUS(StrUtil.getStatus2(t1doBase.getOStatus()));
				
		}else{
			i1=4;
			t1doBase.setCC(StrUtil.getUser1(t1doBase.getCC(), json.getString("useraccount"), json.getString("method")))
			.setCcName(StrUtil.getUser1(t1doBase.getCcName(), json.getString("username"),json.getString("method"))).update();
			t.setUserType(4).setOStatus(t1doBase.getOStatus()).setSTATUS(StrUtil.getStatus2(t1doBase.getOStatus()));

		}
		List<String> list=new ArrayList<String>();
		if(json.getString("method").equals("cover")){
			String[] temp =json.getString("useraccount").split(";");
			String[] temp1 =json.getString("username").split(";");
			String s=""; 
			for (String string : temp) {
				s+="'"+string+"',"		;
			}
			s=s.substring(0, s.length()-1);
			List<T1doPstatus> ts=T1doPstatus.dao.find("select * from t_1do_pstatus where isDelete=1 and SHOW_ID=? and O_USER not in("+s+") and USER_TYPE=?",t1doBase.getShowId(),i1);
			for (int i = 0; i < ts.size(); i++) {
				//String string = ts[i];
				T1doLog.saveLog1(json.getString("SHOW_ID"), user.getString("loginName"), 
						user.getString("username"), user.getString("username").equals(ts.get(i).getOUserName())?
								user.getString("username")+"退出此1do":user.getString("username")+"移除"+ts.get(i).getOUserName()+"出此1do", 8,ts.get(i).getOUserName(),i1);
				
					if(!t1doBase.getOCustomer().contains(ts.get(i).getOUser()))
						list.add(ts.get(i).getOUser());
					
			     
			}
			//如果绑定微信同步踢人
			if(t1doBase.getIsBindingWechatGroup()&&list.size()>0){
				WeChatService.removeUsersOrAddUsers(json.getString("SHOW_ID"), t1doBase.getWechatGroupId(), list, 5);
			}
		
			//删除用户
			DbUtil.update(s, t1doBase.getShowId(), 10, i1, "", "");
			list.clear();
			for(int j = 0; j < temp.length; j++){
				T1doPstatus t12=T1doPstatus.dao.findFirst("select * from t_1do_pstatus where isDelete=1 and SHOW_ID=? and O_USER=? and USER_TYPE=?",t1doBase.getShowId(),temp[j],i1);
				if(t12!=null){
					continue;
				}				
				int i=DbUtil.update(t1doBase.getShowId(),temp[j],11,i1, "", "");
				if(!t1doBase.getOCustomer().contains(temp[j]))
					list.add(temp[j]);
				 if(i==0){
					 
					t.setShowId(json.getString("SHOW_ID")).setOUser(temp[j]).setOUserName(temp1[j]).setOtherid(i1==3?2:0).save();
					
						DoService.sendOneIdo(t1doBase,1,temp[j],temp1[j],t1doBase.getODescribe());//单独发通知i 1加入2查看3反馈
			           
					t.remove("ID");
				 }
				T1doLog.saveLog1(json.getString("SHOW_ID"), user.getString("loginName"), user.getString("username"), user.getString("username")+"邀请"+temp1[j]+"进入此1do", 9,temp1[j],i1);

			}
			//如果绑定微信同步加人
			if(t1doBase.getIsBindingWechatGroup()&&list.size()>0){
				WeChatService.removeUsersOrAddUsers(json.getString("SHOW_ID"), t1doBase.getWechatGroupId(), list, 4);
			}
			
		}else if(json.getString("method").equals("remove")){
			DbUtil.update(t1doBase.getShowId(),json.getString("useraccount"),12,i1, "", "");
			T1doLog.saveLog1(t1doBase.getShowId(), user.getString("loginName"), user.getString("username"), user.getString("username").equals(json.getString("username"))?user.getString("username")+"退出此1do":user.getString("username")+"移除"+json.getString("username")+"出此1do", 8,json.getString("username"),i1);
			//如果绑定微信同步踢人
			if(t1doBase.getIsBindingWechatGroup()&&!t1doBase.getOCustomer().contains(json.getString("useraccount"))) {
				list.add(json.getString("useraccount"));
				WeChatService.removeUsersOrAddUsers(json.getString("SHOW_ID"), t1doBase.getWechatGroupId(), list, 5);
			}
		}else if(json.getString("method").equals("add")){
			String[] temp =json.getString("useraccount").split(";");
			String[] temp1 =json.getString("username").split(";");
			for (int j = 0; j < temp.length; j++) {
				if(!t1doBase.getOCustomer().contains(temp[j]))
					list.add(temp[j]);
				//恢复被删除的用户
				int i=DbUtil.update(t1doBase.getShowId(),temp[j],13,i1, "", "");
			 if(i==0){
				t.setShowId(json.getString("SHOW_ID")).setOUser(temp[j]).setOUserName(temp1[j]).setOtherid(i1==3?2:0).save();
				T1doLog.saveLog1(json.getString("SHOW_ID"), user.getString("loginName"), user.getString("username"), user.getString("username")+"邀请"+temp1[j]+"进入此1do", 9,temp1[j],i1);
				
					DoService.sendOneIdo(t1doBase,1,temp[j],temp1[j],t1doBase.getODescribe());//单独发通知i 1加入2查看3反馈
				t.remove("ID");
			 }
			 
			}
			//如果绑定微信同步加人
			if(t1doBase.getIsBindingWechatGroup()&&list.size()>0){
				WeChatService.removeUsersOrAddUsers(json.getString("SHOW_ID"), t1doBase.getWechatGroupId(), list, 4);
			}
			
		}
		DbUtil.updateExecutore(t1doBase.getShowId());
		//回调接口
		new Thread(new CommonTask(t1doBase,type)).start();
		return 2;
		
	}

	public static Page<Record> getPageDoList(JSONObject user, JSONObject json1) {
		
        String loginName=user==null?json1.getString("loginName"):user.getString("loginName");
		SqlPara sqlPara = new SqlPara();
		int type=json1.getIntValue("type");
		String sql=" o_status!=6 ";			
		if(type==7){
			sql=" (o_status=3 or o_status=4) and LIGHTNING>0";
		}else if(type!=0){
			sql=" o_status="+type;
		}
		int orderType=1;
		if(json1.getString("method").equals("medo")){
			sql+=" and O_EXECUTOR like CONCAT('%','"+loginName+"','%')" ;				
		}else if(json1.getString("method").equals("hedo")){
			orderType=2;
			sql+=" and O_CUSTOMER like CONCAT('%','"+loginName+"','%')";				
		}else if(json1.getString("method").equals("all")){
			orderType=2;
		    sql+=" and (O_EXECUTOR like CONCAT('%','"+loginName+"','%') or O_CUSTOMER like CONCAT('%','"+loginName+"','%') )";				
	    }else{
	    	orderType=3;
	    }
		if(StrUtil.isNotEmpty(json1.getString("base"))){
			sql+=" and O_DESCRIBE like CONCAT('%','"+json1.getString("base")+"','%')";
		}
	    if(StrUtil.isNotEmpty(json1.getString("O_CUSTOMER_NAME"))){
			sql+=" and O_CUSTOMER_NAME like CONCAT('%','"+json1.getString("O_CUSTOMER_NAME")+"','%')";
		}
		if(StrUtil.isNotEmpty(json1.getString("O_EXECUTOR_NAME"))){
			sql+=" and O_EXECUTOR_NAME like CONCAT('%','"+json1.getString("O_EXECUTOR_NAME")+"','%')";
		}
		if(StrUtil.isNotEmpty(json1.getString("O_CREATE_TIME_START"))&&StrUtil.isNotEmpty(json1.getString("O_CREATE_TIME_END"))){
			sql+=" and DATE_FORMAT(O_CREATE_TIME,'%Y-%m-%d')  BETWEEN '"+json1.getString("O_CREATE_TIME_START")+"' and '"+json1.getString("O_CREATE_TIME_END")+"'";
		}
		if(StrUtil.isNotEmpty(json1.getString("O_FINISH_TIME_START"))&&StrUtil.isNotEmpty(json1.getString("O_FINISH_TIME_END"))){
			sql+=" and DATE_FORMAT(O_FINISH_TIME,'%Y-%m-%d')  BETWEEN '"+json1.getString("O_FINISH_TIME_START")+"' and '"+json1.getString("O_FINISH_TIME_END")+"'  ";
		}
	
			if(StrUtil.isNotEmpty(json1.getString("SOURCE"))&&json1.getIntValue("SOURCE")!=0) {
				sql+=" and SOURCE="+json1.getIntValue("SOURCE");
					if(StrUtil.isNotEmpty(json1.getString("O_TYPE_ID"))&&!json1.getString("O_TYPE_ID").equals("0")) {
						sql+=" and O_TYPE_ID="+json1.getString("O_TYPE_ID");
					}
				
						
			}
		String from ="select a.*,b.type TYPE,(case a.LOOK_USER like ? when true  then 1 else 2 end )ISLOOK"
				+ " from (select SHOW_ID,O_TITLE O_DESCRIBE,O_EXECUTOR_NAME,O_CUSTOMER_NAME,O_START_TIME,O_CREATE_TIME,O_FINISH_TIME,"
				+ " star,evaluation,Real_FINISH_TIME,DELETE_TIME,LOOK_USER,SOURCE,O_TYPE_ID "
				+ " from t_1do_base where "+sql+" ORDER BY ID desc)a LEFT JOIN (select * from t_1do_order "
				+ " where useraccount=? and type=?)b on a.show_id=b.show_id ORDER BY modifyTime desc LIMIT ?,10";
		List<Record> t3=Db.find(from,"%"+loginName+"%",loginName,orderType,(json1.getIntValue("pageNumber")-1)*10);
		if(StrUtil.isNotEmpty(json1.getString("sorting"))){
		    from ="select SHOW_ID,O_DESCRIBE,O_EXECUTOR_NAME,O_CUSTOMER_NAME,O_START_TIME,O_CREATE_TIME,O_FINISH_TIME,star,"
					+ "(case LOOK_USER like ? when true  then 1 else 2 end )ISLOOK,evaluation,Real_FINISH_TIME,"
					+ "DELETE_TIME,SOURCE,O_TYPE_ID from t_1do_base  where "+sql+"  ORDER BY "+json1.getString("sorting")+" LIMIT ?,10";
			t3=Db.find(from,"%"+loginName+"%",(json1.getIntValue("pageNumber")-1)*10);

		}
		
		sqlPara.setSql(from);
		Page<Record> kList = Db.paginate(json1.getIntValue("pageNumber"),json1.getIntValue("pageNumber") , sqlPara);
		String from1="select count(*) num from t_1do_base  where "+sql;
		T1doType.setSOURCE(t3);
		JSONObject json2=new JSONObject();
		json2.put("base", t3);
		json2.put("allPage", Db.queryInt(from1));
		json2.put("code", 200);		
		// return json2;
		// TODO Auto-generated method stub
		return null;
	}
	/**
     * @Author coco
     * @Description 看板搜索
     * @Date 2018年7月5日上午9:56:46
    */
	public static JSONObject searchNum(JSONObject json1, String loginName) {
		String sql1="";
		
		if(json1.getString("method").equals("medo")){
			sql1=" and O_EXECUTOR like CONCAT('%','"+loginName+"','%')" ;				
		}else if(json1.getString("method").equals("hedo")){
			sql1=" and O_CUSTOMER like CONCAT('%','"+loginName+"','%')";				
		}else if(json1.getString("method").equals("all")){
		    sql1=" and (O_EXECUTOR like CONCAT('%','"+loginName+"','%') or O_CUSTOMER like CONCAT('%','"+loginName+"','%') ) ";
		    if(StrUtil.isNotEmpty(json1.getString("source"))&&json1.getIntValue("source")==10){
				sql1+=" and SOURCE="+json1.getIntValue("source");
				if(StrUtil.isNotEmpty(json1.getString("oTypeId"))&&!json1.getString("oTypeId").equals("0")) {
					sql1+=" and O_TYPE_ID="+json1.getString("oTypeId");
				}
			}    
		}
		
		JSONObject json2=new JSONObject();
		for (int j = 3; j < 7; j++) {							
		     json2.put(""+j, Db.queryInt("select count(*) num from t_1do_base where o_status=?"+sql1,j));                                                                                                                                                                                                                                                                                      			     
		}
	     json2.put("medoTotal", Db.queryInt("select count(*) num from t_1do_base where o_status<6 and O_EXECUTOR like CONCAT('%','"+loginName+"','%')")); //要我做总数
	     json2.put("hedoTotal", Db.queryInt("select count(*) num from t_1do_base where o_status<6 and O_CUSTOMER like CONCAT('%','"+loginName+"','%')")); //要他做总数
	     json2.put("urge",Db.queryInt("select count(*) num from t_1do_base where (o_status=3 or o_status=4) "+sql1+" and LIGHTNING>0")); //催办数  
		String usql="";
		if(json1.getString("method").equals("medo")){
			usql="USER_TYPE=3 and";
		}else if(json1.getString("method").equals("hedo")){
			usql="USER_TYPE=1 and";
		}else if(json1.getString("method").equals("all")){
			usql="USER_TYPE!=2 and";
		}
		
		String att="select count(*) num from t_1do_base b, (select * from t_1do_pstatus where  "+usql+" isDelete=1 and isRead=? and O_STATUS!=6 and O_USER='"+loginName+"' GROUP BY SHOW_ID)f where b.SHOW_ID=f.SHOW_ID "+sql1+" ";
		json2.put("Y",  Db.queryInt(att,1));
		json2.put("N", Db.queryInt(att,2));
		//json2.put("data", json2.clone());
		json2.put("code", 200);					
		json2.put("message", "Success");
		
		
		return json2;
	}
	/*
	 2019年4月28日 APP看板搜索
	*/
	public  static JSONObject getAppSearchResult(JSONObject json1,String loginName ){
		String O_DESCRIBE="a.O_TITLE O_DESCRIBE";//卡片看板等等，用标题展示
		String sql="(O_EXECUTOR like CONCAT('%','"+loginName+"','%') or O_CUSTOMER like CONCAT('%','"+loginName+"','%') )";			
		if(json1.getString("method").equals("medo")){
			sql="O_EXECUTOR like CONCAT('%','"+loginName+"','%')" ;				
		}else if(json1.getString("method").equals("hedo")){
			sql="O_CUSTOMER like CONCAT('%','"+loginName+"','%')";		
			//督查搜索
		}else if(json1.getString("method").equals("all")&&StrUtil.isNotEmpty(json1.getString("source"))&&!json1.getString("source").equals("app")&&json1.getIntValue("source")==10){
			sql+=" and SOURCE="+json1.getIntValue("source");
			if(StrUtil.isNotEmpty(json1.getString("oTypeId"))&&!json1.getString("oTypeId").equals("0")) {
				sql+=" and O_TYPE_ID="+json1.getString("oTypeId");
			}
			if(StrUtil.isNotEmpty(json1.getString("base"))){
				sql+=" and ( O_DESCRIBE like CONCAT('%','"+json1.getString("base")+"','%')  "
						+ "or O_CUSTOMER_NAME like CONCAT('%','"+json1.getString("base")+"','%') or"
								+ " O_EXECUTOR_NAME like CONCAT('%','"+json1.getString("base")+"','%') )";
			}
			//督查用内容展示
			O_DESCRIBE="a.O_DESCRIBE";
		}  
		
		if(StrUtil.isNotEmpty(json1.getString("relate"))){
			sql+=" and (O_EXECUTOR like CONCAT('%','"+json1.getString("relate")+"','%') or O_CUSTOMER like CONCAT('%','"+json1.getString("relate")+"','%') )";
		}
		int type=json1.getIntValue("type");
		if(type==7){
			sql+=" and (a.o_status=3 or a.o_status=4) and LIGHTNING>0";
		}else if(type==8){
			sql=" o_status<5";
		}else if(type!=0){
			sql+=" and a.o_status="+type;
		}else{
			sql+=" and a.o_status!=6";
		}
		int isLook=json1.getIntValue("isLook");
		String look=isLook!=0?" and isRead="+isLook:"";
		   String from1=StrUtil.isNotEmpty(json1.getString("source"))? " ORDER BY O_CREATE_TIME desc LIMIT ?,? ) g ":" ORDER BY O_CREATE_TIME desc LIMIT ?,? ) g ORDER BY O_CREATE_TIME ";			
			if(type==7||isLook==2||type==3||type==4)
			 from1=StrUtil.isNotEmpty(json1.getString("source"))?" ORDER BY SEND_TIME desc LIMIT ?,? ) g ":" ORDER BY SEND_TIME desc LIMIT ?,? ) g ORDER BY SEND_TIME ";
		boolean flag=true;
		if(StrUtil.isNotEmpty(json1.getString("keyword"))){
			if(json1.getString("keyword").equals("最新回复")){					
				//sql+=" and SEND_TIME =(select MAX(SEND_TIME) from t_1do_base)";
				from1=" ORDER BY SEND_TIME desc LIMIT ?,? ) g limit 1";
				flag=false;
			}else if(json1.getString("keyword").equals("查看最近一个月的任务")){
				sql+=" and date_sub(curdate(), interval 30 day) <= date(O_CREATE_TIME)";					
			}else{
				
				sql+=StrUtil.appendSql(json1.getString("keyword"));
			}
		}
		String from2="select %s";
		String from =" from t_1do_base a LEFT JOIN (select * from t_1do_pstatus where USER_TYPE!=2 " +look+" "
					+ "and isDelete=1 and O_USER='"+loginName+"' GROUP BY SHOW_ID)f on a.SHOW_ID=f.SHOW_ID "
					+"where "+sql+look;
		String value="a.SHOW_ID,a.SOURCE,a.O_TYPE_ID,"+O_DESCRIBE+",a.O_CUSTOMER_NAME,a.O_CUSTOMER,a.AT,a.O_EXECUTOR,"
				    +"a.O_EXECUTOR_NAME,a.SEND_TIME,unix_timestamp(a.O_CREATE_TIME)*1000 O_CREATE_TIME,"
                   +"unix_timestamp(a.O_FINISH_TIME)*1000 O_FINISH_TIME,unix_timestamp(a.Real_FINISH_TIME)*1000 Real_FINISH_TIME"
                   +",unix_timestamp(a.DELETE_TIME)*1000 DELETE_TIME,a.O_IS_DELETED ,"
                   +" a.LIGHTNING,a.LOOKNUM,a.FBNUM,f.USER_TYPE,f.isRead ISLOOK,f.STATUS O_STATUS ";
		List<Record> r3=Db.find("select * from ("+String.format(from2, value)+from+from1,json1.getIntValue("pageNumber"),json1.getIntValue("onePageNumber"));
		Integer ret = Db.queryInt(String.format(from2, "count(*)")+from);
		JSONObject json=new JSONObject();
		T1doType.setSOURCE(r3);
		json.put("base", r3);
		json.put("allPage", flag?ret:1);
		return json;
	}
	/*
	 * 登入
	 */
	public static JSONObject login1do(JSONObject json) {
		JSONObject result=TRegUser.getUser(json);
		if(result==null){
		    result=HttpUtil.loginIm(json.getString("useraccount"));
		    if(result==null) {
		    	JSONObject result1=new JSONObject();
				result1.put("message", "用户不存在");
				result1.put("code", 500);
				return result1;
		    }
			System.out.println(result);
			if(UrlUtil.getUser.equals("http://xcgovapi.hzxc.gov.cn")){
				String str=HttpUtil.getParameter1(result, "/Base-Module/CompanyUser/GetUser",result.getString("loginName"));			
				JSONObject json3= HttpUtil.doPost2(UrlUtil.getUser, str);//http://172.16.8.7:6002
				System.out.println(json3);
				result.put("D_NAME", json3.getJSONObject("user").get("deptName"));
				result.put("U_DEPT_ID", json3.getJSONObject("user").get("deptId"));				
				result.put("POWER", 3);
				result.put("YSCG_POWER", 0);
				TRegUser.saveUser(result);
			}else{
				//{"cShowId":"b5WJZ1bRLDCb7x2B","loginName":"PQV8oo3jeeiDkLbY","LoginToken":"Sl2rcs/fFeU9mYQIr54bK+9vDhU=","useraccount":"fangshengqun","username":"方升群"}
				String url=UrlUtil.getUser+"?CREATE_USER="+result.getString("loginName")+""
						+ "&CREATE_USER_NAME="+result.getString("username")+"&C_SHOW_ID="+result.getString("cShowId")+""
								+ "&C_CODE=xcgov&AuthToken="+result.getString("LoginToken");
				JSONObject json3= HttpUtil.doGet(url);
				System.out.println(json3);
				result.put("D_NAME", json3.getJSONObject("user").get("deptName"));
				result.put("U_DEPT_ID", json3.getJSONObject("user").get("deptId"));
				result.put("POWER", 3);
				result.put("YSCG_POWER", 0);
				TRegUser.saveUser(result);
			}
		result.put("isfw", false);//是否是整理层
	}else if(result.getIntValue("IS_DELETED")==1){
		JSONObject result1=new JSONObject();
		result1.put("message", "用户不存在");
		result1.put("code", 500);
		return result1;		
	}else {
		result.put("isfw", result.getIntValue("POWER")==1);//是否是整理层
	}
		//result.put("isfw", TRegUser.getFlag(json.getString("useraccount")));//是否是整理层
		//result.put("data", result.clone());
		result.put("message", "Success");
		result.put("code", 200);
		return result;		
	}
	/*
	 * 登入
	 */
	public static Msg login(JSONObject json) {
		JSONObject result=TRegUser.getUser(json);
		if(result==null){
		    result=HttpUtil.loginIm(json.getString("useraccount"));
		    if(result==null) {
				return MsgUtil.errorMsg("用户不存在");
		    }
			System.out.println(result);
			if(UrlUtil.getUser.equals("http://xcgovapi.hzxc.gov.cn")){
				String str=HttpUtil.getParameter1(result, "/Base-Module/CompanyUser/GetUser",result.getString("loginName"));			
				JSONObject json3= HttpUtil.doPost2(UrlUtil.getUser, str);//http://172.16.8.7:6002
				System.out.println(json3);
				result.put("D_NAME", json3.getJSONObject("user").get("deptName"));
				result.put("U_DEPT_ID", json3.getJSONObject("user").get("deptId"));				
				result.put("POWER", 3);
				result.put("YSCG_POWER", 0);
				TRegUser.saveUser(result);
			}else{
				//{"cShowId":"b5WJZ1bRLDCb7x2B","loginName":"PQV8oo3jeeiDkLbY","LoginToken":"Sl2rcs/fFeU9mYQIr54bK+9vDhU=","useraccount":"fangshengqun","username":"方升群"}
				String url=UrlUtil.getUser+"?CREATE_USER="+result.getString("loginName")+""
						+ "&CREATE_USER_NAME="+result.getString("username")+"&C_SHOW_ID="+result.getString("cShowId")+""
								+ "&C_CODE=xcgov&AuthToken="+result.getString("LoginToken");
				JSONObject json3= HttpUtil.doGet(url);
				System.out.println(json3);
				result.put("D_NAME", json3.getJSONObject("user").get("deptName"));
				result.put("U_DEPT_ID", json3.getJSONObject("user").get("deptId"));
				result.put("POWER", 3);
				result.put("YSCG_POWER", 0);
				TRegUser.saveUser(result);
			}
		result.put("isfw", false);//是否是整理层
	}else if(result.getIntValue("IS_DELETED")==1){
		return MsgUtil.errorMsg("用户不存在");	
	}else {
		result.put("isfw", result.getIntValue("POWER")==1);//是否是整理层
	}
		return MsgUtil.successMsg(result);

	}
	//普通反馈
	public static Msg feedback(JSONObject user1, JSONObject json,DoController doController) throws UnsupportedEncodingException, IOException {
		int i=0;
		if(user1!=null){
		}else if(json.getString("loginName")!=null){
			user1=setSession(json.getString("loginName"),doController);				
		}else if(json.getString("FourPlatformsUser")!=null){
			i=1;//代表4个平台的反馈消息
		    user1=setSession(FourService.getUserAccount(json.getString("FourPlatformsUser")),doController);				
		}else {
			return MsgUtil.errorMsg("用户未登入");
			
		}
		final JSONObject user=user1;
		if(!json.containsKey("SOURCE")) 
			json.put("source", 0);
		final T1doFeedback t1doFeedback =json.toJavaObject(T1doFeedback.class);
		//查询是否是参与人
		T1doPstatus t1doPstatus=T1doPstatus.getCustomerOrExecutor(t1doFeedback.getShowId(),user.getString("loginName"),3);
		final T1doBase t1doBase=t1doFeedback.getT1doBase();
		t1doFeedback.setFbTime(new Date()).setTimeStamp(new Date().getTime()).setOUser(user.getString("loginName")).setOUserName(user.getString("username"));
		T1doLog t1doLog=json.toJavaObject(T1doLog.class);
		t1doLog.setOpTime(t1doFeedback.getFbTime()).setOUser(user.getString("loginName")).setOUserName(user.getString("username"));	
		//查询操作用户角色（同时拥有发起人和参与人角色取发起人）
		T1doPstatus t1=T1doPstatus.getUser(json.getString("SHOW_ID"),user.getString("loginName"));
		//1普通反馈2回复反馈
		if(t1doFeedback.getFbType()==1||t1doFeedback.getFbType()==2){
			if(StrUtil.isNotEmpty(t1doFeedback.getAT())&&t1doFeedback.getAT().charAt(t1doFeedback.getAT().length()-2)==','){
				return MsgUtil.errorMsg("AT参数错误");
				
		    }
		//List<String> list=t1doFeedback.getATString();
		t1doFeedback.save();
			int ii=1;
			//4个平台反馈有可能带附件
			if(StrUtil.isNotEmpty(t1doFeedback.getAttrPath())) {
				String[] str=t1doFeedback.getAttrPath().split(";");
				for (String string : str) {
					T1doFeedback t=new T1doFeedback();
					t.setShowId(t1doFeedback.getShowId()).setOUser(t1doFeedback.getOUser()).setOUserName(t1doFeedback.getOUserName()).setFbTime(t1doFeedback.getFbTime());
					t.remove("ID");
					t.setAttrPath(string);
					t.setFBCONTENT(string).setFbType(3);
					t.setTimeStamp(t1doFeedback.getTimeStamp()+ii);						
					t.save();
					i++;
				}
			}
			if(t1doPstatus!=null){
				//主动办
				new Thread(new SendIdo(t1doBase,4,t1doFeedback.getFBCONTENT())).start();
				//三实库审批
				new Thread(new SendIdo(t1doBase,5,t1doFeedback.getFBCONTENT())).start();
			}
			
			if(t1doPstatus!=null&&t1doBase.getOStatus()==3){
				//第一个参与人反馈修改工单状态
				t1doBase.setOStatus(4).setSendTime(new Date().getTime()).setFBNUM(t1doBase.getFBNUM()+1).update();	
				DbUtil.update(t1doFeedback.getShowId(),user.getString("loginName"), 15, 0, "", ""); 
	           	new Thread(new SendIdo(t1doBase,33,user.getString("loginName"),t1doFeedback.getAT(),1,t1doFeedback.getFBCONTENT(),T1doPstatus.getUser(t1doFeedback.getShowId()))).start();
	           	//百姓爆料的话回调接口
	           	new Thread(new BxblTask(t1doFeedback.getShowId(), 4,t1doBase.getSOURCE())).start();
			}else{
				t1doBase.setSendTime(new Date().getTime()).setFBNUM(t1doBase.getFBNUM()+1).update();
				final int temp=t1doBase.getOStatus()==3?1:t1doBase.getOStatus();
				//反馈设置用户未读
				DbUtil.update(t1doFeedback.getShowId(),user.getString("loginName"), 16, 0, "", ""); 
	           	new Thread(new SendIdo(t1doBase,temp,user.getString("loginName"),t1doFeedback.getAT(),1,t1doFeedback.getFBCONTENT(),T1doPstatus.getUser(t1doFeedback.getShowId()))).start();

	           	
			}
			
			t1doLog.setLogType(10).setLog(user.getString("username")+"反馈一条信息").setContent(t1doFeedback.getID()+"").save();
			
			//如果绑定微信群则同步到微信群
			if(t1doBase.getIsBindingWechatGroup()&&t1doFeedback.getSource()!=1)
				T1doWechat.saveT1doWechat(t1doFeedback.getID()+"", WeChatService.SendMsg(t1doBase.getWechatGroupId(), user.getString("username"), t1doFeedback.getFBCONTENT()), 2,"");

				
			//和移动端通讯
			new Thread(()->new WebSocketController().sendMessage(t1doBase.getShowId())).start();
			//打标签
			new Thread(()->T1doLabelFeedback.saveAllLabel(t1doFeedback)).start();
		
			//1do中的反馈调用4个平台推送接口，i=1说明是4个平台的反馈，不需要反推
			if(i==0&&t1doBase.getSOURCE()==9) {
			
				new Thread(()->t1doFeedback.setResult(FourService.doEventDispose("02", t1doBase, t1doFeedback,"")).update()).start();
			}
			//回调接口
			new Thread(new CommonTask(t1doBase,2)).start();
			return MsgUtil.successMsg(t1doFeedback);
			
		}else{
			t1doLog.setLogType(t1doFeedback.getFbType());
			String str=t1doLog.getOUserName();
			if(t1doLog.getLogType()==4){
				if(i==1||user.getBooleanValue("isfw")||StrUtil.getflag(t1.getUserType(), 6)){
					//大于5次催报发短信打电话
					if(t1doBase.getLIGHTNING()+1>5){
						String[] result =ShortMessageUtil.sendShortMessage(t1doBase.getShowId(),t1doBase.getOTitle(),t1doFeedback.getOUserName());
						t1doFeedback.setShortMessage(result[0]);
						t1doFeedback.setCallMessage(result[1]);
					}
					t1doBase.setSendTime(new Date().getTime()).setLIGHTNING(t1doBase.getLIGHTNING()+1).update();
					str+="催办此1do";						
					t1doFeedback.setFBCONTENT(str).save();
					Db.update("update t_1do_pstatus set isRead=2,urge_isLook=0 where SHOW_ID=? and isDelete=1 ",t1doBase.getShowId());//and (online=2 or gmt_modified<CURDATE())
					//Db.update("update t_1do_pstatus set urge_isLook=0 where SHOW_ID=?",t1doBase.getShowId());
					//设置催报人，以，隔开
					Db.update("update t_1do_base b,(select SHOW_ID,GROUP_CONCAT(O_USER_NAME) O_USER_NAME,GROUP_CONCAT(O_USER) O_USER from(select SHOW_ID,O_USER_NAME,O_USER from t_1do_feedback where fb_type=4 group by SHOW_ID,O_USER_NAME)a group by SHOW_ID) c" 
                    +" set b.URGENAME=c.O_USER_NAME,b.URGESHOWID=c.O_USER where b.SHOW_ID=c.SHOW_ID and b.SHOW_ID=?",t1doBase.getShowId());
					//发送催报给连接websoct的客户端	
					new Thread(()->new UrgeController().sendMessage(t1doBase.getShowId())).start();
					//催报发综合信息平台催办接口
					new Thread(new SendIdo(t1doBase,44,user.getString("loginName"),"",1,str,T1doPstatus.getUser(t1doBase.getShowId()))).start();
					//催办调用督查考核接口
					new Thread(new SupervisionAndEvaluation(0,t1doBase.getShowId())).start();
				}else{
					//renderJson(JsonUtil.getMap(202, "权限不足"));
					return MsgUtil.errorMsg("权限不足");
					
				}
				
			}else if(t1doLog.getLogType()==5){
				if(i==1||user.getBoolean("isfw")||(t1!=null&&StrUtil.getflag(t1.getUserType(), 7))||t1doBase.getSOURCE()==11||t1doBase.getSOURCE()==12){
					
					//int i=Db.update("update t_1do_status set O_STATUS=5 where SHOW_ID=?",t1doFeedback.getShowId());
					if(t1doBase.getOStatus()>=5){
						return MsgUtil.successMsg("该1do已经办结");
						
					}
					//修改人员工单状态
					T1doPstatus.setStatus(5,"已完成",t1doFeedback.getShowId());
					str+="确认办结";
					t1doFeedback.setFBCONTENT("确认办结").save();
					t1doBase.setRealFinishTime(new Date()).setSendTime(new Date().getTime()).setFBNUM(t1doBase.getFBNUM()+1).setOStatus(5).update();						
		           	new Thread(new SendIdo(t1doBase,55,user.getString("loginName"),"",1,"确认办结",T1doPstatus.getUser(t1doBase.getShowId()))).start();
		          
		           	//办结调用督查考核接口
					new Thread(new SupervisionAndEvaluation(1,t1doBase.getShowId())).start();
					
					
					
		          //如果绑定微信群则同步到微信群
					if(t1doBase.getIsBindingWechatGroup()&&t1doFeedback.getSource()!=1)
						T1doWechat.saveT1doWechat(t1doFeedback.getID()+"", WeChatService.SendMsg(t1doBase.getWechatGroupId(), user.getString("username"), "确认办结"), 2,"");
		           	//指挥平台的1do数据
		           	if(t1doBase.getSOURCE()==9) {
		           		//1do状态改变回调指挥平台接口
		           		FourService.StatusCallback(t1doFeedback.getShowId(), 1,t1doBase.getModule());   	  
		           		//不是4个平台过来的数据
		           		if(i==0) {			          
		           			new Thread(()->t1doFeedback.setResult(FourService.doEventDispose("01", t1doBase, t1doFeedback,"")).update()).start();
		           		}
		           	}else if(t1doBase.getSOURCE()==11) {
		           		new Thread(new YscgTask(t1doBase, 2)).start();//是云上城管数据办结就推送
		           	}else {
		           	//回调接口
		    			new Thread(new CommonTask(t1doBase,2)).start();
		           	}
		          //百姓爆料的话回调接口
		           	new Thread(new BxblTask(t1doFeedback.getShowId(), 5,t1doBase.getSOURCE())).start();
				}else{
				
					return MsgUtil.errorMsg("权限不足");
					
					
				}
				
			}else if(t1doLog.getLogType()==6){
				if(i==1||user.getBoolean("isfw")||StrUtil.getflag(t1.getUserType(),8)){
					t1doFeedback.setFBCONTENT("评价："+t1doFeedback.getFBCONTENT()).save();
					str+="评价了此1do";
					t1doBase.setStar(t1doFeedback.getStar()).setEvaluation(t1doFeedback.getFBCONTENT()).setSendTime(new Date().getTime()).setFBNUM(t1doBase.getFBNUM()+1).update();
					
					 //如果绑定微信群则同步到微信群
					if(t1doBase.getIsBindingWechatGroup()&&t1doFeedback.getSource()!=1)
						T1doWechat.saveT1doWechat(t1doFeedback.getID()+"", WeChatService.SendMsg(t1doBase.getWechatGroupId(), user.getString("username"), "评价："+t1doFeedback.getFBCONTENT()), 2,"");
					new Thread(()->T1doLabelFeedback.saveAllLabel(t1doFeedback)).start();
		           	new Thread(new SendIdo(t1doBase,6,user.getString("loginName"),"",1,t1doFeedback.getFBCONTENT(),T1doPstatus.getUser(t1doBase.getShowId()))).start();

				}else{
					return MsgUtil.errorMsg("权限不足");
					
				}
			}else if(t1doLog.getLogType()==7){
				//修改工单状态
				t1doLog.setLogType(12);
				int status= T1doBase.updateStatus(6,t1doBase.getShowId());
				if(status==1){
					//t1doFeedback.setFBCONTENT("评价："+t1doFeedback.getFBCONTENT()).save();
					str+="删除此1do";
					//T1doLog.saveLog(json.getString("SHOW_ID"), user.getString("loginName"), user.getString("username"), user.getString("username")+"删除此1do", 12,"");
		        	   t1doBase.setSendTime(new Date().getTime()).update();
		        	 //修改人员工单状态
						T1doPstatus.setStatus(6, "已删除", t1doBase.getShowId());
						//修改1do传送门关联表状态
						T1doRelation.updateType(6, t1doBase.getShowId());
						//发送通知
		           	new Thread(new SendIdo(t1doBase,77,user.getString("loginName"),"",1,user.getString("username")+"删除1do:"+t1doBase.getODescribe(),T1doPstatus.getUser(t1doBase.getShowId()))).start();
		           
		          //公共配置回调接口
					new Thread(new CommonTask(t1doBase.setOStatus(6),2)).start();
				  }else{
				
					  return MsgUtil.errorMsg("删除失败");
				  }
			}
			t1doLog.setLog(str).save();
			
			
		    //发送反馈给连接websoct的客户端.
			new Thread(()->new WebSocketController().sendMessage(t1doBase.getShowId())).start();
			
			
			return MsgUtil.successMsg(t1doFeedback);
			
		}

		
	}
	/**
     * @Author coco
     * @Description 设置session		
     * @Date 
    */
	public static  JSONObject setSession(String loginName,DoController doController){
			
		T1doFw t1doFw =T1doFw.getIdoFwForLoginName(loginName);
		boolean isfw=t1doFw!=null;
		JSONObject json1=TRegUser.getUserForShowId(loginName);
		json1.put("isfw", isfw);
		//json1.put("data", json1.clone());
		json1.put("message", "Success");
		json1.put("code", 200);		
		doController.setSessionAttr("user", json1);
		return json1;
		//renderJson(json1);
	}
	
}
