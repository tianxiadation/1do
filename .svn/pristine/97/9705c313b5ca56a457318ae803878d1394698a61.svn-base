package com.demo.interceptor;

import com.demo.common.model.T1doLabel;
import com.demo.util.HttpUtil;

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
		
		T1doLabel.saveList(HttpUtil.getlabel(result), showId,type);		
	}

}
