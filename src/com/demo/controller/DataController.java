 package com.demo.controller;



import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.demo.common.model.T1doBase;
import com.demo.common.model.T1doFeedback;
import com.demo.common.model.T1doUser;
import com.demo.util.HttpUtil;
import com.demo.util.TimeUtil;
import com.jfinal.core.Controller;

/*
 2018年11月2日 coco 注解：处理各种数据库数据
*/

public class DataController extends Controller {
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
	

}
