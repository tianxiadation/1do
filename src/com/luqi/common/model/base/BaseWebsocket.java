package com.luqi.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseWebsocket<M extends BaseWebsocket<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Long id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Long getId() {
		return getLong("id");
	}

	public M setSessionid(java.lang.String sessionid) {
		set("sessionid", sessionid);
		return (M)this;
	}
	
	public java.lang.String getSessionid() {
		return getStr("sessionid");
	}

	public M setShowid(java.lang.String showid) {
		set("showid", showid);
		return (M)this;
	}
	
	public java.lang.String getShowid() {
		return getStr("showid");
	}

	public M setFbid(java.lang.Integer fbid) {
		set("fbid", fbid);
		return (M)this;
	}
	
	public java.lang.Integer getFbid() {
		return getInt("fbid");
	}

	public M setIsOnline(java.lang.Boolean isOnline) {
		set("isOnline", isOnline);
		return (M)this;
	}
	
	public java.lang.Boolean getIsOnline() {
		return get("isOnline");
	}

	public M setGmtCreate(java.lang.Long gmtCreate) {
		set("gmtCreate", gmtCreate);
		return (M)this;
	}
	
	public java.lang.Long getGmtCreate() {
		return getLong("gmtCreate");
	}

	public M setGmtModified(java.lang.Long gmtModified) {
		set("gmtModified", gmtModified);
		return (M)this;
	}
	
	public java.lang.Long getGmtModified() {
		return getLong("gmtModified");
	}

	public M setIsDeleted(java.lang.Boolean isDeleted) {
		set("isDeleted", isDeleted);
		return (M)this;
	}
	
	public java.lang.Boolean getIsDeleted() {
		return get("isDeleted");
	}

}