package com.luqi.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseNotice<M extends BaseNotice<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setTest(java.lang.String test) {
		set("test", test);
		return (M)this;
	}
	
	public java.lang.String getTest() {
		return getStr("test");
	}

	public M setParameter(java.lang.String parameter) {
		set("parameter", parameter);
		return (M)this;
	}
	
	public java.lang.String getParameter() {
		return getStr("parameter");
	}

	public M setResult(java.lang.String result) {
		set("result", result);
		return (M)this;
	}
	
	public java.lang.String getResult() {
		return getStr("result");
	}

	public M setCreateTime(java.util.Date createTime) {
		set("createTime", createTime);
		return (M)this;
	}
	
	public java.util.Date getCreateTime() {
		return get("createTime");
	}

	public M setModifyTime(java.util.Date modifyTime) {
		set("modifyTime", modifyTime);
		return (M)this;
	}
	
	public java.util.Date getModifyTime() {
		return get("modifyTime");
	}

}