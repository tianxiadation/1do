package com.luqi.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.luqi.common.model.T1doBase;
import com.luqi.common.model.T1doProjectStakeholder;
import com.luqi.common.model.T1doPstatus;
import com.luqi.common.model.T1doWechat;
import com.luqi.service.WeChatService;
import com.luqi.util.JsonUtil;
import com.luqi.util.MsgUtil;
import com.luqi.util.StrUtil;

/**
* @author coco
* @date 2020年2月25日 下午3:27:54
* 1do微信同步
*/
public class WeChatController extends Controller{
	
	/**   
	　* 描述：  1do解绑微信群 
	　* 创建人：coco   
	　* 创建时间：2020年2月25日 下午3:33:45         
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	*/
	public void untying() throws UnsupportedEncodingException, IOException {
		JSONObject json=JsonUtil.getJSONObject(getRequest());
		JSONObject user=getSessionAttr("user");
		 T1doPstatus t1=T1doPstatus.getUser(json.getString("SHOW_ID"),user.getString("loginName"));
		 if(user.getBoolean("isfw")||StrUtil.getflag(t1.getUserType(), 1)){
			 JSONObject result=WeChatService.untying(json);
				if(result.getBooleanValue("IsSuccess"))
				    renderJson(MsgUtil.successMsg("解绑成功"));
				else 
					renderJson(MsgUtil.errorMsg(result.getString("Reason")));	
		 }else{
			 renderJson(MsgUtil.errorMsg("权限不足"));
		}
		
		
		
	}
	/** 描述：根据名称搜索微信群
	　* 创建人：coco   
	　* 创建时间：2020年2月25日 下午3:33:45         
	 * @throws UnsupportedEncodingException 
	*/
	public void searchWechatGroupByName() throws UnsupportedEncodingException {
		 JSONObject json=JsonUtil.getJSONObject(getRequest());
		//JSONObject user=getSessionAttr("user");
		// T1doPstatus t1=T1doPstatus.getUser(json.getString("SHOW_ID"),user.getString("loginName"));
		// if(user.getBoolean("isfw")||StrUtil.getflag(t1.getUserType(), 1)){
			
				JSONObject arr=WeChatService.searchWechatGroupByName(json);
				renderJson(MsgUtil.successMsg(arr));
		 //}else{
			// renderJson(MsgUtil.errorMsg("权限不足"));
		//}
		
	}
	
	/** 描述：绑定微信群
	　* 创建人：coco   
	　* 创建时间：2020年2月25日 下午3:33:45         
	 * @throws Exception 
	*/
	public void bindingWechatGroup() throws Exception {
		JSONObject json=JsonUtil.getJSONObject(getRequest());
		JSONObject user=getSessionAttr("user");
		 T1doPstatus t1=T1doPstatus.getUser(json.getString("SHOW_ID"),user.getString("loginName"));
		 if(user.getBoolean("isfw")||StrUtil.getflag(t1.getUserType(), 1)){
			 JSONObject result=WeChatService.bindingWechatGroup(json);
				if(result.getBooleanValue("IsSuccess"))
				    renderJson(MsgUtil.successMsg("成功"));
				else 
					renderJson(MsgUtil.errorMsg(result.getString("Reason")));	
		 }else{
			 renderJson(MsgUtil.errorMsg("权限不足"));
		}
		
		
		
	}
	/**   
	　* 描述：   
	　* 创建人：coco   
	　* 创建时间：2020年2月25日 下午9:15:30         
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	*/
	public void action() throws UnsupportedEncodingException, IOException {
		JSONObject json=JsonUtil.getJSONObject(getRequest());
		T1doBase t1doBase=T1doBase.getIdoBase(json.getString("SHOW_ID"));	

		T1doWechat.saveT1doWechat(json.getString("SHOW_ID"), WeChatService.SendMsg(json.getString("wxGroupId"),"1do小依",WeChatService.getMsg("",t1doBase)), 1,"");

		renderJson(MsgUtil.successMsg(null));
	}


}