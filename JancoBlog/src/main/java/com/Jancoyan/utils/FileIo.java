package com.Jancoyan.utils;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 这个类是一个工具类
 * 专门用来做文件的写入和读取
 * @author Jancoyan
 */
public class FileIo {

    /**
     * 写文件
     * @param path 要写入的文件的路径
     * @param content 文件的内容
     * @return
     */
    public static boolean writeHTMLFile(String path, String content){
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path, true);
            byte[] bytes = content.getBytes();
            fos.write(bytes);
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    /**
     * 删除文件
     * @param path 文件路径
     */
    public static boolean deleteFile(String path){
        File file = new File(path);

        if(!file.exists()){
            // 文件不存在
            return false;
        }else{
            file.delete();
        }
        return true;
    }

}
