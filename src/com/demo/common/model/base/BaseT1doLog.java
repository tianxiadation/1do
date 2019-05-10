package com.demo.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseT1doLog<M extends BaseT1doLog<M>> extends Model<M> implements IBean {

	public M setShowId(java.lang.String showId) {
		set("SHOW_ID", showId);
		return (M)this;
	}
	
	public java.lang.String getShowId() {
		return getStr("SHOW_ID");
	}

	public M setID(java.lang.Integer ID) {
		set("ID", ID);
		return (M)this;
	}
	
	public java.lang.Integer getID() {
		return getInt("ID");
	}

	public M setOUser(java.lang.String oUser) {
		set("O_USER", oUser);
		return (M)this;
	}
	
	public java.lang.String getOUser() {
		return getStr("O_USER");
	}

	public M setOUserName(java.lang.String oUserName) {
		set("O_USER_NAME", oUserName);
		return (M)this;
	}
	
	public java.lang.String getOUserName() {
		return getStr("O_USER_NAME");
	}

	public M setOpTime(java.util.Date opTime) {
		set("op_time", opTime);
		return (M)this;
	}
	
	public java.util.Date getOpTime() {
		return get("op_time");
	}

	public M setLog(java.lang.String log) {
		set("log", log);
		return (M)this;
	}
	
	public java.lang.String getLog() {
		return getStr("log");
	}

	public M setLogType(java.lang.Integer logType) {
		set("log_type", logType);
		return (M)this;
	}
	
	public java.lang.Integer getLogType() {
		return getInt("log_type");
	}

	public M setOprator(java.lang.String oprator) {
		set("oprator", oprator);
		return (M)this;
	}
	
	public java.lang.String getOprator() {
		return getStr("oprator");
	}

	public M setIsoverdue(java.lang.Integer isoverdue) {
		set("isoverdue", isoverdue);
		return (M)this;
	}
	
	public java.lang.Integer getIsoverdue() {
		return getInt("isoverdue");
	}

	public M setModifyTime(java.util.Date modifyTime) {
		set("modifyTime", modifyTime);
		return (M)this;
	}
	
	public java.util.Date getModifyTime() {
		return get("modifyTime");
	}

	public M setContent(java.lang.String content) {
		set("content", content);
		return (M)this;
	}
	
	public java.lang.String getContent() {
		return getStr("content");
	}

}
