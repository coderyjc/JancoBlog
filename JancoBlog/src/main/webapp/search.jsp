<%--
  Created by Jancoyan.
  User: Jancoyan
  Date: 2021/4/11
  Time: 9:30
 --%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" +
            request.getServerName() + ":" + request.getServerPort() +
            request.getContextPath() + "/";
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Result</title>
        <link rel="stylesheet" type="text/css" href="./static/css/articlelist.css"/>
        <script src="./static/js/jquery-1.12.js"></script>
        <base href="<%=basePath%>"/>
    </head>
    <style>
        .article-search-list ul{
            width: 70%;
            margin: 0 auto;
        }
        .article-search-list ul>li{
            width: 100%;
        }
        .article-search-list{
            width: 100%;
            margin: 60px auto;
        }

    </style>
    <script>
        <%
        String keyword = request.getParameter("keyword");
        %>

        var keyword = '<%=keyword%>';
        var currentPage = 1;
        var totalPage = 999;
        // 页面加载之后立即执行
        $(function (){
            // 获取第一页内容
            get_articles(currentPage);
        });

    </script>
    <body>
        <!-- 导航条 -->
        <div class="nav">
            <!-- 回主页的"图标" -->
            <a href="#" class="back-to-index">JancoBlog</a>
        </div>
        <!-- 搜索结果的主体 -->
        <div class="main clearfix">
            <!-- 找到结果的条数 -->
            <h2 class="search-title"></h2>
            <!-- 找到的结果 -->
            <div class="article-search-list">
                <ul></ul>
            </div>
        </div>
    <script>

        // 页面加载完毕之后执行，完成一些初始化工作
        function get_articles(pn) {
            $.ajax({
                url: "search",
                type: "GET",
                data: {
                    "pn": pn,
                    "keyword" :keyword
                },
                success: function (result){
                    // 拿到结果建立文章列表
                    build_article_list(result);
                }
            });
        }

        // 传入result，在不清空页面的情况下进行文章的追加
        function build_article_list(result) {
            var articleList = result.extend.pageInfo.list;

            // 显示 ”找到多少条“ 此类文字
            $(".search-title").text("找到关于\"" + keyword + "\"的结果共 " +
                result.extend.pageInfo.total + " 条");

            totalPage = result.extend.pageInfo.pages;
            $.each(articleList, function (index, item) {

                var articleBoxLi = $("<li></li>").addClass("article-box");
                var articlePath = "./static/p/" + item.articleId + ".html";
                // box内部的a标签
                var aTitle = $("<a></a>").attr("href", articlePath).append(item.articleTitle);
                // 绑定增加浏览量的函数
                aTitle.click(function () {
                    add_article_view(item.articleId);
                });
                //分割线
                var segmentLine = $('<div class="segment-line"></div>');
                // 摘要
                var articleAbstract = $("<div></div>").addClass("article-abstract")
                    .append(item.articleAbstract);
                // 发布时间和浏览量
                var articleInfo = $("<div></div>")
                    .addClass("article-info")
                    .append($("<div></div>").addClass("view-count").append("浏览量："+item.articleViewTime))
                    .append($("<div></div>").addClass("post-time").append("发布日期："+dateFormat(item.articlePostDate)));
                // 向盒子中添加元素
                articleBoxLi.append(aTitle)
                    .append(segmentLine)
                    .append(articleAbstract)
                    .append(articleInfo)
                    .appendTo(".article-search-list ul");
            })
        }


        // 点击文章的时候增加文章阅读量
        function add_article_view(articleId) {
            $.ajax({
                url: "article/view",
                type: "POST",
                data: {
                    "id": articleId,
                }
            });
        }

        /**
         * 将时间戳转化为标准时间
         * @param date 时间戳
         * @returns {string} 标准时间字符串
         */
        function dateFormat(date) {
            var s = new Date(date)
            var y = s.getFullYear()
            var m = (s.getMonth() + 1) < 10 ? '0' + (s.getMonth() + 1) : (s.getMonth() + 1)
            var dd = s.getDate() < 10 ? '0' + s.getDate() : s.getDate()
            var hh = s.getHours() < 10 ? '0' + s.getHours() : s.getHours()
            var mm = s.getMinutes() < 10 ? '0' + s.getMinutes() : s.getMinutes()
            var ss = s.getSeconds() < 10 ? '0' + s.getSeconds() : s.getSeconds()
            var enddate = y + '-' + m + '-' + dd + ' ' + hh + ':' + mm + ":" + ss
            return enddate
        }

        // 监听页面到底部的事件，拉取更多的文章
        $(window).scroll(function(){
            var scrollTop = $(this).scrollTop();
            var scrollHeight = $(document).height();
            var windowHeight = $(this).height();
            if(scrollTop + windowHeight == scrollHeight){
                currentPage += 1;
                get_articles(currentPage);
            }
        });
    </script>
    </body>
</html>
