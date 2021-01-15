package com.luqi.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseHoliday<M extends BaseHoliday<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setDate(java.lang.String date) {
		set("date", date);
		return (M)this;
	}
	
	public java.lang.String getDate() {
		return getStr("date");
	}

	public M setWeek(java.lang.String week) {
		set("week", week);
		return (M)this;
	}
	
	public java.lang.String getWeek() {
		return getStr("week");
	}

	public M setType(java.lang.Integer type) {
		set("type", type);
		return (M)this;
	}
	
	public java.lang.Integer getType() {
		return getInt("type");
	}

	public M setDesc(java.lang.String desc) {
		set("desc", desc);
		return (M)this;
	}
	
	public java.lang.String getDesc() {
		return getStr("desc");
	}

	public M setWeekOfYear(java.lang.String weekOfYear) {
		set("week_of_year", weekOfYear);
		return (M)this;
	}
	
	public java.lang.String getWeekOfYear() {
		return getStr("week_of_year");
	}

	public M setModifytime(java.util.Date modifytime) {
		set("modifytime", modifytime);
		return (M)this;
	}
	
	public java.util.Date getModifytime() {
		return get("modifytime");
	}

	public M setDateTime(java.util.Date dateTime) {
		set("date_time", dateTime);
		return (M)this;
	}
	
	public java.util.Date getDateTime() {
		return get("date_time");
	}

	public M setTime(java.lang.String time) {
		set("time", time);
		return (M)this;
	}
	
	public java.lang.String getTime() {
		return getStr("time");
	}

	public M setDay(java.lang.String day) {
		set("day", day);
		return (M)this;
	}
	
	public java.lang.String getDay() {
		return getStr("day");
	}

	public M setWeeknum(java.lang.String weeknum) {
		set("weeknum", weeknum);
		return (M)this;
	}
	
	public java.lang.String getWeeknum() {
		return getStr("weeknum");
	}

	public M setData(java.lang.String data) {
		set("data", data);
		return (M)this;
	}
	
	public java.lang.String getData() {
		return getStr("data");
	}

	public M setItemId(java.lang.Long itemId) {
		set("item_id", itemId);
		return (M)this;
	}
	
	public java.lang.Long getItemId() {
		return getLong("item_id");
	}

	public M setAtemp(java.lang.String atemp) {
		set("atemp", atemp);
		return (M)this;
	}
	
	public java.lang.String getAtemp() {
		return getStr("atemp");
	}

	public M setBtemp(java.lang.String btemp) {
		set("btemp", btemp);
		return (M)this;
	}
	
	public java.lang.String getBtemp() {
		return getStr("btemp");
	}

	public M setDtemp(java.lang.String dtemp) {
		set("dtemp", dtemp);
		return (M)this;
	}
	
	public java.lang.String getDtemp() {
		return getStr("dtemp");
	}

}