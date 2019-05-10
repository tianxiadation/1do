 package com.demo.controller;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.fileupload.util.Streams;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.common.DemoConfig;
import com.demo.common.model.T1doAttr;
import com.demo.common.model.T1doBase;
import com.demo.common.model.T1doFeedback;
import com.demo.common.model.T1doFw;
import com.demo.common.model.T1doLabel;
import com.demo.common.model.T1doLabelFeedback;
import com.demo.common.model.T1doLog;
import com.demo.common.model.T1doOrder;
import com.demo.common.model.T1doPstatus;
import com.demo.common.model.T1doRelation;
import com.demo.common.model.T1doRelationFeedback;
import com.demo.common.model.T1doRelationRecord;
import com.demo.common.model.T1doType;
import com.demo.common.model.TRegUser;
import com.demo.common.model.Temp;
import com.demo.interceptor.AddLabel;
import com.demo.interceptor.LoginInterceptor;
import com.demo.interceptor.SendIdo;
import com.demo.interfaces.UpdateBase;
import com.demo.service.DoService;
import com.demo.util.DbUtil;
import com.demo.util.ExcelExportUtil;
import com.demo.util.HttpUtil;
import com.demo.util.IDUtil;
import com.demo.util.JsonUtil;
import com.demo.util.MsgUtil;
import com.demo.util.ShortMessageUtil;
import com.demo.util.StrUtil;
import com.demo.util.TimeUtil;
import com.demo.util.UrlUtil;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.MultipartParser;
import com.oreilly.servlet.multipart.ParamPart;
import com.oreilly.servlet.multipart.Part;


public class DoController extends Controller {
	private Logger log=Logger.getLogger(DoController.class);
	public void test() {
		renderJson(MsgUtil.successMsg(UrlUtil.ip+"\n"+DemoConfig.jdbcUrl));
	}
	/*
	 2019年5月6日 coco 注解：显示掩藏相关反馈
	*/
	@Before(LoginInterceptor.class)
	public void updateRelationRecord(){
		JSONObject json=JsonUtil.getJSONObject(getRequest());
	   	JSONObject user=getSessionAttr("user");
	   	if(user.getBooleanValue("isfw")){
	   		Db.update("update t_1do_relation_record set type=case type when 0 then 1 when 1 then 0 end where ID=?",json.getLongValue("ID"));
	   		renderJson(MsgUtil.successMsg("成功"));
	   	}else{
	   		renderJson(MsgUtil.errorMsg("权限不足"));
	   	}
	}
	/*
	 2019年5月6日 coco 注解：相关日志
	 */
	@Before(LoginInterceptor.class)
	public void getRelationRecord(){
		JSONObject json=JsonUtil.getJSONObject(getRequest());
		JSONObject user=getSessionAttr("user");
		String sql=user.getBooleanValue("isfw")?"":" and b.type!=0";
		renderJson(MsgUtil.successMsg(T1doRelationRecord.selectRelation(json.getString("SHOW_ID"),sql)));
	}
	/*
	 2019年5月6日 coco 注解：显示掩藏相关反馈
	*/
	@Before(LoginInterceptor.class)
	public void updateRelationFeedback(){
		JSONObject json=JsonUtil.getJSONObject(getRequest());
    	JSONObject user=getSessionAttr("user");
    	if(user.getBooleanValue("isfw")){
    		Db.update("update t_1do_relation_feedback set type=case type when 0 then 1 when 1 then 0 end where ID=?",json.getLongValue("RFID"));
    		renderJson(MsgUtil.successMsg("成功"));
    	}else{
    		renderJson(MsgUtil.errorMsg("权限不足"));
    	}
	}
	/*
	 2019年5月6日 coco 注解：相关反馈
	 */
	@Before(LoginInterceptor.class)
	public void getRelationFeedback(){
		JSONObject json=JsonUtil.getJSONObject(getRequest());
		JSONObject user=getSessionAttr("user");
		String sql=user.getBooleanValue("isfw")?"":" and (c.O_CUSTOMER like CONCAT('%','"+user.getString("loginName")+"','%') or c.O_EXECUTOR like CONCAT('%','"+user.getString("loginName")+"','%')) and c.O_STATUS!=6  and b.type!=0";			
		renderJson(MsgUtil.successMsg(T1doRelationFeedback.selectRelation(json.getString("SHOW_ID"),sql)));
	}
	/*
	 2019年3月6日 coco 注解：删除关联
	*/
	@Before(LoginInterceptor.class)
	public void deleteRelation() {
		JSONObject json=JsonUtil.getJSONObject(getRequest());
		JSONObject user=getSessionAttr("user");	
		if(user.getBooleanValue("isfw"))
		 renderJson(MsgUtil.successMsg(DbUtil.update(json.getString("SHOW_ID"),json.getString("RELATION_SHOW_ID"),1,0,"","")));
		else
		 renderJson(MsgUtil.errorMsg("无权限删除"));
	}
	/*
	 2019年2月13日 coco 注解：//批量添加关联
	*/
	
	public void batchAddRelation() {
    	JSONObject json=JsonUtil.getJSONObject(getRequest());
		JSONArray array=json.getJSONArray("list");
		for (int i = 0; i < array.size(); i++) {			
			DbUtil.update(json.getString("SHOW_ID"),array.getString(i),2,0,"","");
		}
		renderJson(MsgUtil.successMsg("添加成功"));

	}
	/*
	 2019年2月13日 coco 注解：//传送门关键字查询1do
	*/
	public void selectBybase() {		
    	renderJson(MsgUtil.successMsg(T1doBase.selectBybase(JsonUtil.getJSONObject(getRequest()))));
	}
	/*
	 2019年2月13日 coco 注解：传送门获取相关联1do
	*/
	@Before(LoginInterceptor.class)
	public void getRelation() {
    	JSONObject json=JsonUtil.getJSONObject(getRequest());
    	JSONObject user=getSessionAttr("user");
    	String sql=user.getBooleanValue("isfw")?"":" and (O_CUSTOMER like CONCAT('%','"+user.getString("loginName")+"','%') or O_EXECUTOR like CONCAT('%','"+user.getString("loginName")+"','%')) and b.O_STATUS!=6";			
		renderJson(MsgUtil.successMsg(T1doRelation.selectRelation(json.getString("SHOW_ID"),sql)));
	}
	//关联排序
	public void relationSort() {
		JSONArray array=JsonUtil.getJSONObject(getRequest()).getJSONArray("list");
    	//JSONObject user=getSessionAttr("user");
		for (int i = 0; i < array.size(); i++) {			
			Db.update("update t_1do_relation set sort=? where id=?",i+1,array.getInteger(i));
		}
		renderJson(MsgUtil.successMsg("排序完成"));
	}
	/*
	 2018年12月4日 coco 注解：//添加或删除标签
	 
	*/
	@Before(LoginInterceptor.class)
	public void addOrDeleteLabel() {
    	JSONObject json=JsonUtil.getJSONObject(getRequest());
    	JSONObject user=getSessionAttr("user");
    	T1doLabel l=null;
		if(user.getBooleanValue("isfw")){
	    	if(json.getString("method").equals("add")){	    		
	    		if(T1doLabel.getT1doLabel(json)==null){
	    		    l =json.toJavaObject(T1doLabel.class);
	    			l.setTYPE(2).save();
	    			new Thread(new Runnable() {	
						@Override
						public void run() {T1doRelation.updateSimilarity(json.getString("SHOW_ID"),"",1);}
					}).start();
	    		}else{
	    			renderJson(MsgUtil.errorMsg("标签已存在"));
	    			return;
	    		}
	    	}else{	
	    		l=json.toJavaObject(T1doLabel.class);
	    		l.delete();	    		
	    		new Thread(new Runnable() {
					@Override
					public void run() {T1doRelation.updateSimilarity(json.getString("SHOW_ID"),"and SIMILARITY>0",1);}
				}).start();
	    	}
			renderJson(MsgUtil.successMsg(l));
		}else{
			renderJson(MsgUtil.errorMsg("无操作权限"));
		}
		
	}
	/*
	 2018年10月31日 coco 注解：获得附件
	*/
	public void getAttr() {
    	JSONObject json=JsonUtil.getJSONObject(getRequest()); 	
		renderJson(MsgUtil.successMsg(T1doAttr.getAttr(json.getString("SHOW_ID"))));
	}
	/**
     * 导出Excel
     */
    public void exportExcel() {    
    	//http://localhost:8080/1do/do/exportExcel?type=3
         int type = getParaToInt("type");
         
         List<Record> data = DoService.exportExcel(type);
 		 String fileName="1do"+TimeUtil.getCurrentDateTime("yyyyMMddhhmmss")+".xls";
         File file = ExcelExportUtil.createExcelFile(fileName, data);
 		renderFile(file);
    }
	/*
		 2018年8月14日下午5:32:24 coco  //修改参与人的身份（抄送人或受理人）
	 */
	
	public void chuangUserId() {
			JSONObject json=JsonUtil.getJSONObject(getRequest());
			JSONObject user=getSessionAttr("user");
		if(user.getBooleanValue("isfw")){
			String[] users=json.getString("user").split(";");
		    DbUtil.update(json.getString("SHOW_ID"),"",3,json.getIntValue("otherid"),"","");		
			for(String user1:users){
			DbUtil.update(json.getString("SHOW_ID"),user1,4,json.getIntValue("otherid"),"","");
		    }
		      
			renderJson(JsonUtil.getMap(200, "修改成功"));
		}else {
			  renderJson(JsonUtil.getMap(202, "权限不足")); 

		}
		
	}
	/*
	 2018年7月5日上午9:56:46 coco   //看板搜索
	 */
	
