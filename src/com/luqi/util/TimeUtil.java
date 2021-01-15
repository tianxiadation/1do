package com.luqi.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//import com.sun.media.jfxmedia.track.Track.Encoding;

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
    static SimpleDateFormat MdSDF = new SimpleDateFormat("MM-dd");
    static SimpleDateFormat yMdhmsSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static SimpleDateFormat yMdhmsSDF1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    static SimpleDateFormat hmsSDF = new SimpleDateFormat("HH:mm:ss");
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

    
    /**   
	　* 描述：   比较两时间大小
	　* 创建人：coco   
	　* 创建时间：2019年10月21日 下午1:53:49         
	*/
	public static boolean getFlag(Date now,Date otherTime,int i) {
		
		return (now.getTime()-otherTime.getTime())>=3600000*i;
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
     * 获取时分秒
     *
     * @return
     */
    public static String getHmsSDF() {
        return hmsSDF.format(new Date());
    }

    /**
     * 获取年月日
     *
     * @return
     */
    public static String getDate() {
        return dateSDF.format(new Date());
    }
    /**
     * 获取年月日
     *
     * @return
     */
    public static String getyMdhmsSDF() {
    	return yMdhmsSDF.format(new Date());
    }
    /**
     * 获取年月日"yyyy-MM-dd
     *
     * @return
     */
    public static String getDate(Date date) {
        return yMdSDF.format(date);
    }
    /**
     * 获取月日"MM-dd
     *
     * @return
     */
    public static String getMDDate(Date date) {
    	return MdSDF.format(date);
    }
    /**
     * 获取今天年月日"yyyy-MM-dd
     *
     * @return
     */
    public static String getDateTime() {
    	return yMdSDF.format(new Date());
    }
    public static String getDateTime1() throws UnsupportedEncodingException {
    	/*String str =
    	System.out.println(str);
    	//解码
    	String str1=URLDecoder.decode(str, "UTF-8");
    	System.out.println(str1);*/
      return	URLEncoder.encode( yMdhmsSDF1.format(new Date()),"GBK");
    	
    }
    /**   
	　* 描述：   
	　* 创建人：coco   
	　* 创建时间：2019年11月14日 下午4:09:05         
	*/
	public static long getStrToLongTIme(String date) {
	  
     SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
				return format.parse(date).getTime();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            return 0;
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
    public static boolean getBetweenDays(String t1, String t2)  {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
            Date d1 = format.parse(t1);
            Date d2 = format.parse(t2);
            Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();
            c1.setTime(d1);
            c2.setTime(d2);
            return c1.after(c2);
        
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }
    public static int getBetweenDays1(String t1, String t2)  {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        int betweenDays = 0;
        try {
            Date d1 = format.parse(t1);
            Date d2 = format.parse(t2);
            Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();
            c1.setTime(d1);
            c2.setTime(d2);
// 保证第二个时间一定大于第一个时间
            if (c1.after(c2)) {
                //c1 = c2;
                c1.setTime(d2);
                c2.setTime(d1);
            }
            int betweenYears = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
            betweenDays = c2.get(Calendar.DAY_OF_YEAR)
                    - c1.get(Calendar.DAY_OF_YEAR);
            for (int i = 0; i < betweenYears; i++) {
                c1.set(Calendar.YEAR, (c1.get(Calendar.YEAR) + 1));
                betweenDays += c1.getMaximum(Calendar.DAY_OF_YEAR);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return betweenDays;
    }
    public static Date getNextDay(Date date,int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, +num);//+1今天的时间加一天
        date = calendar.getTime();
        return date;
    }
    public static String getStrNextDay(Date date,int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, +num);//+1今天的时间加一天
        date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
    public static void main(String[] args) {
    	//System.out.println(new Date().getTime()+86400000);
    	//System.out.println(3600*24*1000);
    	
    	//System.out.println(getStrToLongTIme("2019-01-01"));
    	System.out.println(getHmsSDF());
    	//System.out.println(getNextDay(new Date(),-5));
    	// System.out.println(getStrNextDay(new Date(),-1));
    	// System.out.println(mondayToSunday(2));
    	 
		/*
		 * System.out.println(getNextDay(new Date(),5));
		 * 
		 * System.out.println(getBetweenDays("2019-03-01","2019-03-01")); String[]
		 * str=new String[2]; for (String string : str) { System.out.println(str[0]); }
		 */
     }
    public static String mondayToSunday(int week) {
    	//Map<String,String> map = new HashMap();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
 
         Calendar cal = Calendar.getInstance();  
         cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一  
         int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天  
         if(dayWeek==1){
             dayWeek = 8;
         }
       //  System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期  
 
         cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - dayWeek);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值  
         //Date mondayDate = cal.getTime();
        // String weekBegin = sdf.format(mondayDate);  
        // System.out.println("所在周星期一的日期：" + weekBegin);  
 
 
         cal.add(Calendar.DATE, week +cal.getFirstDayOfWeek()-3);
         Date sundayDate = cal.getTime();
         String weekEnd = sdf.format(sundayDate);  
        // System.out.println("所在周星期日的日期：" + weekEnd);
 
        // map.put("mondayDate", weekBegin);
        // map.put("sundayDate", weekEnd);
        return weekEnd;



		
	}
    /**  
     * @Description: String类型毫秒数转换成日期        
     * [@param](http://my.oschina.net/param) lo 毫秒数 
     * @return String yyyy-MM-dd HH:mm:ss    
     */    
     public static String stringToDate(String lo){
       long time = Long.parseLong(lo);
       Date date = new Date(time);
       SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       return sd.format(date);
     }

}