package com.luqi.timer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import com.luqi.service.BoardTaskService;
import com.luqi.service.BoardTaskServiceA;

public class BoardTask extends TimerTask {

	@Override
	public void run() {
		try {
			/*SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (int i =-402;i<-398;i++) {
				Date date=new TimerManager().addDay(new Date(), i);
				System.out.println(s.format(date));
				BoardTaskServiceA.TaskCalculate(date);
			
			}*/
			BoardTaskServiceA.TaskCalculate(new Date());
		} catch (Exception e) {		
			e.printStackTrace();
		}
		
	}

}
