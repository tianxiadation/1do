package com.demo.util;

import com.demo.common.model.TRegUser;

//短信接口
public class ShortMessageUtil {

	/*
	 2019年2月28日 coco 注解：发短信
	*/
	public static String sendShortMessage(String SHOW_ID) {
		//xc123456  //https://tyhy.hzxc.gov.cn:28443/1do
		TRegUser user=TRegUser.dao.findFirst("select GROUP_CONCAT(b.U_MOBILE) U_MOBILE FROM t_1do_pstatus a,t_reg_user b where a.O_USER=b.SHOW_ID and a.SHOW_ID=?"
+" and a.isDelete=1 and a.USER_TYPE=3",SHOW_ID);
		String str="http://218.108.106.128:8080/web/getsms"
				+ "?username=xiacheng1do&password=5937FFAD5ECD5F6DD9A83B83895E480F"
				+ "&mobile="+user.getUMobile()+"&content=您有一条1do被催单,"
						+ "请及时登入OA网站或点击以下链接查看https://tyhy.hzxc.gov.cn:28443/1do/shortMessage/MyHtml.html?SHOW_ID="+SHOW_ID+"&extend=5159000&level=1";
		return HttpUtil.doGet11(str);
	}
	public static void main(String[] args) {
		System.out.println(sendShortMessage(""));
	}
}
