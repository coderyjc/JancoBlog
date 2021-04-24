package com.Jancoyan.test;

import com.Jancoyan.utils.FileIo;
import org.junit.Test;

public class FileIOTest {

    /**
     * 测试读取文本工具类
     */
    @Test
    public void readFileTest(){
        String path = "R:\\0.txt";
        String content = FileIo.readMarkDownFile(path);
        System.out.println(content);
    }

}
