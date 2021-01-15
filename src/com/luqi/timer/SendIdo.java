package com.luqi.timer;

import java.util.List;

import com.luqi.common.model.T1doBase;
import com.luqi.common.model.T1doPstatus;
import com.luqi.service.DoService;

public class SendIdo implements Runnable {
	private T1doBase t1doBase;
	private Integer i;//6评价，5"确认办结"4催办    3改变工单状态，t1doBase.getOStatus()==3?1:t1doBase.getOStatus()
	private String O_USER;
	private String username;
	private Integer type;//1群发2单独发通知3整理层单独发通知
	private String content;
	private List<T1doPstatus> t1;
	



	public SendIdo(T1doBase t1doBase, Integer i, String o_USER, String username, Integer type, String content,
			List<T1doPstatus> t1) {
		super();
		this.t1doBase = t1doBase;
		this.i = i;
		O_USER = o_USER;
		this.username = username;
		this.type = type;
		this.content = content;
		this.t1 = t1;
	}


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
			//群发
			DoService.sendIdo(t1doBase, i, O_USER,content,username,t1);
			if(i==44){
				DoService.cbts(t1doBase.getShowId());//催报发综合信息平台催办接口
			}else{				
				DoService.dbts(t1doBase.getShowId());//其他通知发综合信息平台代办待阅
			}
			break;
		case 2:
			//单独发
			DoService.sendOneIdo(t1doBase, i, O_USER, username,content);
			DoService.dbts(t1doBase.getShowId());
			break;
		case 3:
			//发送给整理层	
			DoService.fwsendOneIdo(t1doBase, i, O_USER, username,content);
			break;
		case 4:
			//审批主动办	
			DoService.approval(t1doBase,content);
			break;
		case 5:
			//三实库审批	
			DoService.ssk(t1doBase,content);
			break;
		default:
			break;
		}
		
	}

}