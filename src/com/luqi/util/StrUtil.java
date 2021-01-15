package com.luqi.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.luqi.common.model.T1doBase;
import com.luqi.common.model.T1doFeedback;
import com.luqi.common.model.T1doPstatus;
import com.luqi.common.model.TRegUser;

public class StrUtil {
	
	 public static void main( String[] args ) throws Exception  
     {  
		 HashMap<String,Integer> map=new HashMap<String,Integer>();
		 map.put("a", 1);
		 map.put("a", -11);
		 map.put("a", map.get("a")+-101);
		 map.put("b", 1);
		 System.out.println(map.get("a"));
		 
		 String[] arr= {"ddd","dddd","ddd"};
		 String[] brr= twoClear(arr);
		 LinkedHashSet<String> temp = new LinkedHashSet<>();
		/*for(int i=0;i<arr.length;i++){
		    temp.add(arr[i]);
		 }*/
		 System.out.println(temp.contains("ddd"));
		 System.out.println(twoClear(arr).toString());
		 
		/*String str = "aaaaaaaaaa只bbbbbbbbbb只ccccccccccee只";  
		  
		System.out.println(StrUtil.bSubstring(str,32));  
		System.out.println(getWordCount(str));  
		System.out.println(getWordCountRegex(str));  
		System.out.println(getWordCountCode(str,"GBK"));  
		System.out.println(getWordCountCode(str,"UTF-8"));  */
     }  
	 public static String[] twoClear(String[] arr){
		  LinkedHashSet<String> temp = new LinkedHashSet<>();
		  for(int i=0;i<arr.length;i++){
                temp.add(arr[i]);
		  }
		  return  temp.toArray(new String[temp.size()]);
		 }
	 public static String bSubstring(String s, int length) throws Exception
	    {

	        byte[] bytes = s.getBytes("Unicode");
	        int n = 0; // 表示当前的字节数
	        int i = 2; // 要截取的字节数，从第3个字节开始
	        for (; i < bytes.length && n < length; i++)
	        {
	            // 奇数位置，如3、5、7等，为UCS2编码中两个字节的第二个字节
	            if (i % 2 == 1)
	            {
	                n++; // 在UCS2第二个字节时n加1
	            }
	            else
	            {
	                // 当UCS2编码的第一个字节不等于0时，该UCS2字符为汉字，一个汉字算两个字节
	                if (bytes[i] != 0)
	                {
	                    n++;
	                }
	            }
	        }
	        // 如果i为奇数时，处理成偶数
	        if (i % 2 == 1)

	        {
	            // 该UCS2字符是汉字时，去掉这个截一半的汉字
	            if (bytes[i - 1] != 0)
	                i = i - 1;
	            // 该UCS2字符是字母或数字，则保留该字符
	            else
	                i = i + 1;
	        }

	        return new String(bytes, 0, i, "Unicode");
	    }  
     /*由于Java是基于Unicode编码的，因此，一个汉字的长度为1，而不是2。 
      * 但有时需要以字节单位获得字符串的长度。例如，“123abc长城”按字节长度计算是10，而按Unicode计算长度是8。 
      * 为了获得10，需要从头扫描根据字符的Ascii来获得具体的长度。如果是标准的字符，Ascii的范围是0至255，如果是汉字或其他全角字符，Ascii会大于255。 
      * 因此，可以编写如下的方法来获得以字节为单位的字符串长度。*/  
     public static int getWordCount(String s)  
     {  
         int length = 0;  
         for(int i = 0; i < s.length(); i++)  
         {  
             int ascii = Character.codePointAt(s, i);  
             if(ascii >= 0 && ascii <=255)  
                 length++;  
             else  
                 length += 2;  
                   
         }  
         return length;  
           
     }  
       
     /*基本原理是将字符串中所有的非标准字符（双字节字符）替换成两个标准字符（**，或其他的也可以）。这样就可以直接例用length方法获得字符串的字节长度了*/  
     public static  int getWordCountRegex(String s)  
     {  
   
         s = s.replaceAll("[^\\x00-\\xff]", "**");  
         int length = s.length();  
         return length;  
     }  
       
