package com.demo.interceptor;

import com.demo.common.model.T1doBase;
import com.demo.service.DoService;

public class SendIdo implements Runnable {
	private T1doBase t1doBase;
	private Integer i;
	private String O_USER;
	private String username;
	private Integer type;//1群发2单独发通知3整理层单独发通知
	private String content;
	
	

	public SendIdo(T1doBase t1doBase, Integer type, String content) {
		super();
		this.t1doBase = t1doBase;
		this.type = type;
		this.content = content;
	}


	public SendIdo(T1doBase t1doBase, Integer i, String o_USER, String username, Integer type) {
		super();
		this.t1doBase = t1doBase;
		this.i = i;
		O_USER = o_USER;
		this.username = username;
		this.type = type;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		switch (type) {
		case 1:
			DoService.sendIdo(t1doBase, i, O_USER);
			if(i==4){
				DoService.cbts(t1doBase.getShowId());//催报发催办接口
			}else{				
				DoService.dbts(t1doBase.getShowId());//其他通知发代办待阅
			}
			break;
		case 2:
			DoService.sendOneIdo(t1doBase, i, O_USER, username);
			DoService.dbts(t1doBase.getShowId());
			break;
		case 3:
			DoService.fwsendOneIdo(t1doBase, i, O_USER, username);
			break;
		case 4:
			DoService.approval(t1doBase,content);
			break;
		case 5:
			DoService.ssk(t1doBase,content);
			break;
		default:
			break;
		}
		
	}

}
