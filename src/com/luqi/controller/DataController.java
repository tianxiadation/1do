 package com.luqi.controller;



import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.luqi.common.model.Notice;
import com.luqi.common.model.T1doBase;
import com.luqi.common.model.T1doFeedback;
import com.luqi.common.model.T1doLog;
import com.luqi.common.model.T1doPstatus;
import com.luqi.common.model.T1doUser;
import com.luqi.common.model.T1doYscg;
import com.luqi.common.model.T1doYscgUser;
import com.luqi.common.model.Temp;
import com.luqi.util.HttpUtil;
import com.luqi.util.MsgUtil;
import com.luqi.util.StrUtil;
import com.luqi.util.TimeUtil;
import com.luqi.util.UrlUtil;

/*
 2018年11月2日 coco 注解：处理各种数据库数据
*/

public class DataController extends Controller {
	/**   
	　* 描述：   
	　* 创建人：coco   
	　* 创建时间：2020年7月1日 下午3:57:28         
	*/
	public void eee() {
		List<T1doBase> list=T1doBase.dao.find("select * from t_1do_base where SOURCE=11 and O_STATUS=5");
		for(T1doBase t:list) {
				String re=HttpUtil.doPost1(UrlUtil.cg_update+"?taskNo="+t.getAPARAMETER(), "{\"taskNo\":\""+t.getAPARAMETER()+"\"}");
				new Notice().setTest(UrlUtil.cg_update+"?taskNo="+t.getAPARAMETER()).setResult(re).save();
		}
		renderJson(MsgUtil.successMsg("数据处理成功"));
	}
	
	/**   
	　* 描述：   
	　* 创建人：coco   
	　* 创建时间：2020年6月19日 上午10:33:37         
	*/
	public void dd() {
		//List<T1doBase> list=T1doBase.dao.find("select * from t_1do_base where source=11 and o_status=3  and O_DESCRIBE like '%\"rectificationStatus\":\"1\"%'");//
		List<T1doBase> list=T1doBase.dao.find("select * from t_1do_base where id in(5928,5929)");//
		for (T1doBase t : list) {
			String re=HttpUtil.doPost1(UrlUtil.cg_update+"?taskNo="+t.getAPARAMETER(), "{\"taskNo\":\""+t.getAPARAMETER()+"\"}");
			new Notice().setTest(UrlUtil.cg_update+"?taskNo="+t.getAPARAMETER()).setResult(re).save();
		}
		renderJson(MsgUtil.successMsg(1111));
	}
	
	
	/**   
	　* 描述：   
	　* 创建人：coco   
	　* 创建时间：2020年6月15日 下午2:01:51         
	*/
	public void n() {
		List<Notice> list=Notice.dao.find("SELECT * FROM `notice` where test like '%streetName%' and id>20091 and test not like '%ids%'");
		String result = null;
		for (Notice notice : list) {
			JSONObject json=JSON.parseObject(notice.getTest());
			json.remove("id");
			//json.put("taskNo", t.getShowId());
			//json.put("problemSource",T1doYscg.getT1doYscg(json.getString("problemSource"), 2));
			//json.put("problemType",T1doYscg.getT1doYscg(json.getString("problemType").split("-")[0], 1));
			//json.put("sitePhotos",json.getString("sitePhotos"));
			json.put("reportingTime",json.getString("reportingTime").length()==10?json.getString("reportingTime")+" "+TimeUtil.getHmsSDF():json.getString("reportingTime").matches("\\d+")?TimeUtil.stringToDate(json.getString("reportingTime")):json.getString("reportingTime"));
			//if(json.getString("rectificationPhotos")!=null)
			//json.put("rectificationPhotos",json.getString("rectificationPhotos"));
			//new Notice().setTest(json.toString()).save();
			result=HttpUtil.doPost1(UrlUtil.yscg_add, json.toString());
			notice.setResult(result).update();
			//JSONObject r=JSON.parseObject(result);
			/*if(r.getIntValue("code")==1) {
				t.setAPARAMETER(r.getJSONObject("data").getIntValue("taskNo")).update();
			}*/
		}
		
		renderJson(MsgUtil.successMsg(111));
	}
	
