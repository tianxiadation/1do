package com.luqi.service;

import java.text.SimpleDateFormat;
import java.util.*;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.luqi.common.model.*;
import com.luqi.dao.LogDao;
import com.luqi.util.DbUtil;
import com.luqi.util.HttpUtil;
import com.luqi.util.StrUtil;
import com.luqi.util.TimeUtil;

/**
* @author coco
* @date 2019年8月21日 下午3:26:56
* 
*/
public class BoardTaskService {
	/**   
	　* 描述：   完整度进展计算
	　* 创建人：coco   
	　* 创建时间：2019年8月21日 下午5:23:50         
	*/
	public static void TaskCalculate(Date date) {
		 //获取所有项目
		List<T1doProject> ts=T1doProject.getAllProject();
		for (T1doProject t : ts) {
		
			 //获取所有项目子节点
			List<T1doBoard> items= T1doBoard.getChildren(t.getItemId());
			 //所有节点的id
	        StringBuilder ids = new StringBuilder();
	        for (T1doBoard record : items) {
	            ids.append(record.getID()).append(",");
	        }
	        Record t1dobase =null;
	        if(ids.length()>0)
	         t1dobase =T1doBase.selectMaxAndMinT1doProject1do(ids.deleteCharAt(ids.length()-1));
	        
			T1doBoardTask tt=new T1doBoardTask();
			tt.setDATE(date);
			tt.setProjectId(t.getItemId());
			if(t1dobase==null|| t1dobase.getStr("O_FINISH_TIME")==null||t1dobase.getStr("O_CREATE_TIME")==null) {
				tt.setDATA("[]").setDTEMP("[]");
				tt.setPRINCIPLE("");
				tt.save();
				tt.remove("ID");
				tt.setDATA("[]").setDTEMP("[]");
				tt.setTASK("任务");
				tt.setCOMPLETION("完成情况");
				tt.setPRINCIPLE("负责人");
				tt.setPlannedTime("计划时间");
				tt.save();
				tt.remove("ID");
			}else {
				//Db.update("update holiday set data= right(time,2) where date BETWEEN ? and ?",);
				//判断今天是否在最大的拟完成时间之后，是的话取今天的值
				String str=TimeUtil.getBetweenDays(TimeUtil.getDate(date), t1dobase.getStr("O_FINISH_TIME"))?TimeUtil.getDate(date): t1dobase.getStr("O_FINISH_TIME");
				Db.update("update holiday set item_id= ? where date BETWEEN ? and ?",t.getItemId(),t1dobase.getStr("O_CREATE_TIME"),str);
				Holiday h=Holiday.getHoliday(t.getItemId());
						
				tt.setDATA(h.getWeeknum()).setDTEMP(h.getWeeknum());
				tt.setPRINCIPLE("");
				tt.save();
				tt.remove("ID");
				tt.setDATA(h.getTime()).setDTEMP(h.getTime());
				tt.setTASK("任务");
				tt.setCOMPLETION("完成情况");
				tt.setPRINCIPLE("负责人");
				tt.setPlannedTime("计划时间");
				tt.save();
				tt.remove("ID");
			}
			for (T1doBoard it : items) {
				tt.setTASK(it.getItemName());
				tt.setCOMPLETION(it.getCOMPLETION());
				String[] str1=T1doProjectStakeholder.getPRINCIPLE(it.getID());
				tt.setPRINCIPLE(str1[1]);
				tt.setPrincipleShowId(str1[0]);
				 //获取项目子节点的关联1do
				tt.setTASK(it.getItemName());
				tt.setItemId(it.getID());
				List<T1doBase> list=T1doBase.selectT1doProject1do(it.getID());
				String[] str=StrUtil.getPlannedDate(list);
				tt.setPlannedDate(str[0]);
				tt.setPlannedTime(str[1]);
				//Db.update("update holiday set data='',atemp='',btemp='',dtemp=''");
				Db.update("update holiday set data= right(time,2),atemp=right(time,2),dtemp=right(time,2),btemp=right(time,2)");
				int k=0;
				T1doBoardTask1do tt1do=new T1doBoardTask1do();
				tt1do.setItemId(it.getID());
				tt1do.setDATE(date);
				for (T1doBase tb : list) {
					//计算各个1do的甘特图
					tt1do.setPlannedTime(TimeUtil.getDate(tb.getOStartTime())+"~"+TimeUtil.getDate(tb.getOFinishTime()));
					calculation(tb,t,it,k,tt,date,1);
					//Db.update("update holiday set atemp='' where date>? and item_id=?",TimeUtil.getDate(date),t.getItemId());
					//Db.update("update holiday set atemp='A' where date=? and item_id=?",TimeUtil.getDate(date),t.getItemId());
					Holiday h=Holiday.getHolidayData(t.getItemId());
					
					tt1do.setShowId(tb.getShowId());
					if(h!=null&&h.getData()!=null) {
					  tt1do.setDATA(h.getData());
					}else {
						tt1do.setDATA("[]");	
					}
					tt1do.save();
					tt1do.remove("ID");
					//Db.update("update holiday set data='',atemp='',dtemp=''");
					Db.update("update holiday set data= right(time,2),atemp=right(time,2),dtemp=right(time,2),btemp=right(time,2)");

				}
				k=0;
				//ABC表示已完成、骷髅、闪电  //D表示当天有关联的日志
				Db.update("update holiday set data='D' where date in (SELECT DATE FROM `t_1do_board_daliy_report` where TASK_ID=? GROUP BY DATE)",it.getID());

				for (T1doBase tb : list) {
					//计算甘特图
					calculation(tb,t,it,k,tt,date,2);
					
				}
				
				Db.update("update holiday set dtemp='D' where date=? and item_id=? and data not in ('B','C','D')",TimeUtil.getDate(date),t.getItemId());
				//Holiday h=Holiday.dao.findFirst("select CONCAT('[',GROUP_CONCAT('\"',data,'\"'),']')data,CONCAT('[',GROUP_CONCAT('\"',atemp,'\"'),']')atemp from holiday where item_id=?",t.getItemId());
				
				if(it.getCOMPLETION().equals("已完成")) {					
					Db.update("update holiday set data= ''  where date>? and item_id=?",TimeUtil.getDate(it.getFinishDate()),t.getItemId());
					Db.update("update holiday set data= 'A'  where date=? and item_id=?",TimeUtil.getDate(it.getFinishDate()),t.getItemId());

					Holiday h=Holiday.getHolidayData(t.getItemId());
					if(h!=null&&h.getData()!=null) {
						tt.setDATA(h.getData()).setBTEMP(h.getBtemp()).setDTEMP(h.getDtemp());
					}else {
					   tt.setDATA("[]").setBTEMP("[]").setDTEMP("[]");
					   		
					}
					tt.save();
					tt.remove("ID");
					
				}else {
					Db.update("update holiday set atemp='' where date>? and item_id=?",TimeUtil.getDate(date),t.getItemId());
					Db.update("update holiday set atemp='A' where date=? and item_id=?",TimeUtil.getDate(date),t.getItemId());
					Holiday h=Holiday.getHolidayData(t.getItemId());
					if(h!=null&&h.getData()!=null) {
						tt.setDATA(h.getData()).setATEMP(h.getAtemp()).setDTEMP(h.getDtemp());
					}else {
					   tt.setDATA("[]").setATEMP("[]").setDTEMP("[]");
					   		
					}
					tt.save();
					tt.setATEMP(null);
					tt.remove("ID");
				}
				
				
				
			}   
			//回归初始状态
		//Db.update("update holiday set item_id= null,data='',atemp='',dtemp=''");
			Db.update("update holiday set data= right(time,2),atemp=right(time,2),dtemp=right(time,2),btemp=right(time,2)");

		}
    

		
		
	}
	/**   
	　* 描述：   
	　* 创建人：coco   
	　* 创建时间：2019年10月11日 上午11:28:26         
	*/
	public static void calculation(T1doBase tb,T1doProject t,T1doBoard it,int k,T1doBoardTask tt,Date date,int type) {
		//tb.setOCreateTime(tb.getOStartTime());//开始时间本来用1do的创建时间现在改成用开始时间这里偷下懒，这样写了之后后面不用改了。
		//ABC表示已完成、骷髅、闪电
		//Db.update("update holiday set data= right(time,2),atemp=right(time,2),dtemp=right(time,2),btemp=right(time,2)  where date BETWEEN ? and ? and item_id=? and data not in('A','B','C','D')",TimeUtil.getDate(tb.getOStartTime()),TimeUtil.getDate(tb.getOFinishTime()),t.getItemId());
		if(tb.getOStatus()<5) {
			//比较今天和拟完成时间的日期 ,true表示今天在拟完成时间后面说明工单超时了
			boolean flag=TimeUtil.getBetweenDays(TimeUtil.getDate(date),TimeUtil.getDate(tb.getOFinishTime()));
			List<T1doFeedback> tf=T1doFeedback.getFb(tb.getShowId());
			if(tf.size()==0) {
				
			}else {
				//比较今天和拟完成时间的日期取最迟时间
				String str=flag?TimeUtil.getDate(date):TimeUtil.getDate(tb.getOFinishTime());
				//修改这段时间都为闪电
				Db.update("update holiday set data= 'C',atemp='C',dtemp='C',btemp='C'  where date BETWEEN ? and ? and item_id=? and data not in('A','B')",TimeUtil.getDate(tf.get(0).getFbTime()),str,t.getItemId());
				if(tf.size()>=5) {
					//设置这段时间的都为骷髅
					Db.update("update holiday set data= 'B',atemp='B',dtemp='B',btemp='B'  where date BETWEEN ? and ? and item_id=? and data not in('A')",TimeUtil.getDate(tf.get(4).getFbTime()),str,t.getItemId());
				}
			}
			//超时了,修改拟完成时间到现在为闪电
			if(flag) {
				Db.update("update holiday set data= 'C',atemp='C',dtemp='C',btemp='C'  where date BETWEEN ? and ? and item_id=? and data not in('A','B')",TimeUtil.getDate(TimeUtil.getNextDay(tb.getOFinishTime(),1)),TimeUtil.getDate(date),t.getItemId());
			    //超5天了，从第5天起到现在修改为骷髅
				if(TimeUtil.getBetweenDays1(TimeUtil.getDate(tb.getOFinishTime()),TimeUtil.getDate(date))>4) {
					Db.update("update holiday set data= 'B',atemp='B',dtemp='B',btemp='B'  where date BETWEEN ? and ? and item_id=? and data not in('A')",TimeUtil.getDate(TimeUtil.getNextDay(tb.getOFinishTime(),5)),TimeUtil.getDate(date),t.getItemId());

			    }
			}
		}else if(tb.getOStatus()==5) {
			//查询办结前的催办
			List<T1doFeedback> tf=T1doFeedback.getFb(tb.getShowId(),tb.getRealFinishTime());
			//比较完成时间和拟完成时间的日期 ,true表示完成在拟完成时间后面说明工单超时了
			boolean flag=TimeUtil.getBetweenDays(TimeUtil.getDate(tb.getRealFinishTime()),TimeUtil.getDate(tb.getOFinishTime()));
            if(tf.size()==0) {
				
			}else {
				//比较完成时间和拟完成时间的日期取最迟时间
				//String str=flag?TimeUtil.getDate(tb.getRealFinishTime()):TimeUtil.getDate(tb.getOFinishTime());
				String str=TimeUtil.getDate(tb.getRealFinishTime());
				//修改这段时间都为闪电
				Db.update("update holiday set data= 'C',atemp='C',dtemp='C',btemp='C' where date BETWEEN ? and ? and item_id=? and data not in('A','B')",TimeUtil.getDate(tf.get(0).getFbTime()),str,t.getItemId());
				if(tf.size()>=5) {
					//设置这段时间的都为骷髅
					Db.update("update holiday set data= 'B',atemp='B',dtemp='B',btemp='B' where date BETWEEN ? and ? and item_id=? and data not in('A')",TimeUtil.getDate(tf.get(4).getFbTime()),str,t.getItemId());
				}
			}
          //超时了,修改拟完成时间到完成时间为闪电
			if(flag) {
				Db.update("update holiday set data= 'C',atemp='C',dtemp='C',btemp='C' where date BETWEEN ? and ? and item_id=? and data not in('A','B')",TimeUtil.getDate(TimeUtil.getNextDay(tb.getOFinishTime(),1)),TimeUtil.getDate(tb.getRealFinishTime()),t.getItemId());
			    //超5天了，从第5天起到现在修改为骷髅
				if(TimeUtil.getBetweenDays1(TimeUtil.getDate(tb.getOFinishTime()),TimeUtil.getDate(date))>4) {
					Db.update("update holiday set data= 'B',atemp='B',dtemp='B',btemp='B' where date BETWEEN ? and ? and item_id=? and data not in('A')",TimeUtil.getDate(TimeUtil.getNextDay(tb.getOFinishTime(),5)),TimeUtil.getDate(tb.getRealFinishTime()),t.getItemId());

			    }
			}
			if(type==1) {
			//修改那天为已完成（待定）
			//Db.update("update holiday set data= 'A' where date=? and item_id=?",TimeUtil.getDate(tb.getRealFinishTime()),t.getItemId());
				Db.update("update holiday set data= '',atemp=''  where date>? and item_id=?",TimeUtil.getDate(tb.getRealFinishTime()),t.getItemId());
				Db.update("update holiday set data= 'A',atemp='A'  where date=? and item_id=?",TimeUtil.getDate(tb.getRealFinishTime()),t.getItemId());
			}
		    		
		}
		//如果该节点已完成修改完成时间后面的都为空,该时间为已完成
		/*
		 * if(it.getCOMPLETION().equals("已完成")&&type==2) { k=1; //Holiday h=Holiday.dao.
		 * findFirst("select CONCAT('[',GROUP_CONCAT('\"',data,'\"'),']')data from holiday where item_id=?"
		 * ,t.getItemId()); Holiday h=Holiday.getHolidayData(t.getItemId());
		 * if(h!=null&&h.getData()!=null) { tt.setBTEMP(h.getData()); }else {
		 * tt.setBTEMP("[]"); }
		 * Db.update("update holiday set data= '',atemp=''  where date>? and item_id=?"
		 * ,TimeUtil.getDate(it.getFinishDate()),t.getItemId()); Db.
		 * update("update holiday set data= 'A',atemp='A'  where date=? and item_id=?"
		 * ,TimeUtil.getDate(it.getFinishDate()),t.getItemId());
		 * 
		 * }
		 */
	}
	/**   
	　* 描述：   
	　* 创建人：coco   
	　* 创建时间：2019年8月21日 下午3:31:12         
	*/
	public static void temp() {
		List<T1doBoardTask> tt=T1doBoardTask.dao.find("select * from t_1do_board_task WHERE DATE='2019-11-15' group by Date");//'
		//List<T1doBoardTask> tt=T1doBoardTask.dao.find("select DATE from t_1do_board_daliy_report where date not in(SELECT DATE FROM `t_1do_board_task` GROUP BY DATE) GROUP BY date");//'
		for (T1doBoardTask t1doBoardTask : tt) {
			Db.update("update t_1do_board a,t_1do_board_task b set a.`COMPLETION`=b.`COMPLETION` where a.id=b.ITEM_ID and b.date=?",t1doBoardTask.getDATE());
			TaskCalculate(t1doBoardTask.getDATE());
		}
		//return null;
	}
	/**   
	　* 描述：   
	　* 创建人：coco   
	　* 创建时间：2019年8月21日 下午3:41:36         
	*/
	public static int getInt() {
		//DbUtil.updateOnline();
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						Db.update("truncate t_1do_label");
						Db.update("truncate t_1do_label_feedback");
						Db.update("truncate t_1do_label_record");
						Db.update("truncate t_1do_relation");
						Db.update("truncate t_1do_relation_feedback");
						Db.update("truncate t_1do_relation_record");
						List<T1doBase> tb=T1doBase.dao.find("select * from t_1do_base");
						
						tb.forEach(t->{
							T1doLabel.saveList(HttpUtil.getlabel(t.getODescribe()), t.getShowId(),2);
						});

						List<T1doFeedback> list=T1doFeedback.dao.find("select * from t_1do_feedback where "
								+ "FB_TYPE=1 or FB_TYPE=2 or FB_TYPE=6");
						list.forEach(t->{
							T1doLabelFeedback.saveAllLabel(t);
						});
						List<Record> records=Db.find("select * from t_1do_record ");
						records.forEach(t->{
							T1doLabelRecord.saveAllLabel1(t);
						});
					
						
					}
				}).start();
				return 1;
	}

	/**
	 * @Author Sherry
	 * @Description 获取日志数据
	 * @Date 1:43 PM 2019/8/21
	 */
	public static void getLog() {
		//获取假期
		Calendar c = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String todayDate = simpleDateFormat.format(c.getTime());
		//假期不回超过半月，取前后半个月的假期
		c.add(Calendar.DAY_OF_MONTH, -15);
		String startDate = simpleDateFormat.format(c.getTime());
		c.add(Calendar.MONTH, 1);
		String endDate = simpleDateFormat.format(c.getTime());
		List<Record> holidays = LogDao.getHoliday(startDate, endDate);
		Calendar today = Calendar.getInstance();
		//假期（今日）
		Record holiday = null;
		for (Record record : holidays) {
			//若今天查到数据
			if (record.getStr("date").trim().equals(todayDate)) {
				holiday = record;
				break;
			}
		}
		//若为周二～周五，且今天假期表中没有||表中状态为4，正常统计昨天数据
		if ((today.get(Calendar.DAY_OF_WEEK) >= Calendar.TUESDAY && today.get(Calendar.DAY_OF_WEEK) <= Calendar.FRIDAY && holiday == null) || (holiday != null && holiday.getInt("type") == 4)) {
			getLogMain(0);
			//若为周一，且列表中没有||状态为3，
		} else if ((holiday != null && holiday.getInt("type") == 3) || (holiday == null && (today.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY))) {
			int days = 0;
			boolean flag = true;
			//统计前几天休息
			while (flag) {
				flag = false;
				today.add(Calendar.DAY_OF_MONTH, -1);
				String date = simpleDateFormat.format(today.getTime());
				//假期
				Record day = null;
				for (Record record : holidays) {
					if (record.getStr("date").trim().equals(date)) {
						day = record;
						break;
					}
				}
				if ((day != null && (day.getInt("type") == 1 || day.getInt("type") == 2)) || (day == null && (today.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || today.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY))) {
					days += 1;
					flag = true;
				}
			}
			getLogMain(days);
		}

	}

	/**
	 * 获取日志主程序
	 *
	 * @param days 一周的第一天统计时，计算前面休息了几天
	 */
	private static void getLogMain(int days){
		//时间
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		c.add(Calendar.DAY_OF_MONTH, -days);
		//结束时间
		Date endDate = c.getTime();
		c.add(Calendar.DAY_OF_MONTH, -1);
		//起始时间
		Date startDate = c.getTime();
		//获取所有项目
		List<T1doBoard> projects = T1doBoard.getAllProjects();
		//获取公司
		List<Record> companies = T1doProjectStakeholder.getCompanies();
		if (companies == null || companies.size() == 0) {
			return;
		}
		for (Record company : companies) {
			//若有合作公司一级，取合作公司下一级，合作公司showId=l6znLJpQXPTnE3B5
			String companyShowId = company.getStr("companyShowId");
			StringBuilder companyShowIds = new StringBuilder();
			companyShowIds.append("'").append(companyShowId).append("'");
			String dept = company.getStr("D_PATH");
			String depts[] = dept.split("●");
			for (int i = 0; i < depts.length; i++) {
				if (depts[i].equals("l6znLJpQXPTnE3B5")) {
					companyShowId = depts[i + 1];
					companyShowIds.append(",'").append(depts[i + 1]).append("'");
					break;
				}
			}
			//获取每个公司的成员
			Record people = T1doProjectStakeholder.getCompanyAccounts(companyShowIds.toString());
			if (people != null && StrKit.notBlank(people.getStr("account"))) {
				//获取日志列表
				List<Record> logs = LogDao.getLogByAccount(people.getStr("account"), startDate, endDate);
				for (Record log : logs) {
					//标签
					String[] workClass = log.getStr("WorkClass").split("\\|");
					//项目在标签的第一个中
					if (workClass.length > 0) {
						String projectName;
						if (StrKit.notBlank(workClass[0])) {
							projectName = workClass[0].trim();
						} else {
							projectName = workClass[1].trim();
						}
						for (T1doBoard project : projects) {
							if (projectName.equals(project.getItemName())) {
								T1doBoardDaliyReport dailyReport = new T1doBoardDaliyReport();
								dailyReport.setCONTENT(log.getStr("WorkName"));
								dailyReport.setDATE(startDate);
								dailyReport.setProjectId(project.getID());
								dailyReport.setCOMPANY(companyShowId);
								dailyReport.setTIME(log.getDate("AddTime"));
								dailyReport.setNUMBER(log.getStr("WorkName").length());
								dailyReport.save();
								break;
							}
						}
					}
				}
			}


		}
	}

	/**
	 * 每天获取日志
	 */
	public static void getLogNew() {
		//时间，今天零点
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		//起始时间
		Date startDate = c.getTime();
		c.add(Calendar.DAY_OF_MONTH, 1);
		//结束时间
		Date endDate = c.getTime();
		//获取所有项目
		List<T1doBoard> projects = T1doBoard.getAllProjects();
		//日志（防止重复查日志）
		Map<String, List<Record>> logMap = new HashMap<>();
		for (T1doBoard project : projects) {
			//获取公司
			List<Record> companies = T1doProjectStakeholder.getProjectCompanies(project.getID());
			for (Record company : companies) {
				//若有合作公司一级，取合作公司下一级，合作公司showId=l6znLJpQXPTnE3B5
				String companyShowId = company.getStr("SHOW_ID");
				//公司成员
				String peopleStr = "";
				//获取日志列表
				List<Record> logs;
				if (logMap.get(companyShowId) == null) {
					//获取每个公司的成员
					Record people = T1doProjectStakeholder.getCompanyAccounts(company.getStr("SHOW_ID"));
					if (people != null) {
						peopleStr = people.getStr("account");
					}
					//插入map
					logs = LogDao.getLogByAccount(peopleStr, startDate, endDate);
					logMap.put(companyShowId, logs);

				}else{
					logs = logMap.get(companyShowId);
				}
				if (logs != null && logs.size() > 0) {
					for (Record log : logs) {
						//标签
						String[] workClass = log.getStr("WorkClass").split("\\|");
						//找到项目
						if (workClass.length > 0) {
							for (String item : workClass) {
								if (item.trim().equals(project.getItemName().trim())) {
									T1doBoardDaliyReport dailyReport = new T1doBoardDaliyReport();
									dailyReport.setCONTENT(log.getStr("WorkName"));
									dailyReport.setDATE(startDate);
									dailyReport.setProjectId(project.getID());
									dailyReport.setCOMPANY(company.getStr("company"));
									dailyReport.setReportId(log.getLong("id"));
									dailyReport.setTIME(log.getDate("AddTime"));
									dailyReport.setNUMBER(log.getStr("WorkName").length());
									dailyReport.save();
									break;
								}
							}
						}
					}
				}
			}
		}
	}
}
