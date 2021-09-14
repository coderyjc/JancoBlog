/**
 * @Author: Yan Jingcun
 * @Date: 2021/7/1
 * @Description:
 * @Version: 1.0
 */

package com.jancoyan.utils;

public class ArticleUtils {

    /**
     * 把换行的\n转换为文本的\n
     * @param str 要替换的文本
     * @return 替换完毕的文本
     */
    public static String NextLineToText(String str){
        String content = str.replaceAll("\n", "\\\\n");
        return content;
    }

    public static String replaceSingleSlash(String str){
        String content = str.replaceAll("'", "\\\\'");
        return content;
    }


    /**
     * 删除所有的html标签
     * 并把所有的tab和换行符都换成空格
     * @param innerHTML 作者写入的md转成的html（带有格式）
     * @return 文章的摘要
     */
    public static String getArticleAbstract(String innerHTML){
        // 去掉所有的HTML标签
        String rst = innerHTML.replaceAll("<.*?>", "");
        // 转换所有的空格
        rst = rst.replaceAll("\\s", " ");
        // 过长截取
        if(rst.length() > 150){
            rst = rst.substring(0, 150) + "......";
        }
        return rst;
    }

}
