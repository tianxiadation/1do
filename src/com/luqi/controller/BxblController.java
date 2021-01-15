 package com.luqi.controller;



import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.luqi.common.model.T1doFeedback;
import com.luqi.util.JsonUtil;
import com.luqi.util.MsgUtil;


public class BxblController extends Controller {
	
	/**   
	　* 描述：   获得百姓爆料处理人发的反馈
	　* 创建人：coco   
	　* 创建时间：2020年7月21日 上午10:03:58         
	*/
	public void getExecutorFeedbacks() {
		JSONObject json=JsonUtil.getJSONObject(getRequest());
		renderJson(MsgUtil.successMsg(T1doFeedback.getExecutorFeedbacks(json.getString("SHOW_ID"))));
	}

}
