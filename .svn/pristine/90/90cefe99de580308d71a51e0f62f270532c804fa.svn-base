package com.luqi.interceptor;

import org.apache.log4j.Logger;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.kit.LogKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.luqi.common.model.T1doIp;
import com.luqi.util.Ip;
import com.luqi.util.JsonUtil;
import com.luqi.util.StrUtil;
import com.luqi.util.UrlUtil;

public class MainInterceptor implements Interceptor {

	private Logger log=Logger.getLogger(MainInterceptor.class);
	
	public void intercept(Invocation inv) {
		
		// TODO Auto-generated method stub
		//LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		//HttpSession session = inv.getController().getSession();
		//System.out.println(session.getId());
		try {	
			String ip=inv.getController().getRequest().getRemoteAddr();
			//new T1doIp().setIp(Ip.getIp(inv.getController().getRequest())).save();	
			new T1doIp().setIp(ip).save();
		    Record r=Db.findFirst("select * from t_1do_ip_filter where ip=?",ip);
			if(r!=null) {
				//log.error(StrUtil.getTrace(e));
				inv.getController().renderJson(JsonUtil.getMap(500, "非法调用"));		
			}else{
				//log.error(inv.getActionKey());
				LogKit.info("Request:" + inv.getActionKey());
				inv.invoke();
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			//log.error(StrUtil.getTrace(e));
			inv.getController().renderJson(JsonUtil.getMap(400, StrUtil.getTrace(e)));			
		}
		

	}

}
