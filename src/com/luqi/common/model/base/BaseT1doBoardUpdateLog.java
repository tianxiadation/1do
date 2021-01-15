package com.luqi.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseT1doBoardUpdateLog<M extends BaseT1doBoardUpdateLog<M>> extends Model<M> implements IBean {

	public M setID(java.lang.Long ID) {
		set("ID", ID);
		return (M)this;
	}
	
	public java.lang.Long getID() {
		return getLong("ID");
	}

	public M setCONTENT(java.lang.String CONTENT) {
		set("CONTENT", CONTENT);
		return (M)this;
	}
	
	public java.lang.String getCONTENT() {
		return getStr("CONTENT");
	}

	public M setUpdateTime(java.util.Date updateTime) {
		set("UPDATE_TIME", updateTime);
		return (M)this;
	}
	
	public java.util.Date getUpdateTime() {
		return get("UPDATE_TIME");
	}

	public M setTYPE(java.lang.Integer TYPE) {
		set("TYPE", TYPE);
		return (M)this;
	}
	
	public java.lang.Integer getTYPE() {
		return getInt("TYPE");
	}

	public M setOUser(java.lang.String oUser) {
		set("O_USER", oUser);
		return (M)this;
	}
	
	public java.lang.String getOUser() {
		return getStr("O_USER");
	}

}