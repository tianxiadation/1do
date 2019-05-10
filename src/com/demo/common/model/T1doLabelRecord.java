package com.demo.common.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.demo.common.model.base.BaseT1doLabelRecord;
import com.demo.util.DbUtil;
import com.demo.util.HttpUtil;
import com.demo.util.StrUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class T1doLabelRecord extends BaseT1doLabelRecord<T1doLabelRecord> {
	public static final T1doLabelRecord dao = new T1doLabelRecord().dao();
	/*
	 2019年5月5日 coco 注解：保存日志标签
	*/
	public static void saveLrLable(Long RRCORDID,String LABEL) {
		 new T1doLabelRecord().setRECORDID(RRCORDID).setLABEL(LABEL).save();
	}
	public static T1doLabelRecord saveLrLable1(Long RRCORDID,String LABEL) {
		return new T1doLabelRecord().setRECORDID(RRCORDID).setLABEL(LABEL);
	}
	/*
	 2019年5月5日 coco 注解：保存所有解析标签
	*/
	public static void saveAllLabel(T1doRecord lr) {
		HashSet<String> hs = StrUtil.getHashSet(HttpUtil.getlabel(lr.getRECORD()));
		List<T1doLabelRecord> list=new ArrayList<T1doLabelRecord>();
		if(hs.size()>0){
			hs.forEach(h->{
				list.add(saveLrLable1(lr.getRECORDID(), h));
				//saveFbLable(fb.getID(), h);
			});
		}
		Db.batchSave(list, 50);
		DbUtil.saveWeight3(lr.getRECORDID());
		DbUtil.insertlr(lr.getRECORDID());
	}
	/*
	 2019年5月5日 coco 注解：保存所有解析标签
	 */
	public static void saveAllLabel1(Record lr) {
		HashSet<String> hs = StrUtil.getHashSet(HttpUtil.getlabel(lr.getStr("RECORD")));
		List<T1doLabelRecord> list=new ArrayList<T1doLabelRecord>();
		if(hs.size()>0){
			hs.forEach(h->{
				list.add(saveLrLable1(lr.getLong("RECORDID"), h));
				//saveFbLable(fb.getID(), h);
			});
		}
		Db.batchSave(list, 50);
		DbUtil.saveWeight3(lr.getLong("RECORDID"));
		DbUtil.insertlr(lr.getLong("RECORDID"));
	}
	/*
	 2019年5月8日 coco 注解：
	*/
	public static void batchSaveAllLabel() {
		List<T1doRecord> list=T1doRecord.dao.find("select * from t_1do_record");
		list.forEach(t->{
			saveAllLabel(t);
			
		});
		//return "";
	}
	
}
