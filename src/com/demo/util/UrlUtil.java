package com.demo.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class UrlUtil {
				//正式地址
	public static String ip="https://tyhy.hzxc.gov.cn:8443";//附件地址
    public static String attrUrl="https://tyhy.hzxc.gov.cn:8443/1do/upload/";//附件地址
    public static String zhxxpt="https://zh.hzxc.gov.cn/hubsupport/";//主动办地址
    public static String url="http://xcgovapi.hzxc.gov.cn/Base-Module/Message";//通知接口
    public static String md="https://tyhy.hzxc.gov.cn:8443/md/do/selectAnalysis";//通知接口
	public static void initialization() {
		
		
			try {
	            //用 getLocalHost() 方法创建的InetAddress的对象
	            InetAddress address = InetAddress.getLocalHost();
	            System.out.println(address.getHostAddress());
	            if(!address.getHostAddress().equals("172.16.9.195")){
	                //测试地址   
					ip="http://59.202.68.43:8080";//附件地址
					attrUrl="http://59.202.68.43:8080/1do/upload/";//附件地址
					zhxxpt="https://zh.hzxc.gov.cn/hubsupport2/";//主动办地址
					url="http://172.16.8.7:6002/Base-Module/Message";//通知接口
					md="http://59.202.68.43:8080/md/do/selectAnalysis";//秒懂搜索接口
	            }/*else if(address.getHostAddress().equals("10.18.28.8")){
	            	 //测试地址   
					ip="http://10.18.28.8:8080";//附件地址
					attrUrl="http://10.18.28.8:8080/1do/upload/";//附件地址
					zhxxpt="https://zh.hzxc.gov.cn/hubsupport2/";//主动办地址
					url="http://172.16.8.7:6002/Base-Module/Message";//通知接口
	            }*/
	          
	        } catch (UnknownHostException e) {
	            e.printStackTrace();
	        }
				
			 	
				
	}
	
}
