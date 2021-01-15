package com.luqi.util;

import com.jfinal.kit.PropKit;

//电话接口
public class MobileUtil {
	//private static Logger log=Logger.getLogger(ShortMessageUtil.class);
	/*
	 2020年1月13日 coco   新建语音账号：xczwyuyin  密码：xc123456
	*/
	public static String sendCall(String mobile) {
		String mo="";
		if(StrUtil.isNotEmpty(mobile)) {
			String[] ss=mobile.split(",");
			for(String s:ss) {
				mo+="86"+s+",";
			}
			
		}
		String str = PropKit.get("call")+"?usr=xczwyuyin&pwd=5937FFAD5ECD5F6DD9A83B83895E480F&mobile="+mo+"&sms=您好，您有1条1Do催办，请尽快处理。&languageCode=zh-CN&pin=1";
		System.out.println("电话通知结果:"+str);
		return HttpUtil.doGet11(str);
	}
	
	
}
