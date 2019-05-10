package com.demo.util;

import com.jfinal.plugin.activerecord.Db;

public class DbUtil {
	/*
	 2019年5月9日 coco 注解：
	*/
	public static void insertIdo(String SHOW_ID) {
	Db.update("insert into t_1do_relation(SHOW_ID,RELATION_SHOW_ID,SIMILARITY) "
		+" select a.SHOW_ID,b.SHOW_ID RELATION_SHOW_ID,(CASE WHEN count(*)/c.cnum*100>count(*)/d.dnum*100 THEN count(*)/d.dnum*100 ELSE count(*)/c.cnum*100 END )SIMILARITY"
		+" from t_1do_label a,t_1do_label b,(select count(*) cnum,SHOW_ID from t_1do_label GROUP BY SHOW_ID) c,"
		+" (select count(*) dnum,SHOW_ID from t_1do_label  GROUP BY SHOW_ID) D"
		+" where a.LABEL=b.LABEL and a.SHOW_ID=? and b.SHOW_ID!=? and a.SHOW_ID=c.SHOW_ID and b.SHOW_ID=d.SHOW_ID"
		+" group by a.SHOW_ID,b.SHOW_ID ",SHOW_ID,SHOW_ID);
	Db.update("insert into t_1do_relation(SHOW_ID,RELATION_SHOW_ID,SIMILARITY) "
		+" select b.SHOW_ID,a.SHOW_ID RELATION_SHOW_ID ,(CASE WHEN count(*)/c.cnum*100>count(*)/d.dnum*100 THEN count(*)/d.dnum*100 ELSE count(*)/c.cnum*100 END )SIMILARITY"
		+" from t_1do_label a,t_1do_label b,(select count(*) cnum,SHOW_ID from t_1do_label GROUP BY SHOW_ID) c,"
		+" (select count(*) dnum,SHOW_ID from t_1do_label  GROUP BY SHOW_ID) D"
		+" where a.LABEL=b.LABEL and a.SHOW_ID=? and b.SHOW_ID!=? and a.SHOW_ID=c.SHOW_ID and b.SHOW_ID=d.SHOW_ID"
		+" group by a.SHOW_ID,b.SHOW_ID ",SHOW_ID,SHOW_ID);
	Db.update("insert into t_1do_relation(SHOW_ID,RELATION_SHOW_ID,SIMILARITY) "+
             " select ?,SHOW_ID,0 from t_1do_base where SHOW_ID "+
              "not in(select RELATION_SHOW_ID from t_1do_relation where SHOW_ID=?) "+
             " and SHOW_ID!=?",SHOW_ID,SHOW_ID,SHOW_ID);
	Db.update("insert into t_1do_relation(SHOW_ID,RELATION_SHOW_ID,SIMILARITY) "+
               "select RELATION_SHOW_ID,SHOW_ID,0 from t_1do_relation where SHOW_ID=? "+
               "and SIMILARITY=0",SHOW_ID);
	}
	/*
	 2019年5月7日 coco 注解：批量添加相关日志（添加1DO时）
	*/
	public static void insertlr1(String SHOW_ID) {
		Db.update("insert into t_1do_relation_record(SHOW_ID,RECORDID,SIMILARITY) "
				+" select a.SHOW_ID,b.RECORDID,(CASE WHEN count(*)/c.cnum*100>count(*)/d.dnum*100 THEN count(*)/d.dnum*100 ELSE count(*)/c.cnum*100 END )SIMILARITY"
				+" from t_1do_label a,t_1do_label_record b,(select count(*) cnum,SHOW_ID from t_1do_label GROUP BY SHOW_ID) c,"
				+" (select count(*) dnum,RECORDID from t_1do_label_record GROUP BY RECORDID) D"
				+" where a.LABEL=b.LABEL and a.SHOW_ID=? and a.SHOW_ID=c.SHOW_ID and b.RECORDID=d.RECORDID"
				+" group by SHOW_ID,RECORDID ",SHOW_ID);
		
	}
	/*
	 2019年5月7日 coco 注解：批量添加相关日志（添加日志时）
	*/
	public static void insertlr(Long RECORDID) {
		Db.update("insert into t_1do_relation_record(SHOW_ID,RECORDID,SIMILARITY) "
				+" select a.SHOW_ID,b.RECORDID,(CASE WHEN count(*)/c.cnum*100>count(*)/d.dnum*100 THEN count(*)/d.dnum*100 ELSE count(*)/c.cnum*100 END )SIMILARITY"
				+" from t_1do_label a,t_1do_label_record b,(select count(*) cnum,SHOW_ID from t_1do_label GROUP BY SHOW_ID) c,"
				+" (select count(*) dnum,RECORDID from t_1do_label_record GROUP BY RECORDID) D"
				+" where a.LABEL=b.LABEL and b.RECORDID=? and a.SHOW_ID=c.SHOW_ID and b.RECORDID=d.RECORDID"
				+" group by SHOW_ID,RECORDID ",RECORDID);
		
	}
	/*
	 2019年5月7日 coco 注解：批量添加相关反馈（添加1do时）
	*/
	public static void insertRF(String SHOW_ID) {
		Db.update("insert into t_1do_relation_feedback(SHOW_ID,FBID,SIMILARITY) "+
				"select a.SHOW_ID,b.FBID,(CASE WHEN count(*)/c.cnum*100>count(*)/d.dnum*100 THEN count(*)/d.dnum*100 ELSE count(*)/c.cnum*100 END )SIMILARITY "+
				"from t_1do_label a,t_1do_label_feedback b,(select count(*) cnum,SHOW_ID from t_1do_label GROUP BY SHOW_ID) c,"+
				"(select count(*) dnum,FBID from t_1do_label_feedback GROUP BY FBID) D "+
				"where a.LABEL=b.LABEL and a.SHOW_ID=? and a.SHOW_ID=c.SHOW_ID and b.FBID=d.FBID "+
				"group by SHOW_ID,FBID ",SHOW_ID);
	}
	/*
	 2019年5月7日 coco 注解：批量添加相关反馈（添加反馈时）
	 */
	public static void insertRF1(Integer FBID) {
		Db.update("insert into t_1do_relation_feedback(SHOW_ID,FBID,SIMILARITY) "+
				"select a.SHOW_ID,b.FBID,(CASE WHEN count(*)/c.cnum*100>count(*)/d.dnum*100 THEN count(*)/d.dnum*100 ELSE count(*)/c.cnum*100 END )SIMILARITY "+
				"from t_1do_label a,t_1do_label_feedback b,(select count(*) cnum,SHOW_ID from t_1do_label GROUP BY SHOW_ID) c,"+
				"(select count(*) dnum,FBID from t_1do_label_feedback GROUP BY FBID) D "+
				"where a.LABEL=b.LABEL and b.FBID=? and a.SHOW_ID=c.SHOW_ID and b.FBID=d.FBID "+
				"group by SHOW_ID,FBID ",FBID);
	}
	/*
	 2019年5月6日 coco 注解：重启服务器后把在线状态全部改成离线状态
	*/
	public static void updateOnline() {
		Db.update("update t_1do_urge_websocket set isOnline=0");
		Db.update("update websocket set isOnline=0");
		Db.update("update t_1do_pstatus set `online`=2");
	}
	/*
	 2019年5月5日 coco 注解：修改关联工单父子工单的状态
	*/
	public static void updateType(String showid) {
		Db.update("update t_1do_relation a,t_1do_base b set a.SORT=-2,a.TYPE=-1 where "+
                  "a.SHOW_ID=b.SHOW_ID and a.RELATION_SHOW_ID=b.PARENT_ID and "+
                  "b.SHOW_ID=?",showid);
		Db.update("update t_1do_relation a,t_1do_base b set a.SORT=-3,a.TYPE=-2 where "+
				  "a.SHOW_ID=b.PARENT_ID and a.RELATION_SHOW_ID=b.SHOW_ID and "+
				  "b.PARENT_ID=?",showid);
		Db.update("update t_1do_relation a,t_1do_base b set a.SORT=-3,a.TYPE=-2 where "+
                  "a.SHOW_ID=b.PARENT_ID and a.RELATION_SHOW_ID=b.SHOW_ID and "+
                  "b.SHOW_ID=?",showid);
		Db.update("update t_1do_relation a,t_1do_base b set a.SORT=-2,a.TYPE=-1 where "+
				  "a.SHOW_ID=b.SHOW_ID and a.RELATION_SHOW_ID=b.PARENT_ID and "+
				  "b.PARENT_ID=?",showid);
	}
	/*
	 2019年4月1日 coco 注解：修改ido工单没有处理人的时候，第一个参与人为处理人
	*/
	public static int updateExecutore(String showid) {
		
		return Db.update("update t_1do_pstatus set otherid=1 where "
				+ "id=(select id from( select * from t_1do_pstatus "
				+ "where SHOW_ID=? and USER_TYPE=3 "
				+ "and isDelete=1 ORDER BY otherid)a GROUP BY SHOW_ID) "
				+ "and otherid=2",showid);
	}
	/*
	 2019年3月8日 coco 注解：
	*/
	public static int update(String showid,String showid1,int type,int temp,String showid2,String showid3) {
		switch (type) {
		case 1:			
			//删除1do关联
			return Db.update("update t_1do_relation a,t_1do_base b set a.sort=0,a.TYPE=b.o_status "
					+ "where ((a.SHOW_ID=? and a.RELATION_SHOW_ID=?) or (a.SHOW_ID=? and a.RELATION_SHOW_ID=?)) and b.SHOW_ID=a.RELATION_SHOW_ID and a.TYPE=0 "
					,showid,showid1,showid1,showid);
		case 2:		
			//批量添加1do关联
			return Db.update("update t_1do_relation set sort=-1,TYPE=0 "
					+ "where (SHOW_ID=? and RELATION_SHOW_ID=?) or (SHOW_ID=? and RELATION_SHOW_ID=?)"
					,showid,showid1,showid1,showid);
		case 3:
			//修改参与人的身份（抄送人或受理人）
			return Db.update("update t_1do_pstatus set otherid=0 where"
					+ " otherid=? and SHOW_ID=? ",temp,showid);
			
		case 4:
			//修改参与人的身份（抄送人或受理人）
			return Db.update("update t_1do_pstatus set otherid=? where"
					+ " SHOW_ID=? and O_USER=?",temp,showid,showid1);
		case 5:
			return Db.update("update t_1do_pstatus set isSend=1,urge_isLook=1 where SHOW_ID=? and O_USER=? and isDelete=1 and USER_TYPE!=2 and (online=2 or gmt_modified<CURDATE())",showid,showid1);
		case 6:	
			return Db.update("update t_1do_fwpstatus set isSend=1 where SHOW_ID=? and O_USER=? and (online=2 or gmt_modified<CURDATE())",showid,showid1);
		case 7:
			//在线已读
			Db.update("update t_1do_pstatus set online=2  where SHOW_ID!=? and O_USER=?",showid,showid1);
			Db.update("update t_1do_pstatus set online=1,isSend=1,urge_isLook=1 where SHOW_ID=? and O_USER=?",showid,showid1);
			Db.update("update t_1do_fwpstatus set online=1,isSend=1 where SHOW_ID=? and O_USER=?",showid,showid1);
			return 1;
		case 8:		
			//修改创建用户
			Db.update("update t_1do_base set CREATE_USER=?,CREATE_USER_NAME=? where CREATE_USER='1call' and SHOW_ID=?",showid1,showid2,showid);
			return 1;
		case 9:
			//修改标题或者内容
			return Db.update("UPDATE t_1do_base set "+showid+"=? ,AT=? where SHOW_ID=?",showid1,showid2,showid3);
		case 10:
			//删除用户
			Db.update("update t_1do_pstatus set isDelete=2  where SHOW_ID=? and O_USER not in("+showid+") and USER_TYPE=?",showid1,temp);
			return 1;
		case 11:			
			return Db.update("update t_1do_pstatus set isDelete=1 where SHOW_ID=? and O_USER=? and USER_TYPE=?",showid,showid1,temp);
		case 12:	
			Db.update("update t_1do_pstatus set isDelete=2  where SHOW_ID=? and O_USER=? and USER_TYPE=?",showid,showid1,temp);
			return 1;
		case 13:		
			//恢复被删除的用户
			return Db.update("update t_1do_pstatus set isDelete=1 where SHOW_ID=? and O_USER=? and USER_TYPE=?",showid,showid1,temp);

		case 14:	
			Db.update("update t_1do_pstatus set online=2 where SHOW_ID=? and O_USER=?",showid,showid1);
			Db.update("update t_1do_fwpstatus set online=2 where SHOW_ID=? and O_USER=?",showid,showid1);
			return 1;
		case 15:
			//第一个参与人反馈修改人员状态
			//Db.update("update t_1do_pstatus set O_STATUS=4,STATUS='已接单' where SHOW_ID=? and USER_TYPE!=3",showid);
			Db.update("update t_1do_pstatus set O_STATUS=4,STATUS='已接单' where SHOW_ID=? ",showid);
			//Db.update("update t_1do_pstatus set isSend=2  where  (online=2 or gmt_modified<CURDATE()) and SHOW_ID=? and (USER_TYPE=1 or O_USER=? ) and isDelete=1",showid,showid1);
			Db.update("update t_1do_pstatus set isSend=2  where  (online=2 or gmt_modified<CURDATE()) and SHOW_ID=? and (USER_TYPE=1 or O_USER=? ) and isDelete=1",showid,showid1);
			Db.update("update t_1do_fwpstatus set isSend=2 where SHOW_ID=? and (online=2 or gmt_modified<CURDATE()) ",showid);
			
			return 1;
		case 16:	
			//反馈设置用户未读
			//Db.update("update t_1do_pstatus set O_STATUS=4,STATUS='已接单' where SHOW_ID=? and O_USER=? and O_STATUS=3",showid,showid1);
			Db.update("update t_1do_pstatus set isSend=2 where SHOW_ID=? and isDelete=1 and (online=2 or gmt_modified<CURDATE())",showid);
			Db.update("update t_1do_fwpstatus set isSend=2 where SHOW_ID=? and (online=2 or gmt_modified<CURDATE())",showid);
			return 1;
		case 17:			
			return 1;
		case 18:			
			return 1;
		case 19:			
			return 1;
		default:
			return 1;
		}
	}
	
}
