package com.Jancoyan.utils;

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


    /**
     * 创建文章
     * @param title 文章名
     * @param userNickname 作者昵称
     * @param innerHTML innerHTML
     * @return HTML content
     */
    public static String createArticle(String title, String userNickname, String innerHTML){
        String content = "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<meta charset=\"utf-8\">" +
                "<title>" + title + "</title>" +
                "    <link rel=\"stylesheet\" href=\"../js/editor.md/css/editormd.min.css\">" +
                "<link rel=\"stylesheet\" href=\"../css/article.css\">" +
                "</head>" +
                "<body><!-- 导航条 -->" +
                "<div class=\"nav\">" +
                "    <!-- 回主页的\"图标\" -->" +
                "    <a href=\"./index.jsp\" class=\"back-to-index\">JancoBlog</a>" +
                "</div>" +
                "" +
                "<!-- 所属分类 -->" +
                "<!-- 这个分类标签是提前写死在网页里的，把相关的js写在另一个文件中-->" +
                "<div class=\"article-sort\">" +
                "首页 > JavaWeb > Maven" +
                "</div>" +
                "<!-- 标题栏 -->" +
                "<h1 class=\"article-title\">" + title + "</h1>" +
                "<!-- 作者栏 -->" +
                "<div class=\"article-info\">" +
                "<div class=\"article-author\">作者：" + userNickname + "</div>" +
                "<div class=\"article-submit-time\">发布时间：" + TimeUtils.getCurrentTimeString() + "</div>" +
                "<div class=\"article-view-time\">浏览量：21</div>" +
                "</div><div class=\"article-content\">" + innerHTML + "</div>" +
                "<!-- 页脚 -->" +
                "<footer>" +
                "<ul>" +
                "    <li><a href=\"#\">关于</a></li>" +
                "</ul>" +
                "</footer>" +
                "</body>" +
                "</html>";
        return content;
    }

}