	public void searchNum() {
		JSONObject json1=JsonUtil.getJSONObject(getRequest());
		JSONObject user=getSessionAttr("user");
		String loginName=user==null?json1.getString("loginName"):user.getString("loginName");
		String sql1="";
		
		if(json1.getString("method").equals("medo")){
			sql1=" and O_EXECUTOR like CONCAT('%','"+loginName+"','%')" ;				
		}else if(json1.getString("method").equals("hedo")){
			sql1=" and O_CUSTOMER like CONCAT('%','"+loginName+"','%')";				
		}else if(json1.getString("method").equals("all")){
		    sql1=" and (O_EXECUTOR like CONCAT('%','"+loginName+"','%') or O_CUSTOMER like CONCAT('%','"+loginName+"','%') ) ";
		}    
		JSONObject json2=new JSONObject();
		for (int j = 3; j < 7; j++) {							
		     Record r=Db.findFirst("select count(*) num from t_1do_base where o_status=? "+sql1,j);
		     json2.put(""+j, r.getInt("num"));                                                                                                                                                                                                                                                                                      			     
		}
	     Record medoTotal=Db.findFirst("select count(*) num from t_1do_base where o_status<6 and O_EXECUTOR like CONCAT('%','"+loginName+"','%')");
	     json2.put("medoTotal", medoTotal.getInt("num")); //催办数  
	     Record hedoTotal=Db.findFirst("select count(*) num from t_1do_base where o_status<6 and O_CUSTOMER like CONCAT('%','"+loginName+"','%')");
	     json2.put("hedoTotal", hedoTotal.getInt("num")); //催办数  
		 Record r=Db.findFirst("select count(*) num from t_1do_base where (o_status=3 or o_status=4) "+sql1+" and LIGHTNING>0");
	     json2.put("urge", r.getInt("num")); //催办数  
		String usql="";
		if(json1.getString("method").equals("medo")){
			usql="USER_TYPE=3 and";
		}else if(json1.getString("method").equals("hedo")){
			usql="USER_TYPE=1 and";
		}else if(json1.getString("method").equals("all")){
			usql="USER_TYPE!=2 and";
		}
		
		String att="select count(*) num from t_1do_base b, (select * from t_1do_pstatus where  "+usql+" isDelete=1 and isSend=? and O_USER='"+loginName+"' GROUP BY SHOW_ID)f where b.SHOW_ID=f.SHOW_ID "+sql1+" ";
		Record r1=Db.findFirst(att,1);
		Record r2=Db.findFirst(att,2);
		json2.put("Y", r1.getInt("num"));
		json2.put("N", r2.getInt("num"));			
		json2.put("code", 200);			
		renderJson(json2);
		
	}
	    
		/*
		 2018年7月5日上午9:56:46 coco   //APP看板搜索
		 */
		
		public void appSearch() {
			JSONObject json1=JsonUtil.getJSONObject(getRequest());
			JSONObject user=getSessionAttr("user");
			String loginName=user==null?json1.getString("loginName"):user.getString("loginName");
			
			String sql="(O_EXECUTOR like CONCAT('%','"+loginName+"','%') or O_CUSTOMER like CONCAT('%','"+loginName+"','%') )";			
			if(json1.getString("method").equals("medo")){
				sql="O_EXECUTOR like CONCAT('%','"+loginName+"','%')" ;				
			}else if(json1.getString("method").equals("hedo")){
				sql="O_CUSTOMER like CONCAT('%','"+loginName+"','%')";				
			}
			if(StrUtil.isNotEmpty(json1.getString("relate"))){
				sql+=" and (O_EXECUTOR like CONCAT('%','"+json1.getString("relate")+"','%') or O_CUSTOMER like CONCAT('%','"+json1.getString("relate")+"','%') )";
			}
			int type=json1.getIntValue("type");
			if(type==7){
				sql+=" and (a.o_status=3 or a.o_status=4) and LIGHTNING>0";
			}else if(type!=0){
				sql+=" and a.o_status="+type;
			}else{
				sql+=" and a.o_status!=6";
			}
			int isLook=json1.getIntValue("isLook");
			String look=isLook!=0?" and isSend="+isLook:"";
			if(StrUtil.isNotEmpty(json1.getString("keyword"))){
				if(json1.getString("keyword").equals("最新回复")){					
					sql+=" and SEND_TIME =(select MAX(SEND_TIME) from t_1do_base)";
				}else if(json1.getString("keyword").equals("查看最近一个月的任务")){
					sql+=" and date_sub(curdate(), interval 30 day) <= date(O_CREATE_TIME)";					
				}else{
					
					sql+=StrUtil.appendSql(json1.getString("keyword"));
				}
			}
			String from ="select a.SHOW_ID,a.O_DESCRIBE,a.O_CUSTOMER_NAME,a.O_CUSTOMER,a.AT,a.O_EXECUTOR,"
					    +"a.O_EXECUTOR_NAME,a.SEND_TIME,unix_timestamp(a.O_CREATE_TIME)*1000 O_CREATE_TIME,"
                        +"unix_timestamp(a.O_FINISH_TIME)*1000 O_FINISH_TIME,unix_timestamp(a.Real_FINISH_TIME)*1000 Real_FINISH_TIME"
                        +",unix_timestamp(a.DELETE_TIME)*1000 DELETE_TIME,a.O_IS_DELETED ,"
                        +" a.LIGHTNING,a.LOOKNUM,a.FBNUM,f.USER_TYPE,f.isSend ISLOOK,f.STATUS O_STATUS "
						+"from t_1do_base a LEFT JOIN (select * from t_1do_pstatus where USER_TYPE!=2 " +look+" "
						+ "and isDelete=1 and O_USER='"+loginName+"' GROUP BY SHOW_ID)f on a.SHOW_ID=f.SHOW_ID "
						+"where "+sql+look;
			
			
		    String from1=StrUtil.isNotEmpty(json1.getString("source"))? " ORDER BY O_CREATE_TIME desc LIMIT ?,? ) g ":" ORDER BY O_CREATE_TIME desc LIMIT ?,? ) g ORDER BY O_CREATE_TIME ";			
			if(type==7||isLook==2)
			from1=StrUtil.isNotEmpty(json1.getString("source"))?" ORDER BY SEND_TIME desc LIMIT ?,? ) g ":" ORDER BY SEND_TIME desc LIMIT ?,? ) g ORDER BY SEND_TIME ";
			
			List<Record> r3=Db.find("select * from ("+from+from1,json1.getIntValue("pageNumber"),json1.getIntValue("onePageNumber"));
			List<Record> r4=Db.find(from);
			JSONObject json2=new JSONObject();
			json2.put("base", r3);
			json2.put("allPage", r4.size());
			renderJson(MsgUtil.successMsg(json2));
			
		}
		
		
		/*
		 2018年7月5日上午9:56:46 coco   //看板搜索 (新)
		*/
		public void search() {
			JSONObject json1=JsonUtil.getJSONObject(getRequest());
			JSONObject user=getSessionAttr("user");
			String loginName=user==null?json1.getString("loginName"):user.getString("loginName");
			
			int type=json1.getIntValue("type");
			String sql=" o_status!=6 ";			
			if(type==7){
				sql=" (o_status=3 or o_status=4) and LIGHTNING>0";
			}else if(type!=0){
				sql=" o_status="+type;
			}
			if(json1.getString("method").equals("medo")){
				sql+=" and O_EXECUTOR like CONCAT('%','"+loginName+"','%')" ;				
			}else if(json1.getString("method").equals("hedo")){
				sql+=" and O_CUSTOMER like CONCAT('%','"+loginName+"','%')";				
			}else if(json1.getString("method").equals("all")){
			    sql+=" and (O_EXECUTOR like CONCAT('%','"+loginName+"','%') or O_CUSTOMER like CONCAT('%','"+loginName+"','%') )";				
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
				sql+=" and DATE_FORMAT(O_FINISH_TIME,'%Y-%m-%d')  BETWEEN '"+json1.getString("O_FINISH_TIME_START")+"' and '"+json1.getString("O_FINISH_TIME_END")+"'";
			}
			
			String from ="select a.*,b.type TYPE,(case a.LOOK_USER like ? when true  then 1 else 2 end )ISLOOK"
					+ " from (select SHOW_ID,O_DESCRIBE,O_EXECUTOR_NAME,O_CUSTOMER_NAME,O_START_TIME,O_FINISH_TIME,"
					+ " star,evaluation,Real_FINISH_TIME,DELETE_TIME,LOOK_USER "
					+ " from t_1do_base where "+sql+" ORDER BY ID desc)a LEFT JOIN (select * from t_1do_order "
					+ " where useraccount=? )b on a.show_id=b.show_id ORDER BY modifyTime desc LIMIT ?,10";
			List<T1doBase> t3=T1doBase.dao.find(from,"%"+loginName+"%",loginName,(json1.getIntValue("pageNumber")-1)*10);
			if(StrUtil.isNotEmpty(json1.getString("sorting"))){
			    from ="select SHOW_ID,O_DESCRIBE,O_EXECUTOR_NAME,O_CUSTOMER_NAME,O_START_TIME,O_FINISH_TIME,star,"
						+ "(case LOOK_USER like ? when true  then 1 else 2 end )ISLOOK,evaluation,Real_FINISH_TIME,"
						+ "DELETE_TIME from t_1do_base  where "+sql+"  ORDER BY "+json1.getString("sorting")+" LIMIT ?,10";
				t3=T1doBase.dao.find(from,"%"+loginName+"%",(json1.getIntValue("pageNumber")-1)*10);

			}
			String from1="select count(*) num from t_1do_base  where "+sql;
			Record r=Db.findFirst(from1);
			
			JSONObject json2=new JSONObject();
			json2.put("base", t3);
			json2.put("allPage", r.getInt("num"));
			json2.put("code", 200);		
			renderJson(json2);
			
		}
		/*
		 2018年7月5日上午9:56:46 coco   //排序
		 */
		
