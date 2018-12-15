package com.demo.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseT1doPset<M extends BaseT1doPset<M>> extends Model<M> implements IBean {

	public M setID(java.lang.Integer ID) {
		set("ID", ID);
		return (M)this;
	}
	
	public java.lang.Integer getID() {
		return getInt("ID");
	}

	public M setShowId(java.lang.String showId) {
		set("SHOW_ID", showId);
		return (M)this;
	}
	
	public java.lang.String getShowId() {
		return getStr("SHOW_ID");
	}

	public M setOUser(java.lang.String oUser) {
		set("O_USER", oUser);
		return (M)this;
	}
	
	public java.lang.String getOUser() {
		return getStr("O_USER");
	}

	public M setEventType(java.lang.String eventType) {
		set("EVENT_TYPE", eventType);
		return (M)this;
	}
	
	public java.lang.String getEventType() {
		return getStr("EVENT_TYPE");
	}

	public M setUserType(java.lang.Integer userType) {
		set("USER_TYPE", userType);
		return (M)this;
	}
	
	public java.lang.Integer getUserType() {
		return getInt("USER_TYPE");
	}

}
