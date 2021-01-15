package com.luqi.controller;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.luqi.common.model.T1doRelationFeedback;
import com.luqi.interceptor.LoginInterceptor;
import com.luqi.util.JsonUtil;
import com.luqi.util.MsgUtil;

/**
* @author coco
* @date 2020年1月13日 下午4:22:08
* 传送门controller
*/
public class PortalController {
	/**
     * @Author coco
     * @Description 整理层可以删除传送门中的相关1Do：删除关联关系。
     * @Date 2019年5月6日
    */
	@Before(LoginInterceptor.class)
	public void deleteDoRelation(){
		/*JSONObject json=JsonUtil.getJSONObject(getRequest());
		JSONObject user=getSessionAttr("user");
		if(user.getBooleanValue("isfw")){
			T1doRelationFeedback.updateType(json.getLongValue("RFID"));
			renderJson(MsgUtil.successMsg("成功"));
		}else{
			renderJson(MsgUtil.errorMsg("权限不足"));
		}*/
	}
}
