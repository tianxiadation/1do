package com.luqi.common.model;

import java.util.HashSet;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.luqi.common.model.base.BaseT1doLabel;
import com.luqi.util.DbUtil;

/**
 * Generated by JFinal.
 * 遗留问题 标签删除、相似度超100
 */
@SuppressWarnings("serial")
public class T1doLabel extends BaseT1doLabel<T1doLabel> {
	public static final T1doLabel dao = new T1doLabel().dao();
	/*
	 2019年2月12日 coco 注解：批量添加标签
	*/
	public static void saveList(String result,String showId,Integer i) {
		Db.delete("delete from t_1do_label where SHOW_ID=? and type=1",showId);
		String[] str=result.split("\\|");
		HashSet<String> hs = new HashSet<String>();
		for (String string : str) {
			if(StrKit.notBlank(string))
			hs.add(string);
		}
		hs.forEach(h->{
			new T1doLabel().setShowId(showId).setLABEL(h).save();
		});
		//DbUtil.saveWeight1(showId);//增加权重标签
		T1doRelation.updateSimilarity(showId,"",i);
	}
	public static void saveList(String result,String showId) {
		String[] str=result.split("\\|");
		HashSet<String> hs = new HashSet<String>();
		for (String string : str) {
			if(StrKit.notBlank(string))
				hs.add(string);
		}
		hs.forEach(h->{
			new T1doLabel().setShowId(showId).setLABEL(h).save();
		});
	}
	/*
	 2019年2月12日 coco 注解：
	*/
	public static T1doLabel getT1doLabel(JSONObject json) {
		return T1doLabel.dao.findFirst("select * from t_1do_label where "
				+ "SHOW_ID=? and LABEL=?",json.getString("SHOW_ID"),json.getString("label"));
	}
}