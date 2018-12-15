package com.demo.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.sun.media.jfxmedia.track.Track.Encoding;

/**
 * @Author: Zzih
 * @Date: 2018/8/13 15:54
 * @Description: 时间工具类
 *
 */
public class TimeUtil {
    /**
     * 年-月-日格式器
     */
    static SimpleDateFormat yMdSDF = new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat yMdhmsSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static SimpleDateFormat yMdhmsSDF1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    /**
     * 年月日格式器
     */
    static SimpleDateFormat dateSDF = new SimpleDateFormat("yyyyMMdd");
    /**
     * 年月格式器
     */
    static SimpleDateFormat yMSDF = new SimpleDateFormat("yyyy-MM");
    /**
     * 年格式器
     */
    static SimpleDateFormat ySDF = new SimpleDateFormat("yyyy");

    /*	public static void main(String[] args) {
            System.out.println(getYearMonth());
            System.out.println(addMonth("2018-12"));
            System.out.println(getDate());
            System.out.println(getDayOfMonth("2018-02"));
            System.out.println(getDay());
            System.out.println("201812:" + weekToTimeQuantum("201812"));
        }*/

    /**
     * 判断时间有效期
     *
     * @param datetmie
     * @return
     */
    public static void main(String[] args) {
        System.out.println(getDateTime());
    }
    public static boolean estimateTime(String datetmie) {
        boolean flag = false;
        Date afterDate = new Date(System.currentTimeMillis()+ 600000);
        Date beforeDate = new Date(System.currentTimeMillis() - 600000);
        Date date = new Date(Long.parseLong(datetmie));
        if (date.after(beforeDate) && date.before(afterDate)) {
            flag = true;
        }
        return flag;
    }

    /**
     * 判断时间是否在5分钟之前
     * @param datetmie
     * @return
     */
    public static boolean estimateTime5M(Date date) {
        boolean flag = false;
        Date beforeDate = new Date(System.currentTimeMillis() - 300000);
        if (date.before(beforeDate)) {
            flag = true;
        }
        return flag;
    }

    public static String weekToTimeQuantum(String weekTime) {
        Integer year = Integer.valueOf(weekTime.substring(0, 4));
        Integer week = Integer.valueOf(weekTime.substring(4, 6));
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR, week);
        String beginTime = yMdSDF.format(cal.getTime());
        cal.add(Calendar.DATE, 7);
        String endTime = yMdSDF.format(cal.getTime());
        return beginTime + " 至 " + endTime;
    }

    /**
     * 获取本月1号的日期 2018-05-01
     *
     * @return String
     */
    public static String getYearMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 1);
        String result = yMdSDF.format(cal.getTime());
        return result;
    }

    /**
     * 给月份加上一个月
     *
     * @param dateStr 年月
     * @return
     */
    public static String addMonth(String dateStr) {
        try {
            Date date = yMSDF.parse(dateStr);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.MONTH, 1);
            String result = yMSDF.format(cal.getTime());
            return result;
        } catch (ParseException e) {
            System.out.println("日期格式错误:" + dateStr);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取年月日
     *
     * @return
     */
    public static String getDate() {
        return dateSDF.format(new Date());
    }
    public static String getDateTime() {
    	return yMdhmsSDF.format(new Date());
    }
    public static String getDateTime1() throws UnsupportedEncodingException {
    	/*String str =
    	System.out.println(str);
    	//解码
    	String str1=URLDecoder.decode(str, "UTF-8");
    	System.out.println(str1);*/
      return	URLEncoder.encode( yMdhmsSDF1.format(new Date()),"utf-8");
    	
    }

    /**
     * 获取当前月天数
     *
     * @param date
     * @return
     */
    public static int getDayOfMonth(String date) {
        Date newDate = new Date();
        if (date != null) {
            try {
                newDate = yMSDF.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(newDate);
        cal.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        cal.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
        int maxDate = cal.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 获取本月的日期 2018-05
     *
     * @return String
     */
    public static String getTodayYearMonth() {
        Calendar cal = Calendar.getInstance();
        String result = yMSDF.format(cal.getTime());
        return result;
    }

    /**
     * 获取当前几号
     *
     * @return
     */
    public static int getDay() {
        Calendar cal = Calendar.getInstance();
        int today = cal.get(Calendar.DAY_OF_MONTH);
        return today;
    }

    /**
     * 获取当前年
     */
    public static String getYear() {
        String year = ySDF.format(new Date());
        return year;
    }
    public static String getCurrentDateTime(String format) {
		SimpleDateFormat t = new SimpleDateFormat(format);
		return t.format(new Date());
	}
}
