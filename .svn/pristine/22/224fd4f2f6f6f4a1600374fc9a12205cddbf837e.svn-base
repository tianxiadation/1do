package com.luqi.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Context;
import org.apache.catalina.Manager;
import org.apache.catalina.Session;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.RequestFacade;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cxy.commonenum.TopicEnum;
import com.cxy.service.MessageService;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.luqi.common.model.T1doAttr;
import com.luqi.common.model.T1doBase;
import com.luqi.common.model.T1doFeedback;
import com.luqi.common.model.T1doLabel;
import com.luqi.common.model.T1doLabelFeedback;
import com.luqi.common.model.T1doLog;
import com.luqi.common.model.T1doProjectStakeholder;
import com.luqi.common.model.T1doPstatus;
import com.luqi.common.model.T1doRelation;
import com.luqi.interceptor.LoginInterceptor;
import com.luqi.timer.BxblTask;
import com.luqi.timer.CommonTask;
import com.luqi.util.DbUtil;
import com.luqi.util.HttpUtil;
import com.luqi.util.IDUtil;
import com.luqi.util.JsonUtil;
import com.luqi.util.MsgUtil;
import com.luqi.util.StrUtil;

public class TestController extends Controller{
	/**   
	　* 描述：   
	　* 创建人：coco   
	　* 创建时间：2020年11月11日 下午3:03:35         
	*/
	public void action1111() {
		List<T1doBase> list=T1doBase.dao.find("SELECT * FROM t_1do_base where source=12 and o_status=5");
		for (T1doBase t1doBase : list) {
		Record	record =Db.findFirst("select * from notice where test like ?","%"+t1doBase.getShowId()+"%");
			if(record==null)
			new Thread(new BxblTask(t1doBase.getShowId(), 5,t1doBase.getSOURCE())).start();
		}
		renderJson(MsgUtil.successMsg(null));
	}
	public void create() {
		
		for(int i=0;i<10000;i++) {
			List<T1doBase> list=T1doBase.dao.find("select * from t_1do_base_copy1");
			List<T1doPstatus> list1=new ArrayList<T1doPstatus>();
			//List<T1doBase> list
			for (T1doBase t1doBase : list) {
				/*JSONObject json=new JSONObject();
				t1doBase.remove("ID");
				json.put("BASE",JSON.parse(t1doBase.toJson()));
				try {
					String result =HttpUtil.doPost("http://localhost:8080/1do/common/createIdo", json);
					System.out.println(result);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				//JSONObject json=JsonUtil.getJSONObject(getRequest());
				//final T1doBase t1doBase =json.getObject("BASE", T1doBase.class);
				//JSONArray arr=json.getJSONArray("ATTR");
				String showId=IDUtil.getUid();
				t1doBase.remove("ID");
				/*if(StrKit.isBlank(t1doBase.getODescribe())||StrUtil.isEmpty(t1doBase.getOCustomer())||StrUtil.isEmpty(t1doBase.getCreateUser())||t1doBase.getSOURCE()==null){
					renderJson(MsgUtil.errorMsg(("内容、发起人、创建人、来源不能为空"));
					return;
				}else {
					//1Do 创建时必须有参与人，默认参与人是创建人
					if(StrUtil.isEmpty(t1doBase.getOExecutor()))
						t1doBase.setOExecutor(t1doBase.getCreateUser()).setOExecutorName(t1doBase.getCreateUserName());
					else if(!t1doBase.getOExecutor().contains(t1doBase.getCreateUser())) 
						t1doBase.setOExecutor(t1doBase.getOExecutor()+";"+t1doBase.getCreateUser()).setOExecutorName(t1doBase.getOExecutorName()+";"+t1doBase.getCreateUserName());
						
				}*/
				t1doBase.setShowId(showId);
				/*if(arr!=null){
					Long time=new Date().getTime();
				for (int i = 0; i < arr.size(); i++) {
					T1doAttr t1doAttr=arr.getObject(i, T1doAttr.class);
					t1doAttr.setShowId(t1doBase.getShowId());
					t1doAttr.save();
					new T1doFeedback().setShowId(t1doBase.getShowId()).setOUser(t1doAttr.getUploadUser())
					.setOUserName(t1doAttr.getUploadUserName()).setFbTime(t1doAttr.getUploadTime()).setTimeStamp(time+i)
					.setFbType(t1doBase.getSOURCE()==2?10:3).setATTRID(t1doAttr.getID()+"").setAttrPath(t1doAttr.getAttrPath()).setFBCONTENT(t1doAttr.getAttrName()==null?"":t1doAttr.getAttrName()).save();
					T1doLog.saveLog(t1doBase.getShowId(),t1doAttr.getUploadUser(), t1doAttr.getUploadUserName(), t1doAttr.getUploadUserName()+"上传"+t1doAttr.getAttrName(), 3, t1doAttr.getAttrName()); 	
				}}			*/
					//推送数据
					//new Thread(new CommonTask(t1doBase,7)).start();
					try {
						String[] users={t1doBase.getOCustomer(),t1doBase.getCreateUser(),t1doBase.getOExecutor()};
						String[] usernames={t1doBase.getOCustomerName(),t1doBase.getCreateUserName(),t1doBase.getOExecutorName()};
						for (int j = 0; j < users.length; j++) {
							if(StrUtil.isEmpty(users[j])){
								break;
							}
							String[] sonUsers=StrUtil.twoClear(users[j].split(";"));
							String[] sonUsernames=StrUtil.twoClear(usernames[j].split(";"));
							for (int iii = 0; iii < sonUsers.length; iii++) {
								//if(j==0&&i>0) {
									//发起人只有一位，传多位过来默认取第一位
									//continue;
								//}
								int k=2;//未读
								String str=sonUsers[iii];
								if(t1doBase.getCreateUser().equals(str)){
									k=1;
								}
								list1.add(new T1doPstatus().setShowId(t1doBase.getShowId()).setOUser(str).
								setOUserName(sonUsernames[iii]).setOStatus(3).setUserType(j+1).setIsRead(k).
								setSTATUS("待接单").setISLOOK(k).setOriginal(1));//.setIsRead(j==0?1:2)							
							}
							
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//T1doLog.saveLog(t1doBase.getShowId(), t1doBase.getCreateUser(), t1doBase.getCreateUserName(), t1doBase.getCreateUserName()+"创建了此1do", 1, "");
			   
			}
			Db.batchSave(list, 1000);
			Db.batchSave(list1, 1000);
		}
		 renderJson(MsgUtil.successMsg(1));
	}
	/**   
	　* 描述：   
	　* 创建人：coco   
	　* 创建时间：2020年7月17日 下午4:46:34         
	*/
	public void actionConfigTest() {
		System.out.println(PropKit.get("test"));
		renderJson(MsgUtil.successMsg(PropKit.get("test")));
	}
	/**
     * @Author coco
     * @Description 1do详情修改发起人/参与人/抄送人
     * @Date  2018年6月27日下午3:07:59 coco
    */
	 @Before(Tx.class)
	public void changeUser1() {
		JSONObject json=JsonUtil.getJSONObject(getRequest());
		JSONObject user=getSessionAttr("user");
		 T1doPstatus t1=T1doPstatus.getUser(json.getString("SHOW_ID"),user.getString("loginName"));
		 int power=T1doProjectStakeholder.getFlag(user.getString("loginName"), json.getString("SHOW_ID"), user.getIntValue("POWER"));
		boolean flag=json.getString("object").equals("参与人")?true:user.getBoolean("isfw")||StrUtil.getflag(t1==null?2:t1.getUserType(), 2)||power!=3;
		 if(flag){
		
					T1doBase t1doBase =T1doBase.getIdoBase(json.getString("SHOW_ID"));
					int userType=json.getString("object").equals("发起人")?1:3;
					String[] useraccount=json.getString("useraccount").split(";");
					List<T1doPstatus> list=T1doPstatus.getUserByType(t1doBase.getShowId(), userType);
					if(json.getString("method").equals("cover")){
						
					}else if(json.getString("method").equals("remove")){
						for (T1doPstatus t1doPstatus : list) {
							for (String ouser : useraccount) {
								if(t1doPstatus.getOUser().equals(ouser)) {
									t1doPstatus.delete();
								}
							}
						}
					}else{
						T1doPstatus tp=new T1doPstatus().setID(0);
						
						for (String ouser : useraccount) {
							tp.remove("ID").setOUser(ouser).setUserType(3).setOStatus(t1doBase.getOStatus())
							.setSTATUS(StrUtil.getStatus2(t1doBase.getOStatus()));
						}
						
					}
					DbUtil.updateExecutore(t1doBase.getShowId());
					renderJson(MsgUtil.successMsg("修改成功"));
		}else{
			renderJson(MsgUtil.errorMsg("权限不足"));
		}
	}
	/*
	 2019年3月20日 coco 注解：
	*/
	public void action() {
		HttpSession session = getSession();
		System.out.println(session.getId());
		JSONObject json=new JSONObject();
		json.put("sss", getPara("test"));
		setSessionAttr("user",json);
		//getSessionContext();
		//session.removeValue(getPara("sessionid"));

		renderJson(MsgUtil.successMsg(json));
		
	}
	@Before(LoginInterceptor.class)
	public void getAction() {
		/*	Enumeration<?> em = getSession().getAttributeNames();  //得到session中所有的属性名
			while (em.hasMoreElements()) {
		     getSession().removeAttribute(em.nextElement().toString()); //遍历删除session中的值
			}*/
		//HttpSession session = getSession();
		//System.out.println(session.getId());
		
		JSONObject value= getSessionAttr("user");
		renderJson(MsgUtil.successMsg(value));
	}
	public void getActiveNum() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, IOException {
		 int activeSessions = 8366;
		    if (getRequest() instanceof RequestFacade) {
		      Field requestField = getRequest().getClass().getDeclaredField("request");
		      requestField.setAccessible(true);
		      Request req = (Request) requestField.get( getRequest());
		      Context context = req.getContext();
		      Manager manager = context.getManager();
		      Session session= manager.findSession(getPara("sessionId"));
		      session.isValid();
		      activeSessions = manager.getActiveSessions();
		    }
		    renderJson(MsgUtil.successMsg(activeSessions));
	}
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
	/*
	 2019年2月12日 coco 注解：
	 */
	public static int getHashSet1(HashSet<String> hs1,List<T1doLabel> list) {
		HashSet<String> hs = new HashSet<String>();
		list.forEach(t->{
			hs.add(t.getLABEL());
		});
		hs1.forEach(t->{
			hs.add(t);
		});
		return hs.size();
		
	}
	/*
	 2019年2月12日 coco 注解：
	 */
	public static int getHashSet2(List<T1doLabelFeedback> list1,List<T1doLabel> list) {
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
	/**   
	　* 描述：   
	　* 创建人：coco   
	　* 创建时间：2019年10月14日 下午3:38:36         
	*/
	public void download() {
		HttpServletResponse response=getResponse();
		Map<String,String> map = new HashMap<String,String>();
		  map.put("loanid", "11");
		  Map<String, String> urlByLoanid= new HashMap<String,String>();
		 // Map<String, String> urlByLoanid = zcmQueryInfoService.queryUrlByLoanid(map);
		  try {
		   if(urlByLoanid!=null){
		    //String wjurl="http://10.0.15.11:8080/gateway//nfs/marvel-core-admin/2017/10/11/compact_seal_17101119371231615_7.pdf";
		    String wjurl="http://59.202.68.43:8080/1do/upload/184476961043644416.doc";
		    //String wjurl = urlByLoanid.get("url");
		    int i = wjurl.lastIndexOf("/");
		    String fileName = wjurl.substring(i+1);
		    
		   URL url = new URL(wjurl); 
		   HttpURLConnection conn = (HttpURLConnection)url.openConnection(); 
		   //设置超时间为3秒 
		   conn.setConnectTimeout(3*1000); 
		   //防止屏蔽程序抓取而返回403错误 
		   conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)"); 
		   //得到输入流 
		   InputStream inputStream = conn.getInputStream(); 
		   //获取自己数组 
		   byte[] bs = readInputStream(inputStream);
		   File file = new File("d:/work/184476961043644416.doc");
		   write(file,bs);
		   response.setContentType("application/octet-stream;charset=ISO8859-1");
		    BufferedOutputStream output = null;
		    BufferedInputStream input = null;
		     try {
		      output = new BufferedOutputStream(response.getOutputStream());

		      // 中文文件名必须转码为 ISO8859-1,否则为乱码
		      String fileNameDown = new String(fileName.getBytes(), "ISO8859-1");
		      // 作为附件下载
		      response.setHeader("Content-Disposition", "attachment;filename=" + fileNameDown);
		  
		      output.write(bs);
		     // response.flushBuffer();
		     } catch (Exception e) {
		    	 e.printStackTrace();
		    	 System.out.println("Download log file error");
		      //log.error("Download log file error", e);
		     } // 用户可能取消了下载
		     finally {
		      if (input != null)
		       try {
		        input.close();
		       } catch (IOException e) {
		        e.printStackTrace();
		       }
		      if (output != null)
		       try {
		        output.close();
		       } catch (IOException e) {
		        e.printStackTrace();
		       }
		     }
		   }
		  } catch (Exception e) {
		   e.printStackTrace();
		  }

		renderJson(MsgUtil.successMsg(1));
	}
	/**
	 * 从输入流中获取字节数组
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */ 
	 public static byte[] readInputStream(InputStream inputStream) throws IOException { 
	 byte[] buffer = new byte[1024]; 
	 int len = 0; 
	 ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
	 while((len = inputStream.read(buffer)) != -1) { 
	 bos.write(buffer, 0, len); 
	 } 
	 bos.close(); 
	 return bos.toByteArray(); 
	 }
	 /**   
	　* 描述：   
	　* 创建人：coco   
	　* 创建时间：2019年10月14日 下午4:50:03         
	*/
	//写文件
		public static void write(File file,byte[] bs) throws Exception{
			//1.创建字节缓冲流输出流
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			
			//2.写数据
			//写单个字节
			//bos.write(100);
			//写一个字节数组
			bos.write(bs);     //getBytes():返回字节数组
			
			//3.刷新并关闭流
			bos.flush();
			bos.close();
			
		}
	public static String getString11111() {
		return null;
		/* File f = new File("d:/work/184476961043644416.doc"); 

		    if (!f.exists()) { 
		    System.out.println("creat " + f.toString()); 

		      f.createNewFile(); 
		     }

		    FileOutputStream fos = new FileOutputStream(f);//InputStream和OutputStream 一般辩证的来看，一个读，一个写，两者差不多的用法。

		  fos.write(buffer, 0, len); 
		  fos.flush();
		  fos.close(); */
	}
}
