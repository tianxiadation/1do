package com.luqi.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseT1doRelationFeedback<M extends BaseT1doRelationFeedback<M>> extends Model<M> implements IBean {

	public M setShowId(java.lang.String showId) {
		set("SHOW_ID", showId);
		return (M)this;
	}
	
	public java.lang.String getShowId() {
		return getStr("SHOW_ID");
	}

	public M setID(java.lang.Long ID) {
		set("ID", ID);
		return (M)this;
	}
	
	public java.lang.Long getID() {
		return getLong("ID");
	}

	public M setFBID(java.lang.Integer FBID) {
		set("FBID", FBID);
		return (M)this;
	}
	
	public java.lang.Integer getFBID() {
		return getInt("FBID");
	}

	public M setSORT(java.lang.Integer SORT) {
		set("SORT", SORT);
		return (M)this;
	}
	
	public java.lang.Integer getSORT() {
		return getInt("SORT");
	}

	public M setSIMILARITY(java.lang.Integer SIMILARITY) {
		set("SIMILARITY", SIMILARITY);
		return (M)this;
	}
	
	public java.lang.Integer getSIMILARITY() {
		return getInt("SIMILARITY");
	}

	public M setTYPE(java.lang.Integer TYPE) {
		set("TYPE", TYPE);
		return (M)this;
	}
	
	public java.lang.Integer getTYPE() {
		return getInt("TYPE");
	}

	public M setMODIFYTIME(java.util.Date MODIFYTIME) {
		set("MODIFYTIME", MODIFYTIME);
		return (M)this;
	}
	
	public java.util.Date getMODIFYTIME() {
		return get("MODIFYTIME");
	}

}