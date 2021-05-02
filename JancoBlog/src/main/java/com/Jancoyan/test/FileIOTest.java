package com.Jancoyan.test;

import com.Jancoyan.utils.FileIo;
import org.junit.Test;

public class FileIOTest {

    @Test
    public void testDeleteImagesFromHtml(){
        String path = "D:\\JavaWEB\\apache-tomcat-9.0.43\\webapps\\JancoBlog" + "\\static\\p\\" + "100001619965461564" + ".html";
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
