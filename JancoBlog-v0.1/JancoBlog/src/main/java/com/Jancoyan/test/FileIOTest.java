package com.Jancoyan.test;

import com.Jancoyan.utils.FileIo;
import org.junit.Test;

public class FileIOTest {

    /**
     * 删除HTML中所用到的所有img文件
     */
    @Test
    public void testDeleteImagesFromHtml(){
        String path = "D:\\JavaWEB\\apache-tomcat-9.0.43\\webapps\\JancoBlog" + "\\static\\p\\" + "100001620256930347" + ".html";
        FileIo.deleteImagesInHtmlFile(path);
    }


    /**
     * 测试读取文本工具类
     */
    @Test
    public void readFileTest(){
        String path = "R:\\0.txt";
        String content = FileIo.readFile(path);
        System.out.println(content);
    }

}
