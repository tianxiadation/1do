package com.luqi.interceptor;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class SimpleCROSFilter implements Interceptor {

	public void intercept(Invocation inv) {
		// TODO Auto-generated method stub
		HttpServletResponse response = inv.getController().getResponse();
		response.setHeader("Access-Control-Allow-Origin", inv.getController().getRequest().getHeader("Origin"));
		//response.setHeader("Access-Control-Allow-Origin", "*");

		
		/*
		 * String[] allowedOrigins = {
		 * "http://xczs.hzxc.gov.cn","http://172.16.8.7:2005",
		 * "http://172.16.8.7:7004","http://172.16.8.7:6005","http://172.16.8.7",
		 * "http://172.16.10.172:7004","http://172.16.9.195:8080",
		 * "http://172.16.10.172:6005",
		 * "http://localhost:8080","http://1call.avatar.com","http://xcgov.hzxc.gov.cn",
		 * "http://dev.avatar.com","http://m.avatar.com","http://m.hzxc.gov.cn",
		 * "https://tyhy.hzxc.gov.cn:8006",
		 * "https://m.hzxc.gov.cn","http://172.16.8.7:2004","http://xczs.hzxc.gov.cn",
		 * "http://172.16.9.216","http://172.16.8.7:7777","http://localhost:3000",
		 * "http://localhost:8100","http://172.16.8.7:8000","http://172.16.9.202:8051",
		 * "https://control.hzxc.gov.cn" ,"https://szjsc.hzcitybrain.com"}; List<String>
		 * list = Arrays.asList(allowedOrigins); String originHeader =
		 * inv.getController().getRequest().getHeader("Origin"); if
		 * (list.contains(originHeader)) {
		 * response.setHeader("Access-Control-Allow-Origin", originHeader); }
		 */
		 
		 
		 
		response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type,Access-Token,X-Requested-With");
		//response.setHeader("Access-Control-Allow-Headers", "DNT,X-Mx-ReqToken,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type");

		//response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		inv.invoke();
	}
}
