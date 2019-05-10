package com.demo.interfaces;

import com.demo.common.model.T1doBase;

public class UpdateBase implements Runnable {
	private T1doBase base;
	private String loginName;
	
	public UpdateBase(T1doBase base, String loginName) {
		super();
		this.base = base;
		this.loginName = loginName;
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
		// TODO Auto-generated method stub
		/*if(!base.getLookUser().contains(loginName)){
			base.setLookUser(base.getLookUser()+","+loginName).update();
		}*/
		base.updateLookNumAndLookUser(loginName);
	}

}
