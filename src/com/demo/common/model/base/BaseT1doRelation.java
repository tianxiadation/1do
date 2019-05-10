package com.demo.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseT1doRelation<M extends BaseT1doRelation<M>> extends Model<M> implements IBean {

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

	public M setRelationShowId(java.lang.String relationShowId) {
		set("RELATION_SHOW_ID", relationShowId);
		return (M)this;
	}
	
	public java.lang.String getRelationShowId() {
		return getStr("RELATION_SHOW_ID");
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

	public M setWEIGHT(java.lang.Integer WEIGHT) {
		set("WEIGHT", WEIGHT);
		return (M)this;
	}
	
	public java.lang.Integer getWEIGHT() {
		return getInt("WEIGHT");
	}

}
