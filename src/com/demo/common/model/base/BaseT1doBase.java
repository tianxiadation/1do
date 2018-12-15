package com.demo.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseT1doBase<M extends BaseT1doBase<M>> extends Model<M> implements IBean {

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

	public M setParentId(java.lang.String parentId) {
		set("PARENT_ID", parentId);
		return (M)this;
	}
	
	public java.lang.String getParentId() {
		return getStr("PARENT_ID");
	}

	public M setOTitle(java.lang.String oTitle) {
		set("O_TITLE", oTitle);
		return (M)this;
	}
	
	public java.lang.String getOTitle() {
		return getStr("O_TITLE");
	}

	public M setODescribe(java.lang.String oDescribe) {
		set("O_DESCRIBE", oDescribe);
		return (M)this;
	}
	
	public java.lang.String getODescribe() {
		return getStr("O_DESCRIBE");
	}

	public M setOCustomer(java.lang.String oCustomer) {
		set("O_CUSTOMER", oCustomer);
		return (M)this;
	}
	
	public java.lang.String getOCustomer() {
		return getStr("O_CUSTOMER");
	}

	public M setOCustomerName(java.lang.String oCustomerName) {
		set("O_CUSTOMER_NAME", oCustomerName);
		return (M)this;
	}
	
	public java.lang.String getOCustomerName() {
		return getStr("O_CUSTOMER_NAME");
	}

	public M setOStartTime(java.util.Date oStartTime) {
		set("O_START_TIME", oStartTime);
		return (M)this;
	}
	
	public java.util.Date getOStartTime() {
		return get("O_START_TIME");
	}

	public M setCreateUser(java.lang.String createUser) {
		set("CREATE_USER", createUser);
		return (M)this;
	}
	
	public java.lang.String getCreateUser() {
		return getStr("CREATE_USER");
	}

	public M setCreateUserName(java.lang.String createUserName) {
		set("CREATE_USER_NAME", createUserName);
		return (M)this;
	}
	
	public java.lang.String getCreateUserName() {
		return getStr("CREATE_USER_NAME");
	}

	public M setOCreateTime(java.util.Date oCreateTime) {
		set("O_CREATE_TIME", oCreateTime);
		return (M)this;
	}
	
	public java.util.Date getOCreateTime() {
		return get("O_CREATE_TIME");
	}

	public M setOFinishTime(java.util.Date oFinishTime) {
		set("O_FINISH_TIME", oFinishTime);
		return (M)this;
	}
	
	public java.util.Date getOFinishTime() {
		return get("O_FINISH_TIME");
	}

	public M setRealFinishTime(java.util.Date realFinishTime) {
		set("Real_FINISH_TIME", realFinishTime);
		return (M)this;
	}
	
	public java.util.Date getRealFinishTime() {
		return get("Real_FINISH_TIME");
	}

	public M setORange(java.lang.String oRange) {
		set("O_RANGE", oRange);
		return (M)this;
	}
	
	public java.lang.String getORange() {
		return getStr("O_RANGE");
	}

	public M setORangeName(java.lang.String oRangeName) {
		set("O_RANGE_NAME", oRangeName);
		return (M)this;
	}
	
	public java.lang.String getORangeName() {
		return getStr("O_RANGE_NAME");
	}

	public M setLastupdateTime(java.util.Date lastupdateTime) {
		set("LASTUPDATE_TIME", lastupdateTime);
		return (M)this;
	}
	
	public java.util.Date getLastupdateTime() {
		return get("LASTUPDATE_TIME");
	}

	public M setOIsDeleted(java.lang.Integer oIsDeleted) {
		set("O_IS_DELETED", oIsDeleted);
		return (M)this;
	}
	
	public java.lang.Integer getOIsDeleted() {
		return getInt("O_IS_DELETED");
	}

	public M setDeleteTime(java.util.Date deleteTime) {
		set("DELETE_TIME", deleteTime);
		return (M)this;
	}
	
	public java.util.Date getDeleteTime() {
		return get("DELETE_TIME");
	}

	public M setDShowId(java.lang.String dShowId) {
		set("D_SHOW_ID", dShowId);
		return (M)this;
	}
	
	public java.lang.String getDShowId() {
		return getStr("D_SHOW_ID");
	}

	public M setOTypeId(java.lang.String oTypeId) {
		set("O_TYPE_ID", oTypeId);
		return (M)this;
	}
	
	public java.lang.String getOTypeId() {
		return getStr("O_TYPE_ID");
	}

	public M setMessageId(java.lang.String messageId) {
		set("MESSAGE_ID", messageId);
		return (M)this;
	}
	
	public java.lang.String getMessageId() {
		return getStr("MESSAGE_ID");
	}

	public M setGROUPID(java.lang.String GROUPID) {
		set("GROUPID", GROUPID);
		return (M)this;
	}
	
	public java.lang.String getGROUPID() {
		return getStr("GROUPID");
	}

	public M setOExecutor(java.lang.String oExecutor) {
		set("O_EXECUTOR", oExecutor);
		return (M)this;
	}
	
	public java.lang.String getOExecutor() {
		return getStr("O_EXECUTOR");
	}

	public M setOExecutorName(java.lang.String oExecutorName) {
		set("O_EXECUTOR_NAME", oExecutorName);
		return (M)this;
	}
	
	public java.lang.String getOExecutorName() {
		return getStr("O_EXECUTOR_NAME");
	}

	public M setLIGHTNING(java.lang.Integer LIGHTNING) {
		set("LIGHTNING", LIGHTNING);
		return (M)this;
	}
	
	public java.lang.Integer getLIGHTNING() {
		return getInt("LIGHTNING");
	}

	public M setCC(java.lang.String CC) {
		set("CC", CC);
		return (M)this;
	}
	
	public java.lang.String getCC() {
		return getStr("CC");
	}

	public M setCcName(java.lang.String ccName) {
		set("CC_NAME", ccName);
		return (M)this;
	}
	
	public java.lang.String getCcName() {
		return getStr("CC_NAME");
	}

	public M setISLOOK(java.lang.Integer ISLOOK) {
		set("ISLOOK", ISLOOK);
		return (M)this;
	}
	
	public java.lang.Integer getISLOOK() {
		return getInt("ISLOOK");
	}

	public M setTYPE(java.lang.Integer TYPE) {
		set("TYPE", TYPE);
		return (M)this;
	}
	
	public java.lang.Integer getTYPE() {
		return getInt("TYPE");
	}

	public M setUserType(java.lang.Integer userType) {
		set("USER_TYPE", userType);
		return (M)this;
	}
	
	public java.lang.Integer getUserType() {
		return getInt("USER_TYPE");
	}

	public M setISAUDIT(java.lang.Integer ISAUDIT) {
		set("ISAUDIT", ISAUDIT);
		return (M)this;
	}
	
	public java.lang.Integer getISAUDIT() {
		return getInt("ISAUDIT");
	}

	public M setStar(java.lang.Integer star) {
		set("star", star);
		return (M)this;
	}
	
	public java.lang.Integer getStar() {
		return getInt("star");
	}

	public M setEvaluation(java.lang.String evaluation) {
		set("evaluation", evaluation);
		return (M)this;
	}
	
	public java.lang.String getEvaluation() {
		return getStr("evaluation");
	}

	public M setSendTime(java.lang.Long sendTime) {
		set("SEND_TIME", sendTime);
		return (M)this;
	}
	
	public java.lang.Long getSendTime() {
		return getLong("SEND_TIME");
	}

	public M setAT(java.lang.String AT) {
		set("AT", AT);
		return (M)this;
	}
	
	public java.lang.String getAT() {
		return getStr("AT");
	}

	public M setSOURCE(java.lang.Integer SOURCE) {
		set("SOURCE", SOURCE);
		return (M)this;
	}
	
	public java.lang.Integer getSOURCE() {
		return getInt("SOURCE");
	}

	public M setAPARAMETER(java.lang.Integer APARAMETER) {
		set("APARAMETER", APARAMETER);
		return (M)this;
	}
	
	public java.lang.Integer getAPARAMETER() {
		return getInt("APARAMETER");
	}

	public M setBPARAMETER(java.lang.Integer BPARAMETER) {
		set("BPARAMETER", BPARAMETER);
		return (M)this;
	}
	
	public java.lang.Integer getBPARAMETER() {
		return getInt("BPARAMETER");
	}

	public M setCPARAMETER(java.lang.Integer CPARAMETER) {
		set("CPARAMETER", CPARAMETER);
		return (M)this;
	}
	
	public java.lang.Integer getCPARAMETER() {
		return getInt("CPARAMETER");
	}

	public M setDPARAMETER(java.lang.Integer DPARAMETER) {
		set("DPARAMETER", DPARAMETER);
		return (M)this;
	}
	
	public java.lang.Integer getDPARAMETER() {
		return getInt("DPARAMETER");
	}

	public M setISAPPROVAL(java.lang.Boolean ISAPPROVAL) {
		set("ISAPPROVAL", ISAPPROVAL);
		return (M)this;
	}
	
	public java.lang.Boolean getISAPPROVAL() {
		return get("ISAPPROVAL");
	}

}
