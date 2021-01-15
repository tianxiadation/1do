package com.luqi.timer;

import java.util.List;

import org.apache.log4j.Logger;

import com.luqi.common.model.T1doPstatus;
import com.luqi.util.HttpClientUtil;
import com.luqi.util.UrlUtil;

/**
* @author coco
* @date 2020年4月23日 上午11:22:37
* 1do对接1call督查考核系统:1do需要在催办和完成的时候触发这个接口
*/
public class SupervisionAndEvaluation implements Runnable {
	private Logger log=Logger.getLogger(SupervisionAndEvaluation.class);
	private int type;
	private String showId;
	
	public SupervisionAndEvaluation(int type, String showId) {
		super();
		this.type = type;
		this.showId = showId;
	}

	@Override
	public void run() {
		 List<T1doPstatus> executor=T1doPstatus.getUserByType(showId, 3);
		 executor.forEach(t->{
			/*JSONObject json=new JSONObject();
			json.put("type", type);
			json.put("systemType", 1);
			json.put("loginName", t.getOUser());*/
			String param=UrlUtil.dckh+"?type="+type+"&systemType=1&loginName="+t.getOUser();
			String result1 = HttpClientUtil.doGet(param);
			log.error(param);
			log.error(result1);
		 });
		 
		
	}
	
}
