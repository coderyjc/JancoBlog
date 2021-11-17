/**
 * @Author: Yan Jingcun
 * @Date: 2021/10/16
 * @Description:
 * @Version: 1.0
 */

package com.jancoyan.jancoblog.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtils {


    /**
     * 删除指定图片
     * @param date 日期
     * @param imageName 图片的名称
     */
    public static void deleteImageIfExists(Date date, String imageName)  {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        String format = simpleDateFormat.format(date);
        String path = null;
        try {
            path = new File(".").getCanonicalPath() + "\\target\\classes\\static\\p\\" +
                    format + "\\" + imageName;
            File file = new File(path);
            if(file.exists()){
                file.delete();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
