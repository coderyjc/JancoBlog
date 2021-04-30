package com.Jancoyan.test;


import com.Jancoyan.utils.TimeUtils;
import org.junit.Test;

import java.util.Date;

public class TimeUtilsTest {

    /**
     * 把时间字符串转化为Date类型
     */
    @Test
    public void testCastTimeStringToDateType(){
        System.out.println(TimeUtils.castDateStringToDateType("2021-11-23"));
    }


    /**
     * 测试获取当前时间 年-月-日 时:分:秒
     */
    @Test
    public void testCurrentTime(){
        String currentTime = TimeUtils.getCurrentTimeString();
        System.out.println(currentTime);
    }

    /**
     * 当前时间戳的字符串形式
     */
    @Test
    public void testCurrentTimeMillions(){
        String timeMillion = TimeUtils.getCurrentTimestamp();
        System.out.println(timeMillion);
    }

    @Test
    public void testConvert(){
        System.out.println(TimeUtils.castDateTypeToDateString(new Date()));
    }
}