		public void sorting() {
			JSONObject json1=JsonUtil.getJSONObject(getRequest());
			JSONObject user=getSessionAttr("user");
			String loginName=user==null?json1.getString("loginName"):user.getString("loginName");
			//String sql="";
			int type=json1.getIntValue("type");
			String sql=type==7?"(o_status=3 or o_status=4) and LIGHTNING>0":"o_status="+type;
			if(StrUtil.isNotEmpty(json1.getString("base"))){
				sql="and O_DESCRIBE like CONCAT('%','"+json1.getString("base")+"','%')";
			}
			
			if(json1.getString("method").equals("medo")){
				sql+=" and O_EXECUTOR like CONCAT('%','"+loginName+"','%')" ;
				
			}else if(json1.getString("method").equals("hedo")){
				sql+=" and O_CUSTOMER like CONCAT('%','"+loginName+"','%')";
				
			}
			
			 if(StrUtil.isNotEmpty(json1.getString("O_CUSTOMER_NAME"))){
					sql+=" and O_CUSTOMER_NAME like CONCAT('%','"+json1.getString("O_CUSTOMER_NAME")+"','%')";
				}
				if(StrUtil.isNotEmpty(json1.getString("O_EXECUTOR_NAME"))){
					sql+=" and O_EXECUTOR_NAME like CONCAT('%','"+json1.getString("O_EXECUTOR_NAME")+"','%')";
				} 
				
			String from ="select SHOW_ID,O_DESCRIBE,O_EXECUTOR_NAME,O_CUSTOMER_NAME,O_START_TIME,O_FINISH_TIME,star,evaluation,Real_FINISH_TIME,DELETE_TIME "
					+ "from t_1do_base  where "+sql+"  ORDER BY "
					+ ""+json1.getString("sorting")+" LIMIT ?,10";
			String from1="select count(*) num  "
					+ "from t_1do_base b  where "+sql;
			List<T1doBase> t3=T1doBase.dao.find(from,(json1.getIntValue("pageNumber")-1)*10);
			Record r=Db.findFirst(from1);
			for(T1doBase t:t3){
				t.set1doIsLook(loginName);
				//t.setLIGHTNING(t.getIdoFeedbacks44().size());
			}
			JSONObject json2=new JSONObject();
			json2.put("base", t3);
			json2.put("allPage", r.getInt("num"));
			json2.put("code", 200);		
			renderJson(json2);
			
		}	    
		/*
		 2018年7月5日上午9:56:46 coco   //看板搜索 (旧)
		 */
		public void search1() {
			JSONObject json1=JsonUtil.getJSONObject(getRequest());
			JSONObject user=getSessionAttr("user");
			String loginName=user==null?json1.getString("loginName"):user.getString("loginName");
			int i=3;
			int type=json1.getIntValue("type");
			String sql=type==7?"(o_status=3 or o_status=4) and LIGHTNING>0":"o_status="+type;
			if(StrUtil.isNotEmpty(json1.getString("base"))){
				sql+=" and O_DESCRIBE like CONCAT('%','"+json1.getString("base")+"','%')";
			}
			//String sql1="";
			if(json1.getString("method").equals("medo")){
				sql+=" and O_EXECUTOR like CONCAT('%','"+loginName+"','%')" ;
				i=1;
			}else if(json1.getString("method").equals("hedo")){
				sql+=" and O_CUSTOMER like CONCAT('%','"+loginName+"','%')";
				i=2;
			}
			if(StrUtil.isNotEmpty(json1.getString("O_CUSTOMER_NAME"))){
				sql+=" and O_CUSTOMER_NAME like CONCAT('%','"+json1.getString("O_CUSTOMER_NAME")+"','%')";
			}
			if(StrUtil.isNotEmpty(json1.getString("O_EXECUTOR_NAME"))){
				sql+=" and O_EXECUTOR_NAME like CONCAT('%','"+json1.getString("O_EXECUTOR_NAME")+"','%')";
			}
			
			
			String from ="select a.*,b.type TYPE,IFNULL(c.log_type-1,2)ISLOOK"
					+ " from (select SHOW_ID,O_DESCRIBE,O_EXECUTOR_NAME,O_CUSTOMER_NAME,O_START_TIME,O_FINISH_TIME,star,evaluation,Real_FINISH_TIME,DELETE_TIME "
					+ " from t_1do_base where "+sql+" ORDER BY ID desc)a LEFT JOIN (select * from t_1do_order "
					+ " where useraccount=? and type="+i+" )b on a.show_id=b.show_id "
					+ " LEFT JOIN (SELECT SHOW_ID,log_type from t_1do_log where O_USER=? "
					+ " and log_type=2 GROUP BY SHOW_ID)c on a.SHOW_ID=c.SHOW_ID"
					+ " ORDER BY modifyTime desc LIMIT ?,10";
			
			String from1="select count(*) num from t_1do_base  where "+sql;
			List<T1doBase> t3=T1doBase.dao.find(from,loginName,loginName,(json1.getIntValue("pageNumber")-1)*10);
			Record r=Db.findFirst(from1);
			
			JSONObject json2=new JSONObject();
			json2.put("base", t3);
			json2.put("allPage", r.getInt("num"));
			json2.put("code", 200);		
			renderJson(json2);
			
		}
		
		
		
		public void login1do() {
			
			JSONObject json=JsonUtil.getJSONObject(getRequest());	
			if(StrUtil.isEmpty(json.getString("useraccount"))){
				renderJson(JsonUtil.getMap(202, "账号错误"));
				return;
			}			
			T1doFw t1doFw =T1doFw.getIdoFw(json.getString("useraccount"));
			boolean isfw=t1doFw==null?false:true;
			JSONObject json1=TRegUser.getUser(json);
			if(json1==null){
			    json1=HttpUtil.loginIm(json.getString("useraccount"));
				System.out.println(json1);
				String str=HttpUtil.getParameter1(json1, "/Base-Module/CompanyUser",json1.getString("loginName"));			
				JSONObject json3= HttpUtil.doPost2("http://xcgovapi.hzxc.gov.cn/Base-Module/CompanyUser", str);
				System.out.println(json3);
				json1.put("D_NAME", json3.get("D_NAME"));
				json1.put("U_DEPT_ID", json3.get("U_DEPT_ID"));
				TRegUser.saveUser(json1);
			}
			json1.put("isfw", isfw);
			setSessionAttr("user", json1);
			json1.put("code", 200);		
			renderJson(json1);
				
			
		}
		//设置session		
		public  JSONObject setSession(String loginName){
			/*JSONObject json=JsonUtil.getJSONObject(getRequest());	
			if(StrUtil.isEmpty(json.getString("useraccount"))){
				renderJson(JsonUtil.getMap(202, "账号错误"));
				return;
			}	*/		
			T1doFw t1doFw =T1doFw.getIdoFwForLoginName(loginName);
			boolean isfw=t1doFw==null?false:true;
			JSONObject json1=TRegUser.getUserForShowId(loginName);
			json1.put("isfw", isfw);
			json1.put("code", 200);		
			setSessionAttr("user", json1);
			return json1;
			//renderJson(json1);
		}
		//通讯录获取最近联系人
		public void GetContact() {
			JSONObject user=getSessionAttr("user");
			String str=HttpUtil.getParameter(user, "/Base-Module/CompanyUser/GetContact");
			System.out.println(str);
			String result   =   HttpUtil.doPost1("http://xcgovapi.hzxc.gov.cn/Base-Module/CompanyUser/GetContact", str);
			renderJson(result);
		}
		//通讯录获取部门和部门人员列表
		public void GetList() {
			JSONObject user=getSessionAttr("user");
			JSONObject json2=JsonUtil.getJSONObject(getRequest());
			String str=HttpUtil.getParameter(user, "/Base-Module/CompanyDept/GetList",json2.getIntValue("isContainChildDeptMember"),json2.getString("parentId"));
			String result   =   HttpUtil.doPost1("http://xcgovapi.hzxc.gov.cn/Base-Module/CompanyDept/GetList", str);		
			renderJson(result);
		}
		public void GetListUser() {
			JSONObject json2=JsonUtil.getJSONObject(getRequest());
			JSONObject user=getSessionAttr("user");
			String str=HttpUtil.getParameter1(user, "/Base-Module/CompanyUser/GetList",json2.getIntValue("isContainChildDeptMember"),json2.getString("deptId"));
			String result   =   HttpUtil.doPost1("http://xcgovapi.hzxc.gov.cn/Base-Module/CompanyUser/GetList", str);
			renderJson(result);
		}
		public void searchUser() {
			JSONObject json2=JsonUtil.getJSONObject(getRequest());
			JSONObject user=getSessionAttr("user");
			String str=HttpUtil.getParameter1(user, "/Base-Module/CompanyUser/GetList",json2.getIntValue("isContainChildDeptMember"),json2.getIntValue("createPage"),json2.getIntValue("pageSize"),json2.getString("searchKey"));
			String result   =   HttpUtil.doPost1("http://xcgovapi.hzxc.gov.cn/Base-Module/CompanyUser/GetList", str);
			renderJson(result);
		}
		
