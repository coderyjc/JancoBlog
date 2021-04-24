package com.Jancoyan.utils;


import java.io.*;

/**
 * 这个类是一个工具类
 * 专门用来做文件的写入和读取
 * @author Jancoyan
 */
public class FileIo {


    /**
     * 如果文件夹不存在，就创建文件夹
     * @param path 文件夹路径
     */
    public static void createDirectoryIfNotExists(String path){
        File file = new File(path);
        if(!file.isDirectory()){
            // 创建文件夹
            file.mkdir();
        }
    }

    /**
     * 写入文件
     * @param path 文件路径
     * @param content 文件内容
     */
    public static void writeFile(String path, String content) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path, true);
            byte[] bytes = content.getBytes();
            fos.write(bytes);
            fos.flush();
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

    /**
     * 读文件
     * @param path 文件全路径
     * @return 文件内容
     */
    public static String readMarkDownFile(String path) {
        FileReader reader = null;
        String content = "";
        try {
            reader = new FileReader(path);
            //准备一个char数组
            char[] chars = new char[40];
            // 开始读
            int readCount = 0;
            while((readCount = reader.read(chars)) != -1) {
                content += new String(chars,0,readCount);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content;
    }
}
