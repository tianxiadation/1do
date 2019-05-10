package com.demo.common.model;

import java.util.Date;
import java.util.List;

import com.demo.common.model.base.BaseWebsocket;
import com.jfinal.plugin.activerecord.Db;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Websocket extends BaseWebsocket<Websocket> {
	public static final Websocket dao = new Websocket().dao();
	
	/*
	 2018年11月23日 coco 注解：保存socket
	*/
	public static void saveSocket(String sessionid,String showid,boolean flag) {
		T1doFeedback fb=T1doFeedback.dao.findFirst("select * from t_1do_feedback order by id desc");
		Websocket w=new Websocket();
		w.setSessionid(sessionid).setShowid(showid).setIsOnline(flag)
		.setFbid(fb.getID()).setGmtCreate(new Date().getTime()).save();
	}
	public static Websocket selectSocket(String sessionid) {
		return dao.findFirst("select * from websocket where sessionid=? order by id desc",sessionid);
	}
	public static void updateScoketIsOnline(String sessionid) {
		Db.update("update websocket set isOnline=false where sessionid=?",sessionid);
	}
	public static void updateScoketFbid(String sessionid) {
		T1doFeedback fb=T1doFeedback.dao.findFirst("select * from t_1do_feedback order by id desc");
		Db.update("update websocket set fbid=? where sessionid=?",fb.getID(),sessionid);
	}
	public static List<Websocket> selectScokets(String showid) {
		
		return Websocket.dao.find("select * from websocket  where showid=? and isOnline=1",showid);
	}
	public static Websocket selectScoket1(String sessionid,String showid) {
		
		return Websocket.dao.findFirst("select * from websocket  where sessionid=? and isOnline=1 and showid=? order by id desc",sessionid,showid);
	}
	
}
