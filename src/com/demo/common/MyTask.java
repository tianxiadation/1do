package com.demo.common;

import java.util.TimerTask;

import com.demo.common.model.T1doRecord;

public class MyTask extends TimerTask {

	@Override
	public void run() {
		try {
			//T1doRecord.batchSaveForTime();
		} catch (Exception e) {
			
			e.printStackTrace();
			//new RobotController().sendErrorMsg();
			//new MessageController().sentMessage();
		}
		
		/*if(list.size()>0){
			System.out.println("Message successfully sent "+list.size());	  
		}else{
			System.out.println("There are no new messages!");
		}*/
		

	}

}