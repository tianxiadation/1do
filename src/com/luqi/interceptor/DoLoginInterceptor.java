package com.luqi.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.luqi.common.model.TRegUser;
import com.luqi.util.JsonUtil;
import com.luqi.util.MsgUtil;

public class DoLoginInterceptor implements Interceptor {




	public void intercept(Invocation inv) {
		if (inv.getController().getRequest().getMethod().equals("OPTIONS")) {
			inv.getController().renderJson("active");
			return;
		}

		// JSONObject user = inv.getController().getSessionAttr("user");
		String action=inv.getActionKey();
		boolean flag=false;
		String[] urls= {"login","getLog","getDoLog","getFeedback","getDoFeedback","Clear"
				,"polling","feedbackUpload","deleteAttr","saveIdo","createIdo","msginfo"
				,"getSourceData","setCallStatus","exportExcel","batchAddRelation","selectBybase","getAttr"};
		for(String url:urls) {
			if( action.indexOf(url)>=0) {
				flag=true;
				break;
			}
		}
		if(flag) {
			inv.invoke();
		}else {
			JSONObject json=JsonUtil.getJSONObject(inv.getController().getRequest());
			JSONObject user=inv.getController().getSessionAttr("user");
			if(json.getString("loginName")==null&&user==null){
				inv.getController().renderJson(MsgUtil.errorMsg("用户未登入"));
				return;
			}else if(json.getString("loginName")!=null&&user==null){
				user=TRegUser.getUserForShowId(json.getString("loginName"));
			}
			if (user != null) {
				inv.getController().setAttr("request", json);
				inv.getController().setAttr("user", user);
				inv.invoke();
			} else {
				inv.getController().renderJson(MsgUtil.errorMsg("用户未登入"));
			}
		}


	}

}