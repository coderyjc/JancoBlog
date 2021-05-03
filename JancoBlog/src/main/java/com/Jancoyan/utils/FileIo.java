package com.Jancoyan.utils;


import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 这个类是一个工具类
 * 专门用来做文件的写入和读取
 * @author Jancoyan
 */
public class FileIo {

    /**
     * 删除指定路径html下面的图像文件
     * @param path 路径
     */
    public static boolean deleteImagesInHtmlFile(String path){
        // 内容
        List<String> tmp = new ArrayList<>();
        String content = readFile(path);
        String regex = "<img (.*?)>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        String prefix = path.substring(0, path.lastIndexOf("static"));
        while (matcher.find()){
            String temp = matcher.group(1).toString().replace('/', '\\');
            temp =
                    prefix + temp.substring(temp.indexOf("static"),
                    temp.lastIndexOf("\" alt"));
            temp = temp.replaceAll("[\\\\]", "\\\\\\\\");
            tmp.add(temp);
        }
        for (String s : tmp){
            deleteFileIfExists(s);
        }
        return true;
    }



    /**
     * 删除指定路径的文件
     * @param path 文件的全路径
     */
    public static void deleteFileIfExists(String path){
        File file = new File(path);
        if(file.exists()){
            file.delete();
        }
    }

    /**
     * 上传保存文件
     * @param multipartFile 多文件对象
     * @param directory 文件夹全程
     * @param fileName 文件名
     */
    public static void uploadPicture(MultipartFile multipartFile,
                                     String directory,
                                     String fileName){
        // 文件夹不存在就创建
        createDirectoryIfNotExists(directory);

        FileOutputStream fileOutputStream = null;
        String fullPath = directory + "/" + fileName;

        try {
            fileOutputStream = new FileOutputStream(fullPath);
            // 文件写入
            fileOutputStream.write(multipartFile.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 如果文件夹不存在，就创建文件夹
     * @param path 文件夹路径
     */
    public static void createDirectoryIfNotExists(String path){
        File file = new File(path);
        if(!file.isDirectory()){
            // 递归创建文件夹
            file.mkdirs();
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
    public static String readFile(String path) {
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
