package com.demo.util;

import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.demo.common.model.T1doBase;
import com.demo.common.model.T1doFeedback;
import com.demo.common.model.T1doPstatus;
import com.demo.common.model.TRegUser;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;

public class StrUtil {
	/**
	 * 字符串为 null 或者为  "" 时返回 true
	 */
	public static boolean isEmpty(String str) {
		return str == null || "".equals(str.trim()) ? true : false;
	}
	
	/**
	 * 字符串不为 null 而且不为  "" 时返回 true
	 */
	public static boolean isNotEmpty(String str) {
		return str == null || "".equals(str.trim()) ? false : true;
	}
	
	/*
	 2018年6月22日上午11:34:47 方升群   //得到唯一的字符串
	*/
	public static String getOnly(String...str) {
		String result="";
		HashSet<String> hashset=new HashSet<String>();
		for(String msg:str){
			String[] s=msg.split(";");
			for(String temp:s){
				hashset.add(temp);
			}
		}
		for(String arr:hashset){
			result+=arr+";";
		}
		return result;
	}
	
	/*
	 2018年6月25日下午2:51:52 方升群  //处理状态 1.已送达2.无（创建人状态）3.待接单4.已接单5.已完成
	*/
	public static String getStatus(String STATUS) {
		switch (STATUS) {
		case "1":
			return "已送达";
		case "2":
			return "无";
		case "3":
			return "待接单";
		case "4":
			return "已接单";
		case "5":
			return "已完成";
		default:
			return "";
		}
		
	}
	public static String getStatus(int STATUS) {
		switch (STATUS) {
		case 1:
			return "已送达";
		case 2:
			return "无";
		case 3:
			return "待接单";
		case 4:
			return "已接单";
		case 5:
			return "已完成";
		default:
			return "";
		}
		
	}
	//1发起人2创建人3受理人4受邀人
	public static String getUserType(String TYPE) {
		switch (TYPE) {
		case "1":
			return "发起人";
		case "2":
			return "创建人";
		case "3":
			return "受理人";
		case "4":
			return "受邀人";
		default:
			return "";
		}
		
	}
	//订阅事件1.送达2.查看3.反馈4.催办5.办结6.评价
	public static String getEventType(int TYPE) {
		switch (TYPE) {
		case 1:
			return "送达";
		case 2:
			return "查看";
		case 3:
			return "反馈";
		case 4:
			return "催办";
		case 5:
			return "办结";
		case 6:
			return "评价";
		default:
			return "";
		}
		
	}
	/*
	 2018年6月27日上午10:01:43 方升群  //获得催办，反馈人员 以xxx；xxx格式返回
	*/
	public static String getStr(List<T1doFeedback> t1doFeedbacks) {
		String str="";
		for(T1doFeedback t:t1doFeedbacks){
			str+=t.getOUserName()+";";
		}
		if(StrUtil.isNotEmpty(str)){
			str=str.substring(0, str.length()-1);
		}
		return str;
	}
	    /*
		 2018年7月5日下午3:28:46 方升群  
		*/
		public static String getUser(String arr,String brr,String method) {
			HashSet<String> hs=new HashSet<String>();
			String[] temp = arr.split(";");
			for(String tem:temp){
				hs.add(tem);
			}
			if(method.equals("add")){
				String[] temp1 = brr.split(";");
				for(String brr1:temp1){
					hs.add(brr1);
				}
			}else{
				hs.remove(brr);
			}
			String result="";
			for(String str:hs){
				result+=str+";";
			}
			return result.substring(0, result.length()-1);
		}
		public static String getUser1(String arr,String brr,String method) {
			String str="";
			if(method.equals("cover")){
				str=brr;
			}else if(method.equals("add")){
				if(StrUtil.isEmpty(arr)){
					str=brr;
				}else{
					if(arr.indexOf(brr)==-1){
						str=arr+";"+brr;
					}else{
						str=arr;
					}
					

				}
			}else{
				if(arr.indexOf(";")==-1){
					str=arr.replace(brr, "");
				}if(arr.indexOf(brr)==0){
					if(arr.equals(brr)){
						str="";
					}else{
						str=arr.replace(brr+";", "");

					}
				}else{
					str=arr.replace(";"+brr, "");
				}
			}	
			return str;
		}
	
