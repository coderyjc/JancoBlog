/**
 * @Author: Yan Jingcun
 * @Date: 2021/7/1
 * @Description:
 * @Version: 1.0
 */

package com.jancoyan.jancoblog.utils;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jancoyan.jancoblog.pojo.Article;

public class ArticleUtils {


    /**
     * 获取管理文章时候的wrapper，只有从 v_article_manage_show 中查询的时候才能用这个util
     * @param condition 条件
     * @return wrapper
     */
    public static QueryWrapper<Article> generateManageArticleWrapperByCondition(QueryWrapper<Article> wrapper,
                                                                                  String condition){

        String[] split = condition.split("--");
        for (String item : split) {
            String[] split2 = item.split("=");
            if(split2.length < 2){
                continue;
            }

            if("article_author_name".equals(split2[0])){
                wrapper.like("user_name", split2[1]);
            }else if("article_title".equals(split2[0])){
                wrapper.like("article_title", split2[1]);
            }else if("type".equals(split2[0])){
                wrapper.eq("article_type", split2[1]);
            }else if("start".equals(split2[0])){
                wrapper.gt("article_post_time", split2[1]);
            }else if("end".equals(split2[0])){
                wrapper.lt("article_post_time", split2[1]);
            }else if("rank_view".equals(split2[0])){
                if ("1".equals(split2[1])) {
                    wrapper.orderByAsc("article_view_count");
                } else {
                    wrapper.orderByDesc("article_view_count");
                }
            }else if("rank_like".equals(split2[0])){
                if ("1".equals(split2[1])) {
                    wrapper.orderByAsc("article_like_count");
                } else {
                    wrapper.orderByDesc("article_like_count");
                }
            }else if("rank_comment".equals(split2[0])){
                if ("1".equals(split2[1])) {
                    wrapper.orderByAsc("article_comment_count");
                } else {
                    wrapper.orderByDesc("article_comment_count");
                }
            }
        }

        return wrapper;
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

}