     /*按特定的编码格式获取长度*/  
     public static int getWordCountCode(String str, String code) throws UnsupportedEncodingException{  
         return str.getBytes(code).length;  
     }  
	/*
	 2019年5月5日 coco 注解：
	*/
	public static HashSet<String> getHashSet(String result) {
		String[] str=result.split("\\|");
		HashSet<String> hs = new HashSet<String>();
		for (String string : str) {
			if(StrKit.notBlank(string))
			hs.add(string);
		}
		return hs;
	}
	//获得完整异常信息
	public static String getTrace(Throwable t) {
	    StringWriter stringWriter= new StringWriter();
	    PrintWriter writer= new PrintWriter(stringWriter);
	    t.printStackTrace(writer);
	    StringBuffer buffer= stringWriter.getBuffer();
	    return buffer.toString();
	}
	
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
	 2018年6月25日下午2:51:52 方升群  //处理状态 1.待接单2.无（创建人状态）3.待接单4.已接单5.已完成
	*/
	public static String getStatus(String STATUS) {
		switch (STATUS) {
		case "1":
			return "待接单";
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
			return "待接单";
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
				if(type==1||type==6||type==3){
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
			//String str="select * from t_1do_pstatus where SHOW_ID=? and USER_TYPE!=2 and isDelete=1";
			String str="select * from t_1do_pstatus where SHOW_ID=? and USER_TYPE!=2 and isDelete=1 and isRead=2 and O_USER!='"+O_USER+"'";
			//String str1="select * from t_1do_pstatus where SHOW_ID=? and (USER_TYPE=1 or O_USER='"+O_USER+"') and isDelete=1";
			String str1="select * from t_1do_pstatus where SHOW_ID=? and USER_TYPE=1 and isRead=2 and O_USER!='"+O_USER+"' and isDelete=1";
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
				String[] result={"已接单",str};
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
						return "待接单";
					case 4:				
						return "已接单";
					case 5:				
						return "已完成";
					default:
						return "待接单";
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
						//list.add(text.substring(1, text.length()-2));
						list.add(text);
						
						//System.out.println(start+"-"+end+":"+text); 
					}
					
					Db.update("update t_1do_pstatus set sort=3,otherid=2 where SHOW_ID=? and otherid=1 and USER_TYPE=3",tb.getShowId());
					
					list.forEach(t->{
						JSONObject json=getJson(t);
						if(StrKit.notBlank(json.getString("name"))){
							String[] strs=json.getString("name").split(";");
							for (String string : strs) {
								List<TRegUser> list1=TRegUser.dao.find("select * from t_reg_user where u_true_name=?",string);
								list1.forEach(t1->{
									result.add(t1.getShowId());
									if(!tb.getOExecutor().contains(t1.getShowId())){
										Db.delete("delete from t_1do_pstatus where SHOW_ID=? and O_USER=? and isDelete=2",tb.getShowId(),t1.getShowId());
										new T1doPstatus().setShowId(tb.getShowId()).setOUser(t1.getShowId()).setOUserName(t1.getUTrueName()).setOStatus(tb.getOStatus()).setSTATUS(getStatus2(tb.getOStatus())).setUserType(3).setOtherid(1).setSort(1).save();
										tb.setOExecutor(t1.getShowId()+";"+tb.getOExecutor()).setOExecutorName(t1.getUTrueName()+";"+tb.getOExecutorName());
									}else{
										Db.update("update t_1do_pstatus set sort=1,otherid=1 where SHOW_ID=? and O_USER=? and USER_TYPE=3",tb.getShowId(),t1.getShowId());
									}
								});
							}
						}
						
						//result
					});		
					JSONArray arr=JSON.parseArray(tb.getAT());
					if(arr!=null)
					arr.forEach(a->{
						String[] brr=a.toString().split("@");
						result.add(brr[0]);
						if(!tb.getOExecutor().contains(brr[0])){
							Db.delete("delete from t_1do_pstatus where SHOW_ID=? and O_USER=? and isDelete=2",tb.getShowId(),brr[0]);
							new T1doPstatus().setShowId(tb.getShowId()).setOUser(brr[0]).setOUserName(brr[1]).setOStatus(tb.getOStatus()).setSTATUS(getStatus2(tb.getOStatus())).setUserType(3).setOtherid(1).setSort(2).save();
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
		/*
		 2019年3月19日 coco 注解：
		*/
		public static JSONObject getJson(String content) {
			JSONObject json=new JSONObject();
			json.put("text", content);
			String str=HttpUtil.doPost1("https://tyhy.hzxc.gov.cn:8443/md/leader/isApprove", json.toString());
			if(StrKit.notBlank(str)){
				JSONObject json1= JSON.parseObject(str);
				if(StrKit.notBlank(json1.getString("data"))){
					return JSON.parseObject(json1.getString("data"));
				}
			}
			return json;			
		}
		/*
		 2019年3月21日 coco 注解：参与人的状态
		*/
		public static String getStatus1(int oStatus) {
			switch (oStatus) {
			case 3:
				return "待接单";
				
			case 4:
				return "已接单";
				
			case 5:
				return "已完成";
			
			case 6:
		    	return "已删除";
		
			default:
		    	return "已删除";

			}
		}
		/*
		 2019年3月21日 coco 注解：发起人的状态
		 */
		public static String getStatus2(int oStatus) {
			switch (oStatus) {
			case 3:
				return "待接单";
				
			case 4:
				return "已接单";
				
			case 5:
				return "已完成";
				
			case 6:
				return "已删除";
				
			default:
				return "已删除";
				
			}
		}
		
	/*	public static void main(String[] args) {
	        String str="零";
	        System.out.println(chineseNumber2Int(str));  //===>  123
	        
	        String from ="select %s from t_1do_base a LEFT JOIN (select * from t_1do_pstatus where USER_TYPE!=2 "
					+ "and isDelete=1 and O_USER='' GROUP BY SHOW_ID)f on a.SHOW_ID=f.SHOW_ID "
					+"where ";
		String value="a.SHOW_ID,a.O_TITLE O_DESCRIBE,a.O_CUSTOMER_NAME,a.O_CUSTOMER,a.AT,a.O_EXECUTOR,"
				    +"a.O_EXECUTOR_NAME,a.SEND_TIME,unix_timestamp(a.O_CREATE_TIME)*1000 O_CREATE_TIME,"
	                +"unix_timestamp(a.O_FINISH_TIME)*1000 O_FINISH_TIME,unix_timestamp(a.Real_FINISH_TIME)*1000 Real_FINISH_TIME"
	                +",unix_timestamp(a.DELETE_TIME)*1000 DELETE_TIME,a.O_IS_DELETED ,"
	                +" a.LIGHTNING,a.LOOKNUM,a.FBNUM,f.USER_TYPE,f.isRead ISLOOK,f.STATUS O_STATUS";
		String ss=String.format(from, value);
		System.out.println(ss);
	    }*/
	    

	    //截取数字  【读取字符串中第一个连续的字符串，不包含后面不连续的数字】
	    public static String getNumbers(String content) {  
	        Pattern pattern = Pattern.compile("\\d+");  
	        Matcher matcher = pattern.matcher(content);  
	        while (matcher.find()) {  
	           return matcher.group(0);  
	        }  
	        return "";  
	    }  
		/**
	     * 中文數字转阿拉伯数组【十万九千零六十  --> 109060】
	     * @author 雪见烟寒
	     * @param chineseNumber
	     * @return
	     */
	    private static int chineseNumber2Int(String chineseNumber){
	        int result = 0;
	        int temp = 1;//存放一个单位的数字如：十万
	        int count = 0;//判断是否有chArr
	        char[] cnArr = new char[]{'一','二','三','四','五','六','七','八','九'};
	        char[] chArr = new char[]{'十','百','千','万','亿'};
	        for (int i = 0; i < chineseNumber.length(); i++) {
	            boolean b = true;//判断是否是chArr
	            char c = chineseNumber.charAt(i);
	            for (int j = 0; j < cnArr.length; j++) {//非单位，即数字
	                if (c == cnArr[j]) {
	                    if(0 != count){//添加下一个单位之前，先把上一个单位值添加到结果中
	                        result += temp;
	                        temp = 1;
	                        count = 0;
	                    }
	                    // 下标+1，就是对应的值
	                    temp = j + 1;
	                    b = false;
	                    break;
	                }
	            }
	            if(b){//单位{'十','百','千','万','亿'}
	                for (int j = 0; j < chArr.length; j++) {
	                    if (c == chArr[j]) {
	                        switch (j) {
	                        case 0:
	                            temp *= 10;
	                            break;
	                        case 1:
	                            temp *= 100;
	                            break;
	                        case 2:
	                            temp *= 1000;
	                            break;
	                        case 3:
	                            temp *= 10000;
	                            break;
	                        case 4:
	                            temp *= 100000000;
	                            break;
	                        default:
	                            break;
	                        }
	                        count++;
	                    }
	                }
	            }
	            if (i == chineseNumber.length() - 1) {//遍历到最后一个字符
	                result += temp;
	            }
	        }
	        return result;
	    }
		//调用
		
		/*	{
			    "data": {
			        "cbcs": "[催办次数]",
			        "nr": "[内容]",
			        "fqr": "[发起人]",
			        "slr": "[受理人]",
			        "cyr": "[参与人]"
			    },
			    "message": "Success",
			    "status": 0
			}*/
		public static String appendSql(String string) {
			String result=HttpUtil.doPost(UrlUtil.md, JsonUtil.getJsonString(string));
			JSONObject json=JSON.parseObject(result);
			JSONObject data=json.getJSONObject("data");
			String sql="";
			System.out.println("1懂调用结果"+result);
			if(data!=null){
				if(StrKit.notBlank(data.getString("cbcs"))){
					if(StrKit.notBlank(getNumbers(data.getString("cbcs")))){
						sql+=" and LIGHTNING="+getNumbers(data.getString("cbcs"));
					}else if((chineseNumber2Int(data.getString("cbcs"))==1&&data.getString("cbcs").contains("一"))
							||chineseNumber2Int(data.getString("cbcs"))>1){
						sql+=" and LIGHTNING="+chineseNumber2Int(data.getString("cbcs"));
					}else if(data.getString("cbcs").contains("零")){
						sql+=" and LIGHTNING=0";
					}		
				}
				if(StrKit.notBlank(data.getString("nr"))){				
					sql+=" and O_DESCRIBE like CONCAT('%','"+data.getString("nr")+"','%') ";						
				}
				if(StrKit.notBlank(data.getString("fqr"))){				
					sql+=" and O_CUSTOMER_NAME like CONCAT('%','"+data.getString("fqr")+"','%') ";						
				}
				if(StrKit.notBlank(data.getString("cyr"))){				
					sql+=" and O_EXECUTOR_NAME like CONCAT('%','"+data.getString("cyr")+"','%') ";						
				}
				if(StrKit.notBlank(data.getString("slr"))){				
					sql+=" and O_HANDLE_NAME like CONCAT('%','"+data.getString("slr")+"','%') ";						
				}
				if(StrKit.notBlank(data.getString("cbr"))){				
					sql+=" and URGENAME like CONCAT('%','"+data.getString("cbr")+"','%') ";						
				}
			}else{
				sql+=" and ( O_DESCRIBE like CONCAT('%','"+string+"','%') or  O_CUSTOMER_NAME like CONCAT('%','"+string+"','%') "
						+ " or  O_EXECUTOR_NAME like CONCAT('%','"+string+"','%') ) ";
			}
			
			return sql;
		}
		//更加1do数据计算计划日期
		public static String[] getPlannedDate(List<T1doBase> list) {
			String[] str= {null,null};
			if(list.size()==0) {
			    return str;
			}else {
				List<Date> arr=new ArrayList<Date>();
				for (T1doBase t : list) {
					arr.add(t.getOStartTime());
					arr.add(t.getOFinishTime());
				}
				Collections.sort(arr);
				str[0]=TimeUtil.getMDDate(arr.get(0)).replace("-", ".")+"-"+TimeUtil.getMDDate(arr.get(arr.size()-1)).replace("-", ".");
				str[1]=TimeUtil.getDate(arr.get(0))+"~"+TimeUtil.getDate(arr.get(arr.size()-1));
				return str;
			}
		}
		
	   
	    //获得发送消息接口参数
	    public static String SendData(JSONObject user, List<String>  account, long timestamp, String msg) {
	        JSONObject paramJson = new JSONObject();
	        //String[] to = {account};
	        paramJson.put("to", account);
	        paramJson.put("from",  user.getString("loginName"));
	        paramJson.put("content", msg);
	        paramJson.put("type", "Text");
	        paramJson.put("info", "{\"nickName\":\"" +user.getString("username") + "\",\"loginName\":\"" + user.getString("useraccount") + "\"}");
	        paramJson.put("clientMsgId", timestamp);
	        return paramJson.toString();

	    }
	    //获得登入接口参数
	    public static String getData(String name) {
	        //int i=(100000+(int)(Math.random()*900000));
	        String loginName = name;
	        long timestamp = new Date().getTime();
	        int nonce = 100000 + (int) (Math.random() * 900000);
	        //String appId="3OjQ5nZLGmId1AGV"  ;
	        String appSecret = "1Call";
	        List<String> list = new ArrayList<String>();
	        list.add(loginName);
	        list.add(timestamp + "");
	        list.add(nonce + "");
	        Collections.sort(list);
	        StringBuilder content = new StringBuilder();
	        for (String s : list) {
	            content.append(s);
	        }
	        String signature = getSha1(content.toString() + appSecret);
	        String echostr = "abcdef";
	        JSONObject map = new JSONObject();
	        map.put("appId", "3OjQ5nZLGmId1AGV");
	        map.put("loginName", loginName);
	        map.put("nonce", nonce);
	        map.put("echostr", echostr);
	        map.put("timestamp", timestamp);
	        map.put("signature", signature);
	        return map.toString();
	    }

	    public static String getSha1(String str) {
	        if (str == null || str.length() == 0) {
	            return null;
	        }
	        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
	                'a', 'b', 'c', 'd', 'e', 'f'};
	        try {
	            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
	            mdTemp.update(str.getBytes("UTF-8"));

	            byte[] md = mdTemp.digest();
	            int j = md.length;
	            char buf[] = new char[j * 2];
	            int k = 0;
	            for (int i = 0; i < j; i++) {
	                byte byte0 = md[i];
	                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
	                buf[k++] = hexDigits[byte0 & 0xf];
	            }
	            return new String(buf);
	        } catch (Exception e) {
	            // TODO: handle exception
	            return null;
	        }
	    }
	    //获得登入接口返回值中的参数token":"iV4PZULZuhv6pglCwaTD1bBKL/Q=","userId":"XzaqamVZPDtAR3WP","userName":"xiejie"；
	    public static String[] getToken(String result) {
	    	                                                                                 
	        //{"Header":{"ResCode":8200,"IsSuccess":true,"Reason":""},"Body":{"response":{"IsSuccess":true,"TotalCount":1,"Data":{"trueName":"谢洁","code":"xcgov","cShowId":"b5WJZ1bRLDCb7x2B","userName":"xiejie","userId":"XzaqamVZPDtAR3WP","echostr":"abcdef","token":"xdliiJTHZjSVN+nEZdIa51+t9g8="},"Code":8200,"Reason":""}}}
	        JSONObject json33 = JSON.parseObject(result);
	        String Data1 = json33.getString("Body");
	        JSONObject json22 = JSON.parseObject(Data1);
	        String Data2 = json22.getString("response");
	        JSONObject json11 = JSON.parseObject(Data2);
	        String Data = json11.getString("Data");
	        JSONObject json1 = JSON.parseObject(Data);
	        String token = json1.getString("token");
	        String userId = json1.getString("userId");
	        String userName = json1.getString("userName");
	        String trueName = json1.getString("trueName");
	        String cShowId = json1.getString("cShowId");
	        String[] str = {token, userId, userName, trueName, cShowId};
	  
	        return str;
	    }
	    /**   
		　* 描述：   根据分词打标签接口结果获得aa|bb|cc格式的词
		　* 创建人：coco   
		　* 创建时间：2019年9月26日 下午5:51:59         
		*/
		public static String getLebel(String label) {
			JSONObject json=JSON.parseObject(label);
			if(json.getInteger("code")==2000) {
				
				JSONObject json1=json.getJSONObject("data");
				JSONArray arr=json1.getJSONArray("result");
				StringBuilder str=new StringBuilder("");
				for (int i = 0; i < arr.size(); i++) {
					JSONObject a=arr.getJSONObject(i);
					str.append(a.getString("text")).append("|");
				}
				return str.toString();
			}else {
				return "";
			}
				
		}
		public static String[] assignment(JSONObject user) {
		      //{"iV4PZULZuhv6pglCwaTD1bBKL/Q=","XzaqamVZPDtAR3WP",xiejie","谢洁","b5WJZ1bRLDCb7x2B"}
			String[] str= {user.getString("LoginToken"),user.getString("loginName"),
					user.getString("useraccount"),user.getString("username"),user.getString("cShowId")};
			return str;
		}
	    
}
