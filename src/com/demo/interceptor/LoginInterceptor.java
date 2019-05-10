package com.demo.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.demo.util.MsgUtil;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class LoginInterceptor implements Interceptor {

	
	public void intercept(Invocation inv) {
		   JSONObject user = inv.getController().getSessionAttr("user");
	      if (user != null) {
	         inv.invoke();
	      } else {
	         inv.getController().renderJson(MsgUtil.errorMsg("用户未登入"));
	      }		
	
	}

}
