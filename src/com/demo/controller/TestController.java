package com.demo.controller;

import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.demo.common.model.T1doBase;
import com.demo.common.model.T1doLabel;
import com.demo.common.model.T1doRelation;
import com.demo.util.HttpUtil;
import com.demo.util.StrUtil;
import com.jfinal.core.Controller;

public class TestController extends Controller{
	
	/*
	 2019年2月12日 coco 注解：给所有1do打上标签
	*/
	public void addLabel() {
		List<T1doBase> list=T1doBase.dao.find("select * from t_1do_base ");
		list.forEach(t->{
			//new Thread(new AddLabel(t.getODescribe(),t.getShowId())).start(); 
			T1doLabel.saveList(HttpUtil.getlabel(t.getODescribe()),t.getShowId());
		});
		renderJson(1);
	}
	/*
	 2019年2月12日 coco 注解：//计算关联1do
	*/
	public void relation() {
		List<T1doBase> base=T1doBase.dao.find("select * from t_1do_base  ");
		base.forEach(t->{
			List<T1doLabel> list=T1doLabel.dao.find("select * from t_1do_label where SHOW_ID=?",t.getShowId());
			List<T1doBase> base1=T1doBase.dao.find("select * from t_1do_base where ID>? ",t.getID());
			base1.forEach(t1->{
				List<T1doLabel> list1=T1doLabel.dao.find("select * from t_1do_label where SHOW_ID=?",t1.getShowId());
				int result=getHashSet(list, list1);
				int i=list.size()>list1.size()?list.size():list1.size();
				int a=(int) ((double)(list.size()+list1.size()-result)/i*100);
				T1doRelation.saveRelation(t.getShowId(), t1.getShowId(), a,t1.getOStatus());
				T1doRelation.saveRelation(t1.getShowId(), t.getShowId(), a,t.getOStatus());
				
			});
		});
		renderJson(1);
	}
	/*
	 2019年2月12日 coco 注解：
	*/
	public static int getHashSet(List<T1doLabel> list,List<T1doLabel> list1) {
		HashSet<String> hs = new HashSet<String>();
		list.forEach(t->{
			hs.add(t.getLABEL());
		});
		list1.forEach(t->{
			hs.add(t.getLABEL());
		});
		return hs.size();
		
	}
	public static void main(String[] args) {
		String content="发起人在内容中@或者“请余年牵头”字样，xxx为受理人，如果内容中无受理人，则受理人默认为第一个参与人；"
				+ "如有其他参与者，且只有一个受理人，删除受理人时，默认顺位第一人为受理人请方升群牵头";
		/*HashSet<String> list=StrUtil.getQTR(content,"[\"6ZYbRyY7mDivQ5e7@焦俊\",\"v65VKKvaaZT0dZAJ@陆芸\"]");
		list.forEach(t->{
			System.out.println(t);
		});*/
	}
}