		//通讯录获取用户信息
		public void CompanyUser() {
			JSONObject json2=JsonUtil.getJSONObject(getRequest());
			JSONObject user=getSessionAttr("user");
			String str=HttpUtil.getParameter1(user, "/Base-Module/CompanyUser",json2.getString("SHOW_ID"));
			String result   =   HttpUtil.doPost1("http://xcgovapi.hzxc.gov.cn/Base-Module/CompanyUser", str);
			renderJson(result);
		}
		/*
		 2018年6月28日上午3:50:16 coco  //模拟1do登出
		 */
		public void exit1do() {
			removeSessionAttr("user");
			renderJson(JsonUtil.getMap(200, "退出成功"));
		}
		/*
		 2018年6月25日下午3:37:00 coco  //1do详情
		*/
		@Before(Tx.class)
		public void getIdoMessage() {
	  
	    
	    	JSONObject json=JsonUtil.getJSONObject(getRequest()); 	
			final T1doBase t1doBase=T1doBase.getIdoBase2(json.getString("SHOW_ID"));
			JSONObject user1=getSessionAttr("user");
			if(json.getString("loginName")==null&&user1==null){
				renderJson(JsonUtil.getMap(400, "用户未登入"));
				return;
			}else if(t1doBase==null){
				renderJson(JsonUtil.getMap(400, "工单不存在"));
				return;
			}else if(json.getString("loginName")!=null&&user1==null){
				user1=setSession(json.getString("loginName"));
				
			}
			final String loginName=user1==null?json.getString("loginName"):user1.getString("loginName");
			if((t1doBase.getOIsDeleted()==2&&StrUtil.isNotEmpty(json.getString("loginName")))||(t1doBase.getOIsDeleted()==2&&!user1.getBooleanValue("isfw"))){
			
				DbUtil.update(t1doBase.getShowId(),loginName, 5, 0,"","");
				DbUtil.update(t1doBase.getShowId(),loginName, 6, 0,"","");
				renderJson(JsonUtil.getMap(200, "该1do已删除"));
				return;
			}
			final String username=user1.getString("username");
			//修改
			int i=DbUtil.update(t1doBase.getShowId(),loginName, 5, 0,"","");	
			int j=DbUtil.update(t1doBase.getShowId(),loginName, 6, 0,"","");
			//在线已读
			DbUtil.update(t1doBase.getShowId(),loginName, 7, 0,"","");
			
			//修改创建用户
			DbUtil.update(t1doBase.getShowId(),loginName, 8, 0,username,"");			
			T1doPstatus t2=T1doPstatus.getCustomerOrExecutor(json.getString("SHOW_ID"),loginName,3);
			//查询该1do参与人是否查看过
			Record r=Db.findFirst("select * from t_1do_log a,t_1do_pstatus b where a.SHOW_ID=b.SHOW_ID and a.SHOW_ID=? and b.USER_TYPE=3 and a.O_USER=b.O_USER",json.getString("SHOW_ID"));
			new T1doLog().setShowId(json.getString("SHOW_ID")).setOUser(loginName)
			.setOUserName(username).setOpTime(new Date()).
			setLog(username+"查看此1do").setLogType(2).save();
           if(t2!=null&&r==null){
        	   t1doBase.setSendTime(new Date().getTime()).update();            
           	new Thread(new SendIdo(t1doBase,2,loginName,"",1)).start();
           } 
           new Thread(new UpdateBase(t1doBase,loginName)).start();
			
			t1doBase.put("O_STATUS", t1doBase.getOStatus());
			t1doBase.put("ccp", t1doBase.getUser(2));
			t1doBase.put("executor", t1doBase.getUser(1));
			t1doBase.put("CUSTOMER_LIST", t1doBase.getUser(3));
			t1doBase.put("EXECUTOR_LIST", t1doBase.getUser(5));
			t1doBase.put("executor", t1doBase.getUser(1));
			t1doBase.put("O_LABEL", t1doBase.getLabel());
			LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();  
			map.put("BASE", t1doBase);
			map.put("code", 200);		
			renderJson(map);
		}
		