		public static void main(String[] args) {
		//	String[] str="方升群;谢洁;".split(";");
			for(int i=1;i<13;i++){
				System.out.println(getflag(4,i));
			}
			
	}
		/*
		 2018年8月10日上午11:29:43 方升群   新的权限
		*/
		public static boolean getflag(int userType,int eventType) {
			
			/*
			修改标题、内容 1
			编辑参与人 2
			完成时间 3
			设置受理人、抄送人4
			反馈5
			催办 6
			办结 7 
			评价 8
			删除 9
			恢复10
			重做11
			*/
			
			switch (userType) {
			case 1:
				switch (eventType) {
				case 1:					
					return true;
				case 2:
					
					return true;

				case 3:
					
					return true;

				case 4:
					
					return false;
				case 5:
					
					return true;
				case 6:
					
					return true;
				case 7:
					
					return true;
				case 8:
					
					return true;
				case 9:
					
					return true;
				case 10:
					
					return false;
				case 11:
					
					return false;
				default:
					return false;
				}
			case 3:
				
				switch (eventType) {
				case 1:					
					return false;
				case 2:
					
					return true;

				case 3:
					
					return false;

				case 4:
					
					return false;
				case 5:
					
					return true;
				case 6:
					
					return false;
				case 7:
					
					return false;
				case 8:
					
					return false;
				case 9:
					
					return false;
				case 10:
					
					return false;
				case 11:
					
					return false;
				default:
					return false;
				}
			default:
				return false;
			}
		}
		/*
		 2018年7月23日上午11:29:43 方升群   旧的权限
		 */
		public static boolean getflag1(int userType,int eventType) {
			
			/*修改标题、内容 1
			附件 2
			完成时间 3
			发起人 4
			受理人 5
			抄送人 6
			通知 7
			拆项 8
			催办 9
			办结 10 
			评价 11
			删除 12*/
			
			switch (userType) {
			case 1:
				switch (eventType) {
				case 1:					
					return true;
				case 2:
					
					return true;
					
				case 3:
					
					return true;
					
				case 4:
					
					return true;
				case 5:
					
					return true;
				case 6:
					
					return true;
				case 7:
					
					return true;
				case 8:
					
					return true;
				case 9:
					
					return true;
				case 10:
					
					return true;
				case 11:
					
					return true;
				case 12:					
					return false;
				default:
					return false;
				}
			case 3:
				
				switch (eventType) {
				case 1:					
					return false;
				case 2:
					
					return true;
					
				case 3:
					
					return false;
					
				case 4:
					
					return false;
				case 5:
					
					return true;
				case 6:
					
					return true;
				case 7:
					
					return false;
				case 8:
					
					return true;
				case 9:
					
					return false;
				case 10:
					
					return false;
				case 11:
					
					return true;
				case 12:					
					return false;
				default:
					return false;
				}
			case 4:
				
				switch (eventType) {
				case 1:					
					return false;
				case 2:
					
					return true;
					
				case 3:
					
					return false;
					
				case 4:
					
					return false;
				case 5:
					
					return true;
				case 6:
					
					return true;
				case 7:
					
					return false;
				case 8:
					
					return true;
				case 9:
					
					return true;
				case 10:
					
					return false;
				case 11:
					
					return true;
				case 12:					
					return false;
				default:
					return false;
				}
				
			default:
				return false;
			}
		}
		/*
		 2018年8月8日下午11:49:41 方升群 //通知内容
		*/
		public static String getType(int i,int type) {
			switch (i) {
			case 1:
				if(type==1||type==6){
					return "已送达";
				}else if(type==3){
					return "待接单";
				}else{
					return "";
				}
				
			case 2:
				return "待接单";
			case 3:
				return "已接单";				
			case 4:
				return "已接单";	
			case 5:
				
				return "已完成";	
			case 6:
				
				return "已完成";	
			case 7:
				
				return "已删除";	
			case 8:
				
				return "已恢复";	
			default:
				return "";
			}
			
		}
		public static String[] getSql(int i,String O_USER,int o_status) {
			String str="select * from t_1do_pstatus where SHOW_ID=? and USER_TYPE!=2 and isDelete=1";
			String str1="select * from t_1do_pstatus where SHOW_ID=? and (USER_TYPE=1 or O_USER='"+O_USER+"') and isDelete=1";
			switch (i) {
			case 1:				
				//return new String[]{"待接单","select * from t_1do_pstatus where SHOW_ID=? and USER_TYPE in(1,3) and isDelete=1"};				
				return new String[]{"待接单",str};				
			case 2:
				return new String[]{"待接单",str1};
			case 3:
				return new String[]{"已接单",str1};				
			case 4:
				return new String[]{"已接单",str};	
			case 5:
				return new String[]{"已完成",str};
			case 6:
				return new String[]{"已完成",str};
			case 7:
				return new String[]{"已删除",str};
			case 8:
				return new String[]{"已恢复",str};
			case 9:
				String[] strs=getSql(o_status==3?1:o_status,O_USER,1);
				return new String[]{strs[0],strs[1]+" and O_USER!='"+O_USER+"'"};				
			default:
				String[] result={"",""};
				return result;
			}
			
		}
		/*
		 2018年10月30日 coco 注解：//根据角色，任务状态 显示通知文字。
		*/
		public static String getSql(int O_IS_DELETED,int USER_TYPE,int O_STATUS) {
			switch (O_IS_DELETED) {
			case 2:				
				return "已删除";

			default:
				switch (USER_TYPE) {
				case 3:				
					switch (O_STATUS) {
					case 3:				
						return "待接单";
					case 4:				
						return "已接单";
					case 5:				
						return "已完成";
					default:
						return "待接单";
					}

				default:
					switch (O_STATUS) {
					case 3:				
						return "已送达";
					case 4:				
						return "已接单";
					case 5:				
						return "已完成";
					default:
						return "已送达";
					}
				}
			}
			
		}
		/*
		 2019年2月25日 coco 注解：获得字符串中的牵头人
		*/
		public static void getQTR(T1doBase tb) {
			HashSet<String> list=new HashSet<String>();
			HashSet<String> result=new HashSet<String>();
			String reg="请[\u4e00-\u9fa5]+牵头";
			//将正则表达式编译进指定模式
					Pattern pat = Pattern.compile(reg);
					//获取匹配器，通过指定模式和输入内容
					Matcher mat = pat.matcher(tb.getODescribe());
					//输出匹配结果
					while(mat.find()){		//true:找到一个匹配结果
						//int start = mat.start();   //返回起始位置
						//int end = mat.end();		//结束位置
						String text = mat.group();   //匹配到的内容
						//System.out.println(text.length()-2);
						list.add(text.substring(1, text.length()-2));
						
						//System.out.println(start+"-"+end+":"+text); 
					}
					
					Db.update("update t_1do_pstatus set sort=3,otherid=2 where SHOW_ID=? and otherid=1 and USER_TYPE=3",tb.getShowId());
					
					list.forEach(t->{
						List<TRegUser> list1=TRegUser.dao.find("select * from t_reg_user where u_true_name=?",t);
						list1.forEach(t1->{
							result.add(t1.getShowId());
							if(!tb.getOExecutor().contains(t1.getShowId())){
								new T1doPstatus().setShowId(tb.getShowId()).setOUser(t1.getShowId()).setOUserName(t1.getUTrueName()).setOStatus(3).setUserType(3).setOtherid(1).setSort(1).save();
								tb.setOExecutor(t1.getShowId()+";"+tb.getOExecutor()).setOExecutorName(t1.getUTrueName()+";"+tb.getOExecutorName());
							}else{
								Db.update("update t_1do_pstatus set sort=1,otherid=1 where SHOW_ID=? and O_USER=? and USER_TYPE=3",tb.getShowId(),t1.getShowId());
							}
						});
						//result
					});		
					JSONArray arr=JSON.parseArray(tb.getAT());
					if(arr!=null)
					arr.forEach(a->{
						String[] brr=a.toString().split("@");
						result.add(brr[0]);
						if(!tb.getOExecutor().contains(brr[0])){
							new T1doPstatus().setShowId(tb.getShowId()).setOUser(brr[0]).setOUserName(brr[1]).setOStatus(3).setUserType(3).setOtherid(1).setSort(2).save();
							//tb.setOExecutor(brr[0]+";"+tb.getOExecutor()).setOExecutorName(brr[1]+";"+tb.getOExecutorName()).update();
						}else{
							Db.update("update t_1do_pstatus set sort=2,otherid=1 where SHOW_ID=? and O_USER=? and USER_TYPE=3",tb.getShowId(),brr[0]);
						}
						
					});		
			
			
			if(StrKit.notBlank(tb.getOExecutor())){
				String[] str1=tb.getOExecutor().split(";");
				result.add(str1[0]);
			}
			result.forEach(r->{
				Db.update("update t_1do_pstatus set otherid=1 where SHOW_ID=? and O_USER=? and USER_TYPE=3",tb.getShowId(),r);
			});
			Db.update("update t_1do_pstatus set otherid=2 where SHOW_ID=? and USER_TYPE=3 and otherid!=1",tb.getShowId());
			//return result;
			T1doPstatus tps=T1doPstatus.dao.findFirst("SELECT  SHOW_ID,GROUP_CONCAT(O_USER) O_USER,GROUP_CONCAT(O_USER_NAME)O_USER_NAME FROM (select * from t_1do_pstatus where SHOW_ID=? and USER_TYPE=3 order by sort )a",tb.getShowId());
			if(tps!=null)
			tb.setOExecutor(tps.getOUser()==null?"":tps.getOUser().replace(",", ";")).setOExecutorName(tps.getOUserName()==null?"":tps.getOUserName().replace(",", ";")).update();
			
		}
}
