package com.luqi.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map.Entry;

import com.jfinal.core.Controller;

public class UrlUtil {
	//正式地址
	
	/*
	 * public static String ip="https://tyhy.hzxc.gov.cn:8443";//附件地址 public static
	 * String jdlj="D:\\1do\\upload\\";//绝对路径 public static String
	 * attrUrl="https://tyhy.hzxc.gov.cn:8443/1do/upload/";//附件地址
	 */	 	
	
	public static String ip="https://1do.hzxc.gov.cn:8443";//附件地址 
	public static  String jdlj="E:\\1do\\upload\\";//绝对路径
	public static String  attrUrl="https://1do.hzxc.gov.cn:8443/1do/upload/";//附件地址
	 	
	
    public static String zhxxpt="https://zh.hzxc.gov.cn/hubsupport/";//主动办地址
    public static String url="http://xcgovapi.hzxc.gov.cn/Base-Module/Message";//通知接口
    public static String md="https://tyhy.hzxc.gov.cn:8443/md/do/selectAnalysis";//秒懂搜索接口
   // public static String getUser="http://172.16.8.7:6002";//获得用户
    public static String getUser="http://xcgovapi.hzxc.gov.cn";//获得用户
    public static String loginURL="http://xcgov.hzxc.gov.cn";//1call登入
    public static String batchPut="http://172.16.9.110:6003/Base-Module/Message/BatchPut";//批量发通知
    //4个平台
    public static String four="http://172.16.9.209:81";
    //综合指挥平台
    public static String commandPlatform="https://control.hzxc.gov.cn";
    //分词
    public static String segword_api="http://59.202.68.44:8016/segword_api";
    //三实库
    public static String ssk="http://59.202.68.28:8080/ssk/qs/approval";
    //微信同步地址
    //public static String weChat="http://172.16.9.110:5211";
    public static String weChat="http://10.18.44.234:5211";
    //督查考核
    public static String dckh="http://172.16.8.7:8417/Warning-Modules/Warning/Do";
    //云上城管新建
    //public static String yscg="http://112.17.122.168:8079/xc-event/messag-linkage/1call-1do";
    public static String yscg="http://59.202.68.224:8079/xc-event/messag-linkage/1call-1do";
    //云上城管修改
    public static String yscg_update="http://59.202.68.224:8079/xc-event/messag-linkage/1call-1do/update";
    //城管修改(办结)
    public static String cg_update="http://59.202.68.224:8079/xc-event/call-do/updae/do";
    //云上城管新建、修改同步
    public static String yscg_add="http://59.202.68.224:8079/xc-event/call-do/add";
    //云上城管删除同步
    public static String yscg_delete="http://59.202.68.224:8079/xc-event/call-do/delete/do";
    //百姓爆料
   //public static String bxbl="https://szgdjsc.hzxc.gov.cn:9443/disclosure-api";
     public static String bxbl="https://zzdsecond.hzxc.gov.cn/disclosure-api";
     
 	//public static String ido="http://193.168.57.11:8080";
 	 //获取用户信息url
 	public static String getUserInfoUrl = "http://193.168.57.5:8080/auth/account/info";
 	
 	//通讯录
 		public static String addressBook="http://193.168.57.5:8013";

 		public static String appId="5c6cba30b2ca402180833e2252e26f7a";
 		public static String appSecret="46c94d71da959dbc110bd1c4c0ba2cbe74868455";

	public static void initialization() {
				
	            if(!isHostAddress()){
	                //测试地址   
					ip="http://59.202.68.43:8080";//附件地址
					attrUrl="http://59.202.68.43:8080/1do/upload/";//附件地址
					//attrUrl="http://localhost:8080/1do/upload/";//附件地址
					zhxxpt="https://zh.hzxc.gov.cn/hubsupport2/";//主动办地址
					url="http://172.16.8.7:6002/Base-Module/Message";//通知接口
					md="http://59.202.68.43:8080/md/do/selectAnalysis";//秒懂搜索接口
					//jdlj="D:\\1do\\upload\\";//绝对路径
					batchPut="http://172.16.8.7:6003/Base-Module/Message/BatchPut";//批量发通知
					four="http://172.16.9.117:80";
					commandPlatform="http://172.16.8.7:8051";
					//ssk="http://59.202.68.43:8080/ssk/qs/approval";
				    weChat="http://172.16.8.7:5211";
				    dckh="http://172.16.8.7:8417/Warning-Modules/Warning/Do";
				    
				    yscg="http://59.202.68.224:8079/xc-event/messag-linkage/1call-1do";
				    
				    yscg_update="http://59.202.68.224:8079/xc-event/messag-linkage/1call-1do/update";
				    
				    cg_update="http://59.202.68.224:8079/xc-event/call-do/updae/do";
				    
				    bxbl="https://hqt.baitutech.cn/disclosure";
				    
					//segword_api="http://59.202.68.43:8016/segword_api";
				   // loginURL="http://172.16.8.7:6002";//获得用户

					//loginURL="http://1call.avatar.com";//通知接口
					/*getUser="http://172.16.8.7:6003/Base-Module/CompanyUser/GetUser";//通知接口
*/	            }
	          
	       
				
	}
	/**
	 * 是否是正式环境
	 * @return
	 */
	 public static boolean isHostAddress(){
		 boolean flag=true;
		 try {
			InetAddress address = InetAddress.getLocalHost();
			 System.out.println(address.getHostAddress());
			 flag=address.getHostAddress().equals("172.16.9.231");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return flag;
	 }
	 
	 public static String formatMap(Controller c) {
			StringBuilder sb = new StringBuilder("");
			boolean isFirst = true;
			for (Entry<String, String[]> entry : c.getParaMap().entrySet()) {
	 
				if (isFirst)
					isFirst = false;
				else
					sb.append("&");
	 
				String key = entry.getKey();
				String valueformat = "";
				for (String value : entry.getValue()) {
					if (valueformat.length() == 0)
						valueformat += value;
					else
						valueformat += "," + value;
				}
	 
				sb.append(key).append("=").append(valueformat);
	 
			}
			return sb.toString();
		}
	

}