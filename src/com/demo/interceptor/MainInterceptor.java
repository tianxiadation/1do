package com.demo.interceptor;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class MainInterceptor implements Interceptor {

	private Logger log=Logger.getLogger(MainInterceptor.class);
	
	public void intercept(Invocation inv) {
		
		// TODO Auto-generated method stub
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		try {
			//HttpSession session = inv.getController().getSession();
			//System.out.println(session.getId());
			inv.invoke();
		}catch(Exception e) {
			e.printStackTrace();
			log.error(inv.getActionKey()+":"+e.getMessage());
			map.put("code", 400);
			map.put("message", e.getMessage());
			inv.getController().renderJson(map);
			
		}
		

	}

}
