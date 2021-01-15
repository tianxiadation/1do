package com.luqi.controller;

import com.jfinal.core.Controller;
import com.luqi.util.MsgUtil;
import com.luqi.util.TestUtil;


public class ShortMessageController extends Controller {
	//短信通过url跳转打开app
	public void open1call(){
		redirect("1call://open/1do/"+getPara("SHOW_ID")); 
	}
	/*
	 2019年3月4日 coco 注解：
	*/
	public void action() {
		new TestUtil().action();
		renderJson(MsgUtil.successMsg("本方法中返回"));
	}
}
