package com.demo.util;

import com.jfinal.core.Controller;

//测试工具
public class TestUtil extends Controller{
	/*
	 2019年3月4日 coco 注解：
	*/
	public void action() {
		renderJson(MsgUtil.successMsg("方法中拦截返回"));
		return ;
	}
}
