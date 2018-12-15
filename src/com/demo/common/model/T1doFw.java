package com.demo.common.model;

import com.demo.common.model.base.BaseT1doFw;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class T1doFw extends BaseT1doFw<T1doFw> {
	public static final T1doFw dao = new T1doFw().dao();
	
	/*
	 2018年6月24日上午1:06:43 方升群
	 */
	public static T1doFw getIdoFw(String O_USER) {
		return T1doFw.dao.findFirst("select * from t_1do_fw where O_USER=?", O_USER);
		
	}
	/*
	 2018年8月9日上午11:15:06 方升群  //是否查看
	*/
	public int getIsLook(String showid) {
		Record r=Db.findFirst("select * from t_1do_log where SHOW_ID=? and O_USER=?",showid,get("icallshowid"));
		return r==null?2:1;
	}
	/*
	 2018年8月10日上午10:15:01 方升群
	*/
	//查询是否是整理层
	public static T1doFw getfw(String loginName) {
		return T1doFw.dao.findFirst("select * from t_1do_fw where icallshowid=?",loginName);
	}
}