		/*
		 2018年6月27日下午3:07:59 coco  //1do详情修改发起时间/完成时间
		*/
		 @Before(Tx.class)
		public void changeTime() {
			JSONObject json=JsonUtil.getJSONObject(getRequest());
			JSONObject user=getSessionAttr("user");
	    	T1doPstatus t1=T1doPstatus.getUser(json.getString("SHOW_ID"),user.getString("loginName"));   
			if(user.getBooleanValue("isfw")||StrUtil.getflag(t1.getUserType(), 3)){
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
			 JSONObject user=getSessionAttr("user");
			 T1doPstatus t1=T1doPstatus.getUser(json.getString("SHOW_ID"),user.getString("loginName"));
			 if(user.getBoolean("isfw")||StrUtil.getflag(t1.getUserType(), 1)){
				T1doBase t=T1doBase.getIdoBase(json.getString("SHOW_ID"));
				if(StrKit.isBlank(json.getString("content"))){
					renderJson(JsonUtil.getMap(200, "修改内容不能为空"));
					return;
				}else if(json.getString("target").equals("O_DESCRIBE")){
					if(StrKit.notBlank(t.getODescribe())){
						if(t.getODescribe().equals(json.getString("content"))){
							renderJson(JsonUtil.getMap(200, "修改内容不能与原内容相同"));
							return;
						}
					}
				}else if(json.getString("target").equals("O_TITLE")){
					if(StrKit.notBlank(t.getOTitle())){
						if(t.getOTitle().equals(json.getString("content"))){
							renderJson(JsonUtil.getMap(200, "修改标题不能与原标题相同"));
							return;
						}
					}
				}
				int i= DbUtil.update(json.getString("target"), json.getString("content"), 9, 0,json.getString("AT"),json.getString("SHOW_ID"));				
				if(i==1){
					 T1doLog.saveLog(json.getString("SHOW_ID"),user.getString("loginName"),user.getString("username"), user.getString("username")+"修改此1do", 14,new Temp(t.getODescribe(),json.getString("content")).toString());
					 if(json.getString("target").equals("O_DESCRIBE")){			 
						new Thread(new AddLabel(t.getODescribe(), json.getString("SHOW_ID"),1)).start();//批量添加标签
						StrUtil.getQTR(t);
						  
					 }
					 renderJson(JsonUtil.getMap(200, "修改成功"));
				 }else{
					 renderJson(JsonUtil.getMap(201, "修改失败"));
				 }
			}else{
				renderJson(JsonUtil.getMap(202, "权限不足"));
			}
			
			
		 }
		/*
		 2018年6月27日下午3:07:59 coco  //1do详情修改发起人/参与人/抄送人(2018.12.4需要修改重复添加删除人员是出现重复问题。)
		 */
		 @Before(Tx.class)
		public void changeUser() {
			JSONObject json=JsonUtil.getJSONObject(getRequest());
			//T1doBase t1doBase=T1doBase.getIdoBase(json.getString("SHOW_ID"));
			JSONObject user=getSessionAttr("user");
			 T1doPstatus t1=T1doPstatus.getUser(json.getString("SHOW_ID"),user.getString("loginName"));
			boolean flag=json.getString("object").equals("参与人")?true:user.getBoolean("isfw")||StrUtil.getflag(t1.getUserType(), 2);
			 if(flag){
			
						T1doBase t1doBase =T1doBase.getIdoBase(json.getString("SHOW_ID"));
						
						//T1doStatus t1doStatus=t1doBase.getIdoStatus();
						T1doPstatus t= new T1doPstatus();
						int i1=0;
						if(json.getString("object").equals("发起人")){
							i1=1;
							t1doBase.setOCustomer(StrUtil.getUser1(t1doBase.getOCustomer(), json.getString("useraccount"), json.getString("method")))
							.setOCustomerName(StrUtil.getUser1(t1doBase.getOCustomerName(), json.getString("username"), json.getString("method"))).update();
						
						}else if(json.getString("object").equals("参与人")){
							i1=3;
							t1doBase.setOExecutor(StrUtil.getUser1(t1doBase.getOExecutor(), json.getString("useraccount"), json.getString("method")))
							.setOExecutorName(StrUtil.getUser1(t1doBase.getOExecutorName(), json.getString("username"), json.getString("method"))).update();
							t.setUserType(3).setOStatus(t1doBase.getOStatus()).setSTATUS(StrUtil.getStatus2(t1doBase.getOStatus()));
								
						}else{
							i1=4;
							t1doBase.setCC(StrUtil.getUser1(t1doBase.getCC(), json.getString("useraccount"), json.getString("method")))
							.setCcName(StrUtil.getUser1(t1doBase.getCcName(), json.getString("username"),json.getString("method"))).update();
							t.setUserType(4).setOStatus(t1doBase.getOStatus()).setSTATUS(StrUtil.getStatus2(t1doBase.getOStatus()));
			
						}
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
								T1doLog.saveLog(json.getString("SHOW_ID"), user.getString("loginName"), user.getString("username"), user.getString("username").equals(ts.get(i).getOUserName())?user.getString("username")+"退出此1do":user.getString("username")+"移除"+ts.get(i).getOUserName()+"出此1do", 8,ts.get(i).getOUserName());
							
							}
							//删除用户
							DbUtil.update(s, t1doBase.getShowId(), 10, i1, "", "");
							for(int j = 0; j < temp.length; j++){
								T1doPstatus t12=T1doPstatus.dao.findFirst("select * from t_1do_pstatus where isDelete=1 and SHOW_ID=? and O_USER=? and USER_TYPE=?",t1doBase.getShowId(),temp[j],i1);
								if(t12!=null){
									continue;
								}				
								int i=DbUtil.update(t1doBase.getShowId(),temp[j],11,i1, "", "");
								 if(i==0){
									 
									t.setShowId(json.getString("SHOW_ID")).setOUser(temp[j]).setOUserName(temp1[j]).save();
				
										DoService.sendOneIdo(t1doBase,1,temp[j],temp1[j]);//单独发通知i 1加入2查看3反馈
							           
									t.remove("ID");
								 }
								T1doLog.saveLog(json.getString("SHOW_ID"), user.getString("loginName"), user.getString("username"), user.getString("username")+"邀请"+temp1[j]+"进入此1do", 9,temp1[j]);
			
							}
							
						}else if(json.getString("method").equals("remove")){
							DbUtil.update(t1doBase.getShowId(),json.getString("useraccount"),12,i1, "", "");
							T1doLog.saveLog(t1doBase.getShowId(), user.getString("loginName"), user.getString("username"), user.getString("username").equals(json.getString("username"))?user.getString("username")+"退出此1do":user.getString("username")+"移除"+json.getString("username")+"出此1do", 8,json.getString("username"));
						}else{
							String[] temp =json.getString("useraccount").split(";");
							String[] temp1 =json.getString("username").split(";");
							for (int j = 0; j < temp.length; j++) {
								//恢复被删除的用户
								int i=DbUtil.update(t1doBase.getShowId(),temp[j],13,i1, "", "");
							 if(i==0){
								t.setShowId(json.getString("SHOW_ID")).setOUser(temp[j]).setOUserName(temp1[j]).setOtherid(i1==3?2:0).save();
								T1doLog.saveLog(json.getString("SHOW_ID"), user.getString("loginName"), user.getString("username"), user.getString("username")+"邀请"+temp1[j]+"进入此1do", 9,temp1[j]);
			
									DoService.sendOneIdo(t1doBase,1,temp[j],temp1[j]);//单独发通知i 1加入2查看3反馈
								t.remove("ID");
							 }
							}
							
							
						}
						DbUtil.updateExecutore(t1doBase.getShowId());
						renderJson(JsonUtil.getMap(200, "修改成功"));
			}else{
				renderJson(JsonUtil.getMap(202, "权限不足"));
			}
		}
		//置顶
		 @Before(Tx.class)
		public void top() {
			JSONObject json=JsonUtil.getJSONObject(getRequest());
			JSONObject user=getSessionAttr("user"); 	
			T1doOrder t=json.toJavaObject(T1doOrder.class);
			T1doOrder t1=T1doOrder.getT1doOrder(json.getString("SHOW_ID"),user.getString("loginName"),json.getIntValue("type"));
			if(t1==null){
				t.setUseraccount(user.getString("loginName")).save();
				renderJson(JsonUtil.getMap(200, "置顶成功"));
			}else{
				t1.delete();
				//t1.setModifyTime(new Date()).update();
				renderJson(JsonUtil.getMap(200, "取消置顶成功"));

			}
		}
		
		
		
	    /*
		 2018年6月27日下午2:26:01 coco   //判断是否是整理层
		*/
		public void isfw() {
			JSONObject user=getSessionAttr("user"); 	
			T1doFw t1doFw =T1doFw.getIdoFw(user.getString("loginName"));
			if(t1doFw==null){
				renderJson(JsonUtil.getMap(201, false));
			}else{
				renderJson(JsonUtil.getMap(200, true));
			}
			
		}
	    
		/*
		 2018年6月26日下午11:20:20 coco  //获得操作日志
		 */
		public void getLog() {
			JSONObject json=JsonUtil.getJSONObject(getRequest()); 	
			T1doBase t1doBase =json.toJavaObject(T1doBase.class);
			List<T1doLog> t1doLogs=t1doBase.getIdoLogs1();
			renderJson(t1doLogs); 
		}
	    /*
		 2018年6月26日下午10:50:37 coco  //获得反馈消息。
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
		  //  List<T1doFeedback> getIdoFeedbacks11= T1doFeedback.dao.find("select  O_USER_NAME from t_1do_feedback where SHOW_ID=? and FB_TYPE!=4",json.getString("SHOW_ID"));
		   // int count=T1doFeedback.getNum(json.getString("SHOW_ID"));
		    setSessionAttr(json.getString("SHOW_ID"), T1doFeedback.getNum(json.getString("SHOW_ID")));
			renderJson(t1doFeedbacks);
		}
		/*
		 2018年6月26日下午10:50:37 coco  //获得反馈消息。（城市大脑）
		 */
		public void getFeedbacks() { 
			JSONObject json=JsonUtil.getJSONObject(getRequest()); 	
			T1doBase t1doBase =json.toJavaObject(T1doBase.class);
			List<T1doFeedback> t1doFeedbacks=t1doBase.getIdoFeedbacks1(json.getIntValue("id"),json.getIntValue("num"));
			renderJson(t1doFeedbacks);
		}
		/*
		 2018年7月1日下午8:34:11 coco  //轮询
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
				     getIdoFeedbacks12=T1doFeedback.dao.find("select ID,O_USER_NAME,TIME_STAMP,FB_TIME,O_USER,FBCONTENT,FB_TYPE,FB_USER_NAME,FB_USER,ATTR_PATH,star,AT from t_1do_feedback where SHOW_ID=? and FB_TYPE!=4  and isoverdue=1 LIMIT ?,?",str,i,n);;
				   
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
		public void closeIdo() {
			JSONObject json=JsonUtil.getJSONObject(getRequest()); 	
			JSONObject user=getSessionAttr("user");
			DbUtil.update(json.getString("SHOW_ID"),user.getString("loginName"), 14, 0, "", "");
			renderJson(JsonUtil.getMap(200, "下线成功"));
	
		}
	    
		//附件反馈

		@Before(Tx.class)
		public void feedbackUpload() throws IOException {
			MultipartParser mp = new MultipartParser(this.getRequest(),2147483647, false, false,"UTF-8");//52428800=50*1024*1024    最大2147483647
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
		
			JSONObject user=getSessionAttr("user");
			//JSONObject json1=JSON.parseObject("{\"useraccount\": \"fangshengqun\",\"username\": \"coco\"}");
			T1doFeedback t1doFeedback =json.toJavaObject(T1doFeedback.class);
			t1doFeedback.setFbTime(new Date()).setTimeStamp(new Date().getTime()).setOUser(user.getString("loginName")).setOUserName(user.getString("username"));
			T1doAttr t1doAttr=t1doFeedback.getIdoAttr();
			//String attrId="";
			String fn="";
			//String ATTR_PATH="";
			//for(HashMap<String,String> uploadFile:uploadFiles){
		    for (int i = 0; i < uploadFiles.size(); i++) {
		
	
				
				t1doAttr.setAttrPath(UrlUtil.attrUrl+uploadFiles.get(i));
				t1doAttr.setAttrName(uploadFiles1.get(i));
				fn+=uploadFiles1.get(i);
				//ATTR_PATH+="UrlUtil.attrUrl"+uploadFiles.get(i);
				t1doAttr.save();
				//t1doAttr.setAttrOrder(t1doAttr.getID()).update();
				//attrId+=t1doAttr.getID();
				t1doFeedback.setATTRID(t1doAttr.getID()+"").setAttrPath(t1doAttr.getAttrPath()).setFBCONTENT(t1doAttr.getAttrName()).save();
				t1doAttr.remove("ID");
				t1doFeedback.remove("ID");
				T1doLog.saveLog(t1doFeedback.getShowId(), user.getString("loginName"), user.getString("username"), user.getString("username")+"上传"+uploadFiles1.get(i), 3, uploadFiles1.get(i));
				
		    }
			//t1doFeedback.setATTRID(attrId).setAttrPath(ATTR_PATH).setFBCONTENT(fn).save();
			//T1doLog t1doLog=t1doFeedback.getIdoLog();
			if(t1doFeedback.getFbType()==2){
				t1doFeedback.setFBCONTENT("回复"+t1doFeedback.getFbUserName()+" "+fn).update();
			}
			feedback1(t1doFeedback,user);
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					new WebSocketController().sendMessage(t1doFeedback.getShowId());
					
				}
			}).start();
			//t1doLog.save();
			renderJson(MsgUtil.successMsg(t1doFeedback));
			//renderJson(JsonUtil.getMap(200, "反馈成功"));
		}
		
		public static void feedback1(final T1doFeedback t1doFeedback,final JSONObject user) {
			//JSONObject json=JsonUtil.getJSONObject(getRequest());
			//final JSONObject user=getSessionAttr("user");
			//final T1doFeedback t1doFeedback =json.toJavaObject(T1doFeedback.class);
			//查询是否是参与人
			final T1doBase t1doBase=t1doFeedback.getT1doBase();
			T1doPstatus t1doPstatus=T1doPstatus.getCustomerOrExecutor(t1doFeedback.getShowId(),user.getString("loginName"),3);
			//T1doStatus t1doStatus=t1doFeedback.getIdoStatus();
			//T1doPstatus t1=T1doPstatus.getUser(t1doFeedback.getShowId(),user.getString("loginName"));
			//t1doFeedback.save();
				if(t1doPstatus!=null&&t1doBase.getOStatus()==3){
					t1doBase.setOStatus(4).update();
					//t1doPstatus.setOStatus(4).update();
					t1doBase.setSendTime(new Date().getTime()).setFBNUM(t1doBase.getFBNUM()+1).update();
					DbUtil.update(t1doFeedback.getShowId(),user.getString("loginName"), 15, 0, "", "");
					
		           	new Thread(new SendIdo(t1doBase,3,user.getString("loginName"),"",1)).start();
				}else{
					t1doBase.setSendTime(new Date().getTime()).setFBNUM(t1doBase.getFBNUM()+1).update();
					DbUtil.update(t1doFeedback.getShowId(),user.getString("loginName"), 16, 0, "", "");
					final int temp=t1doBase.getOStatus()==3?1:t1doBase.getOStatus();									
		           	new Thread(new SendIdo(t1doBase,temp,user.getString("loginName"),"",1)).start();

				}

				//renderJson(MsgUtil.successMsg(t1doFeedback));
			
			
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
		//批量删除
		@Before(Tx.class)
		public void deleteAlldo(){
			//JSONObject json=JsonUtil.getJSONObject(getRequest()); 	
			JSONArray doList=JsonUtil.getJSONObject(getRequest()).getJSONArray("list");
			JSONObject user=getSessionAttr("user");
			String loginName=user.getString("loginName");
			String username=user.getString("username");
		    for (int j = 0; j < doList.size(); j++) {
				String showID=doList.getString(j);
			T1doPstatus t1=T1doPstatus.getUser(showID,loginName);
			final T1doBase t1doBase=T1doBase.getIdoBase(showID);
			
				if(user.getBoolean("isfw")||StrUtil.getflag(t1.getUserType(), 9)){
					//T1doStatus t=T1doStatus.dao.findFirst("select * from t_1do_status where SHOW_ID=?",showID);
					if(t1doBase.getOStatus()==5){
						int i=Db.update("update t_1do_base set O_IS_DELETED=2 ,DELETE_TIME=now(),O_STATUS=6 where SHOW_ID=?",showID);	  
						if(i==1){
							T1doLog.saveLog(showID, loginName, username, username+"删除此1do", 12,"");	
				        	   t1doBase.setSendTime(new Date().getTime()).update();
							
				           	new Thread(new SendIdo(t1doBase,7,loginName,"",1)).start();
				           	
				           	Db.update("update t_1do_relation set TYPE=6 where  RELATION_SHOW_ID=?",showID);
				           	Db.update("update t_1do_pstatus set O_STATUS=6,STATUS='已删除' where SHOW_ID=?",showID);
							System.out.println("删除成功");
						}else{
							System.out.println("删除失败");
						}
					}else{
						renderJson(JsonUtil.getMap(202, "任务进行中不能删除"));
						return;
					}
					
				}else{
					System.out.println("权限不足");

					renderJson(JsonUtil.getMap(202, "权限不足"));
					return;
				}
		     }
			renderJson(JsonUtil.getMap(200, "删除成功"));
			}
		//删除或恢复或者重做
		@Before(Tx.class)
		public void deleteIdoOrRestoreIdoOrRedo(){
			JSONObject json=JsonUtil.getJSONObject(getRequest()); 	
			 JSONObject user=getSessionAttr("user");
			 T1doPstatus t1=T1doPstatus.getUser(json.getString("SHOW_ID"),user.getString("loginName"));
				final T1doBase t1doBase=T1doBase.getIdoBase(json.getString("SHOW_ID"));
			if(json.getString("result").equals("delete")){//删除
			 if(user.getBoolean("isfw")||StrUtil.getflag(t1.getUserType(), 9)){
				//T1doStatus t=T1doStatus.dao.findFirst("select * from t_1do_status where SHOW_ID=?",json.getString("SHOW_ID"));
				if(t1doBase.getOStatus()==5){
					//修改
					Db.update("update t_1do_pstatus set isSend=2 where SHOW_ID=? and O_USER=? and isDelete=1 and USER_TYPE!=2 and (online=2 or gmt_modified<CURDATE())",t1doBase.getShowId(),user.getString("loginName"));
					Db.update("update t_1do_fwpstatus set isSend=2 where SHOW_ID=? and O_USER=? and (online=2 or gmt_modified<CURDATE())",t1doBase.getShowId(),user.getString("loginName"));
					int i=Db.update("update t_1do_base set O_IS_DELETED=2 ,DELETE_TIME=now(),O_STATUS=6 where SHOW_ID=?",json.getString("SHOW_ID"));	  
					if(i==1){
						T1doLog.saveLog(json.getString("SHOW_ID"), user.getString("loginName"), user.getString("username"), user.getString("username")+"删除此1do", 12,"");
			        	   t1doBase.setSendTime(new Date().getTime()).update();
						 
			           	new Thread(new SendIdo(t1doBase,7,user.getString("loginName"),"",1)).start();
			           	Db.update("update t_1do_pstatus set O_STATUS=6,STATUS='已删除' where SHOW_ID=?",json.getString("SHOW_ID"));
						Db.update("update t_1do_relation set TYPE=6 where  RELATION_SHOW_ID=?",json.getString("SHOW_ID"));
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
			if(user.getBoolean("isfw")){
			int i=Db.update("update t_1do_base set O_IS_DELETED=1 ,DELETE_TIME=null,O_STATUS=5 where SHOW_ID=?",json.getString("SHOW_ID"));
			if(i==1){
				T1doLog.saveLog(json.getString("SHOW_ID"), user.getString("loginName"), user.getString("username"), user.getString("username")+"恢复此1do", 13,"");
	        	   t1doBase.setSendTime(new Date().getTime()).update();
				 
	           	new Thread(new SendIdo(t1doBase,8,user.getString("loginName"),"",1)).start();
	           	Db.update("update t_1do_pstatus set O_STATUS=5,STATUS='已完成' where SHOW_ID=?",json.getString("SHOW_ID"));
				Db.update("update t_1do_relation set TYPE=5 where  RELATION_SHOW_ID=?",json.getString("SHOW_ID"));
				renderJson(JsonUtil.getMap(200, "恢复成功"));
			  }else{
				renderJson(JsonUtil.getMap(201, "恢复失败"));
			  }
			}else{
				renderJson(JsonUtil.getMap(202, "权限不足"));
			}
		}else{//重做
			if(user.getBoolean("isfw")){
			Db.update("update t_1do_base set O_IS_DELETED=1 ,SEND_TIME=?,O_STATUS=3,LOOKNUM=0,FBNUM=0,LIGHTNING=0 where SHOW_ID=?",new Date().getTime(),json.getString("SHOW_ID"));	
			Db.update("update t_1do_pstatus set O_STATUS=USER_TYPE,isSend=2 where SHOW_ID=?",json.getString("SHOW_ID"));
			//Db.update("update t_1do_status set O_STATUS=3 where SHOW_ID=?",json.getString("SHOW_ID"));
			Db.update("update t_1do_feedback set isoverdue=2 where SHOW_ID=?",json.getString("SHOW_ID"));
			Db.update("update t_1do_log set isoverdue=2 where SHOW_ID=?",json.getString("SHOW_ID"));
			Db.update("update t_1do_fwpstatus set isSend=2 where SHOW_ID=? ",t1doBase.getShowId());	
			T1doLog.saveLog(json.getString("SHOW_ID"), user.getString("loginName"), user.getString("username"), user.getString("username")+"要求重做此1do", 15,"");
			 
           	new Thread(new SendIdo(t1doBase,1,user.getString("loginName"),"",1)).start();
           	Db.update("update t_1do_pstatus set O_STATUS=1,STATUS='已送达' where SHOW_ID=? and USER_TYPE=1",json.getString("SHOW_ID"));
           	Db.update("update t_1do_pstatus set O_STATUS=3,STATUS='待接单' where SHOW_ID=? and USER_TYPE=3",json.getString("SHOW_ID"));
			Db.update("update t_1do_relation set TYPE=3 where  RELATION_SHOW_ID=?",json.getString("SHOW_ID"));
			renderJson(JsonUtil.getMap(200, "该1do已进入重做流程"));
			}else{
				renderJson(JsonUtil.getMap(202, "权限不足"));
			}
		}
			
		}
		//附件单独上传		
		@Before(Tx.class)
		public void upload() throws IOException {
			MultipartParser mp = new MultipartParser(this.getRequest(), 2147483647, false, false,"UTF-8");//52428800=50*1024*1024
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
			JSONObject user=getSessionAttr("user");
			T1doAttr t1doAttr=new T1doAttr().setShowId(json.getString("SHOW_ID")).setUploadUser(user.getString("loginName")).setUploadTime(new Date())
					.setIsFb(1);
			//String attrId="";
			String fn="";
			List<T1doAttr> list = new ArrayList<T1doAttr>();
			for (int i = 0; i < uploadFiles.size(); i++) {
			
				t1doAttr.setAttrPath(UrlUtil.attrUrl+uploadFiles.get(i));
				t1doAttr.setAttrName(uploadFiles1.get(i));
				fn+=uploadFiles1.get(i)+" ";
				t1doAttr.save();
				//t1doAttr.setAttrOrder(t1doAttr.getID()).update();
				list.add(t1doAttr);
				//attrId+=t1doAttr.getID()+";";
				t1doAttr.remove("ID");
				T1doLog.saveLog(json.getString("SHOW_ID"),user.getString("loginName"),user.getString("username"),t1doAttr.getUploadUserName()+"上传"+fn, 3, fn); 	
			}
		
			/*T1doLog t1doLog=new T1doLog().setShowId(json.getString("SHOW_ID")).setOUser(user.getString("loginName")).setOUserName(user.getString("username"))
					.setOpTime(new Date()).setLogType(3).setLog(user.getString("username")+"上传"+fn);
			t1doLog.save();*/

			renderJson(list);
		}
		 
		
		/*
		 2018年6月26日上午10:17:23 coco  新建1do保存1
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
						File t1 = new File("D:\\1do\\upload\\");//设置本地上传文件对象（并重命名）
						File t = new File(t1,fileName);//设置本地上传文件对象（并重命名）
						FileOutputStream out = new FileOutputStream(t);
		        		Streams.copy(filePart.getInputStream(), out, true);
		        		uploadFiles.add(fileName);
		        		uploadFiles1.add(filePart.getFileName());
					}
				}
			JSONObject user=getSessionAttr("user");
			if(user==null){
				if(json.toString().contains("loginName")){					
					user=setSession(json.getString("loginName"));
				}else{
					renderJson(MsgUtil.errorMsg("用户未登入"));
					return;
				}
			}
			final T1doBase t1doBase =json.toJavaObject(T1doBase.class);
			String showId=IDUtil.getUid();
			t1doBase.saveBase(user, showId, uploadFiles.size());
			t1doBase.savefw();//保存整理层为查看通知做准备。
			T1doPstatus.saveIdoPstatus2(t1doBase);
			StrUtil.getQTR(t1doBase);//设置处理人和抄送人
			T1doAttr t1doAttr=t1doBase.newIdoAttr();	
			T1doLog.saveLog(t1doBase.getShowId(), t1doBase.getCreateUser(), t1doBase.getCreateUserName(), t1doBase.getCreateUserName()+"创建了此1do", 1, "");
			Long time=new Date().getTime();
			for (int i = 0; i < uploadFiles.size(); i++) {		
			    t1doAttr.setAttrPath(UrlUtil.attrUrl+uploadFiles.get(i)).setAttrName(uploadFiles1.get(i)).save();
				new T1doFeedback().setShowId(t1doBase.getShowId()).setOUser(t1doAttr.getUploadUser())
				.setOUserName(t1doAttr.getUploadUserName()).setFbTime(t1doAttr.getUploadTime())
				.setFbType(3).setATTRID(t1doAttr.getID()+"").setAttrPath(t1doAttr.getAttrPath()).setTimeStamp(time+i).setFBCONTENT(t1doAttr.getAttrName()).save();
				
				t1doAttr.remove("ID");
				T1doLog.saveLog(t1doBase.getShowId(),t1doAttr.getUploadUser(), t1doAttr.getUploadUserName(), t1doAttr.getUploadUserName()+"上传"+t1doAttr.getAttrName(), 3, t1doAttr.getAttrName()); 	
			}			
	        new Thread(new SendIdo(t1doBase,1,user.getString("loginName"),"",1)).start();
	        new Thread(new AddLabel(t1doBase.getODescribe(), showId,2)).start();//批量添加标签
	      
			renderJson(JsonUtil.getMap(200, "创建1do成功！"));
		}
		/*
		 2018年6月26日上午10:17:23 coco  新建1do保存1
		 */
		@Before(Tx.class)
		public void newIdo() throws IOException {
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
					File t1 = new File("D:\\1do\\upload\\");//设置本地上传文件对象（并重命名）
					File t = new File(t1,fileName);//设置本地上传文件对象（并重命名）
					FileOutputStream out = new FileOutputStream(t);
					Streams.copy(filePart.getInputStream(), out, true);
					uploadFiles.add(fileName);
					uploadFiles1.add(filePart.getFileName());
				}
			}
			JSONObject user=setSession(json.getString("loginName"));				
			final T1doBase t1doBase =json.toJavaObject(T1doBase.class);
			String showId=IDUtil.getUid();
			t1doBase.saveBase(user, showId, uploadFiles.size());
			t1doBase.savefw();//保存整理层为查看通知做准备。
			T1doPstatus.saveIdoPstatus2(t1doBase);
			StrUtil.getQTR(t1doBase);//设置处理人和抄送人
			T1doAttr t1doAttr=t1doBase.newIdoAttr();	
			T1doLog.saveLog(t1doBase.getShowId(), t1doBase.getCreateUser(), t1doBase.getCreateUserName(), t1doBase.getCreateUserName()+"创建了此1do", 1, "");
			Long time=new Date().getTime();
			for (int i = 0; i < uploadFiles.size(); i++) {		
				t1doAttr.setAttrPath(UrlUtil.attrUrl+uploadFiles.get(i)).setAttrName(uploadFiles1.get(i)).save();
				new T1doFeedback().setShowId(t1doBase.getShowId()).setOUser(t1doAttr.getUploadUser())
				.setOUserName(t1doAttr.getUploadUserName()).setFbTime(t1doAttr.getUploadTime())
				.setFbType(3).setATTRID(t1doAttr.getID()+"").setAttrPath(t1doAttr.getAttrPath()).setTimeStamp(time+i).setFBCONTENT(t1doAttr.getAttrName()).save();
				
				t1doAttr.remove("ID");
				T1doLog.saveLog(t1doBase.getShowId(),t1doAttr.getUploadUser(), t1doAttr.getUploadUserName(), t1doAttr.getUploadUserName()+"上传"+t1doAttr.getAttrName(), 3, t1doAttr.getAttrName()); 	
			}			
			new Thread(new SendIdo(t1doBase,1,user.getString("loginName"),"",1)).start();
			new Thread(new AddLabel(t1doBase.getODescribe(), showId,2)).start();//批量添加标签
			
			renderJson(JsonUtil.getMap(200, "创建1do成功！"));
		}

		/*
		 2018年6月25日下午3:59:19 coco  //普通反馈 （新）2019.1.30
		 */
		@Before(Tx.class)
		public void feedback() {
			JSONObject json=JsonUtil.getJSONObject(getRequest());
			 JSONObject user1=getSessionAttr("user");
			if(json.getString("loginName")==null&&user1==null){
				renderJson(JsonUtil.getMap(400, "用户未登入"));
				return;
			}else if(json.getString("loginName")!=null&&user1==null){
				user1=setSession(json.getString("loginName"));				
			}
			final JSONObject user=user1;
			final T1doFeedback t1doFeedback =json.toJavaObject(T1doFeedback.class);
			//查询是否是参与人
			T1doPstatus t1doPstatus=T1doPstatus.getCustomerOrExecutor(t1doFeedback.getShowId(),user.getString("loginName"),3);
			//T1doStatus t1doStatus=t1doFeedback.getIdoStatus();
			final T1doBase t1doBase=t1doFeedback.getT1doBase();
			t1doFeedback.setFbTime(new Date()).setTimeStamp(new Date().getTime()).setOUser(user.getString("loginName")).setOUserName(user.getString("username"));
			T1doLog t1doLog=json.toJavaObject(T1doLog.class);
			t1doLog.setOpTime(t1doFeedback.getFbTime()).setOUser(user.getString("loginName")).setOUserName(user.getString("username"));	
			T1doPstatus t1=T1doPstatus.getUser(json.getString("SHOW_ID"),user.getString("loginName"));
			if(t1doFeedback.getFbType()==1||t1doFeedback.getFbType()==2){
				if(StrUtil.isNotEmpty(t1doFeedback.getAT())&&
					t1doFeedback.getAT().charAt(t1doFeedback.getAT().length()-2)==','){
					renderJson(MsgUtil.errorMsg("AT参数错误"));
					return;
				}
				t1doFeedback.save();
				if(t1doPstatus!=null){
					//主动办
					new Thread(new SendIdo(t1doBase,4,t1doFeedback.getFBCONTENT())).start();
					//三实库
					new Thread(new SendIdo(t1doBase,5,t1doFeedback.getFBCONTENT())).start();
				}
				
				if(t1doPstatus!=null&&t1doBase.getOStatus()==3){
					//第一个参与人反馈修改工单状态
					//t1doBase.update();
					//t1doPstatus.setOStatus(4).setSTATUS("已接单").update();
					t1doBase.setOStatus(4).setSendTime(new Date().getTime()).setFBNUM(t1doBase.getFBNUM()+1).update();	
					DbUtil.update(t1doFeedback.getShowId(),user.getString("loginName"), 15, 0, "", ""); 
		           	new Thread(new SendIdo(t1doBase,3,user.getString("loginName"),"",1)).start();
		           	
				}else{
					t1doBase.setSendTime(new Date().getTime()).setFBNUM(t1doBase.getFBNUM()+1).update();
					final int temp=t1doBase.getOStatus()==3?1:t1doBase.getOStatus();
					//反馈设置用户未读
					DbUtil.update(t1doFeedback.getShowId(),user.getString("loginName"), 16, 0, "", ""); 
		           	new Thread(new SendIdo(t1doBase,temp,user.getString("loginName"),"",1)).start();

		           	
				}
				
				//String str=user.getString("username")+"反馈:"+t1doFeedback.getFBCONTENT();
				t1doLog.setLogType(10).setLog(user.getString("username")+"反馈一条信息").save();
				new Thread( new Runnable() {	
					@Override
					public void run() {	
						new WebSocketController().sendMessage(t1doBase.getShowId());
					}				
				}).start();
				new Thread( new Runnable() {	
					@Override
					public void run() {	
						T1doLabelFeedback.savelabel(t1doFeedback);
					}				
				}).start();
				//renderJson(JsonUtil.getMap(200, "反馈成功"));
				renderJson(MsgUtil.successMsg(t1doFeedback));
				return;
			}else{
				t1doLog.setLogType(t1doFeedback.getFbType());
				String str=t1doLog.getOUserName();
				if(t1doLog.getLogType()==4){
					if(user.getBooleanValue("isfw")||StrUtil.getflag(t1.getUserType(), 6)){
						
						if(t1doBase.getLIGHTNING()+1>5){
							t1doFeedback.setShortMessage(ShortMessageUtil.sendShortMessage(t1doBase.getShowId(),t1doBase.getODescribe().length()>30?t1doBase.getODescribe().substring(0,30):t1doBase.getODescribe(),t1doFeedback.getOUserName()));
						}
						t1doBase.setSendTime(new Date().getTime()).setLIGHTNING(t1doBase.getLIGHTNING()+1).update();
						str+="催办此1do";						
						t1doFeedback.setFBCONTENT(str).save();
						Db.update("update t_1do_pstatus set isSend=2,urge_isLook=0 where SHOW_ID=? and isDelete=1 ",t1doBase.getShowId());//and (online=2 or gmt_modified<CURDATE())
						//Db.update("update t_1do_pstatus set urge_isLook=0 where SHOW_ID=?",t1doBase.getShowId());
						Db.update("update t_1do_base b,(select SHOW_ID,GROUP_CONCAT(O_USER_NAME) O_USER_NAME,GROUP_CONCAT(O_USER) O_USER from(select SHOW_ID,O_USER_NAME,O_USER from t_1do_feedback where fb_type=4 group by SHOW_ID,O_USER_NAME)a group by SHOW_ID) c" 
                        +" set b.URGENAME=c.O_USER_NAME,b.URGESHOWID=c.O_USER where b.SHOW_ID=c.SHOW_ID and b.SHOW_ID=?",t1doBase.getShowId());
						new Thread( new Runnable() {	
							@Override
							public void run() {	
						           new UrgeController().sendMessage(t1doBase.getShowId());//发送催报给连接websoct的客户端						 
							}				
						}).start();
						new Thread(new SendIdo(t1doBase,4,user.getString("loginName"),"",1)).start();

					}else{
						//renderJson(JsonUtil.getMap(202, "权限不足"));
						renderJson(MsgUtil.errorMsg("权限不足"));
						return;
					}
					
				}else if(t1doLog.getLogType()==5){
					if(user.getBoolean("isfw")||StrUtil.getflag(t1.getUserType(), 7)){
						
						//int i=Db.update("update t_1do_status set O_STATUS=5 where SHOW_ID=?",t1doFeedback.getShowId());
						if(t1doBase.getOStatus()>=5){
							renderJson(JsonUtil.getMap(200, "该1do已经办结"));
							return;
						}
						Db.update("update t_1do_pstatus set O_STATUS=5,STATUS='已完成'  where SHOW_ID=? ",t1doFeedback.getShowId());
						Db.update("update t_1do_pstatus set isSend=2 where SHOW_ID=? and (online=2 or gmt_modified<CURDATE())",t1doFeedback.getShowId());
						Db.update("update t_1do_fwpstatus set isSend=2 where SHOW_ID=? and (online=2 or gmt_modified<CURDATE())",t1doBase.getShowId());
						str+="确认办结";
						t1doFeedback.setFBCONTENT("确认办结").save();
						t1doBase.setRealFinishTime(new Date()).setSendTime(new Date().getTime()).setFBNUM(t1doBase.getFBNUM()+1).setOStatus(5).update();						
						 
			           	new Thread(new SendIdo(t1doBase,5,user.getString("loginName"),"",1)).start();
					}else{
						//renderJson(JsonUtil.getMap(202, "权限不足"));
						renderJson(MsgUtil.errorMsg("权限不足"));
						
						return;
					}
					
				}else if(t1doLog.getLogType()==6){
					if(user.getBoolean("isfw")||StrUtil.getflag(t1.getUserType(),8)){
						t1doFeedback.setFBCONTENT("评价："+t1doFeedback.getFBCONTENT()).save();
						str+="评价了此1do";
						t1doBase.setStar(t1doFeedback.getStar()).setEvaluation(t1doFeedback.getFBCONTENT()).setSendTime(new Date().getTime()).setFBNUM(t1doBase.getFBNUM()+1).update();
						
						new Thread( new Runnable() {	
							@Override
							public void run() {	
								T1doLabelFeedback.savelabel(t1doFeedback);
							}				
						}).start();
			           	new Thread(new SendIdo(t1doBase,6,user.getString("loginName"),"",1)).start();

					}else{
						renderJson(MsgUtil.errorMsg("权限不足"));
						
						//renderJson(JsonUtil.getMap(202, "权限不足"));
						return;
					}
				}		
				t1doLog.setLog(str).save();
				//t1doLog.save();
				
				new Thread( new Runnable() {	
					@Override
					public void run() {	
				//renderJson(JsonUtil.getMap(200, "反馈成功"));
				new WebSocketController().sendMessage(t1doBase.getShowId());//发送反馈给连接websoct的客户端.
			    }				
		        }).start();
				renderJson(MsgUtil.successMsg(t1doFeedback));
				return;
			}
			
		}
		
		
		/*
		 2018年6月25日下午3:59:19 coco  //删除反馈
		 */
		@Before(Tx.class)
		public void deleteFeedback() {
			JSONObject json=JsonUtil.getJSONObject(getRequest());
			JSONObject user=getSessionAttr("user");
			T1doFeedback t=T1doFeedback.dao.findById(json.getIntValue("ID"));
			//int i=Db.update("update t_1do_feedback set FB_TYPE=7 where ID=? and O_USER=?",json.getIntValue("ID"),json1.getString("loginName"));
		    if(t.getOUser().equals(user.getString("loginName"))){
		    	T1doLog.saveLog(t.getShowId(), user.getString("loginName"), user.getString("username"), t.getID()+"被删除", 11,"");
			    t.setFbType(7).update();
			    t.setFbType(8).remove("ID").save();
		    	renderJson(JsonUtil.getMap(200, "删除反馈成功"));
			}else{
				renderJson(JsonUtil.getMap(201, "删除反馈不成功"));

			}
			
		}
		
	   
		
	
        /*
		 2018年6月21日 coco //创建保存1do（1call转1do时）
		*/
	    @Before(Tx.class)
		public void createIdo() {
			JSONObject json=JsonUtil.getJSONObject(getRequest());
			log.error(json.toString());
			System.out.println(json.toString());
			final T1doBase t1doBase =json.getObject("BASE", T1doBase.class);
			JSONArray arr=json.getJSONArray("ATTR");
			String showId=IDUtil.getUid();
			if(StrKit.isBlank(t1doBase.getCreateUser())){
				t1doBase.setCreateUser("1call").setCreateUserName("1call");
			}
			t1doBase.saveBase(showId, arr==null?0:arr.size());
			t1doBase.savefw();//保存整理层为查看通知做准备。
			log.error(t1doBase.getShowId());
			if(arr!=null){
				Long time=new Date().getTime();
			for (int i = 0; i < arr.size(); i++) {
				T1doAttr t1doAttr=arr.getObject(i, T1doAttr.class);
				t1doAttr.setShowId(t1doBase.getShowId());
				t1doAttr.save();
				new T1doFeedback().setShowId(t1doBase.getShowId()).setOUser(t1doAttr.getUploadUser())
				.setOUserName(t1doAttr.getUploadUserName()).setFbTime(t1doAttr.getUploadTime()).setTimeStamp(time+i)
				.setFbType(t1doBase.getSOURCE()==2?10:3).setATTRID(t1doAttr.getID()+"").setAttrPath(t1doAttr.getAttrPath()).setFBCONTENT(t1doAttr.getAttrName()).save();
				T1doLog.saveLog(t1doBase.getShowId(),t1doAttr.getUploadUser(), t1doAttr.getUploadUserName(), t1doAttr.getUploadUserName()+"上传"+t1doAttr.getAttrName(), 3, t1doAttr.getAttrName()); 	
			}}
			
			new Thread(new AddLabel(t1doBase.getODescribe(), showId,2)).start();//批量添加标签
			T1doPstatus.saveIdoPstatus2(t1doBase);
			StrUtil.getQTR(t1doBase);//设置处理人和抄送人
			T1doLog.saveLog(t1doBase.getShowId(), t1doBase.getCreateUser(), t1doBase.getCreateUserName(), t1doBase.getCreateUserName()+"创建了此1do", 1, "");
			
           	new Thread(new SendIdo(t1doBase,1,t1doBase.getCreateUser(),"",1)).start();
           
			renderJson(JsonUtil.getMap(200, t1doBase.getShowId()));
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
	     
	      
		

}
