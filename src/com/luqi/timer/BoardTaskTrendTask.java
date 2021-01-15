package com.luqi.timer;

import com.jfinal.kit.LogKit;
import com.jfinal.plugin.activerecord.Record;
import com.luqi.common.model.T1doBoard;
import com.luqi.common.model.T1doBoardTaskProgress;
import com.luqi.common.model.T1doBoardTaskRemarks;
import com.luqi.common.model.T1doProject;

import java.util.Calendar;
import java.util.List;
import java.util.TimerTask;
import java.util.stream.Collectors;

/**
 * @author Sherry
 * @ClassName BoardTaskTrendTask
 * @Description 项目看板任务完成趋势图定时任务（每天0点计算前一天任务完成情况）
 * @date 2020/1/6 2:24 PM
 */
public class BoardTaskTrendTask extends TimerTask {
    @Override
    public void run() {
        try {
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_MONTH, -1);
            List<T1doProject> projects = T1doProject.getNotCompleteProjects();
            for (T1doProject project : projects) {
                //项目完成情况对象
                T1doBoardTaskProgress taskProgress = new T1doBoardTaskProgress();
                taskProgress.setDATE(c.getTime());
                taskProgress.setProjectId(project.getItemId());
                //计算完成情况
                List<Record> projectChildren = T1doBoard.getProjectChildren(project.getItemId(), 0);
                if (projectChildren != null && projectChildren.size() > 0) {
                    List<Record> completion = projectChildren.stream().filter(item -> "已完成".equals(item.getStr("COMPLETION"))).collect(Collectors.toList());
                    taskProgress.setAllNumber(projectChildren.size());
                    taskProgress.setAlreadyNumber(completion.size());
                    taskProgress.setRATE(Math.round(((float) completion.size()) * 100 / projectChildren.size()));
                } else {
                    taskProgress.setAllNumber(0);
                    taskProgress.setAlreadyNumber(0);
                    taskProgress.setRATE(0);
                }
                taskProgress.save();
            }
        } catch (Exception e) {
            LogKit.error("项目进展计算失败～");
        }
    }


}
