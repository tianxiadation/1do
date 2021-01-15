package com.luqi.controller;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.luqi.interceptor.DoLoginInterceptor;
import com.luqi.interceptor.LoginInterceptor;
import com.luqi.service.DoService;
import com.luqi.util.JsonUtil;
import com.luqi.util.MsgUtil;

/**
* @author coco
* @date 2020年4月10日 上午9:30:00
* 督查指挥
*/
@Before(DoLoginInterceptor.class)
public class SupervisionAndCommandController extends Controller{

	/**
     * @Author coco
     * @Description 督查列表
     * @Date 2018年7月5日上午9:56:46
    */
	public void doList() {
		JSONObject json=getAttr("request");
		JSONObject user=getAttr("user");
	    Page<Record> data=DoService.getDoBase(user,json);
	    renderJson(MsgUtil.successMsg(data));
	}
	

}
