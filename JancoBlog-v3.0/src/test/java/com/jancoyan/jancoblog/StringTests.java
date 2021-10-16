/**
 * @Author: Yan Jingcun
 * @Date: 2021/10/16
 * @Description:
 * @Version: 1.0
 */

package com.jancoyan.jancoblog;

import com.jancoyan.jancoblog.utils.TimeUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTests extends JancoBlogApplicationTests{

    @Test
    public void getMonthTests(){
        String now =
                TimeUtils.getCurrentTimeString().substring(0, 7);
        System.out.println(now);
    }

    @Test
    public void getFileName(){
        String html =
                "<p><img style=\"max-width:70%\" src=\"http://localhost:8080/p/2021-10/48a4e027c9c14e3db05f75a38889004e.jpg\" alt=\"3.jpg\"></p>\n" +
                "<p><img style=\"max-width:70%\" src=\"http://localhost:8080/p/2021-10/8fdd9fabd6bb46758ef563bbf72c1566.jpg\" alt=\"4.jpg\"></p>\n" +
                "<p><img style=\"max-width:70%\" " +
                "src=\"http://localhost:8080/p/2021-10/5f0759d32b9043579d2a31af9566d3ac.jpg\" alt=\"1.jpg\"></p>\n";

        String regex = "src=\".*?\"";

        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(html);
        List<String> list = new ArrayList<>(16);
        while (matcher.find()){
            // 当前匹配到的字符串
            String fileName = matcher.group();
            fileName = fileName.substring(fileName.lastIndexOf('/') + 1, fileName.length() - 1);
            list.add(fileName);
        }
    }


}
