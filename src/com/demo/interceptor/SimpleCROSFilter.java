package com.demo.interceptor;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class SimpleCROSFilter implements Interceptor {

	public void intercept(Invocation inv) {
		// TODO Auto-generated method stub
		HttpServletResponse response = inv.getController().getResponse();
		//response.setHeader("Access-Control-Allow-Origin", "*");
		//response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
		String[] allowedOrigins = { "http://xczs.hzxc.gov.cn","http://172.16.8.7:2005","http://172.16.8.7:7004","http://172.16.8.7:6005","http://172.16.8.7","http://172.16.10.172:7004","http://172.16.9.195:8080","http://172.16.10.172:6005", "http://localhost:8080",
				"http://1call.avatar.com","http://xcgov.hzxc.gov.cn","http://dev.avatar.com","http://m.avatar.com","http://m.hzxc.gov.cn","https://tyhy.hzxc.gov.cn:8006","https://m.hzxc.gov.cn"};
		List<String> list = Arrays.asList(allowedOrigins);
		String originHeader = inv.getController().getRequest().getHeader("Origin");
		if (list.contains(originHeader)) {
			response.setHeader("Access-Control-Allow-Origin", originHeader);
		}
		response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type,Access-Token,X-Requested-With");
		//response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		inv.invoke();
	}
}
