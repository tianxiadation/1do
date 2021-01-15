package com.luqi.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.luqi.common.model.TRegUser;
import com.luqi.util.JsonUtil;
import com.luqi.util.MsgUtil;

public class LoginInterceptor implements Interceptor {
	
	
	public void intercept(Invocation inv) {
		if (inv.getController().getRequest().getMethod().equals("OPTIONS")) {
			inv.getController().renderJson("active");
			return;
		}
		JSONObject user = inv.getController().getSessionAttr("user");
		if (user != null) {

			inv.invoke();
		} else {
			inv.getController().renderJson(MsgUtil.errorMsg("用户未登入"));
		}

	}
}
