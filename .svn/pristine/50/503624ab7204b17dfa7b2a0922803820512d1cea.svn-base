package com.luqi.interfaces;

import java.util.Date;

import com.luqi.common.model.T1doBase;
import com.luqi.common.model.T1doLog;
import com.luqi.util.DbUtil;

public class UpdateBase implements Runnable {
	private T1doBase base;
	private String loginName;
	private String username;
	
	public UpdateBase(T1doBase base, String loginName, String username) {
		super();
		this.base = base;
		this.loginName = loginName;
		this.username = username;
	}


	public T1doBase getBase() {
		return base;
	}

	public void setBase(T1doBase base) {
		this.base = base;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Override
	public void run() {
		

		//修改
		DbUtil.update(base.getShowId(),loginName, 5, 0,"","");	
		DbUtil.update(base.getShowId(),loginName, 6, 0,"","");
		//在线已读
		DbUtil.update(base.getShowId(),loginName, 7, 0,"","");			
		new T1doLog().setShowId(base.getShowId()).setOUser(loginName)
		.setOUserName(username).setOpTime(new Date()).
		setLog(username+"查看此1do").setLogType(2).save();
	
	
		// TODO Auto-generated method stub
		/*if(!base.getLookUser().contains(loginName)){
			base.setLookUser(base.getLookUser()+","+loginName).update();
		}*/
		base.updateLookNumAndLookUser(loginName);
	}

}
