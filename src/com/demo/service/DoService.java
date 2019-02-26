package com.demo.service;


import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class DoService {

	public static List<Record> exportExcel(int type){
		String shi="否";
		//String temp=type==6?" and a.O_IS_DELETED=2 ":"";
		if(type==5||type==6){
			shi="是";
			//type=5;
		}
		
		/*String sql = "SELECT a.O_DESCRIBE,a.O_CREATE_TIME,a.O_FINISH_TIME,"
				+ "a.Real_FINISH_TIME,a.O_CUSTOMER_NAME,a.O_EXECUTOR_NAME,'"+shi+"' FINISH FROM t_1do_base a,"
				+ "t_1do_status b where a.SHOW_ID=b.SHOW_ID and b.O_STATUS=?"+temp; */
		String sql = "SELECT O_DESCRIBE,O_CREATE_TIME,O_FINISH_TIME,"
				+ "Real_FINISH_TIME,O_CUSTOMER_NAME,O_EXECUTOR_NAME,'"+shi+"' FINISH FROM t_1do_base where O_STATUS=?"; 
		return Db.find(sql,type);
	}

	
}
