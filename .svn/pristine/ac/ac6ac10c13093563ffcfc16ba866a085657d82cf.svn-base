package com.luqi.controller;

import java.util.Date;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.luqi.common.model.T1doAttr;
import com.luqi.common.model.T1doBase;
import com.luqi.common.model.T1doFeedback;
import com.luqi.common.model.T1doLog;
import com.luqi.common.model.T1doPstatus;
import com.luqi.common.model.T1doType;
import com.luqi.interfaces.UpdateBase;
import com.luqi.timer.CommonTask;
import com.luqi.util.IDUtil;
import com.luqi.util.JsonUtil;
import com.luqi.util.MsgUtil;
import com.luqi.util.StrUtil;
import com.nimbusds.jose.util.IntegerUtils;


/**
* @author coco 公共的接口
* @date 2020年7月31日 上午10:42:24
* 
*/
public class CommonController extends Controller {
	/**   
	　* 描述：   
	　* 创建人：coco   
	　* 创建时间：2020年7月31日 上午10:42:41         
	*/
	public void getDataList() {
		JSONObject json=JsonUtil.getJSONObject(getRequest());
		JSONObject user=getSessionAttr("user");
		if(json.getString("loginName")==null&&user==null){
			renderJson(MsgUtil.errorMsg("用户未登入"));
			return;
		}else if(json.getString("loginName")!=null&&user==null){
			user=new DoController().setSession(json.getString("loginName"));
		}
		String loginName=user==null?json.getString("loginName"):user.getString("loginName");
		int type=json.getIntValue("status");
		String sql=" o_status!=6 ";			
		if(type!=0){
			sql=" o_status="+type;
		} 
		if(json.getIntValue("SOURCE")!=0) {
			sql+=" and SOURCE="+json.getIntValue("SOURCE");
		}
		sql+=" and (O_EXECUTOR like CONCAT('%','"+loginName+"','%') or O_CUSTOMER like CONCAT('%','"+loginName+"','%') )";
		if(json.getIntValue("O_TYPE_ID")!=0) {
			sql+="and a.O_TYPE_ID="+json.getIntValue("O_TYPE_ID");
		}
		if(StrUtil.isNotEmpty(json.getString("content"))){
				sql+=" and O_DESCRIBE like CONCAT('%','"+json.getString("content")+"','%')";
		}
		//int pageNumber=getParaToInt("pageNumber");
    	//Boolean isDeleted=getParaToBoolean("is_deleted",false);
		
		/*Page<T1doBase> page  =T1doBase.dao.paginate(json.getIntValue("pageNumber"), json.getIntValue("pageSize"), false,  "select SHOW_ID,O_DESCRIBE,O_CUSTOMER,O_CUSTOMER_NAME,O_CREATE_TIME"
				+ ",O_FINISH_TIME,REAL_FINISH_TIME,SOURCE,O_STATUS,O_EXECUTOR,O_EXECUTOR_NAME,O_TYPE_ID",
				"from t_1do_base where  "+sql+" ORDER BY id desc");*/
    	Page<T1doBase> page  =T1doBase.dao.paginate(json.getIntValue("pageNumber"), json.getIntValue("pageSize"), false,  "select a.id,SHOW_ID,O_DESCRIBE,O_CUSTOMER,O_CUSTOMER_NAME,O_CREATE_TIME\r\n" + 
    			"    			,O_FINISH_TIME,REAL_FINISH_TIME,SOURCE,O_STATUS,O_EXECUTOR,O_EXECUTOR_NAME,a.O_TYPE_ID,b.O_SOURE_NAME ",
    			" from t_1do_base a left join (select * from t_1do_type b GROUP BY O_PARENT_ID) b on a.SOURCE=b.O_PARENT_ID where "+sql+"  ORDER BY a.id desc  ");
		renderJson(MsgUtil.successMsg(page));
	}
	/**
     * @Author coco
     * @Description 1do详情
     * @Date 2018年6月25日下午3:37:00
    */
	@Before(Tx.class)
	public void getIdoMessage() {
  
    	JSONObject json=JsonUtil.getJSONObject(getRequest()); 	
		final T1doBase t1doBase=T1doBase.getIdoBase2(json.getString("SHOW_ID"));
		JSONObject user1=getSessionAttr("user");
		if(json.getString("loginName")==null&&user1==null){
			renderJson(MsgUtil.errorMsg("用户未登入"));
			return;
		}else if(t1doBase==null){
			renderJson(MsgUtil.errorMsg("工单不存在"));
			return;
		}else if(json.getString("loginName")!=null&&user1==null){
			user1=new DoController().setSession(json.getString("loginName"));
		}
		//final JSONObject user=user1;
		final String loginName=user1.getString("loginName");//user1==null?json.getString("loginName"):
		final String username=user1.getString("username");
		/**
		 * 来源 0、全部  1、call 或者oa 2、主动办 3、三实库 4、其他 5、领导批示6.城市大脑,7综合信息系统，
		 * 	8、1call办9、城市大脑综合指挥平台 10、督查指挥，11云上城管，12百姓爆料，13红旗班
		 */
			new Thread(new UpdateBase(t1doBase,loginName,username)).start();
			renderJson(MsgUtil.successMsg(t1doBase));
	}
	/**
     * @Author coco
     * @Description 1call转1do
     * @Date 2018年6月21日
    */
    @Before(Tx.class)
	public void createIdo() {
		JSONObject json=JsonUtil.getJSONObject(getRequest());
		final T1doBase t1doBase =json.getObject("BASE", T1doBase.class);
		JSONArray arr=json.getJSONArray("ATTR");
		String showId=IDUtil.getUid();
		if(StrKit.isBlank(t1doBase.getODescribe())||StrUtil.isEmpty(t1doBase.getOCustomer())||StrUtil.isEmpty(t1doBase.getCreateUser())||t1doBase.getSOURCE()==null){
			renderJson(MsgUtil.errorMsg("内容、发起人、创建人、来源不能为空"));
			return;
		}else {
			//1Do 创建时必须有参与人，默认参与人是创建人
			if(StrUtil.isEmpty(t1doBase.getOExecutor()))
				t1doBase.setOExecutor(t1doBase.getCreateUser()).setOExecutorName(t1doBase.getCreateUserName());
			else if(!t1doBase.getOExecutor().contains(t1doBase.getCreateUser())) 
				t1doBase.setOExecutor(t1doBase.getOExecutor()+";"+t1doBase.getCreateUser()).setOExecutorName(t1doBase.getOExecutorName()+";"+t1doBase.getCreateUserName());
				
		}
		t1doBase.saveBase(showId, arr==null?0:arr.size());
		if(arr!=null){
			Long time=new Date().getTime();
		for (int i = 0; i < arr.size(); i++) {
			T1doAttr t1doAttr=arr.getObject(i, T1doAttr.class);
			t1doAttr.setShowId(t1doBase.getShowId());
			t1doAttr.save();
			new T1doFeedback().setShowId(t1doBase.getShowId()).setOUser(t1doAttr.getUploadUser())
			.setOUserName(t1doAttr.getUploadUserName()).setFbTime(t1doAttr.getUploadTime()).setTimeStamp(time+i)
			.setFbType(t1doBase.getSOURCE()==2?10:3).setATTRID(t1doAttr.getID()+"").setAttrPath(t1doAttr.getAttrPath()).setFBCONTENT(t1doAttr.getAttrName()==null?"":t1doAttr.getAttrName()).save();
			T1doLog.saveLog(t1doBase.getShowId(),t1doAttr.getUploadUser(), t1doAttr.getUploadUserName(), t1doAttr.getUploadUserName()+"上传"+t1doAttr.getAttrName(), 3, t1doAttr.getAttrName()); 	
		}}			
			//推送数据
			new Thread(new CommonTask(t1doBase,7)).start();
			T1doPstatus.saveIdoPstatus2(t1doBase,2);
			T1doLog.saveLog(t1doBase.getShowId(), t1doBase.getCreateUser(), t1doBase.getCreateUserName(), t1doBase.getCreateUserName()+"创建了此1do", 1, "");
	    renderJson(MsgUtil.successMsg(t1doBase.getShowId()));
   
    }
    /**   
	　* 描述：   注册来源
	　* 创建人：coco   
	　* 创建时间：2021年1月12日 下午3:34:44         
	*/
	public void registerSource() {
		String sourceName=getPara("sourceName");
		if(StrUtil.isEmpty(sourceName)) {
			renderJson(MsgUtil.errorMsg("来源名称不能为空"));
		}else {
			T1doType t=new T1doType();
			t.setOSoureName(sourceName);
			t.save();
			t.setOParentId(t.getID());
			t.update();
			JSONObject json=new JSONObject();
			json.put("SOURCE",t.getID());
			json.put("O_TYPE_ID",0);
			json.put("SOURCE_NAME",sourceName);
			
			renderJson(MsgUtil.successMsg(json));
		}
		
	}
	/**   
	　* 描述：   注册事件类型
	　* 创建人：coco   
	　* 创建时间：2021年1月12日 下午3:34:44         
	*/
	public void registerType() {
		String source=getPara("source");
		String typeName=getPara("typeName");
		if(StrUtil.isEmpty(typeName)||StrUtil.isEmpty(source)) {
			renderJson(MsgUtil.errorMsg("来源或者事件类型不能为空"));
		}else {
			T1doType t1=T1doType.dao.findById(Integer.valueOf(source));
			if(t1==null) {
				renderJson(MsgUtil.errorMsg("来源不存在"));
				return;
			}
			T1doType t=new T1doType();
			t.setOSoureName(t1.getOSoureName());
			t.setOTypeName(typeName);
			t.setOtherName(typeName);
			t.setOParentId(Integer.valueOf(source));
			t.save();
			t.setOTypeId(t.getID());
			t.update();
			JSONObject json=new JSONObject();
			json.put("SOURCE",t.getOParentId());
			json.put("O_TYPE_ID",t.getID());
			json.put("TYPE_NAME",t.getOTypeName());
			
			renderJson(MsgUtil.successMsg(json));
		}
		
	}
	/**   
	　* 描述：   修改来源根据showid
	　* 创建人：coco   
	　* 创建时间：2021年1月12日 下午5:05:04         
	*/
	
	public void update1doSource(){
		String SHOW_ID=getPara("showId");
		String SOURCE=getPara("source");
		int ok=Db.update("update t_1do_base set SOURCE=? where SHOW_ID=?",SOURCE,SHOW_ID);
		renderJson(MsgUtil.successMsg(ok));
	}
	
    
}