	/**   
	　* 描述：   
	　* 创建人：coco   
	　* 创建时间：2020年6月15日 上午9:28:25         
	*/
	public void e() {
		List<T1doBase> list=T1doBase.dao.find("select ID,SHOW_ID,O_DESCRIBE,O_EXECUTOR from t_1do_base where source=11 and o_status<6  and o_executor=''");//
		//List<T1doBase> list=T1doBase.dao.find("select * from t_1do_base where source=11 and o_status<6 and o_describe like CONCAT('%','{','%')");
		//List<T1doBase> list=T1doBase.dao.find("select * from t_1do_base where source=11 and o_status<6 and o_executor='GDrRBmLenquJAoGB'");
		list.forEach(t->{
			try {
				//if(StrUtil.isEmpty(t.getOExecutor())) {
				JSONObject json=JSON.parseObject(t.getODescribe());
			
				//String problemType=json.getString("problemType").split("-")[0];
				String streetName=json.getString("streetName");

				//T1doYscgUser t1=T1doYscgUser.dao.findFirst("select * from t_1do_yscg_user where type like CONCAT('%','"+problemType+"','%')");
				
				//String result=HttpUtil.doPost1("http://localhost:8080/1do/do/changeUser", "{\"SHOW_ID\":\""+t.getShowId()+"\",\"object\":\"参与人\",\"method\":\"add\",\"username\":\""+t1.getUTrueName()+"\",\"useraccount\":\""+t1.getShowId()+"\"}");
				//System.out.println(result);
				JSONArray arr=json.getJSONArray("executors");
				if(arr.size() !=0) {
					
				T1doYscgUser t2=T1doYscgUser.dao.findFirst("select * from t_1do_yscg_user where type=?",streetName);
				
						String result2=HttpUtil.doPost1("http://localhost:8080/1do/do/changeUser", "{\"SHOW_ID\":\""+t.getShowId()+"\",\"object\":\"参与人\",\"method\":\"add\",\"username\":\""+t2.getUTrueName()+"\",\"useraccount\":\""+t2.getShowId()+"\"}");
				System.out.println(result2);
				}
				//}
				
				/*	List<String> l=new ArrayList<String>();
					l.add(t2.getShowId());
					json.put("executors",l);
					t.setODescribe(json.toString()).update();*/
				//System.out.println(result2);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
		
		renderJson(MsgUtil.successMsg("成功"));	
		renderJson(MsgUtil.successMsg("成功"));
	}
	/**   
	　* 描述：   
	　* 创建人：coco   
	　* 创建时间：2020年6月11日 下午3:23:06         
	*/
	public void yscg() {
		//List<T1doBase> list=T1doBase.dao.find("select * from t_1do_base where source=11 and o_status<6 and o_describe like CONCAT('%','{','%')");
		List<T1doBase> list=T1doBase.dao.find("select * from t_1do_base where source=11 and o_status<6 and o_executor='GDrRBmLenquJAoGB'");
		list.forEach(t->{
			try {
				JSONObject json=JSON.parseObject(t.getODescribe());
				String problemType=json.getString("problemType").split("-")[0];
				String streetName=json.getString("streetName");

				T1doYscgUser t1=T1doYscgUser.dao.findFirst("select * from t_1do_yscg_user where type like CONCAT('%','"+problemType+"','%')");
				
				String result=HttpUtil.doPost1("http://localhost:8080/1do/do/changeUser", "{\"SHOW_ID\":\""+t.getShowId()+"\",\"object\":\"参与人\",\"method\":\"add\",\"username\":\""+t1.getUTrueName()+"\",\"useraccount\":\""+t1.getShowId()+"\"}");
				System.out.println(result);
				T1doYscgUser t2=T1doYscgUser.dao.findFirst("select * from t_1do_yscg_user where type=?",streetName);
				String result2=HttpUtil.doPost1("http://localhost:8080/1do/do/changeUser", "{\"SHOW_ID\":\""+t.getShowId()+"\",\"object\":\"参与人\",\"method\":\"add\",\"username\":\""+t2.getUTrueName()+"\",\"useraccount\":\""+t2.getShowId()+"\"}");
				System.out.println(result2);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
		
		renderJson(MsgUtil.successMsg("成功"));
	}
	/**   
	　* 描述：   
	　* 创建人：coco   
	　* 创建时间：2019年10月30日 下午5:46:40         
	*/
	public void four() {
		renderJson(MsgUtil.successMsg(UrlUtil.four));
	}
	/*
	 2019年2月27日 coco 注解：
	*/
	public void urgeName() {
		List<T1doBase> list=T1doBase.dao.find("select * from t_1do_base where id>1774");
		renderJson();
	}
	/*
	 2019年2月27日 coco 注解：
	*/
	public void action13() {
		List<T1doBase> list=T1doBase.dao.find("select * from t_1do_base where id>1774");
		list.forEach(t->{
			T1doPstatus.saveIdoPstatus2(t,1);		
		});
		renderJson(11);
	}
	/*
	 2019年2月19日 coco 注解：
	*/
	public void deleteRelation() {
		List<T1doBase> list=T1doBase.dao.find("select SHOW_ID from t_1do_base");
		list.forEach(t->{
			list.forEach(t1->{
			Db.update("update t_1do_relation set sort=1 where SHOW_ID=? and RELATION_SHOW_ID=?",t.getShowId(),t1.getShowId());
		});
		});
		renderJson(1);
	}
	/*
	 2019年2月18日 coco 注解：修改日志content（状态为14）
	*/
	public void changeLogContent() {
		List<T1doBase> list=T1doBase.dao.find("select * from t_1do_base where  id<1427");
		list.forEach(t->{
			List<T1doLog> list1=T1doLog.dao.find("select * from t_1do_log where SHOW_ID=? and log_type=14",t.getShowId());
			if(list1.size()>0){
				String temp=list1.get(list1.size()-1).getContent();
				list1.get(list1.size()-1).setContent(new Temp(temp,t.getODescribe()).toString()).update();		
				for(int i=list1.size()-2;i>=0;i--){
					String str=list1.get(i).getContent();
					list1.get(i).setContent(new Temp(str,temp).toString()).update();
					temp=str;
				}

			}
		});
		renderJson(1);
	}
	/*
	 2018年11月20日 coco 注解：测试主动办接口
	*/
	public void action6() throws UnsupportedEncodingException {
		//调用主动办接口
		T1doBase t1doBase=T1doBase.getIdoBase("94620919024058368");
		String result1 =HttpUtil.doPost11("http://172.16.8.18:8080/1call/getSchemeStart?id="+t1doBase.getAPARAMETER()+"&schemeStart=3&examineTime="+TimeUtil.getDateTime1());
		JSONObject json=JSON.parseObject(result1);
		if(json.getInteger("code")==200){
			t1doBase.setCPARAMETER(3).update();
		}
	}
	/*
	 2018年11月9日 coco 注解：
	*/
	public void action4() {
		List<T1doBase> list=T1doBase.dao.find("select * from t_1do_base where O_DESCRIBE like '%@%'");
		
		List<T1doUser> user1=T1doUser.dao.find("select * from t_1do_user");
		for (T1doUser user : user1) {
			for (T1doBase tf : list) {
				if(tf.getODescribe().indexOf("@"+user.getUTrueName())>=0){
					if(tf.getAT()==null){
						tf.setAT("[\""+user.getShowId()+"@"+user.getUTrueName()+"\"]").update();
					}else{
						tf.setAT(tf.getAT()+"[\""+user.getShowId()+"@"+user.getUTrueName()+"\"]").update();
					}
				}
			}
		}
		renderJson(1);
	}
	/*
	 2018年11月9日 coco 注解：
	 */
	public void action5() {
		List<T1doBase> list=T1doBase.dao.find("select * from t_1do_base where O_DESCRIBE like '%@%'");
		
		List<T1doUser> user1=T1doUser.dao.find("select * from t_1do_user");
		for (T1doUser user : user1) {
			for (T1doBase tf : list) {
				if(tf.getODescribe().indexOf("@"+user.getUTrueName())>=0){
					tf.setODescribe(tf.getODescribe().replace("[\""+user.getUTrueName()+"\" @\""+user.getShowId()+"\"]", "")).update();
					//String ss="[\""+user.getUTrueName()+"\" @\""+user.getShowId()+"\"]";
				}
			}
		}
		renderJson(1);
	}
	/*
	 2018年11月9日 coco 注解：
	 */
	public void action2() {
		List<T1doFeedback> list=T1doFeedback.dao.find("select * from t_1do_feedback where FBCONTENT like '%@%'");
		
		List<T1doUser> user1=T1doUser.dao.find("select * from t_1do_user");
		for (T1doUser user : user1) {
			for (T1doFeedback tf : list) {
				if(tf.getFBCONTENT().indexOf("@"+user.getUTrueName())>=0){
					if(tf.getAT()==null){
						tf.setAT("[\""+user.getShowId()+"@"+user.getUTrueName()+"\"]").update();
					}else{
						tf.setAT(tf.getAT()+"[\""+user.getShowId()+"@"+user.getUTrueName()+"\"]").update();
					}
				}
			}
		}
		renderJson(1);
	}
	/*
	 2018年11月9日 coco 注解：
	 */
	public void action3() {
		List<T1doFeedback> list=T1doFeedback.dao.find("select * from t_1do_feedback where FBCONTENT like '%@%'");
		
		List<T1doUser> user1=T1doUser.dao.find("select * from t_1do_user");
		for (T1doUser user : user1) {
			for (T1doFeedback tf : list) {
				if(tf.getFBCONTENT().indexOf("@"+user.getUTrueName())>=0){
					tf.setFBCONTENT(tf.getFBCONTENT().replace("[\""+user.getUTrueName()+"\" @\""+user.getShowId()+"\"]", "")).update();
					//String ss="[\""+user.getUTrueName()+"\" @\""+user.getShowId()+"\"]";
				}
			}
		}
		renderJson(1);
	}
	/*
	 2018年11月9日 coco 注解：
	*/
	public void action1() {
		List<T1doFeedback> list=T1doFeedback.dao.find("select * from t_1do_feedback where FBCONTENT like '%@%'");
		List<T1doUser> user=T1doUser.dao.find("select * from t_1do_user");
		int i=0;
		for (T1doFeedback t1doFeedback : list) {
			for (T1doUser t1doUser : user) {
				String ss="<span class=\"at-text\" contenteditable=\"false\" u_true_name=\""+t1doUser.getUTrueName()+"\" "
						+ "show_id=\""+t1doUser.getShowId()+"\">@"+t1doUser.getUTrueName()+"</span>";
				t1doFeedback.setFBCONTENT(t1doFeedback.getFBCONTENT().replace(ss, "@"+t1doUser.getUTrueName()+" ")).update();
			}
			i++;
			if(i==10){
				break;
			}
		}
		
		renderJson(1);
	}
	public static void main(String[] args) {
		
		String str="<span class=\"at-text\" contenteditable=\"false\" u_true_name=\"于璟明\" "
				+ "show_id=\"l8BpE16JnNUL8Lqp\">@于璟明</span><span class=\"at-text\" "
				+ "contenteditable=\"false\" u_true_name=\"张开代\" show_id=\"D2JydYO9qqiaDlqj\">"
				+ "@张开代</span><span class=\"at-text\" contenteditable=\"false\" u_true_name=\"金江锋\" "
				+ "show_id=\"6lZPvNAzOlHapo0V\">@金江锋</span>";
		String ss="<span class=\"at-text\" contenteditable=\"false\" u_true_name=\"于璟明\" "
				+ "show_id=\"l8BpE16JnNUL8Lqp\">@于璟明</span>";
		System.out.println(str.indexOf(ss));
		/*String[] arr=str.split("@[\u4e00-\u9fa5]+");
		for (String string : arr) {
			String str1="u_true_name=[\u4e00-\u9fa5]+";
			//String str1="u_true_name=[\u4e00-\u9fa5]+";
			System.out.println(string);
		}*/
		/*String temp="<span[\u4e00-\u9fa5=\"0-9a-zA-Z]+>@[\u4e00-\u9fa5]+</span>";
	
		Pattern pat=Pattern.compile(temp);
		Matcher mat=pat.matcher(str);
		while(mat.find()){
			String arr=mat.group();
			System.out.print(arr);
		}*/
	}
	
	/**
     *   处理回复反馈特殊格式字符串。
     */
    public void action() {
    	//Db.update("update t_1do_feedback set FBCONTENT=REPLACE(FBCONTENT,"</span>：</div>","") where FB_TYPE=2");
    	List<T1doFeedback> list=T1doFeedback.dao.find("select * from t_1do_feedback where fb_type=2");
    	for(T1doFeedback t:list){
    		try {
				if(t.getFBCONTENT().indexOf(t.getFbUserName())==0){
					t.setFBCONTENT(t.getFBCONTENT().substring(t.getFbUserName().length())).update();
					System.out.println(1);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(2);
			}
    	}
    	renderJson(1);
    }
	/**   
	　* 描述：   
	　* 创建人：coco   
	　* 创建时间：2019年11月19日 下午2:36:14         
	*/
	public void action11() {
		T1doFeedback tb=T1doFeedback.dao.findFirst("SELECT * FROM t_1do_feedback where id=1402");
		
		renderJson(MsgUtil.successMsg(tb.getATString()));
	}

}