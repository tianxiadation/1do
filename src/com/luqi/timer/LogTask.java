package com.luqi.timer;

import com.jfinal.kit.LogKit;
import com.luqi.service.BoardService;
import com.luqi.service.BoardTaskService;

import java.util.TimerTask;

/**
 * @ClassName LogTask
 * @Description
 * @auther Sherry
 * @date 2019/8/22 10:26 AM
 */
public class LogTask extends TimerTask {
    @Override
    public void run() {
        try {
            BoardTaskService.getLogNew();
        } catch (Exception e) {
            e.printStackTrace();
            LogKit.error(e.getMessage());
        }

    }
}
