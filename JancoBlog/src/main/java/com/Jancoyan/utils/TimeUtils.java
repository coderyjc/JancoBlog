package com.Jancoyan.utils;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * @author Jancoyan
 */
public class TimeUtils {

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


}
