package com.luqi.common.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.luqi.common.model.base.BaseT1doLabelFeedback;
import com.luqi.controller.TestController;
import com.luqi.timer.AddLabel;
import com.luqi.util.DbUtil;
import com.luqi.util.HttpUtil;
import com.luqi.util.StrUtil;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class T1doLabelFeedback extends BaseT1doLabelFeedback<T1doLabelFeedback> {
	public static final T1doLabelFeedback dao = new T1doLabelFeedback().dao();

		/*
		 2019年5月5日 coco 注解：保存反馈标签
		*/
		public static void saveFbLable(Integer FBID,String LABEL) {
			 new T1doLabelFeedback().setFBID(FBID).setLABEL(LABEL).save();
		}
		public static T1doLabelFeedback saveFbLable1(Integer FBID,String LABEL) {
			return new T1doLabelFeedback().setFBID(FBID).setLABEL(LABEL);
		}
		/*
		 2019年5月5日 coco 注解：保存所有解析标签
		*/
		public static void saveAllLabel(T1doFeedback fb) {
			//HashSet<String> hs = StrUtil.getHashSet(HttpUtil.getlabel(fb.getFBCONTENT()));
			HashSet<String> hs = StrUtil.getHashSet(AddLabel.getLebel(fb.getFBCONTENT()));
			List<T1doLabelFeedback> list=new ArrayList<T1doLabelFeedback>();
			if(hs.size()>0){
				hs.forEach(h->{
					list.add(saveFbLable1(fb.getID(), h));
					//saveFbLable(fb.getID(), h);
				});
			}
			Db.batchSave(list, 50);
			//DbUtil.saveWeight2(fb.getID());
			DbUtil.insertRF1(fb.getID());
		}
		/*
		 2019年5月5日 coco 注解：解析标签(过期)
		 */
		public static void savelabel111(T1doFeedback fb) {
			HashSet<String> hs = StrUtil.getHashSet(HttpUtil.getlabel(fb.getFBCONTENT()));
			if(hs.size()>0){
				hs.forEach(h->{
					saveFbLable(fb.getID(), h);
				});
				DbUtil.saveWeight2(fb.getID());
				DbUtil.insertRF1(fb.getID());
				/*List<T1doBase> base1=T1doBase.dao.find("select * from t_1do_base where SHOW_ID!=? ",fb.getShowId());
				base1.forEach(t1->{
					List<T1doLabel> list1=T1doLabel.dao.find("select * from t_1do_label where SHOW_ID=?",t1.getShowId());
					int result=TestController.getHashSet1(hs,list1);
					int i=hs.size()>list1.size()?hs.size():list1.size();
					int a=(int) ((double)(hs.size()+list1.size()-result)/i*100);
					T1doRelationFeedback.saveRF(t1.getShowId(), fb.getID(), a);
				});*/
			}
		}
		/*
		 2019年5月5日 coco 注解：新增1do解析标签
		 */
		public static void savelabel(T1doBase t) {
			List<T1doLabel> list1=T1doLabel.dao.find("select * from t_1do_label where SHOW_ID=?",t.getShowId());
			/*List<T1doFeedback> list=T1doFeedback.dao.find("select * from t_1do_feedback where SHOW_ID!=?"
                               + " and FB_TYPE=1 or FB_TYPE=2 or FB_TYPE=6",t.getShowId());*/
			List<T1doFeedback> list=T1doFeedback.dao.find("select FBID ID from t_1do_label_feedback a,t_1do_feedback b  where a.FBID=b.ID and b.SHOW_ID!=? GROUP BY FBID",t.getShowId());
			list.forEach(t1->{
				
				List<T1doLabelFeedback> list2=T1doLabelFeedback.dao.find("select * from t_1do_label_feedback where FBID=?",t1.getID());
				if(list2.size()>0){
					
					int result=TestController.getHashSet2(list2,list1);
					int i=list2.size()>list1.size()?list2.size():list1.size();
					int a=(int) ((double)(list2.size()+list1.size()-result)/i*100);
					T1doRelationFeedback.saveRF(t.getShowId(), t1.getID(), a);
				}
			});
		}
		
}