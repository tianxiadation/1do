package com.demo.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseT1doRecord<M extends BaseT1doRecord<M>> extends Model<M> implements IBean {

	public M setRECORDID(java.lang.Long RECORDID) {
		set("RECORDID", RECORDID);
		return (M)this;
	}
	
	public java.lang.Long getRECORDID() {
		return getLong("RECORDID");
	}

	public M setID(java.lang.Integer ID) {
		set("ID", ID);
		return (M)this;
	}
	
	public java.lang.Integer getID() {
		return getInt("ID");
	}

	public M setNAME(java.lang.String NAME) {
		set("NAME", NAME);
		return (M)this;
	}
	
	public java.lang.String getNAME() {
		return getStr("NAME");
	}

	public M setACCOUNT(java.lang.String ACCOUNT) {
		set("ACCOUNT", ACCOUNT);
		return (M)this;
	}
	
	public java.lang.String getACCOUNT() {
		return getStr("ACCOUNT");
	}

	public M setCREATETIME(java.util.Date CREATETIME) {
		set("CREATETIME", CREATETIME);
		return (M)this;
	}
	
	public java.util.Date getCREATETIME() {
		return get("CREATETIME");
	}

	public M setRECORD(java.lang.String RECORD) {
		set("RECORD", RECORD);
		return (M)this;
	}
	
	public java.lang.String getRECORD() {
		return getStr("RECORD");
	}

	public M setTYPE(java.lang.Integer TYPE) {
		set("TYPE", TYPE);
		return (M)this;
	}
	
	public java.lang.Integer getTYPE() {
		return getInt("TYPE");
	}

}
