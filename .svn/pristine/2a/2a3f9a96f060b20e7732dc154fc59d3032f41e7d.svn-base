package com.luqi.timer;

import java.util.TimerTask;

import com.luqi.common.model.T1doRecord;

public class MyTask extends TimerTask {

	@Override
	public void run() {
		try {
			T1doRecord.batchSaveForTime();
		} catch (Exception e) {			
			e.printStackTrace();

		}
		
		

	}

}
