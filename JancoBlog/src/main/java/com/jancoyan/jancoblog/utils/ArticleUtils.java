/**
 * @Author: Yan Jingcun
 * @Date: 2021/7/1
 * @Description:
 * @Version: 1.0
 */

package com.jancoyan.jancoblog.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jancoyan.jancoblog.model.domain.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArticleUtils {

    public static QueryWrapper<Article> generateManageArticleWrapperByCondition(
            QueryWrapper<Article> wrapper, String userName, String articleTitle,
            String articleType, String start, String end, String articleViewCount,
            String articleLikeCount, String articleCommentCount){

        // 查询请求
        if(ArticleUtils.isConditionValidate(userName)){
            wrapper.like("user_name", userName);
        }
        if(ArticleUtils.isConditionValidate(articleTitle)){
            wrapper.like("article_title", articleTitle);
        }
        if(ArticleUtils.isConditionValidate(articleType)){
            wrapper.eq("article_type", articleType);
        }
        if(ArticleUtils.isConditionValidate(start)){
            wrapper.gt("article_post_time", start);
        }
        if(ArticleUtils.isConditionValidate(end)){
            wrapper.lt("article_post_time", end);
        }
        if(ArticleUtils.isConditionValidate(articleViewCount)){
            if ("1".equals(articleViewCount)) {
                wrapper.orderByAsc("article_view_count");
            } else {
                wrapper.orderByDesc("article_view_count");
            }
        }
        if(ArticleUtils.isConditionValidate(articleLikeCount)){
            if ("1".equals(articleLikeCount)) {
                wrapper.orderByAsc("article_like_count");
            } else {
                wrapper.orderByDesc("article_like_count");
            }
        }
        if(ArticleUtils.isConditionValidate(articleCommentCount)){
            if ("1".equals(articleCommentCount)) {
                wrapper.orderByAsc("article_comment_count");
            } else {
                wrapper.orderByDesc("article_comment_count");
            }
        }

        return wrapper;
    }

    /**
     * 判断条件是否有效
     * @param attr
     * @return
     */
    public static boolean isConditionValidate(String attr){
        if(null == attr || "".equals(attr) || "null".equals(attr)){
            return false;
        }
        return true;
    }

    /**
     * 把换行的\n转换为文本的\n
     * @param str 要替换的文本
     * @return 替换完毕的文本
     */
    public static String nextLineToText(String str){
        return str.replaceAll("\n", "\\\\n");
    }

    /**
     * 把单引号替换为 \' 进行转义
     * @param str 文章的内容
     * @return 转换好的文章
     */
    public static String replaceSingleSlash(String str){
        return str.replaceAll("'", "\\\\'");
    }

    /**
     * 获取文章的默认摘要
     * @param innerHTML 作者写入的md转成的html（带有格式）
     * @return 文章的摘要
     */
    public static String getArticleDefaultSummary(String innerHTML){
        // 去掉所有的HTML标签
        String rst = innerHTML.replaceAll("<.*?>", "");
        // 转换所有的空格
        rst = rst.replaceAll("\\s", " ");
        // 过长截取
        if(rst.length() >= 100){
            rst = rst.substring(0, 100) + "......";
        }
        return rst;
    }

    /**
     * 获取文章id
     * @param userId 用户id
     * @return 文章id
     */
    public static String getArticleId(Integer userId, long now){
        String str = String.valueOf(userId);
        str += String.valueOf(now);
        return str;
    }


    /**
     * 标准化博文中的图片，宽度调整为容器大小的70%
     * @param html
     * @return
     */
    public static String simplifyImages(String html){
        return html.replaceAll("<img src", "<img style=\"max-width:70%\" src");
    }


    /**
     * 获取文章中的图片名称
     * @param html html标签
     * @return
     */
    public static List<String> getPicturesInArticle(String html){
        String regex = "src=\".*?\"";

        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(html);
        List<String> fileNames = new ArrayList<>(16);
        while (matcher.find()){
            // 当前匹配到的字符串
            String fileName = matcher.group();
            fileName = fileName.substring(fileName.lastIndexOf('/') + 1, fileName.length() - 1);
            fileNames.add(fileName);
            System.out.println(fileName);
        }

        return fileNames;
    }

}
