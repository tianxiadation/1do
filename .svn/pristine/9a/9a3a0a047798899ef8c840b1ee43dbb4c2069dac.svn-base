package com.luqi.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.jfinal.kit.LogKit;
import com.jfinal.kit.PropKit;
import com.luqi.common.model.TRegUser;

//短信接口
public class ShortMessageUtil {
	//private static Logger log=Logger.getLogger(ShortMessageUtil.class);
	/*
	 2019年2月28日 coco 注解：发短信
	*/
	public static String[] sendShortMessage(String SHOW_ID, String content, String name) {

		TRegUser user=TRegUser.dao.findFirst("select GROUP_CONCAT(b.U_MOBILE) U_MOBILE FROM t_1do_pstatus a,t_reg_user b where a.O_USER=b.SHOW_ID and a.SHOW_ID=?"
                                             +" and a.isDelete=1 and a.USER_TYPE=3",SHOW_ID);
		String str = null;
		try {
			str = PropKit.get("shortMessage")+"/web/getsms?username=xiacheng1do&password=5937FFAD5ECD5F6DD9A83B83895E480F"
					+ "&mobile="+user.getUMobile()+"&content=您的任务:+"+URLEncoder.encode(content,"UTF-8")+"...被"+name+"催办了,请及时登入OA网站或点击以下链接查看"+UrlUtil.ip+"/1do/shortMessage/MyHtml.html?SHOW_ID="+SHOW_ID+"&extend=5159000&level=1";
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			LogKit.error(StrUtil.getTrace(e));
			e.printStackTrace();
		}
	
		return new String[]{HttpUtil.doGet11(str),MobileUtil.sendCall(user.getUMobile())};
	}
	
	
}
