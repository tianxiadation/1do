package com.luqi.dao;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.Date;
import java.util.List;

/**
 * @ClassName LogDao
 * @Description 日志系统dao
 * @auther Sherry
 * @date 2019/8/21 1:47 PM
 */
public class LogDao {

    /**
     * 获取一定时间之内的假期
     * @return
     */
    public static List<Record> getHoliday(String startDate, String endDate) {
        String sql = "SELECT * FROM cuibanholiday WHERE [date] > ? and [date] < ?";
        return Db.use("record").find(sql,startDate,endDate);
    }

    /**
     * 获取当日日志内容
     * @param account
     * @return
     */
    public static List<Record> getLogByAccount(String account, Date startDate, Date endDate) {
        String sql = "SELECT wd.id, wd.WorkName, wp.AddTime, wd.WorkClass,wp.InputTime FROM WorkRecord wd , WorkReport wp,PUser u " +
                "WHERE wd.WorkReportID = wp.id AND wp.ExecutiveAccount = u.userCode AND u.userName in ("+account+") AND InputTime >= ? AND InputTime < ?";
        return Db.use("record").find(sql, startDate,endDate);
    }
}
