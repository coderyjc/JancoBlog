package com.Jancoyan.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * @author Jancoyan
 */
public class TimeUtils {

    /**
     * 把日期字符串转化为 yyyy-MM-dd 日期类型
     * @param dateStr 日期字符串
     * @return 日期类型
     */
    public static Date castDateStringToDateTypeYMD(String dateStr){
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 把日期字符串转化为日期 yyyy-MM-dd HH:mm:ss 形式
     * @param dateStr 字符串类型
     * @return date对象
     */
    public static Date caseDateStringToDateTypeYMDHMS(String dateStr){
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 获取当前系统时间，格式为 yyyy-MM-dd HH:mm:ss
     * @return 时间字符串
     */
    public static String getCurrentTimeString(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return df.format(new Date());
    }

    /**
     * 获取当前系统时间戳的字符串形式
     * @return
     */
    public static String getCurrentTimestamp(){
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     * 将java Date 将对象转化为日期字符串形式
     * @param date
     * @return
     */
    public static String castDateTypeToDateString(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }


}
