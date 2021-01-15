package com.luqi.timer;

import com.luqi.common.model.T1doLabel;
import com.luqi.util.HttpUtil;
import com.luqi.util.JsonUtil;
import com.luqi.util.StrUtil;
import com.luqi.util.UrlUtil;

public class AddLabel implements Runnable {
	private String result;
	private String showId;
	private Integer type;
	
	

	public AddLabel(String result, String showId, Integer type) {
		super();
		this.result = result;
		this.showId = showId;
		this.type = type;
	}



	@Override
	public void run() {
		
		//T1doLabel.saveList(HttpUtil.getlabel(result), showId,type);//（旧方法）
		//调用分词系统打标签接口（新方法）
		T1doLabel.saveList(getLebel(result), showId,type);		
	}
	/**   
	　* 描述：   
	　* 创建人：coco   
	　* 创建时间：2019年9月26日 下午6:43:11         
	*/
	public static String getLebel(String result) {
		String label=HttpUtil.doPost(UrlUtil.segword_api+"/analysis/getLabels", JsonUtil.getSegwordParameter(result).toString());		
		return StrUtil.getLebel(label);
	}
}