package com.demo.util;

import com.jfinal.plugin.activerecord.Db;

public class FlagUtil {
	/*
	 2019年4月1日 coco 注解：//查看是否存在参与人
	*/
	public static boolean getFlag(String showid) {
		return Db.findFirst("select * from t_1do_pstatus where SHOW_ID=? and USER_TYPE=3 AND isDelete=1",showid)==null?true:false;
	}
}
