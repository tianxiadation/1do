package com.luqi.timer;

import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import com.alibaba.fastjson.JSONObject;
import com.luqi.common.model.Notice;
import com.luqi.common.model.T1doBase;
import com.luqi.common.model.T1doCallback;
import com.luqi.common.model.T1doCallbackField;
import com.luqi.service.BoardTaskServiceA;
import com.luqi.util.HttpUtil;
import com.luqi.util.UrlUtil;

/**
 * 
 * @author 39805
 * 公共任务回调方法
 */
public class CommonTask extends TimerTask {
	private T1doBase t;
	private int type;//类型1、公用（如showid），2、状态，3、内容，4、标题，5、发起人，6、参与人,7推送数据字段
	
	public CommonTask(T1doBase t, int type) {
		super();
		this.t = t;
		this.type = type;
	}

	@Override
	public void run() {
		try {
			T1doCallback tc=T1doCallback.getT1doCallback(t.getSOURCE(),type);
			if(tc!=null) {
				List<T1doCallbackField> tcfs=T1doCallbackField.getT1doCallbackField(tc.getId());
				JSONObject json=new JSONObject();
				for (T1doCallbackField tcf : tcfs) {
					json.put(tcf.getFieldName(), t.getStr(tcf.getFieldName()));
				}
				
				//查看是什么环境就用什么接口
				String result=HttpUtil.doPost1(UrlUtil.isHostAddress()?tc.getTestUrl():tc.getFormalUrl(), json.toString());
				new Notice().setTest(UrlUtil.isHostAddress()?tc.getTestUrl():tc.getFormalUrl()).setResult(result).setParameter(json.toString()).save();
			}
			
		} catch (Exception e) {		
			e.printStackTrace();
		}
		
	}

}
